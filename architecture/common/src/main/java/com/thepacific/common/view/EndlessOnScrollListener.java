package com.thepacific.common.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public final class EndlessOnScrollListener extends RecyclerView.OnScrollListener {

  // The minimum amount of items to have below your current scroll position before loading more
  private final int visibleThreshold;
  private final LinearLayoutManager layoutManager;
  // The total number of items in the data set after the last load
  private int previousTotal = 0;
  // True if we are still waiting for the last set of data to load
  private boolean loading = true;
  private int firstVisibleItem, visibleItemCount, totalItemCount;
  private int currentPageIndex = 1;
  private OnLoadMore onLoadMore;
  private boolean reachedEndItem;

  public EndlessOnScrollListener(LinearLayoutManager linearLayoutManager) {
    this(linearLayoutManager, 3);
  }

  public EndlessOnScrollListener(LinearLayoutManager linearLayoutManager, int visibleThreshold) {
    this.visibleThreshold = visibleThreshold;
    this.layoutManager = linearLayoutManager;
  }

  @Override
  public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    super.onScrolled(recyclerView, dx, dy);
    if (reachedEndItem) {
      return;
    }
    visibleItemCount = recyclerView.getChildCount();
    totalItemCount = layoutManager.getItemCount();
    firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
    if (loading) {
      if (totalItemCount > previousTotal) {
        loading = false;
        previousTotal = totalItemCount;
      }
    }
    if (!loading
        && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)
        && onLoadMore != null) {
      // End has been reached
      // Do something
      currentPageIndex++;
      onLoadMore.loadMore(currentPageIndex);
      loading = true;
    }
  }

  public void setOnLoadMore(OnLoadMore onLoadMore) {
    this.onLoadMore = onLoadMore;
  }

  public void reset() {
    previousTotal = 0;
    currentPageIndex = 1;
    reachedEndItem = false;
  }

  public void setReachedEndItem(boolean reachedEndItem) {
    this.reachedEndItem = reachedEndItem;
  }

  public interface OnLoadMore {

    void loadMore(int currentPageIndex);
  }
}
