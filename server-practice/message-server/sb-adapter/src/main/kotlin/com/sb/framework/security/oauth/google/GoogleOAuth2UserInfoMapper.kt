package com.sb.framework.security.oauth.google

import com.sb.application.user.dto.OAuth2ProvisioningCommand
import com.sb.framework.security.oauth.OAuth2UserInfoMapper
import org.springframework.stereotype.Component


@Component
class GoogleOAuth2UserInfoMapper(
) : OAuth2UserInfoMapper {

    override fun supports(provider: String) = provider == "google"

    override fun map(attributes: Map<String, Any>) =
        OAuth2ProvisioningCommand(
            provider = "google",
            providerUserId = attributes["sub"] as String,
            email = attributes["email"] as String,
            nickname = attributes["name"] as String,
            profileImage = attributes["picture"] as String?,
        )
}