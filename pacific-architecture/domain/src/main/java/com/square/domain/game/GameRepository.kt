package com.square.domain.game

import com.square.guava.domain.Source

interface GameRepository {

    suspend fun getDownTimer(): Source<DownTimer>

    suspend fun getStatus(): Source<Status>
}