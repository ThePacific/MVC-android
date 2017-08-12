package com.thepacific.clean;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.thepacific.presentation.core.Activity;
import com.thepacific.presentation.core.ViewModel;
import javax.inject.Inject;

public class MainActivity extends Activity {

  @Inject
  App app;

  MainViewModel model;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    model = fetchViewModel();
    findViewById(R.id.btn_he).setOnClickListener(i -> model.load().subscribe());
  }

  @Override
  protected Class<? extends ViewModel> modelClass() {
    return MainViewModel.class;
  }

  private void displayBottomSheet() {
    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();
    String tag = BottomSheetDialog.class.getSimpleName();
    Fragment prev = fm.findFragmentByTag(tag);
    if (prev != null) {
      ft.remove(prev);
    }
    BottomSheetDialog newFragment = BottomSheetDialog.newInstance();
    newFragment.show(ft, tag);
  }
}
