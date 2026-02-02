package lkdcode.transaction.domains.company.domain.model

data class SignUpModel(
    val email: String,
    val password: Password,
    val companyName: String,
    val brn: String,
    val signUpKey: String,
    val termsList: List<String>,
) {
    fun convertValidateCompanyModel(): ValidateCompanyModel =
        ValidateCompanyModel(brn = brn, companyName = companyName)
}

data class ValidateCompanyModel(
    val brn: String,
    val companyName: String,
)