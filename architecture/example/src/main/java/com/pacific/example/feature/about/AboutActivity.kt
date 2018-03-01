package com.pacific.example.feature.about

import android.os.Bundle
import com.pacific.arch.example.R
import com.pacific.arch.example.databinding.ActivityAboutBinding
import com.pacific.arch.presentation.contentView
import com.pacific.example.base.BaseActivity

class AboutActivity : BaseActivity() {
    private val binding: ActivityAboutBinding by contentView(R.layout.activity_about)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
    }
}
