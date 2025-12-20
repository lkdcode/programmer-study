package com.sb.framework.security.oauth

import com.sb.application.user.dto.OAuth2ProvisioningCommand

interface OAuth2UserInfoMapper {
    fun supports(provider: String): Boolean
    fun map(attributes: Map<String, Any>): OAuth2ProvisioningCommand
}