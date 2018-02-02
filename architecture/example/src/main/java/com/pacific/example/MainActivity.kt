package com.pacific.arch.example

import android.os.Bundle
import android.widget.Toast
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.core.SearchResult
import com.baidu.mapapi.search.geocode.*
import com.bumptech.glide.Glide
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
                if (result == null || result.error == SearchResult.ERRORNO.PERMISSION_UNFINISHED) {
                    toast("没有检索到经纬度,请确保网络正常")
                    return
                }
                if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                    toast("经纬度(${result.location.latitude},${result.location.longitude})")
                    return
                }

                toast("检索出错：${result.error}")
            }

            override fun onGetReverseGeoCodeResult(result: ReverseGeoCodeResult?) {
                if (result == null || result.error == SearchResult.ERRORNO.PERMISSION_UNFINISHED) {
                    toast("没有解析到地址,请确保网络正常")
                    return
                }
                if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                    toast("地址:${result.addressDetail.countryName},"
                            + "${result.addressDetail.province},"
                            + "${result.addressDetail.city}")
                    return
                }

                toast("解析出错：${result.error}")
            }
        })

        geoCoder.geocode(GeoCodeOption().city("北京").address("海淀区上地十街10号"))

        geoCoder.reverseGeoCode(ReverseGeoCodeOption().newVersion(1).location(LatLng(40.00, 118.00)))
    }

    override fun onDestroy() {
        super.onDestroy()
        geoCoder.destroy()
    }

    fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}
