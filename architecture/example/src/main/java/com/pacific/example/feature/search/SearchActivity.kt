package com.pacific.example.feature.search

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.AutoCompleteTextView
import com.pacific.arch.example.R
import com.pacific.example.base.BaseActivity
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val autoComplete = search_view.findViewById<AutoCompleteTextView>(android.support.v7.appcompat.R.id.search_src_text)
        autoComplete.setTextColor(ContextCompat.getColor(this, android.R.color.white))
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
        }
    }
}
