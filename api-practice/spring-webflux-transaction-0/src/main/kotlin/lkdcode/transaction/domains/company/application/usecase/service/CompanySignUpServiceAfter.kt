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
import lkdcode.transaction.domains.company.domain.model.SignUpModel
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Mono

@Service
class CompanySignUpServiceAfter(
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
    private val transactionalOperator: TransactionalOperator,
) : CompanySignUpUsecase {

    override fun signUp(command: SignUpCommand): Mono<Void> =
        validate(command)
            .map { it.toDomain() }
            .flatMap { model ->
                nextAvailableCompanyCode()
                    .flatMap { code -> saveAll(code, model) }
                    .then(identityAuthenticationCommandPort.remove(model.signUpKey))
                    .doOnSuccess { applicationEventPublisher.publishEvent(model) }
                    .then()
            }

    private fun validate(command: SignUpCommand): Mono<SignUpCommand> =
        passwordValidator
            .validatePasswordPattern(command.password)
            .then(passwordValidator.validatePasswordMatch(command.password, command.passwordConfirm))
            .then(identityAuthenticationValidator.requireVerified(command.signUpKey))
            .then(validateCompanyUsecase.validate(command.convertValidateCompanyModel()))
            .then(companyValidator.validateDuplicateBrn(command.brn))
            .thenReturn(command)

    private fun saveAll(code: CompanyCode, model: SignUpModel): Mono<Void> =
        transactionalOperator.transactional(
            companyCommandPort.save(code, model)
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
                        }
                }
        )

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