package com.pacific.app.core.dagger

import javax.inject.Scope
import kotlin.annotation.AnnotationRetention.RUNTIME

/**
 * Scope for the entire app runtime.
 */
@Scope
@Retention(RUNTIME)
annotation class AppScope