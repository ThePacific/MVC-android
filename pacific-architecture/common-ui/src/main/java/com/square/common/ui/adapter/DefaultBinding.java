package com.square.common.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.SparseArray;
import android.view.View;
import android.widget.*;

import androidx.annotation.*;

public final class DefaultBinding {

    private final SparseArray<View> views;

    public final View itemView;

    public DefaultBinding(View itemView) {
        this.itemView = itemView;
        this.views = new SparseArray<>();
    }

    public <V extends View> V findView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = AdapterUtils.findView(itemView, viewId);
            views.put(viewId, view);
        }
        return (V) view;
    }

    public DefaultBinding setText(int viewId, CharSequence value) {
        TextView view = findView(viewId);
        view.setText(value);
        return this;
    }

    public DefaultBinding setText(int viewId, @StringRes int stringRes) {
        TextView view = findView(viewId);
        view.setText(stringRes);
        return this;
    }

    public DefaultBinding setImageResource(int viewId, @DrawableRes int imageRes) {
        ImageView view = findView(viewId);
        view.setImageResource(imageRes);
        return this;
    }

    public DefaultBinding setBackgroundColor(int viewId, @ColorInt int color) {
        View view = findView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public DefaultBinding setBackgroundRes(int viewId, @DrawableRes int backgroundRes) {
        View view = findView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public DefaultBinding setTextColor(int viewId, @ColorInt int textColor) {
        TextView view = findView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public DefaultBinding setTextColorRes(int viewId, @ColorRes int textColorRes) {
        TextView view = findView(viewId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setTextColor(itemView.getContext().getResources().getColor(textColorRes, null));
        } else {
            view.setTextColor(itemView.getContext().getResources().getColor(textColorRes));
        }
        return this;
    }

    public DefaultBinding setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = findView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public DefaultBinding setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = findView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public DefaultBinding setAlpha(int viewId, @FloatRange(from = 0.0, to = 1.0) float value) {
        findView(viewId).setAlpha(value);
        return this;
    }

    public DefaultBinding setVisible(int viewId, int visibility) {
        findView(viewId).setVisibility(visibility);
        return this;
    }

    public DefaultBinding setTypeface(int viewId, Typeface typeface) {
        TextView view = findView(viewId);
        view.setTypeface(typeface);
        view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        return this;
    }

    public DefaultBinding setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = findView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public DefaultBinding setProgress(int viewId, int progress) {
        ProgressBar view = findView(viewId);
        view.setProgress(progress);
        return this;
    }

    public DefaultBinding setProgress(int viewId, int progress, int max) {
        ProgressBar view = findView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public DefaultBinding setMax(int viewId, int max) {
        ProgressBar view = findView(viewId);
        view.setMax(max);
        return this;
    }

    public DefaultBinding setRating(int viewId, float rating) {
        RatingBar view = findView(viewId);
        view.setRating(rating);
        return this;
    }

    public DefaultBinding setRating(int viewId, float rating, int max) {
        RatingBar view = findView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public DefaultBinding setTag(int viewId, Object tag) {
        View view = findView(viewId);
        view.setTag(tag);
        return this;
    }

    public DefaultBinding setTag(int viewId, int key, Object tag) {
        View view = findView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public DefaultBinding setChecked(int viewId, boolean checked) {
        View view = findView(viewId);
        if (view instanceof CompoundButton) {
            ((CompoundButton) view).setChecked(checked);
        } else if (view instanceof CheckedTextView) {
            ((CheckedTextView) view).setChecked(checked);
        } else {
            ((Checkable) view).setChecked(checked);
        }
        return this;
    }

    @NonNull
    public Context context() {
        return itemView.getContext();
    }
}