package com.pacific.core

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
import androidx.core.content.getSystemService
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.pacific.core.initializer.AppInitializer
import com.pacific.guava.coroutines.Bus
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import java.lang.ref.WeakReference

/**
 * https://proandroiddev.com/connectivity-network-internet-state-change-on-android-10-and-above-311fb761925
 * https://github.com/YarikSOffice/ConnectivityPlayground
 */
object AppManager : AppInitializer, LifecycleObserver, Application.ActivityLifecycleCallbacks {

    private val cm by lazy {
        contextApp.getSystemService<ConnectivityManager>()!!
    }

    private val networkBroadcastReceiver by lazy {
        createNetworkBroadcastReceiver()
    }

    private val networkCallback by lazy {
        createNetworkCallback()
    }

    private var weakCurrentActivity: WeakReference<Activity?>? = null

    @SuppressLint("MissingPermission")
    override fun initialize(app: Application) {
        contextApp.registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        notifyNetworkChanged(isNetworkConnected())
        monitorNetworkConnectivity()
        Bus.subscribe()
            .onEach { onBusEvent(it) }
            .catch { e -> e.printStackTrace() }
            .launchIn(GlobalScope)
    }

    private fun onBusEvent(event: Pair<Int, Any>) {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onMoveToForeground() {
        Timber.d("isAppInForeground true")
        isAppInForeground.postValue(true)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onMoveToBackground() {
        Timber.d("isAppInForeground false")
        isAppInForeground.postValue(false)
    }

    override fun onActivityPaused(activity: Activity?) {
    }

    override fun onActivityResumed(activity: Activity?) {
        weakCurrentActivity = WeakReference(activity)
    }

    override fun onActivityStarted(activity: Activity?) {
    }

    override fun onActivityDestroyed(activity: Activity?) {
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity?) {
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
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
                        .build(),
                    networkCallback
                )
            }
            else -> contextApp.registerReceiver(
                networkBroadcastReceiver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
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
        isNetworkConnected.postValue(isConnected)
    }

    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    private fun isNetworkConnected(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val capabilities: NetworkCapabilities? = cm.getNetworkCapabilities(cm.activeNetwork)
            capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
        } else {
            val info: NetworkInfo? = cm.activeNetworkInfo
            info?.isConnectedOrConnecting ?: false
        }
    }
}