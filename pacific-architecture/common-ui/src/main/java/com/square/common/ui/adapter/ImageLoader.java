package com.square.common.ui.adapter;

import android.widget.ImageView;
import androidx.annotation.NonNull;

public interface ImageLoader {

    void onImageLoad(@NonNull ImageView imageView, @NonNull ViewHolder holder);
}
