package com.pacific.guava.jvm.inject

import javax.inject.Scope
import kotlin.annotation.AnnotationRetention.RUNTIME

/**
 * Scope for the entire app runtime.
 */
@Scope
@Retention(RUNTIME)
annotation class AppScope