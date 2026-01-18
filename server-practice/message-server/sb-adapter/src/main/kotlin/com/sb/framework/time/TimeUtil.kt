package com.sb.framework.time

import java.time.Instant
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

val KST: ZoneId = ZoneId.of("Asia/Seoul")
val UTC: ZoneId = ZoneId.of("UTC")

val DATE_TIME_FORMAT_ISO: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
val NANO_DATE_TIME_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
val NORMAL_TIME_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
val TIME_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

val BASIC_ISO_DATE: DateTimeFormatter = DateTimeFormatter.BASIC_ISO_DATE

fun nowUTC(): Instant = Instant.now()
fun nowTS(): Long = nowUTC().toEpochMilli()
fun nowZoneDateTime() : ZonedDateTime = nowUTC().atZone(KST)

fun convertUTC(target: String): Instant = Instant.parse(target)
fun convertKstOffsetDateTime(target: String): OffsetDateTime = convertUTC(target).atZone(KST).toOffsetDateTime()
fun convertKstOffsetDateTime(): OffsetDateTime = Instant.now().atZone(KST).toOffsetDateTime()

fun nowDateKST(): LocalDate = convertKstOffsetDateTime().toLocalDate()

fun nowKSTTimeFormat(): String = Instant.now().atZone(KST).toLocalDateTime().format(NANO_DATE_TIME_FORMAT)

fun LocalDate.convertBasicIOSDate(): String = this.format(BASIC_ISO_DATE)

//fun ObjectId.toKstZonedDateTime(): ZonedDateTime = Instant.ofEpochSecond(this.timestamp.toLong()).atZone(KST)

//fun ObjectId.toKstLocalDateTime(): LocalDateTime = this.toKstZonedDateTime().toLocalDateTime()

//fun convertObjectIdString(instantString: String) = ObjectId(Date.from(convertUTC(instantString))).toString()