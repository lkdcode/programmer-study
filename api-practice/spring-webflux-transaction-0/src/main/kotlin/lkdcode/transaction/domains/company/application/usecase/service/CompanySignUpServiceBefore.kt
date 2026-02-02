package lkdcode.transaction.domains.company.application.usecase.service

import lkdcode.transaction.domains.company.application.model.SignUpCommand
import lkdcode.transaction.domains.company.application.port.out.CompanyCommandPort
import lkdcode.transaction.domains.company.application.port.out.CompanyQueryPort
import lkdcode.transaction.domains.company.application.port.out.IdentityAuthenticationCommandPort
import lkdcode.transaction.domains.company.application.usecase.*
import lkdcode.transaction.domains.company.application.validator.CompanyValidator
import lkdcode.transaction.domains.company.application.validator.IdentityAuthenticationValidator
import lkdcode.transaction.domains.company.application.validator.PasswordValidator
import lkdcode.transaction.domains.company.domain.model.CompanyCode
import org.springframework.context.ApplicationEventPublisher
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

class CompanySignUpServiceBefore(
    private val passwordValidator: PasswordValidator,
    private val identityAuthenticationValidator: IdentityAuthenticationValidator,
    private val validateCompanyUsecase: ValidateCompanyUsecase,
    private val companyValidator: CompanyValidator,
    private val companyQueryPort: CompanyQueryPort,
    private val companyCommandPort: CompanyCommandPort,
    private val createPlanUsecase: CreatePlanUsecase,
    private val createUserUsecase: CreateUserUsecase,
    private val commandTermsUsecase: CommandTermsUsecase,
    private val identityAuthenticationCommandPort: IdentityAuthenticationCommandPort,
    private val applicationEventPublisher: ApplicationEventPublisher,
) : CompanySignUpUsecase {

    @Transactional
    override fun signUp(command: SignUpCommand): Mono<Void> =
        passwordValidator
            .validatePasswordPattern(command.password)
            .then(passwordValidator.validatePasswordMatch(command.password, command.passwordConfirm))
            .then(identityAuthenticationValidator.requireVerified(command.signUpKey))
            .then(validateCompanyUsecase.validate(command.convertValidateCompanyModel()))
            .then(companyValidator.validateDuplicateBrn(command.brn))
            .then(Mono.fromCallable { command.toDomain() })
            .flatMap { model ->
                nextAvailableCompanyCode()
                    .flatMap { code -> companyCommandPort.save(code, model) }
                    .flatMap { companyId ->
                        createPlanUsecase
                            .saveWhenCompanySignUp(companyId.value)
                            .thenReturn(companyId)
                    }
                    .flatMap { companyId ->
                        createUserUsecase
                            .createUserWhenCompanySignup(companyId, model)
                            .flatMap { userId ->
                                commandTermsUsecase
                                    .saveTermsWhenCompanySignUp(userId, model.termsList)
                                    .thenReturn(userId)
                            }
                    }
                    .then(identityAuthenticationCommandPort.remove(model.signUpKey))
                    .doOnSuccess { applicationEventPublisher.publishEvent(model) }
                    .then()
            }

    private fun nextAvailableCompanyCode(): Mono<CompanyCode> =
        Mono.defer {
            val code = CompanyCode.init()
            companyQueryPort
                .existsByCode(code.value)
                .flatMap { exists ->
                    if (exists) nextAvailableCompanyCode()
                    else Mono.just(code)
                }
        }
}