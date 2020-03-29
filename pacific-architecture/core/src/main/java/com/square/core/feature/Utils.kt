package com.square.core.feature

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.square.guava.android.context.instantiate

const val GAME_FEATURE = "com.square.feature.game"
const val GAME_ACTIVITY_REQUEST_CODE = 1000
const val GAME_ACTIVITY_NAME = "$GAME_FEATURE.GameActivity"
const val GAME_FRAGMENT_NAME = "$GAME_FEATURE.GameFragment"

const val ACCESS_FEATURE = "com.square.feature.access"
const val ACCESS_ACTIVITY_NAME = "$ACCESS_FEATURE.AccessActivity"
const val ACCESS_ACTIVITY_REQUEST_CODE = 1100

const val ZYGOTE_FEATURE = "com.square.feature.zygote"
const val ZYGOTE_ACTIVITY_REQUEST_CODE = 1200

const val CHART_FEATURE = "com.square.feature.game.chart"
const val ROAD_FRAGMENT_NAME = "$CHART_FEATURE.road.RoadFragment"


fun Context.createGameActivityIntent(
        gameId: Long,
        gameCategoryId: Long,
        gameType: Long,
        gameName: String
): Intent {
    return Intent().also {
        it.action = Intent.ACTION_VIEW
        it.setClassName(this, GAME_ACTIVITY_NAME)
        it.putExtra("gameId", gameId)
        it.putExtra("gameCategoryId", gameCategoryId)
        it.putExtra("gameType", gameType)
        it.putExtra("gameName", gameName)
    }
}

fun Context.createAccessActivityIntent(): Intent {
    return Intent().also {
        it.action = Intent.ACTION_VIEW
        it.setClassName(this, ACCESS_ACTIVITY_NAME)
    }
}

fun FragmentActivity.createRoadFragment(
        categoryId: Long,
        gameId: Long,
        gameTypeId: Long
): Fragment {
    return instantiate(ROAD_FRAGMENT_NAME).apply {
        arguments = Bundle().apply {
            putLong("categoryId", categoryId)
            putLong("gameId", gameId)
            putLong("gameTypeId", gameTypeId)
        }
    }
}