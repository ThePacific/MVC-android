package com.pacific.example.feature.zygote

import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.ProgressBar
import com.pacific.arch.example.R
import com.pacific.arch.presentation.activityViewModel
import com.pacific.arch.presentation.jumpTo
import com.pacific.arch.views.widget.verifySDK
import com.pacific.example.base.BaseActivity
import com.pacific.example.feature.main.MainActivity
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.kotlin.autoDisposable

class SplashActivity : BaseActivity() {
    val model by activityViewModel(SplashViewModel::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if (!verifySDK(Build.VERSION_CODES.LOLLIPOP)) {
            val progress: ProgressBar = findViewById(R.id.progress)
            progress.indeterminateDrawable.setColorFilter(ContextCompat.getColor(
                    this,
                    android.R.color.white),
                    PorterDuff.Mode.SRC_IN)
        }
        model.initialize().autoDisposable(scope()).subscribe({
            jumpTo(this@SplashActivity, MainActivity::class.java)
        })
    }

}
