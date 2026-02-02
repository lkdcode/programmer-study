package lkdcode.transaction.domains.company.application.model

import lkdcode.transaction.domains.company.domain.model.Password
import lkdcode.transaction.domains.company.domain.model.SignUpModel
import lkdcode.transaction.domains.company.domain.model.ValidateCompanyModel

data class SignUpCommand(
    val email: String,
    val password: String,
    val passwordConfirm: String,
    val companyName: String,
    val brn: String,
    val signUpKey: String,
    val termsList: List<String>,
) {
    fun toDomain(): SignUpModel = SignUpModel(
        email = email,
        password = Password.of(password),
        companyName = companyName,
        brn = brn,
        signUpKey = signUpKey,
        termsList = termsList,
    )

    fun convertValidateCompanyModel(): ValidateCompanyModel =
        ValidateCompanyModel(brn = brn, companyName = companyName)
}