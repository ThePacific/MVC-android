package com.pacific.guava.android.text

import okio.ByteString.Companion.encodeUtf8

fun String.md5Key(): String = this.encodeUtf8().md5().hex()