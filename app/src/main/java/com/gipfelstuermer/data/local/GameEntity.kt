package com.gipfelstuermer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class Difficulty {
    EASY, MEDIUM, HARD
}

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nameKey: String,
    val descriptionKey: String,
    val rulesKey: String? = null,
    val difficulty: Difficulty,
    val locationIndoor: Boolean,
    val locationOutdoor: Boolean,
    val ageGroup: AgeGroup = AgeGroup.MITTEL,
    val gameType: GameType = GameType.KLETTERN,
    val isFavorite: Boolean = false
)
