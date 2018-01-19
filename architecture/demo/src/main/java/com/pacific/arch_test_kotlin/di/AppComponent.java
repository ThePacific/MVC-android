package com.pacific.arch_test_kotlin.di;

import com.pacific.arch_test_kotlin.App;
import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AppModule.class,
        ActivityBuildersModule.class,
        AndroidSupportInjectionModule.class
})
interface AppComponent extends AndroidInjector<App> {
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<App> {
    }
}
