package com.pacific.arch.example

import android.os.Bundle
import android.util.Log
import com.pacific.arch.presentation.Activity
import com.pacific.arch.presentation.activityViewModel
import com.pacific.example.MainViewModel
import com.pacific.example.data.MyContent
import com.pacific.example.data.MyDatabase
import javax.inject.Inject

class MainActivity : Activity() {

    @Inject
    lateinit var app: App

    private val model by activityViewModel(MainViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e("_______________", app.toString())
        Log.e("_______________", model.toString())
        Thread({
            MyDatabase.getInstance(this)!!.contentDao().save(MyContent(110, "just for test"))
            Log.e("****", MyDatabase.getInstance(this)!!.contentDao().loadAll().value!!.content)
        }).start()
    }

}
