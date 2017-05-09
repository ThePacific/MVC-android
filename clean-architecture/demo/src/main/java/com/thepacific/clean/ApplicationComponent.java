package com.thepacific.clean;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(
        modules = {
                ApplicationModule.class,
                ActivityBindingModule.class,
                AndroidSupportInjectionModule.class
        }
)
public interface ApplicationComponent extends AndroidInjector<MainApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<MainApplication> {
    }
}
