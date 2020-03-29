package com.square.feature.zygote

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import com.square.common.glide.GlideApp
import com.square.common.ui.databinding.dataBinding
import com.square.core.feature.GAME_ACTIVITY_REQUEST_CODE
import com.square.core.feature.createGameActivityIntent
import com.square.core.mvvm.BaseActivity
import com.square.feature.zygote.databinding.ActivityZygoteBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ZygoteActivity : BaseActivity() {

    private val binding: ActivityZygoteBinding by dataBinding(R.layout.activity_zygote)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.layoutZygote.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        ViewCompat.setOnApplyWindowInsetsListener(binding.imgZygote) { v, insets ->
            return@setOnApplyWindowInsetsListener insets
        }

        GlideApp.with(this)
            .load(R.drawable.zygote_bg)
            .into(binding.imgZygote)

        lifecycleScope.launch {
            withContext(Dispatchers.Default) {
                delay(3000)
            }
            startActivityForResult(
                createGameActivityIntent(
                    1L,
                    1L,
                    1L,
                    "NICE"
                ),
                GAME_ACTIVITY_REQUEST_CODE
            )
        }
    }
}
