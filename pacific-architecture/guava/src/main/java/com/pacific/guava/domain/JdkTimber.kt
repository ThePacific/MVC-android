package com.pacific.guava.domain

interface JdkTimber {

    fun d(message: String, vararg args: String)

    companion object {

        @JvmField
        val SYSTEM: JdkTimber = object : JdkTimber {

            override fun d(message: String, vararg args: String) {
                println(message + "->" + args.joinToString())
            }
        }
    }
}