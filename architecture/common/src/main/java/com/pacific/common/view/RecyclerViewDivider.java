package com.pacific.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.pacific.guava.Preconditions;

public class RecyclerViewDivider extends RecyclerView.ItemDecoration {

  private final Rect mBounds = new Rect();
  private final boolean mVertical;
  private final Drawable mDivider;
  private final int mStrokeWidth;
  private final int mLeftMargin;
  private final int mRightMargin;
  private final int mTopMargin;
  private final int mBottomMargin;
  private final boolean mHideLastDivider;

  private final MarginFactory mLeftMarginFactory;
  private final MarginFactory mRightMarginFactory;
  private final MarginFactory mTopMarginFactory;
  private final MarginFactory mBottomMarginFactory;
  private final DrawableFactory mDrawableFactory;
  private final DisplayFilter mDisplayFilter;
  private int lastPosition = -1;

  private RecyclerViewDivider(Builder builder) {
    this.mDivider = builder.drawable;
    this.mStrokeWidth = builder.strokeWidth;
    this.mVertical = builder.vertical;
    this.mLeftMargin = builder.leftMargin;
    this.mRightMargin = builder.rightMargin;
    this.mTopMargin = builder.topMargin;
    this.mBottomMargin = builder.bottomMargin;
    this.mHideLastDivider = builder.hideLastDivider;
    this.mLeftMarginFactory = builder.leftMarginFactory;
    this.mRightMarginFactory = builder.rightMarginFactory;
    this.mTopMarginFactory = builder.topMarginFactory;
    this.mBottomMarginFactory = builder.bottomMarginFactory;
    this.mDrawableFactory = builder.drawableFactory;
    this.mDisplayFilter = builder.displayFilter;
  }

