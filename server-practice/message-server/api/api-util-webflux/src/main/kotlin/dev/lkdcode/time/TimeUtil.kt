package dev.lkdcode.time

import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


val KST: ZoneId = ZoneId.of("Asia/Seoul")
val UTC: ZoneId = ZoneId.of("UTC")

val TIME_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

val BASIC_ISO_DATE: DateTimeFormatter = DateTimeFormatter.BASIC_ISO_DATE

fun nowUTC(): Instant = Instant.now()
fun nowKST(): OffsetDateTime = Instant.now().atZone(KST).toOffsetDateTime()

fun nowTS(): Long = nowUTC().toEpochMilli()