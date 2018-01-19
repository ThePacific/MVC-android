package com.pacific.arch.data

interface Envelope<out T> {
    val isSuccess: Boolean
    fun code(): Int
    fun message(): String
    fun data(): T
}