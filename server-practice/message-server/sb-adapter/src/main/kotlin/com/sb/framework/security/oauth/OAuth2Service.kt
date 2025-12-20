package com.sb.framework.security.oauth

import com.sb.application.user.ports.input.command.OAuth2UserProvisioningUsecase
import kotlinx.coroutines.reactor.mono
import org.springframework.security.oauth2.client.userinfo.DefaultReactiveOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.ReactiveOAuth2UserService
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


@Service
class OAuth2Service(
    private val mappers: List<OAuth2UserInfoMapper>,
    private val oAuth2UserProvisioningUsecase: OAuth2UserProvisioningUsecase
) : ReactiveOAuth2UserService<OAuth2UserRequest, OAuth2User> {

    override fun loadUser(userRequest: OAuth2UserRequest): Mono<OAuth2User> =
        DefaultReactiveOAuth2UserService()
            .loadUser(userRequest)
            .flatMap { oauthUser ->
                val provider = userRequest.clientRegistration.registrationId
                val mapper = mappers.first { it.supports(provider) }

                val command = mapper.map(oauthUser.attributes)

                Mono
                    .defer {
                        mono {
                            oAuth2UserProvisioningUsecase.provision(command)
                        }
                    }
                    .thenReturn(oauthUser.defaultOAuth2User())
            }

    private fun OAuth2User.defaultOAuth2User() =
        DefaultOAuth2User(
            this.authorities,
            this.attributes,
            "sub"
        )
}