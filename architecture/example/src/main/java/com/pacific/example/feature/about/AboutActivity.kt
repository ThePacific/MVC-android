package com.pacific.example.feature.about

import android.os.Bundle
import com.pacific.example.R
import com.pacific.example.databinding.ActivityAboutBinding
import com.pacific.arch.presentation.contentView
import com.pacific.example.base.BaseActivity

class AboutActivity : BaseActivity() {
    private val binding: ActivityAboutBinding by contentView(R.layout.activity_about)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
    }
}
