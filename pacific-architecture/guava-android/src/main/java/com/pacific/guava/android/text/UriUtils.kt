package com.pacific.guava.android.text

import android.net.Uri
import okio.ByteString.Companion.encodeUtf8

fun Uri.md5Key():String = this.toString().encodeUtf8().md5().hex()