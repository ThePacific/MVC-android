@file:JvmName("CanvasUtils")

package com.pacific.guava.android.ui.view

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect

fun Canvas.drawTextInCenter(text: String, textPaint: Paint) {
    val textBounds = Rect().also {
        textPaint.getTextBounds(text, 0, text.length, it)
    }
    val textWidth = textPaint.measureText(text)
    val textHeight = textBounds.height()
    this.drawText(
        text,
        textBounds.centerX() - textWidth / 2f,
        textBounds.centerY() + textHeight / 2f,
        textPaint
    )
}