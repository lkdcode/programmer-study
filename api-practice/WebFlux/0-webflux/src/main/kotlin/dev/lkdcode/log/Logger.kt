package dev.lkdcode.log

object Logger {
    @JvmStatic
    fun onNext(v: Any?) = println("onNext: $v")

    @JvmStatic
    fun onError(t: Throwable) = t.printStackTrace()

    @JvmStatic
    fun onComplete() = println("onComplete")
}