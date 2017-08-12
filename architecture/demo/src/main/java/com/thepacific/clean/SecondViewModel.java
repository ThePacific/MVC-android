package com.thepacific.clean;

import android.app.Application;

import com.thepacific.presentation.core.ViewModel;
import javax.inject.Inject;

public class SecondViewModel extends ViewModel {

    @Inject
    public SecondViewModel(Application application) {
        super(application);
    }
}
