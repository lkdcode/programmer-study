package run.moku.framework.log

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified T> T.logging(): Logger = LoggerFactory.getLogger(T::class.java)