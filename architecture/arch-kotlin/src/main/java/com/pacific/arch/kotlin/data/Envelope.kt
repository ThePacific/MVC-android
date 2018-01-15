package com.pacific.arch.kotlin.data

interface Envelope<out T> {
    val isSuccess: Boolean
    fun code(): Int
    fun message(): String
    fun data(): T
}