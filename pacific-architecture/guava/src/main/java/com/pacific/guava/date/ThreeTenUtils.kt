package com.pacific.guava.date

import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

val FORMATTER_DD_MM_YYYY_HH_MM_SS: DateTimeFormatter by lazy {
    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
}

val FORMATTER_DD_MM_YYYY_HH_MM: DateTimeFormatter by lazy {
    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
}

val FORMATTER_DD_MM_YYYY: DateTimeFormatter by lazy {
    DateTimeFormatter.ofPattern("dd/MM/yyyy")
}

val jdk8Zone: ZoneId by lazy {
    ZoneId.systemDefault()
}

fun Long.yyyy_mm_dd_hh_mm_ss(): String {
    return Instant.ofEpochMilli(this)
        .atZone(jdk8Zone)
        .toLocalDateTime()
        .format(FORMATTER_DD_MM_YYYY_HH_MM_SS)
}

fun Long.yyyy_mm_dd_hh_mm(): String {
    return Instant.ofEpochMilli(this)
        .atZone(jdk8Zone)
        .toLocalDateTime()
        .format(FORMATTER_DD_MM_YYYY_HH_MM)
}

fun Long.dd_mm_yyyy(): String {
    return Instant.ofEpochMilli(this)
        .atZone(jdk8Zone)
        .toLocalDateTime()
        .format(FORMATTER_DD_MM_YYYY)
}


fun Long.toZonedDateTime(): ZonedDateTime {
    return Instant.ofEpochMilli(this)
        .atZone(jdk8Zone)
}

fun Long.toLocalDateTime(): LocalDateTime {
    return Instant.ofEpochMilli(this)
        .atZone(jdk8Zone)
        .toLocalDateTime()
}

fun Long.toLocalDate(): LocalDate {
    return this.toLocalDateTime().toLocalDate()
}

fun Long.toInstant(): Instant {
    return Instant.ofEpochMilli(this)
}

fun LocalDate.toJdk7Date(): Date {
    return try {
        java.sql.Date.valueOf(this.toString())
    } catch (e: Exception) {
        Date(this.atStartOfDay(jdk8Zone).toInstant().toEpochMilli())
    }
}

fun LocalDateTime.toJdk7Date(): Date {
    return try {
        java.sql.Timestamp.valueOf(this.toString())
    } catch (e: Exception) {
        Date(this.atZone(jdk8Zone).toInstant().toEpochMilli())
    }
}

fun Date.toZonedDateTime(): ZonedDateTime {
    return Instant.ofEpochMilli(this.time).atZone(jdk8Zone)
}

fun Date.toJdk8Instant(): Instant {
    return Instant.ofEpochMilli(this.time)
}

fun toZonedDateTime1(yyyy_mm_dd_hh_mm_ss: String): ZonedDateTime {
    return LocalDateTime.parse(yyyy_mm_dd_hh_mm_ss, FORMATTER_DD_MM_YYYY_HH_MM_SS)
        .atZone(jdk8Zone)
}

fun toZonedDateTime2(yyyy_mm_dd_hh_mm: String): ZonedDateTime {
    return LocalDateTime.parse(yyyy_mm_dd_hh_mm, FORMATTER_DD_MM_YYYY_HH_MM)
        .atZone(jdk8Zone)
}

fun toZonedDateTime3(dd_mm_yyyy: String): ZonedDateTime {
    return LocalDateTime.parse(dd_mm_yyyy, FORMATTER_DD_MM_YYYY)
        .atZone(jdk8Zone)
}

fun toInstant1(yyyy_mm_dd_hh_mm_ss: String): Instant {
    return toZonedDateTime1(yyyy_mm_dd_hh_mm_ss).toInstant()
}

fun toInstant2(yyyy_mm_dd_hh_mm: String): Instant {
    return toZonedDateTime2(yyyy_mm_dd_hh_mm).toInstant()
}

fun toInstant3(dd_mm_yyyy: String): Instant {
    return toZonedDateTime3(dd_mm_yyyy).toInstant()
}

fun toEpochMilli1(yyyy_mm_dd_hh_mm_ss: String): Long {
    return toInstant1(yyyy_mm_dd_hh_mm_ss).toEpochMilli()
}

fun toEpochMilli2(yyyy_mm_dd_hh_mm: String): Long {
    return toInstant2(yyyy_mm_dd_hh_mm).toEpochMilli()
}

fun toEpochMilli3(dd_mm_yyyy: String): Long {
    return toInstant3(dd_mm_yyyy).toEpochMilli()
}