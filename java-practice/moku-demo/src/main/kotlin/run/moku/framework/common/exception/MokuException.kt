package run.moku.framework.common.exception

class MokuException(
    override val message: String,
    val errorCode: Int
) : RuntimeException(message) {

}