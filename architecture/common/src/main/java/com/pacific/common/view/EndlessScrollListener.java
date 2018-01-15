package com.pacific.common.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public final class EndlessScrollListener extends RecyclerView.OnScrollListener {

  // The minimum amount of items to have below your current scroll position before loading more
  private final int threshold;
  private final LinearLayoutManager lm;
  // The total number of items in the data set after the last load
  private int preTotal = 0;
  // True if we are still waiting for the last set of data to load
  private boolean loading = true;
  private int firstVisiblePosition, visibleCount, totalCount;
  private int page = 1;
  private Loader loader;
  private boolean isReachEnding;
  private boolean isLock = false;

  public EndlessScrollListener(LinearLayoutManager lm) {
    this(lm, 1);
  }

  public EndlessScrollListener(LinearLayoutManager lm, int threshold) {
    this.threshold = threshold;
    this.lm = lm;
  }

  @Override
  public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    if (isReachEnding || isLock) {
      return;
    }
    visibleCount = recyclerView.getChildCount();
    totalCount = lm.getItemCount();
    firstVisiblePosition = lm.findFirstVisibleItemPosition();
    if (loading) {
      if (totalCount > preTotal) {
        loading = false;
        preTotal = totalCount;
      }
    }
    if (!loading
        && loader != null
        && (totalCount - visibleCount) <= (firstVisiblePosition + threshold)) {
      // End has been reached
      // Do something
      page++;
      loader.onLoad(page);
      loading = true;
    }
  }

  public void setLoader(Loader loader) {
    this.loader = loader;
  }

  public void reset() {
    preTotal = 0;
    page = 1;
    isReachEnding = false;
    loading = false;
    isLock = false;
  }

  public boolean isReachEnding() {
    return isReachEnding;
  }

  public void setLock(boolean isLock) {
    this.isLock = isLock;
  }

  public interface Loader {

    void onLoad(int page);
  }
}
