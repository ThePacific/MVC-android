package com.pacific.arch.example

import android.os.Bundle
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.core.SearchResult
import com.baidu.mapapi.search.geocode.*
import com.pacific.arch.presentation.Activity
import com.pacific.arch.presentation.activityViewModel
import com.pacific.example.MainViewModel
import javax.inject.Inject


class MainActivity : Activity() {

    @Inject
    lateinit var app: App

    private val model by activityViewModel(MainViewModel::class.java)

    private val geoCoder: GeoCoder = GeoCoder.newInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        geoCoder.setOnGetGeoCodeResultListener(object : OnGetGeoCoderResultListener {
            override fun onGetGeoCodeResult(result: GeoCodeResult?) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有检索到结果
                    return
                }

                //获取地理编码结果
            }

            override fun onGetReverseGeoCodeResult(result: ReverseGeoCodeResult?) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有找到检索结果

                }

                //获取反向地理编码结果
            }
        })

        geoCoder.geocode(GeoCodeOption().city("北京").address("海淀区上地十街10号"))


        geoCoder.reverseGeoCode(ReverseGeoCodeOption().location(LatLng(118.0, 168.0)))
    }

    override fun onDestroy() {
        super.onDestroy()
        geoCoder.destroy()
    }
}
