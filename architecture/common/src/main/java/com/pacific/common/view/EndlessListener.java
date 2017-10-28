package com.pacific.common.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public final class EndlessListener extends RecyclerView.OnScrollListener {

  // The minimum amount of items to have below your current scroll position before loading more
  private final int threshold;
  private final LinearLayoutManager lm;
  // The total number of items in the data set after the last load
  private int preTotal = 0;
  // True if we are still waiting for the last set of data to load
  private boolean loading = true;
  private int firstVisiblePosition, visibleCount, totalCount;
  private int page = 1;
  private OnLoadMore onLoadMore;
  private boolean isReachEnding;
  private boolean isLock = false;

  public EndlessListener(LinearLayoutManager lm) {
    this(lm, 1);
  }

  public EndlessListener(LinearLayoutManager lm, int threshold) {
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
        && (totalCount - visibleCount) <= (firstVisiblePosition + threshold)
        && onLoadMore != null) {
      // End has been reached
      // Do something
      page++;
      onLoadMore.loadMore(page);
      loading = true;
    }
  }

  public void setOnLoadMore(OnLoadMore onLoadMore) {
    this.onLoadMore = onLoadMore;
  }

  public void reset() {
    preTotal = 0;
    page = 1;
    isReachEnding = false;
    loading = false;
    isLock = false;
  }

  public void reachEnding() {
    this.isReachEnding = true;
  }

  public void setLock(boolean lock) {
    isLock = lock;
  }

  public interface OnLoadMore {

    void loadMore(int page);
  }
}
