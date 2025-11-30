package dev.lkdcode.log

import org.slf4j.Logger
import org.slf4j.LoggerFactory


inline fun <reified T> T.log(): Logger =
    LoggerFactory.getLogger(T::class.java)

inline fun <reified T> T.logInfo(message: String?, vararg any: Any?) =
    LoggerFactory.getLogger(T::class.java).info(message, any)

inline fun <reified T> T.logWarn(message: String?, vararg any: Any?) =
    LoggerFactory.getLogger(T::class.java).warn(message, any)

inline fun <reified T> T.logError(message: String?, vararg any: Any?) =
    LoggerFactory.getLogger(T::class.java).error(message, any)