package com.square.domain.game

import com.square.guava.domain.Source
import javax.inject.Inject

class GetStatus @Inject constructor(
    private val gameRepository: GameRepository
) {

    suspend operator fun invoke(): Source<Status> {
        return gameRepository.getStatus()
    }
}