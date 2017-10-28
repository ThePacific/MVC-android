package com.pacific.arch_demo;

import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import com.pacific.presentation.common.OkRouter;
import com.pacific.presentation.core.Activity;
import javax.inject.Inject;

public class SecondActivity extends Activity {

  SecondViewModel model;
  //  Rx2Timer timer;
  Button button;

  public static final String CLOSE = "com.barry.arch.close";

  @Inject
  App app;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_second);
    model = fetchViewModel();
    button = findViewById(R.id.btn_timer);
//    timer = Rx2Timer.builder()
//        .onEmit(it -> button.setText(String.valueOf(it)))
//        .onError(e -> button.setText("0"))
//        .onComplete(() -> button.setText("0"))
//        .build();
//    button.setOnClickListener(it -> timer.start());

    ProgressBar determinate = findViewById(R.id.determinate);
    determinate.setProgress(20);

    ProgressBar buffer = findViewById(R.id.buffer);
    buffer.setProgress(20);
    buffer.setSecondaryProgress(50);

    ProgressBar progressBar = findViewById(R.id.progressBar);
    progressBar.setIndeterminate(false);
  }

  @Override
  public boolean applyFinishAction() {
    return false;
  }

  @Override
  protected Class<? extends ViewModel> modelClass() {
    return SecondViewModel.class;
  }

  private void displayBottomSheet() {
    OkRouter.showDialogFragment(getSupportFragmentManager(), BottomSheet.newInstance());
  }
}
