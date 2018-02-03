package com.pacific.example.di

import com.pacific.adapter.RecyclerAdapter
import com.pacific.arch.presentation.OnActivity
import com.pacific.example.NewOkHttpLibraryGlideModule
import dagger.Component
import dagger.Module
import dagger.Provides

@OnActivity
@Component(modules = [(GliModule::class)])
interface GlideComponent {

    fun inject(newOkHttpLibraryGlideModule: NewOkHttpLibraryGlideModule)

    @Component.Builder
    interface Builder {
        fun glideModule(glideModule: AGlideModule): Builder
        fun build(): GlideComponent
    }
}

@Module
@OnActivity
class GliModule {
    @Provides
    fun provideRecyclerAdapter() = RecyclerAdapter()
}