  @Override
  public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
    if (parent.getLayoutManager() == null || (mDivider == null && mDrawableFactory == null)) {
      return;
    }
    if (mVertical) {
      drawVertical(c, parent);
    } else {
      drawHorizontal(c, parent);
    }
  }

  private void drawVertical(Canvas canvas, RecyclerView parent) {
    canvas.save();
    int left;
    int right;

    final int childCount = mHideLastDivider ? parent.getChildCount() - 1 : parent.getChildCount();
    for (int i = 0; i < childCount; i++) {
      final View child = parent.getChildAt(i);
      final int position = getAdapterPosition(child);
      if (!isDisplayDivider(position)) {
        continue;
      }

      //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
      final int leftMargin = getLeftMargin(position);
      final int rightMargin = getRightMargin(position);

      if (parent.getClipToPadding()) {
        left = parent.getPaddingLeft();
        right = parent.getWidth() - parent.getPaddingRight() - rightMargin;
        canvas.clipRect(left, parent.getPaddingTop(), right,
            parent.getHeight() - parent.getPaddingBottom());
        left = left + leftMargin;
      } else {
        left = leftMargin;
        right = parent.getWidth() - rightMargin;
      }

      parent.getDecoratedBoundsWithMargins(child, mBounds);
      final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
      final int top = bottom - getIntrinsicHeight(position);
      Drawable drawable = getDivider(position);
      drawable.setBounds(left, top, right, bottom);
      drawable.draw(canvas);
    }
    canvas.restore();
  }

  private void drawHorizontal(Canvas canvas, RecyclerView parent) {
    canvas.save();
    int top;
    int bottom;

    final int childCount = mHideLastDivider ? parent.getChildCount() - 1 : parent.getChildCount();
    for (int i = 0; i < childCount; i++) {
      final View child = parent.getChildAt(i);
      final int position = getAdapterPosition(child);
      if (!isDisplayDivider(position)) {
        continue;
      }

      //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
      final int topMargin = getTopMargin(position);
      final int bottomMargin = getBottomMargin(position);

      if (parent.getClipToPadding()) {
        top = parent.getPaddingTop();
        bottom = parent.getHeight() - parent.getPaddingBottom() - bottomMargin;
        canvas.clipRect(parent.getPaddingLeft(), top, parent.getWidth() - parent.getPaddingRight(),
            bottom);
        top = top + topMargin;
      } else {
        top = topMargin;
        bottom = parent.getHeight() - bottomMargin;
      }

      parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
      final int right = mBounds.right + Math.round(child.getTranslationX());
      final int left = right - getIntrinsicWidth(position);
      Drawable drawable = getDivider(position);
      drawable.setBounds(left, top, right, bottom);
      drawable.draw(canvas);
    }
    canvas.restore();
  }

  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
      RecyclerView.State state) {
    lastPosition = parent.getAdapter().getItemCount() - 1;
    final int position = getAdapterPosition(view);
    if (mDivider == null && null == getDivider(position)) {
      outRect.set(0, 0, 0, 0);
      return;
    }
    if (mVertical) {
      outRect.set(0, 0, 0, getIntrinsicHeight(position));
    } else {
      outRect.set(0, 0, getIntrinsicWidth(position), 0);
    }
  }

  private int getAdapterPosition(View view) {
    return ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
  }

  private Drawable getDivider(int position) {
    if (mDrawableFactory != null) {
      return mDrawableFactory.getDrawable(position);
    }
    return mDivider;
  }

  private boolean isDisplayDivider(int position) {
    if (mDisplayFilter == null) {
      return true;
    }
    return mDisplayFilter.isDisplay(position);
  }

  private int getIntrinsicHeight(int position) {
    if (!isDisplayDivider(position) || (mHideLastDivider && position == lastPosition)) {
      return 0;
    }
    if (mDrawableFactory != null) {
      Drawable drawable = mDrawableFactory.getDrawable(position);
      if (drawable instanceof ColorDrawable) {
        return mDrawableFactory.getStrokeWidth(position);
      } else {
        return drawable.getIntrinsicHeight();
      }
    }
    if (mDivider instanceof ColorDrawable) {
      return mStrokeWidth;
    }
    return mDivider.getIntrinsicHeight();
  }

  private int getIntrinsicWidth(int position) {
    if (!isDisplayDivider(position) || (mHideLastDivider && position == lastPosition)) {
      return 0;
    }
    if (mDrawableFactory != null) {
      Drawable drawable = mDrawableFactory.getDrawable(position);
      if (drawable instanceof ColorDrawable) {
        return mDrawableFactory.getStrokeWidth(position);
      } else {
        return drawable.getIntrinsicWidth();
      }
    }
    if (mDivider instanceof ColorDrawable) {
      return mStrokeWidth;
    }
    return mDivider.getIntrinsicWidth();
  }

  private int getLeftMargin(int position) {
    if (mLeftMarginFactory == null) {
      return mLeftMargin;
    }
    return mLeftMarginFactory.getMargin(position);
  }

  private int getRightMargin(int position) {
    if (mRightMarginFactory == null) {
      return mRightMargin;
    }
    return mRightMarginFactory.getMargin(position);
  }

  private int getTopMargin(int position) {
    if (mTopMarginFactory == null) {
      return mTopMargin;
    }
    return mTopMarginFactory.getMargin(position);
  }

  private int getBottomMargin(int position) {
    if (mBottomMarginFactory == null) {
      return mBottomMargin;
    }
    return mBottomMarginFactory.getMargin(position);
  }

  public void addTo(RecyclerView recyclerView) {
    recyclerView.addItemDecoration(this);
  }

  public static Builder builder(@Nullable Context context) {
    Preconditions.checkNotNull(context);
    return new Builder(context);
  }

  public static class Builder {

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private final Context context;
    private Drawable drawable;
    private int strokeWidth = 0;
    private int leftMargin = 0;
    private int rightMargin = 0;
    private int topMargin = 0;
    private int bottomMargin = 0;
    private boolean hideLastDivider = false;
    private boolean vertical = true;
    private MarginFactory leftMarginFactory;
    private MarginFactory rightMarginFactory;
    private MarginFactory topMarginFactory;
    private MarginFactory bottomMarginFactory;
    private DrawableFactory drawableFactory;
    private DisplayFilter displayFilter;

    private Builder(Context context) {
      this.context = context;
      final TypedArray a = context.obtainStyledAttributes(ATTRS);
      this.drawable = a.getDrawable(0);
      if (this.drawable == null) {
        Log.e("RecyclerViewDivider", "@android:attr/listDivider was not set");
      }
      a.recycle();
    }

    public RecyclerViewDivider build() {
      return new RecyclerViewDivider(this);
    }

    public Builder color(@ColorRes int id, @DimenRes int strokeWidth) {
      this.drawable = new ColorDrawable(ContextCompat.getColor(context, id));
      this.strokeWidth = context.getResources().getDimensionPixelSize(strokeWidth);
      return this;
    }

    public Builder drawable(@DrawableRes int id) {
      this.drawable = ResourcesCompat.getDrawable(context.getResources(), id, context.getTheme());
      return this;
    }

    public Builder leftMargin(@DimenRes int id) {
      this.leftMargin = context.getResources().getDimensionPixelSize(id);
      return this;
    }

    public Builder rightMargin(@DimenRes int id) {
      this.rightMargin = context.getResources().getDimensionPixelSize(id);
      return this;
    }

    public Builder topMargin(@DimenRes int id) {
      this.topMargin = context.getResources().getDimensionPixelSize(id);
      return this;
    }

    public Builder bottomMargin(@DimenRes int id) {
      this.bottomMargin = context.getResources().getDimensionPixelSize(id);
      return this;
    }

    public Builder horizontal() {
      this.vertical = false;
      return this;
    }

    public Builder hideLastDivider() {
      this.hideLastDivider = true;
      return this;
    }

    public Builder leftMarginFactory(MarginFactory marginFactory) {
      this.leftMarginFactory = marginFactory;
      return this;
    }

    public Builder rightMarginFactory(MarginFactory marginFactory) {
      this.rightMarginFactory = marginFactory;
      return this;
    }

    public Builder topMarginFactory(MarginFactory marginFactory) {
      this.topMarginFactory = marginFactory;
      return this;
    }

    public Builder bottomMarginFactory(MarginFactory marginFactory) {
      this.bottomMarginFactory = marginFactory;
      return this;
    }

    public Builder drawableFactory(DrawableFactory drawableFactory) {
      this.drawableFactory = drawableFactory;
      return this;
    }

    public Builder displayFilter(DisplayFilter displayFilter) {
      this.displayFilter = displayFilter;
      return this;
    }
  }

  public interface DisplayFilter {

    boolean isDisplay(int position);
  }

  public interface DrawableFactory {

    Drawable getDrawable(int position);

    int getStrokeWidth(int position);
  }

  public interface MarginFactory {

    int getMargin(int position);
  }
}