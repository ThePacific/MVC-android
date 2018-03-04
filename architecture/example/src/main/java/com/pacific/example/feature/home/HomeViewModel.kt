package com.pacific.example.feature.home

import com.pacific.arch.data.Source
import com.pacific.arch.example.App
import com.pacific.arch.rx.applyNewThread
import com.pacific.example.base.RxAwareViewModel
import com.pacific.example.model.Apk
import io.reactivex.Observable
import javax.inject.Inject

class HomeViewModel @Inject constructor(app: App) : RxAwareViewModel(app) {

    fun loadApk(): Observable<Source<List<Apk>>> {
        return Observable
                .fromCallable {
                    val base = "http://i.imgur.com/"
                    val ext = ".jpg"
                    val urls = arrayOf(
                            base + "CqmBjo5" + ext, base + "zkaAooq" + ext, base + "0E2tgV7" + ext,
                            base + "0gqnEaY" + ext, base + "9gbQ7YR" + ext, base + "aFhEEby" + ext,
                            base + "P5JLfjk" + ext, base + "nz67a4F" + ext, base + "dFH34N5" + ext,
                            base + "FI49ftb" + ext, base + "DvpvklR" + ext, base + "DNKnbG8" + ext,
                            base + "yAdbrLp" + ext, base + "55w5Km7" + ext, base + "NIwNTMR" + ext,
                            base + "DAl0KB8" + ext, base + "xZLIYFV" + ext, base + "HvTyeh3" + ext,
                            base + "Ig9oHCM" + ext, base + "7GUv9qa" + ext, base + "i5vXmXp" + ext,
                            base + "glyvuXg" + ext, base + "u6JF6JZ" + ext, base + "ExwR7ap" + ext,
                            base + "Q54zMKT" + ext, base + "9t6hLbm" + ext, base + "F8n3Ic6" + ext,
                            base + "P5ZRSvT" + ext, base + "jbemFzr" + ext, base + "8B7haIK" + ext,
                            base + "aSeTYQr" + ext, base + "OKvWoTh" + ext, base + "zD3gT4Z" + ext,
                            base + "CqmBjo5" + ext, base + "zkaAooq" + ext, base + "0E2tgV7" + ext,
                            base + "CqmBjo5" + ext, base + "zkaAooq" + ext, base + "0E2tgV7" + ext,
                            base + "0gqnEaY" + ext, base + "9gbQ7YR" + ext, base + "aFhEEby" + ext,
                            base + "P5JLfjk" + ext, base + "nz67a4F" + ext, base + "dFH34N5" + ext,
                            base + "FI49ftb" + ext, base + "DvpvklR" + ext, base + "DNKnbG8" + ext,
                            base + "yAdbrLp" + ext, base + "55w5Km7" + ext, base + "NIwNTMR" + ext,
                            base + "DAl0KB8" + ext, base + "xZLIYFV" + ext, base + "HvTyeh3" + ext,
                            base + "Ig9oHCM" + ext, base + "7GUv9qa" + ext, base + "i5vXmXp" + ext,
                            base + "glyvuXg" + ext, base + "u6JF6JZ" + ext, base + "ExwR7ap" + ext,
                            base + "Q54zMKT" + ext, base + "9t6hLbm" + ext, base + "F8n3Ic6" + ext,
                            base + "P5ZRSvT" + ext, base + "jbemFzr" + ext, base + "8B7haIK" + ext,
                            base + "aSeTYQr" + ext, base + "OKvWoTh" + ext, base + "zD3gT4Z" + ext,
                            base + "CqmBjo5" + ext, base + "zkaAooq" + ext, base + "0E2tgV7" + ext,
                            base + "0gqnEaY" + ext, base + "9gbQ7YR" + ext, base + "aFhEEby" + ext,
                            base + "P5JLfjk" + ext, base + "nz67a4F" + ext, base + "dFH34N5" + ext,
                            base + "FI49ftb" + ext, base + "DvpvklR" + ext, base + "DNKnbG8" + ext,
                            base + "yAdbrLp" + ext, base + "55w5Km7" + ext, base + "NIwNTMR" + ext,
                            base + "DAl0KB8" + ext, base + "xZLIYFV" + ext, base + "HvTyeh3" + ext,
                            base + "Ig9oHCM" + ext, base + "7GUv9qa" + ext, base + "i5vXmXp" + ext,
                            base + "glyvuXg" + ext, base + "u6JF6JZ" + ext, base + "ExwR7ap" + ext,
                            base + "Q54zMKT" + ext, base + "9t6hLbm" + ext, base + "F8n3Ic6" + ext,
                            base + "P5ZRSvT" + ext, base + "jbemFzr" + ext, base + "8B7haIK" + ext,
                            base + "aSeTYQr" + ext, base + "OKvWoTh" + ext, base + "zD3gT4Z" + ext,
                            base + "CqmBjo5" + ext, base + "zkaAooq" + ext, base + "0E2tgV7" + ext,
                            base + "CqmBjo5" + ext, base + "zkaAooq" + ext, base + "0E2tgV7" + ext,
                            base + "0gqnEaY" + ext, base + "9gbQ7YR" + ext, base + "aFhEEby" + ext,
                            base + "P5JLfjk" + ext, base + "nz67a4F" + ext, base + "dFH34N5" + ext,
                            base + "FI49ftb" + ext, base + "DvpvklR" + ext, base + "DNKnbG8" + ext,
                            base + "yAdbrLp" + ext, base + "55w5Km7" + ext, base + "NIwNTMR" + ext,
                            base + "DAl0KB8" + ext, base + "xZLIYFV" + ext, base + "HvTyeh3" + ext,
                            base + "Ig9oHCM" + ext, base + "7GUv9qa" + ext, base + "i5vXmXp" + ext,
                            base + "glyvuXg" + ext, base + "u6JF6JZ" + ext, base + "ExwR7ap" + ext,
                            base + "Q54zMKT" + ext, base + "9t6hLbm" + ext, base + "F8n3Ic6" + ext,
                            base + "P5ZRSvT" + ext, base + "jbemFzr" + ext, base + "8B7haIK" + ext,
                            base + "aSeTYQr" + ext, base + "OKvWoTh" + ext, base + "zD3gT4Z" + ext,
                            base + "CqmBjo5" + ext, base + "zkaAooq" + ext, base + "0E2tgV7" + ext,
                            base + "0gqnEaY" + ext, base + "9gbQ7YR" + ext, base + "aFhEEby" + ext,
                            base + "P5JLfjk" + ext, base + "nz67a4F" + ext, base + "dFH34N5" + ext,
                            base + "FI49ftb" + ext, base + "DvpvklR" + ext, base + "DNKnbG8" + ext,
                            base + "yAdbrLp" + ext, base + "55w5Km7" + ext, base + "NIwNTMR" + ext,
                            base + "DAl0KB8" + ext, base + "xZLIYFV" + ext, base + "HvTyeh3" + ext,
                            base + "Ig9oHCM" + ext, base + "7GUv9qa" + ext, base + "i5vXmXp" + ext,
                            base + "glyvuXg" + ext, base + "u6JF6JZ" + ext, base + "ExwR7ap" + ext,
                            base + "Q54zMKT" + ext, base + "9t6hLbm" + ext, base + "F8n3Ic6" + ext,
                            base + "P5ZRSvT" + ext, base + "jbemFzr" + ext, base + "8B7haIK" + ext,
                            base + "aSeTYQr" + ext, base + "OKvWoTh" + ext, base + "zD3gT4Z" + ext,
                            base + "CqmBjo5" + ext, base + "zkaAooq" + ext, base + "0E2tgV7" + ext,
                            base + "CqmBjo5" + ext, base + "zkaAooq" + ext, base + "0E2tgV7" + ext,
                            base + "0gqnEaY" + ext, base + "9gbQ7YR" + ext, base + "aFhEEby" + ext,
                            base + "P5JLfjk" + ext, base + "nz67a4F" + ext, base + "dFH34N5" + ext,
                            base + "FI49ftb" + ext, base + "DvpvklR" + ext, base + "DNKnbG8" + ext,
                            base + "yAdbrLp" + ext, base + "55w5Km7" + ext, base + "NIwNTMR" + ext,
                            base + "DAl0KB8" + ext, base + "xZLIYFV" + ext, base + "HvTyeh3" + ext,
                            base + "Ig9oHCM" + ext, base + "7GUv9qa" + ext, base + "i5vXmXp" + ext,
                            base + "glyvuXg" + ext, base + "u6JF6JZ" + ext, base + "ExwR7ap" + ext,
                            base + "Q54zMKT" + ext, base + "9t6hLbm" + ext, base + "F8n3Ic6" + ext,
                            base + "P5ZRSvT" + ext, base + "jbemFzr" + ext, base + "8B7haIK" + ext,
                            base + "aSeTYQr" + ext, base + "OKvWoTh" + ext, base + "zD3gT4Z" + ext,
                            base + "CqmBjo5" + ext, base + "zkaAooq" + ext, base + "0E2tgV7" + ext,
                            base + "0gqnEaY" + ext, base + "9gbQ7YR" + ext, base + "aFhEEby" + ext,
                            base + "P5JLfjk" + ext, base + "nz67a4F" + ext, base + "dFH34N5" + ext,
                            base + "FI49ftb" + ext, base + "DvpvklR" + ext, base + "DNKnbG8" + ext,
                            base + "yAdbrLp" + ext, base + "55w5Km7" + ext, base + "NIwNTMR" + ext,
                            base + "DAl0KB8" + ext, base + "xZLIYFV" + ext, base + "HvTyeh3" + ext,
                            base + "Ig9oHCM" + ext, base + "7GUv9qa" + ext, base + "i5vXmXp" + ext,
                            base + "glyvuXg" + ext, base + "u6JF6JZ" + ext, base + "ExwR7ap" + ext,
                            base + "Q54zMKT" + ext, base + "9t6hLbm" + ext, base + "F8n3Ic6" + ext,
                            base + "P5ZRSvT" + ext, base + "jbemFzr" + ext, base + "8B7haIK" + ext,
                            base + "aSeTYQr" + ext, base + "OKvWoTh" + ext, base + "zD3gT4Z" + ext,
                            base + "CqmBjo5" + ext, base + "zkaAooq" + ext, base + "0E2tgV7" + ext,
                            base + "CqmBjo5" + ext, base + "zkaAooq" + ext, base + "0E2tgV7" + ext,
                            base + "0gqnEaY" + ext, base + "9gbQ7YR" + ext, base + "aFhEEby" + ext,
                            base + "P5JLfjk" + ext, base + "nz67a4F" + ext, base + "dFH34N5" + ext,
                            base + "FI49ftb" + ext, base + "DvpvklR" + ext, base + "DNKnbG8" + ext,
                            base + "yAdbrLp" + ext, base + "55w5Km7" + ext, base + "NIwNTMR" + ext,
                            base + "DAl0KB8" + ext, base + "xZLIYFV" + ext, base + "HvTyeh3" + ext,
                            base + "Ig9oHCM" + ext, base + "7GUv9qa" + ext, base + "i5vXmXp" + ext,
                            base + "glyvuXg" + ext, base + "u6JF6JZ" + ext, base + "ExwR7ap" + ext,
                            base + "Q54zMKT" + ext, base + "9t6hLbm" + ext, base + "F8n3Ic6" + ext,
                            base + "P5ZRSvT" + ext, base + "jbemFzr" + ext, base + "8B7haIK" + ext,
                            base + "aSeTYQr" + ext, base + "OKvWoTh" + ext, base + "zD3gT4Z" + ext,
                            base + "CqmBjo5" + ext, base + "zkaAooq" + ext, base + "0E2tgV7" + ext,
                            base + "0gqnEaY" + ext, base + "9gbQ7YR" + ext, base + "aFhEEby" + ext,
                            base + "P5JLfjk" + ext, base + "nz67a4F" + ext, base + "dFH34N5" + ext,
                            base + "FI49ftb" + ext, base + "DvpvklR" + ext, base + "DNKnbG8" + ext,
                            base + "yAdbrLp" + ext, base + "55w5Km7" + ext, base + "NIwNTMR" + ext,
                            base + "DAl0KB8" + ext, base + "xZLIYFV" + ext, base + "HvTyeh3" + ext,
                            base + "Ig9oHCM" + ext, base + "7GUv9qa" + ext, base + "i5vXmXp" + ext,
                            base + "glyvuXg" + ext, base + "u6JF6JZ" + ext, base + "ExwR7ap" + ext,
                            base + "Q54zMKT" + ext, base + "9t6hLbm" + ext, base + "F8n3Ic6" + ext,
                            base + "P5ZRSvT" + ext, base + "jbemFzr" + ext, base + "8B7haIK" + ext,
                            base + "aSeTYQr" + ext, base + "OKvWoTh" + ext, base + "zD3gT4Z" + ext,
                            base + "CqmBjo5" + ext, base + "zkaAooq" + ext, base + "0E2tgV7" + ext,
                            base + "CqmBjo5" + ext, base + "zkaAooq" + ext, base + "0E2tgV7" + ext,
                            base + "0gqnEaY" + ext, base + "9gbQ7YR" + ext, base + "aFhEEby" + ext,
                            base + "P5JLfjk" + ext, base + "nz67a4F" + ext, base + "dFH34N5" + ext,
                            base + "FI49ftb" + ext, base + "DvpvklR" + ext, base + "DNKnbG8" + ext,
                            base + "yAdbrLp" + ext, base + "55w5Km7" + ext, base + "NIwNTMR" + ext,
                            base + "DAl0KB8" + ext, base + "xZLIYFV" + ext, base + "HvTyeh3" + ext,
                            base + "Ig9oHCM" + ext, base + "7GUv9qa" + ext, base + "i5vXmXp" + ext,
                            base + "glyvuXg" + ext, base + "u6JF6JZ" + ext, base + "ExwR7ap" + ext,
                            base + "Q54zMKT" + ext, base + "9t6hLbm" + ext, base + "F8n3Ic6" + ext,
                            base + "P5ZRSvT" + ext, base + "jbemFzr" + ext, base + "8B7haIK" + ext,
                            base + "aSeTYQr" + ext, base + "OKvWoTh" + ext, base + "zD3gT4Z" + ext,
                            base + "z77CaIt" + ext)
                    val list = ArrayList<Apk>()
                    urls.mapTo(list) { Apk(it) }
                    return@fromCallable Source.success<List<Apk>>(list)
                }
                .onErrorReturn { Source.failure(it) }
                .applyNewThread()
                .startWith(Source.inProgress())
    }
}