package com.gipfelstuermer.data.repository

import com.gipfelstuermer.data.local.GameDao
import com.gipfelstuermer.data.local.GameEntity
import kotlinx.coroutines.flow.Flow

class GameRepository(private val gameDao: GameDao) {

    fun getAllGames(): Flow<List<GameEntity>> = gameDao.getAllGames()

    fun getIndoorGames(): Flow<List<GameEntity>> = gameDao.getIndoorGames()

    fun getOutdoorGames(): Flow<List<GameEntity>> = gameDao.getOutdoorGames()

    fun getGameById(id: Int): Flow<GameEntity?> = gameDao.getGameById(id)

    suspend fun toggleFavorite(gameId: Int, isFavorite: Boolean) {
        gameDao.updateFavorite(gameId, isFavorite)
    }
}
