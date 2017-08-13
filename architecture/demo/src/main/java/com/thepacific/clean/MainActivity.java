package com.thepacific.clean;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import com.thepacific.presentation.common.RouterUtil;
import com.thepacific.presentation.core.Activity;
import com.thepacific.presentation.core.ViewModel;
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
    findViewById(R.id.button).setOnClickListener(i -> model.load(0).subscribe(it -> {
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
          RouterUtil.showDialogFragment(getSupportFragmentManager(), BottomSheet.newInstance());
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
