package com.pacific.guava.android.mvvm

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.net.*
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.pacific.guava.android.net.isNetworkAvailable
import timber.log.Timber
import java.lang.ref.WeakReference

/**
 * app管理类
 */
object AppManager : LifecycleObserver, Application.ActivityLifecycleCallbacks {

    @Volatile
    private var isInitialized = false

    private val cm by lazy {
        AndroidX.myApp.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private val networkCallback by lazy {
        createNetworkCallback()
    }

    private var weakCurrentActivity: WeakReference<Activity>? = null

    /**
     * 所有Activity列表
     */
    private var sActivities = ArrayList<Activity>()
    val activities: List<Activity> get() = sActivities

    @SuppressLint("MissingPermission")
    fun initialize() {
        if (isInitialized) {
            return
        }
        isInitialized = true
        AndroidX.myApp.registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        notifyNetworkChanged(isNetworkAvailable(AndroidX.myApp))
        monitorNetworkConnectivity()
    }

    /**
     * 转入前台回调
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onMoveToForeground() {
        Timber.d("isAppInForeground true")
        AndroidX.isAppInForeground.value = true
    }

    /**
     * 转入后台回调
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onMoveToBackground() {
        Timber.d("isAppInForeground false")
        AndroidX.isAppInForeground.value = false
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        sActivities.add(activity)
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
        weakCurrentActivity = WeakReference(activity)
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        require(sActivities.remove(activity))
    }

    /**
     * 获取当前Activity
     */
    fun currentActivity(): Activity? {
        return weakCurrentActivity?.get()
    }

    /**
     * 获取某个Activity
     */
    fun getActivity(predicate: (Activity) -> Boolean): Activity? {
        val list = sActivities.filter {
            predicate.invoke(it)
        }
        if (list.isEmpty()) {
            return null
        }
        return list[0]
    }

    /**
     * 开始监听网络状态
     */
    @Suppress("DEPRECATION")
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    private fun monitorNetworkConnectivity() {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                cm.registerDefaultNetworkCallback(networkCallback)
            }
            else -> {
                cm.registerNetworkCallback(
                    NetworkRequest.Builder()
                        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                        .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
                        .build(),
                    networkCallback
                )
            }
        }
    }

    /**
     * 网络监听回调
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun createNetworkCallback(): ConnectivityManager.NetworkCallback {

        return object : ConnectivityManager.NetworkCallback() {

            @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
            override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
                super.onBlockedStatusChanged(network, blocked)
                Timber.d("Network->onBlockedStatusChanged")
            }

            @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                Timber.d("Network->onCapabilitiesChanged")
                notifyNetworkChanged(
                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                )
            }

            @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
            override fun onLost(network: Network) {
                super.onLost(network)
                Timber.d("Network->onLost")
                notifyNetworkChanged(false)
            }

            @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
            override fun onLinkPropertiesChanged(
                network: Network,
                linkProperties: LinkProperties
            ) {
                super.onLinkPropertiesChanged(network, linkProperties)
                Timber.d("Network->onLinkPropertiesChanged")
            }

            @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
            override fun onUnavailable() {
                super.onUnavailable()
                Timber.d("Network->onUnavailable")
            }

            @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
            override fun onLosing(network: Network, maxMsToLive: Int) {
                super.onLosing(network, maxMsToLive)
                Timber.d("Network->onLosing")
            }

            @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                Timber.d("Network->onAvailable")
            }
        }
    }

    /**
     * 推送网络状态改变
     */
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    private fun notifyNetworkChanged(isConnected: Boolean) {
        if (isConnected != AndroidX.isNetworkConnected.value) {
            AndroidX.isNetworkConnected.value = isConnected
        }
    }
}