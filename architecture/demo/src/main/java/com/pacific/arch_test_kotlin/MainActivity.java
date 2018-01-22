package com.pacific.arch_test_kotlin;

import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.pacific.presentation.Activity;
import com.pacific.presentation.Router;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import javax.inject.Inject;

public class MainActivity extends Activity {

    @Inject
    App app;

    MainViewModel model;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model = fetchViewModel();
        progressBar = findViewById(R.id.progressbar);
        findViewById(R.id.button).setOnClickListener(v -> Router.start(this, SecondActivity.class));
        findViewById(R.id.button).setOnClickListener(i -> model.load(0)
                .to(AutoDispose.with(AndroidLifecycleScopeProvider.from(this)).forObservable())
                .subscribe(it -> {
                    switch (it.status) {
                        case IN_PROGRESS:
                            progressBar.setVisibility(View.VISIBLE);
                            break;
                        case IRRELEVANT:
                            progressBar.setVisibility(View.GONE);
                            break;
                        case ERROR:
                            progressBar.setVisibility(View.GONE);
                            break;
                        case SUCCESS:
                            progressBar.setVisibility(View.GONE);
                            Router.showDialogFragment(getSupportFragmentManager(), BottomSheet.newInstance());
                            break;
                        default:
                            throw new UnsupportedOperationException();
                    }
                }));
    }

    @Override
    protected Class<? extends ViewModel> modelClass() {
        return MainViewModel.class;
    }
}