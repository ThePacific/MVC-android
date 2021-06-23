package com.pacific.guava.android.mvvm.dagger

import javax.inject.Scope
import kotlin.annotation.AnnotationRetention.RUNTIME

/**
 * dagger依赖注解app空间
 */
@Scope
@Retention(RUNTIME)
annotation class AppScope