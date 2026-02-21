package com.gipfelstuermer.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    @Query("SELECT * FROM games ORDER BY id ASC")
    fun getAllGames(): Flow<List<GameEntity>>

    @Query("SELECT * FROM games WHERE locationIndoor = 1 ORDER BY id ASC")
    fun getIndoorGames(): Flow<List<GameEntity>>

    @Query("SELECT * FROM games WHERE locationOutdoor = 1 ORDER BY id ASC")
    fun getOutdoorGames(): Flow<List<GameEntity>>

    @Query("SELECT * FROM games WHERE id = :id")
    fun getGameById(id: Int): Flow<GameEntity?>

    @Query("UPDATE games SET isFavorite = :isFavorite WHERE id = :gameId")
    suspend fun updateFavorite(gameId: Int, isFavorite: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(games: List<GameEntity>)

    @Query("SELECT COUNT(*) FROM games")
    suspend fun getCount(): Int
}
