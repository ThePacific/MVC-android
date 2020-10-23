package com.pacific.guava.jvm.domain

interface PlatformTimber {

    fun d(tag: String, message: String)

    fun d(e: Throwable)

    fun e(tag: String, message: String)

    fun e(e: Throwable)

    companion object {

        @JvmField
        val SYSTEM: PlatformTimber = object : PlatformTimber {

            override fun d(tag: String, message: String) {
                println("$tag->$message")
            }

            override fun d(e: Throwable) {
                e.printStackTrace()
            }

            override fun e(tag: String, message: String) {
                println("$tag->$message")
            }

            override fun e(e: Throwable) {
                e.printStackTrace()
            }
        }
    }
}