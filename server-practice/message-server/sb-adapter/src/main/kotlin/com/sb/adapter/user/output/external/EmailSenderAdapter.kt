package com.sb.adapter.user.output.external

import com.sb.application.user.ports.output.external.EmailSenderPort
import com.sb.domain.user.value.Email
import com.sb.domain.user.value.IdentityVerificationToken
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component
import kotlin.text.Charsets.UTF_8

@Component
class EmailSenderAdapter(
    private val mailSender: JavaMailSender,
    @Value("\${spring.mail.username:}") private val defaultFrom: String,
) : EmailSenderPort {
    private val log = LoggerFactory.getLogger(javaClass)

    private val verificationEmailTemplate: String by lazy {
        ClassPathResource("template/email-verification.html")
            .inputStream
            .bufferedReader(UTF_8)
            .use { it.readText() }
    }

    override suspend fun sendVerificationCode(email: Email, token: IdentityVerificationToken) {
        val subject = "[숨붓] 이메일 인증 코드"
        val html = verificationEmailTemplate
            .replace("{{CODE}}", token.value)

        val message = mailSender.createMimeMessage()
        MimeMessageHelper(message, false, UTF_8.name())
            .apply {
                if (defaultFrom.isNotBlank()) {
                    setFrom(defaultFrom)
                }
                setTo(email.value)
                setSubject(subject)
                setText(html, true)
            }

        mailSender.send(message)

        log.info("Sent email verification code. email={}", email.value)
    }
}