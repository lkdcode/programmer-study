package com.sb.domain.user.service

import com.sb.domain.user.aggregate.UserAggregate
import com.sb.domain.user.repository.UserQueryRepository
import com.sb.domain.user.repository.UserRepository
import com.sb.domain.user.value.Email
import com.sb.domain.user.value.EmailVerificationToken
import com.sb.domain.user.value.Nickname
import com.sb.domain.user.value.OAuthSubject
import com.sb.domain.user.value.Password

/**
 * (정책)
 * - 회원가입은 Google(OAuth) 또는 Email(이메일 인증 완료)로만 가능
 * - Email 가입의 경우 비밀번호 정책을 만족해야 함(Password VO에서 검증)
 */
class UserRegistrationService(
    private val userRepository: UserRepository,
    private val userQueryRepository: UserQueryRepository,
    private val emailVerificationService: EmailVerificationService,
) {
    fun registerWithEmail(
        email: Email,
        nickname: Nickname,
        password: Password,
        verificationToken: EmailVerificationToken,
    ): UserAggregate {
        require(!userQueryRepository.existsByEmail(email)) { "이미 가입된 이메일입니다." }
        require(emailVerificationService.isVerified(email, verificationToken)) { "이메일 인증이 필요합니다." }

        val aggregate = UserAggregate.registerWithEmail(email, nickname, password)
        return userRepository.save(aggregate)
    }

    fun registerWithGoogle(
        email: Email,
        nickname: Nickname,
        subject: OAuthSubject,
    ): UserAggregate {
        require(!userQueryRepository.existsByEmail(email)) { "이미 가입된 이메일입니다." }

        val aggregate = UserAggregate.registerWithGoogle(email, nickname, subject)
        return userRepository.save(aggregate)
    }
}


