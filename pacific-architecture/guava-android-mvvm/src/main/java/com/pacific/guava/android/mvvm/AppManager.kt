package com.pacific.guava.android.mvvm

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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

object AppManager : LifecycleObserver, Application.ActivityLifecycleCallbacks {

    @Volatile
    private var isInitialized = false

    private val cm by lazy {
        AndroidX.myApp.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private val networkBroadcastReceiver by lazy {
        createNetworkBroadcastReceiver()
    }

    private val networkCallback by lazy {
        createNetworkCallback()
    }

    private var weakCurrentActivity: WeakReference<Activity>? = null

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

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onMoveToForeground() {
        Timber.d("isAppInForeground true")
        AndroidX.isAppInForeground.value = true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onMoveToBackground() {
        Timber.d("isAppInForeground false")
        AndroidX.isAppInForeground.value = false
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
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
    }

    fun currentActivity(): Activity? {
        return weakCurrentActivity?.get()
    }

    @Suppress("DEPRECATION")
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    private fun monitorNetworkConnectivity() {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                cm.registerDefaultNetworkCallback(networkCallback)
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                cm.registerNetworkCallback(
                    NetworkRequest.Builder()
                        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                        .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
                        .build(),
                    networkCallback
                )
            }
            else -> {
                AndroidX.myApp.registerReceiver(
                    networkBroadcastReceiver,
                    IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
                )
            }
        }
    }

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

    private fun createNetworkBroadcastReceiver(): BroadcastReceiver {

        return object : BroadcastReceiver() {

            @Suppress("DEPRECATION")
            @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
            override fun onReceive(context: Context, intent: Intent) {
                when (intent.action) {
                    ConnectivityManager.CONNECTIVITY_ACTION -> {
                        // on some devices ConnectivityManager.getActiveNetworkInfo() does not provide the correct network state
                        // https://issuetracker.google.com/issues/37137911
                        val info: NetworkInfo? = cm.activeNetworkInfo
                        val fallbackInfo: NetworkInfo? = intent.getParcelableExtra(
                            ConnectivityManager.EXTRA_NETWORK_INFO
                        )
                        // a set of dirty workarounds
                        if (info?.isConnectedOrConnecting == true) {
                            notifyNetworkChanged(info.isConnectedOrConnecting)
                        } else if (
                            info != null &&
                            fallbackInfo != null &&
                            info.isConnectedOrConnecting != fallbackInfo.isConnectedOrConnecting
                        ) {
                            notifyNetworkChanged(fallbackInfo.isConnectedOrConnecting)
                        } else {
                            notifyNetworkChanged(
                                (info ?: fallbackInfo)?.isConnectedOrConnecting == true
                            )
                        }
                    }
                    else -> return
                }
            }
        }
    }

    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    private fun notifyNetworkChanged(isConnected: Boolean) {
        if (isConnected != AndroidX.isNetworkConnected.value) {
            AndroidX.isNetworkConnected.value = isConnected
        }
    }
}