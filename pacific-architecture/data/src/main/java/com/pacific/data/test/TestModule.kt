package com.pacific.data.test

import androidx.annotation.VisibleForTesting
import com.pacific.guava.io.mkdirs
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Named

@VisibleForTesting
@Module(includes = [TestBinder::class])
class TestModule {

    @Provides
    @Named("appExternalCacheDir")
    fun provideAppExternalCacheDir(): File {
        return mkdirs("cache")
    }
}