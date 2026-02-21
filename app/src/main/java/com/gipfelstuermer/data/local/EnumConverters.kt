package com.gipfelstuermer.data.local

import androidx.room.TypeConverter

class EnumConverters {

    @TypeConverter
    fun toDifficulty(value: String): Difficulty = Difficulty.valueOf(value)

    @TypeConverter
    fun fromDifficulty(difficulty: Difficulty): String = difficulty.name

    @TypeConverter
    fun toAgeGroup(value: String): AgeGroup = AgeGroup.valueOf(value)

    @TypeConverter
    fun fromAgeGroup(ageGroup: AgeGroup): String = ageGroup.name

    @TypeConverter
    fun toGameType(value: String): GameType = GameType.valueOf(value)

    @TypeConverter
    fun fromGameType(gameType: GameType): String = gameType.name
}
