package com.square.core

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
import com.square.core.initializer.AppInitializer
import com.square.guava.coroutines.Bus
import com.square.guava.domain.illegalArgumentException
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import java.lang.ref.WeakReference

object AppManager : AppInitializer, LifecycleObserver, Application.ActivityLifecycleCallbacks {

    private val connectivityManager by lazy {
        app.getSystemService<ConnectivityManager>()
    }

    private val networkBroadcastReceiver by lazy {
        createNetworkBroadcastReceiver()
    }

    private val networkCallback by lazy {
        createNetworkCallback()
    }

    private lateinit var app: Application
    private var weakCurrentActivity: WeakReference<Activity>? = null

    @SuppressLint("MissingPermission")
    override fun initialize(app: Application) {
        AppManager.app = app
        monitorNetworkConnectivity()
        AppManager.app.registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
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

    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    private fun monitorNetworkConnectivity() {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                connectivityManager?.registerDefaultNetworkCallback(networkCallback)
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                connectivityManager?.registerNetworkCallback(
                    NetworkRequest.Builder()
                        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                        .build(),
                    networkCallback
                )
            }
            else -> app.registerReceiver(
                networkBroadcastReceiver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun createNetworkCallback(): ConnectivityManager.NetworkCallback {
        return object : ConnectivityManager.NetworkCallback() {
            override fun onCapabilitiesChanged(
                network: Network?,
                networkCapabilities: NetworkCapabilities?
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                Timber.d("Network->onCapabilitiesChanged")
            }

            override fun onLinkPropertiesChanged(
                network: Network?,
                linkProperties: LinkProperties?
            ) {
                super.onLinkPropertiesChanged(network, linkProperties)
                Timber.d("Network->onLinkPropertiesChanged")
            }

            override fun onUnavailable() {
                super.onUnavailable()
                Timber.d("Network->onUnavailable")
            }

            override fun onLosing(network: Network?, maxMsToLive: Int) {
                super.onLosing(network, maxMsToLive)
                Timber.d("Network->onLosing")
            }

            @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
            override fun onLost(network: Network?) {
                super.onLost(network)
                Timber.d("Network->onLost")
                notifyNetworkChanged()
            }

            @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
            override fun onAvailable(network: Network?) {
                super.onAvailable(network)
                Timber.d("Network->onAvailable")
                notifyNetworkChanged()
            }
        }
    }

    private fun createNetworkBroadcastReceiver(): BroadcastReceiver {

        return object : BroadcastReceiver() {

            @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
            override fun onReceive(context: Context, intent: Intent) {
                when (intent.action) {
                    ConnectivityManager.CONNECTIVITY_ACTION -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            illegalArgumentException(ConnectivityManager.CONNECTIVITY_ACTION)
                        } else {
                            notifyNetworkChanged()
                        }
                    }
                    else -> return
                }
            }
        }
    }

    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    private fun notifyNetworkChanged() {
        isNetworkConnected.postValue(
            connectivityManager?.activeNetworkInfo?.isConnected == true
        )
    }
}