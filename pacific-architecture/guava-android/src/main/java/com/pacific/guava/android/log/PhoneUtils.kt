/**
 * 1. link@ https://handstandsam.com/2017/05/04/identifying-an-android-device/
 */
package com.pacific.guava.android.log

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import okio.ByteString.Companion.encodeUtf8
import java.util.*

const val ARMEABI = 1
const val ARMEABI_V7 = 2
const val ARM64_V8A = 3
const val X86 = 4
const val X86_64 = 5
const val MIPS = 6
const val MIPS_64 = 7

/**
 * IMEI (International Mobile Equipment Identity)
 * Returns the unique device ID, for example, the IMEI for GSM and the MEID or ESN for CDMA phones.
 * Return null if device ID is not available
 */
@SuppressLint("all")
@SuppressWarnings("deprecation")
@RequiresPermission(Manifest.permission.READ_PHONE_STATE)
fun getIMEI(context: Context): String? {
    val telephonyManager = ContextCompat.getSystemService(context, TelephonyManager::class.java)!!
    val uuid = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        telephonyManager.imei
    } else {
        telephonyManager.deviceId
    }
    if (uuid.isNullOrEmpty()) {
        Log.d("Pacific", "No IMEI")
    }
    return uuid
}

/**
 * Phone Number
 * Returns the phone number string for line 1, for example, the MSISDN or a GSM phone.
 * Return null if it is unavailable.
 */
@SuppressLint("all")
@RequiresPermission(
    anyOf = [
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.READ_SMS,
        Manifest.permission.READ_PHONE_NUMBERS
    ]
)
fun getPhoneNumber(context: Context): String? {
    val telephonyManager = ContextCompat.getSystemService(context, TelephonyManager::class.java)!!
    val number = telephonyManager.line1Number
    if (number.isNullOrEmpty()) {
        Log.d("Pacific", "No Phone Number")
    }
    return number
}

/**
 * ICCID (Sim Serial Number)
 * Returns the serial number of the SIM, if applicable.
 * Return null if it is unavailable
 */
@SuppressLint("all")
@RequiresPermission(Manifest.permission.READ_PHONE_STATE)
fun getICCID(context: Context): String? {
    val telephonyManager = ContextCompat.getSystemService(context, TelephonyManager::class.java)!!
    val uuid = telephonyManager.subscriberId
    if (uuid.isNullOrEmpty()) {
        Log.d("Pacific", "No ICCID")
    }
    return uuid
}

/**
 * This is a 64-bit quantity that is generated and stored when the device first boots.
 * It is reset when the device is wiped.
 * It is unique device-wide per OS install,
 * but only unique per application starting with Android O (with old applications grandfathered in)
 */
fun getAndroidId(context: Context): String? {
    val uniqueId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    if (uniqueId.isNullOrEmpty()) {
        Log.d("Pacific", "No ANDROID_ID")
    }
    return uniqueId
}

fun uniqueId(context: Context): String {
    var uniqueId: String? = getIMEI(context)
    if (uniqueId.isNullOrEmpty()) {
        uniqueId = getICCID(context)
        if (uniqueId.isNullOrEmpty()) {
            uniqueId = getAndroidId(context)
            if (uniqueId.isNullOrEmpty() ||
                uniqueId == "9774d56d682e549c" ||
                uniqueId.contains("android", true)
            ) {
                uniqueId = UUID.randomUUID().toString()
            }
        }
    }
    return uniqueId.encodeUtf8().md5().hex()
}

fun isEmulator(context: Context): Boolean {
    val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    val networkOperator = tm.networkOperatorName.toLowerCase()
    val fingerPrint = Build.FINGERPRINT
    return "android" == networkOperator
            || fingerPrint.startsWith("unknown")
            || fingerPrint.contains("generic")
            || fingerPrint.contains("vbox")
}

fun getBuildConfigValue(context: Context, key: String): Any? {
    try {
        val clazz = Class.forName(context.packageName + ".BuildConfig")
        val field = clazz.getField(key)
        return field.get(null)
    } catch (e: ClassNotFoundException) {
        Log.d("Pacific", "Unable to get the BuildConfig, is this built with ANT?")
    } catch (e: NoSuchFieldException) {
        Log.d("Pacific", "$key is not a valid field. Check your build.gradle")
    } catch (e: IllegalAccessException) {
        Log.d("Pacific", "Illegal Access Exception: Let's print a stack trace")
    }
    return null
}

fun deviceInfo(): String {
    return TextUtils.join(
        "-",
        arrayOf(Build.BRAND, Build.MODEL, getCupArchDescription(), Build.VERSION.RELEASE)
    )
}

fun getRandomUUID(): String = UUID.randomUUID().toString()

fun getCupArch(): Int {
    val arch = System.getProperty("os.arch")!!.toLowerCase()
    if (arch.contains("mip")) {
        return if (arch.contains("64")) {
            MIPS_64
        } else {
            MIPS
        }
    }
    if (arch.contains("86")) {
        return if (arch.contains("64")) {
            X86_64
        } else {
            X86
        }
    }
    if (arch.contains("ar")) {
        return when {
            arch.contains("64") -> ARM64_V8A
            arch.contains("7") -> ARMEABI_V7
            else -> ARMEABI
        }
    }
    throw AssertionError("Unknown CPU")
}

fun getCupArchDescription(): String {
    return when (getCupArch()) {
        ARMEABI -> "armeabi"
        ARMEABI_V7 -> "armeabi_v7"
        ARM64_V8A -> "arm64_v8a"
        X86 -> "x86"
        X86_64 -> "x86_64"
        MIPS -> "mips"
        MIPS_64 -> "mips_64"
        else -> "Unknown CUP"
    }
}