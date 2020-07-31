package com.pacific.data.test

import androidx.annotation.VisibleForTesting
import dagger.Module

@VisibleForTesting
@Module(includes = [TestBinder::class])
class TestModule {
}