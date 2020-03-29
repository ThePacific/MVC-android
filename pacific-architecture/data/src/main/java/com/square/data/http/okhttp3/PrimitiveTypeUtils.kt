package com.square.data.http.okhttp3

fun Int?.nullSafe(): Int = this ?: 0

fun Long?.nullSafe(): Long = this ?: 0L

fun Short?.nullSafe(): Short = this ?: 0

fun Float?.nullSafe(): Float = this ?: 0.00f

fun Double?.nullSafe(): Double = this ?: 0.00

fun Boolean?.nullSafe(): Boolean = this ?: false

fun String?.nullSafe(): String = this ?: ""

fun String?.safeToInt(): Int = if (this.isNullOrEmpty()) 0 else this.toInt()

fun String?.safeToLong(): Long = if (this.isNullOrEmpty()) 0L else this.toLong()

fun String?.safeToShort(): Short = if (this.isNullOrEmpty()) 0 else this.toShort()

fun String?.safeToBoolean(): Boolean = if (this.isNullOrEmpty()) false else this.toBoolean()

fun String?.safeToFloat(): Float = if (this.isNullOrEmpty()) 0.00f else this.toFloat()

fun String?.safeToDouble(): Double = if (this.isNullOrEmpty()) 0.00 else this.toDouble()