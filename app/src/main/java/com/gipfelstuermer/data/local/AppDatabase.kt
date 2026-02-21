package com.gipfelstuermer.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [GameEntity::class], version = 2, exportSchema = false)
@TypeConverters(EnumConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gipfelstuermer_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(DatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    populateDatabase(database.gameDao())
                }
            }
        }

        override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
            super.onDestructiveMigration(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    populateDatabase(database.gameDao())
                }
            }
        }

        suspend fun populateDatabase(gameDao: GameDao) {
            val games = listOf(
                // --- Bestehende 30 Spiele (mit ageGroup + gameType) ---
                GameEntity(nameKey = "game_1_name", descriptionKey = "game_1_desc", rulesKey = "game_1_rules", difficulty = Difficulty.MEDIUM, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.MITTEL, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_2_name", descriptionKey = "game_2_desc", rulesKey = "game_2_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.KLEIN, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_3_name", descriptionKey = "game_3_desc", rulesKey = "game_3_rules", difficulty = Difficulty.MEDIUM, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.MITTEL, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_4_name", descriptionKey = "game_4_desc", rulesKey = "game_4_rules", difficulty = Difficulty.HARD, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.GROSS, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_5_name", descriptionKey = "game_5_desc", rulesKey = "game_5_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.KLEIN, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_6_name", descriptionKey = "game_6_desc", rulesKey = "game_6_rules", difficulty = Difficulty.MEDIUM, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.MITTEL, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_7_name", descriptionKey = "game_7_desc", rulesKey = "game_7_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.KLEIN, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_8_name", descriptionKey = "game_8_desc", rulesKey = "game_8_rules", difficulty = Difficulty.HARD, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.KLEIN, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_9_name", descriptionKey = "game_9_desc", rulesKey = "game_9_rules", difficulty = Difficulty.MEDIUM, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.GROSS, gameType = GameType.KRAFT),
                GameEntity(nameKey = "game_10_name", descriptionKey = "game_10_desc", rulesKey = "game_10_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.KLEIN, gameType = GameType.AUFWAERMEN),
                GameEntity(nameKey = "game_11_name", descriptionKey = "game_11_desc", rulesKey = "game_11_rules", difficulty = Difficulty.MEDIUM, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.KLEIN, gameType = GameType.AUFWAERMEN),
                GameEntity(nameKey = "game_12_name", descriptionKey = "game_12_desc", rulesKey = "game_12_rules", difficulty = Difficulty.HARD, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.MITTEL, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_13_name", descriptionKey = "game_13_desc", rulesKey = "game_13_rules", difficulty = Difficulty.EASY, locationIndoor = false, locationOutdoor = true, ageGroup = AgeGroup.MITTEL, gameType = GameType.AUFWAERMEN),
                GameEntity(nameKey = "game_14_name", descriptionKey = "game_14_desc", rulesKey = "game_14_rules", difficulty = Difficulty.MEDIUM, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.MITTEL, gameType = GameType.AUFWAERMEN),
                GameEntity(nameKey = "game_15_name", descriptionKey = "game_15_desc", rulesKey = "game_15_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.MITTEL, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_16_name", descriptionKey = "game_16_desc", rulesKey = "game_16_rules", difficulty = Difficulty.HARD, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.MITTEL, gameType = GameType.AUFWAERMEN),
                GameEntity(nameKey = "game_17_name", descriptionKey = "game_17_desc", rulesKey = "game_17_rules", difficulty = Difficulty.MEDIUM, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.MITTEL, gameType = GameType.AUFWAERMEN),
                GameEntity(nameKey = "game_18_name", descriptionKey = "game_18_desc", rulesKey = "game_18_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.GROSS, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_19_name", descriptionKey = "game_19_desc", rulesKey = "game_19_rules", difficulty = Difficulty.MEDIUM, locationIndoor = false, locationOutdoor = true, ageGroup = AgeGroup.KLEIN, gameType = GameType.AUFWAERMEN),
                GameEntity(nameKey = "game_20_name", descriptionKey = "game_20_desc", rulesKey = "game_20_rules", difficulty = Difficulty.HARD, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.MITTEL, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_21_name", descriptionKey = "game_21_desc", rulesKey = "game_21_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.KLEIN, gameType = GameType.AUFWAERMEN),
                GameEntity(nameKey = "game_22_name", descriptionKey = "game_22_desc", rulesKey = "game_22_rules", difficulty = Difficulty.MEDIUM, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.KLEIN, gameType = GameType.AUFWAERMEN),
                GameEntity(nameKey = "game_23_name", descriptionKey = "game_23_desc", rulesKey = "game_23_rules", difficulty = Difficulty.HARD, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.GROSS, gameType = GameType.KRAFT),
                GameEntity(nameKey = "game_24_name", descriptionKey = "game_24_desc", rulesKey = "game_24_rules", difficulty = Difficulty.EASY, locationIndoor = false, locationOutdoor = true, ageGroup = AgeGroup.MITTEL, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_25_name", descriptionKey = "game_25_desc", rulesKey = "game_25_rules", difficulty = Difficulty.MEDIUM, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.GROSS, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_26_name", descriptionKey = "game_26_desc", rulesKey = "game_26_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.GROSS, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_27_name", descriptionKey = "game_27_desc", rulesKey = "game_27_rules", difficulty = Difficulty.HARD, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.MITTEL, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_28_name", descriptionKey = "game_28_desc", rulesKey = "game_28_rules", difficulty = Difficulty.MEDIUM, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.GROSS, gameType = GameType.AUFWAERMEN),
                GameEntity(nameKey = "game_29_name", descriptionKey = "game_29_desc", rulesKey = "game_29_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.GROSS, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_30_name", descriptionKey = "game_30_desc", rulesKey = "game_30_rules", difficulty = Difficulty.MEDIUM, locationIndoor = false, locationOutdoor = true, ageGroup = AgeGroup.GROSS, gameType = GameType.KLETTERN),

                // --- Neue Spiele: KLEIN + KLETTERN (31–38) ---
                GameEntity(nameKey = "game_31_name", descriptionKey = "game_31_desc", rulesKey = "game_31_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.KLEIN, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_32_name", descriptionKey = "game_32_desc", rulesKey = "game_32_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.KLEIN, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_33_name", descriptionKey = "game_33_desc", rulesKey = "game_33_rules", difficulty = Difficulty.MEDIUM, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.KLEIN, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_34_name", descriptionKey = "game_34_desc", rulesKey = "game_34_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.KLEIN, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_35_name", descriptionKey = "game_35_desc", rulesKey = "game_35_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.KLEIN, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_36_name", descriptionKey = "game_36_desc", rulesKey = "game_36_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.KLEIN, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_37_name", descriptionKey = "game_37_desc", rulesKey = "game_37_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.KLEIN, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_38_name", descriptionKey = "game_38_desc", rulesKey = "game_38_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.KLEIN, gameType = GameType.KLETTERN),

                // --- Neue Spiele: KLEIN + AUFWAERMEN (39–45) ---
                GameEntity(nameKey = "game_39_name", descriptionKey = "game_39_desc", rulesKey = "game_39_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.KLEIN, gameType = GameType.AUFWAERMEN),
                GameEntity(nameKey = "game_40_name", descriptionKey = "game_40_desc", rulesKey = "game_40_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.KLEIN, gameType = GameType.AUFWAERMEN),
                GameEntity(nameKey = "game_41_name", descriptionKey = "game_41_desc", rulesKey = "game_41_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.KLEIN, gameType = GameType.AUFWAERMEN),
                GameEntity(nameKey = "game_42_name", descriptionKey = "game_42_desc", rulesKey = "game_42_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.KLEIN, gameType = GameType.AUFWAERMEN),
                GameEntity(nameKey = "game_43_name", descriptionKey = "game_43_desc", rulesKey = "game_43_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.KLEIN, gameType = GameType.AUFWAERMEN),
                GameEntity(nameKey = "game_44_name", descriptionKey = "game_44_desc", rulesKey = "game_44_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.KLEIN, gameType = GameType.AUFWAERMEN),
                GameEntity(nameKey = "game_45_name", descriptionKey = "game_45_desc", rulesKey = "game_45_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.KLEIN, gameType = GameType.AUFWAERMEN),

                // --- Neue Spiele: KLEIN + KRAFT (46–50) ---
                GameEntity(nameKey = "game_46_name", descriptionKey = "game_46_desc", rulesKey = "game_46_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.KLEIN, gameType = GameType.KRAFT),
                GameEntity(nameKey = "game_47_name", descriptionKey = "game_47_desc", rulesKey = "game_47_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.KLEIN, gameType = GameType.KRAFT),
                GameEntity(nameKey = "game_48_name", descriptionKey = "game_48_desc", rulesKey = "game_48_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.KLEIN, gameType = GameType.KRAFT),
                GameEntity(nameKey = "game_49_name", descriptionKey = "game_49_desc", rulesKey = "game_49_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.KLEIN, gameType = GameType.KRAFT),
                GameEntity(nameKey = "game_50_name", descriptionKey = "game_50_desc", rulesKey = "game_50_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.KLEIN, gameType = GameType.KRAFT),

                // --- Neue Spiele: MITTEL + KLETTERN (51–60) ---
                GameEntity(nameKey = "game_51_name", descriptionKey = "game_51_desc", rulesKey = "game_51_rules", difficulty = Difficulty.MEDIUM, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.MITTEL, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_52_name", descriptionKey = "game_52_desc", rulesKey = "game_52_rules", difficulty = Difficulty.MEDIUM, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.MITTEL, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_53_name", descriptionKey = "game_53_desc", rulesKey = "game_53_rules", difficulty = Difficulty.MEDIUM, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.MITTEL, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_54_name", descriptionKey = "game_54_desc", rulesKey = "game_54_rules", difficulty = Difficulty.MEDIUM, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.MITTEL, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_55_name", descriptionKey = "game_55_desc", rulesKey = "game_55_rules", difficulty = Difficulty.MEDIUM, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.MITTEL, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_56_name", descriptionKey = "game_56_desc", rulesKey = "game_56_rules", difficulty = Difficulty.HARD, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.MITTEL, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_57_name", descriptionKey = "game_57_desc", rulesKey = "game_57_rules", difficulty = Difficulty.MEDIUM, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.MITTEL, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_58_name", descriptionKey = "game_58_desc", rulesKey = "game_58_rules", difficulty = Difficulty.MEDIUM, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.MITTEL, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_59_name", descriptionKey = "game_59_desc", rulesKey = "game_59_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.MITTEL, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_60_name", descriptionKey = "game_60_desc", rulesKey = "game_60_rules", difficulty = Difficulty.MEDIUM, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.MITTEL, gameType = GameType.KLETTERN),

                // --- Neue Spiele: MITTEL + AUFWAERMEN (61–67) ---
                GameEntity(nameKey = "game_61_name", descriptionKey = "game_61_desc", rulesKey = "game_61_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.MITTEL, gameType = GameType.AUFWAERMEN),
                GameEntity(nameKey = "game_62_name", descriptionKey = "game_62_desc", rulesKey = "game_62_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.MITTEL, gameType = GameType.AUFWAERMEN),
                GameEntity(nameKey = "game_63_name", descriptionKey = "game_63_desc", rulesKey = "game_63_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.MITTEL, gameType = GameType.AUFWAERMEN),
                GameEntity(nameKey = "game_64_name", descriptionKey = "game_64_desc", rulesKey = "game_64_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.MITTEL, gameType = GameType.AUFWAERMEN),
                GameEntity(nameKey = "game_65_name", descriptionKey = "game_65_desc", rulesKey = "game_65_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.MITTEL, gameType = GameType.AUFWAERMEN),
                GameEntity(nameKey = "game_66_name", descriptionKey = "game_66_desc", rulesKey = "game_66_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.MITTEL, gameType = GameType.AUFWAERMEN),
                GameEntity(nameKey = "game_67_name", descriptionKey = "game_67_desc", rulesKey = "game_67_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.MITTEL, gameType = GameType.AUFWAERMEN),

                // --- Neue Spiele: MITTEL + KRAFT (68–73) ---
                GameEntity(nameKey = "game_68_name", descriptionKey = "game_68_desc", rulesKey = "game_68_rules", difficulty = Difficulty.MEDIUM, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.MITTEL, gameType = GameType.KRAFT),
                GameEntity(nameKey = "game_69_name", descriptionKey = "game_69_desc", rulesKey = "game_69_rules", difficulty = Difficulty.MEDIUM, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.MITTEL, gameType = GameType.KRAFT),
                GameEntity(nameKey = "game_70_name", descriptionKey = "game_70_desc", rulesKey = "game_70_rules", difficulty = Difficulty.MEDIUM, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.MITTEL, gameType = GameType.KRAFT),
                GameEntity(nameKey = "game_71_name", descriptionKey = "game_71_desc", rulesKey = "game_71_rules", difficulty = Difficulty.MEDIUM, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.MITTEL, gameType = GameType.KRAFT),
                GameEntity(nameKey = "game_72_name", descriptionKey = "game_72_desc", rulesKey = "game_72_rules", difficulty = Difficulty.MEDIUM, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.MITTEL, gameType = GameType.KRAFT),
                GameEntity(nameKey = "game_73_name", descriptionKey = "game_73_desc", rulesKey = "game_73_rules", difficulty = Difficulty.HARD, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.MITTEL, gameType = GameType.KRAFT),

                // --- Neue Spiele: GROSS + KLETTERN (74–83) ---
                GameEntity(nameKey = "game_74_name", descriptionKey = "game_74_desc", rulesKey = "game_74_rules", difficulty = Difficulty.HARD, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.GROSS, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_75_name", descriptionKey = "game_75_desc", rulesKey = "game_75_rules", difficulty = Difficulty.HARD, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.GROSS, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_76_name", descriptionKey = "game_76_desc", rulesKey = "game_76_rules", difficulty = Difficulty.HARD, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.GROSS, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_77_name", descriptionKey = "game_77_desc", rulesKey = "game_77_rules", difficulty = Difficulty.MEDIUM, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.GROSS, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_78_name", descriptionKey = "game_78_desc", rulesKey = "game_78_rules", difficulty = Difficulty.HARD, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.GROSS, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_79_name", descriptionKey = "game_79_desc", rulesKey = "game_79_rules", difficulty = Difficulty.HARD, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.GROSS, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_80_name", descriptionKey = "game_80_desc", rulesKey = "game_80_rules", difficulty = Difficulty.HARD, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.GROSS, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_81_name", descriptionKey = "game_81_desc", rulesKey = "game_81_rules", difficulty = Difficulty.MEDIUM, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.GROSS, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_82_name", descriptionKey = "game_82_desc", rulesKey = "game_82_rules", difficulty = Difficulty.MEDIUM, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.GROSS, gameType = GameType.KLETTERN),
                GameEntity(nameKey = "game_83_name", descriptionKey = "game_83_desc", rulesKey = "game_83_rules", difficulty = Difficulty.HARD, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.GROSS, gameType = GameType.KLETTERN),

                // --- Neue Spiele: GROSS + AUFWAERMEN (84–88) ---
                GameEntity(nameKey = "game_84_name", descriptionKey = "game_84_desc", rulesKey = "game_84_rules", difficulty = Difficulty.HARD, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.GROSS, gameType = GameType.AUFWAERMEN),
                GameEntity(nameKey = "game_85_name", descriptionKey = "game_85_desc", rulesKey = "game_85_rules", difficulty = Difficulty.EASY, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.GROSS, gameType = GameType.AUFWAERMEN),
                GameEntity(nameKey = "game_86_name", descriptionKey = "game_86_desc", rulesKey = "game_86_rules", difficulty = Difficulty.MEDIUM, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.GROSS, gameType = GameType.AUFWAERMEN),
                GameEntity(nameKey = "game_87_name", descriptionKey = "game_87_desc", rulesKey = "game_87_rules", difficulty = Difficulty.MEDIUM, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.GROSS, gameType = GameType.AUFWAERMEN),
                GameEntity(nameKey = "game_88_name", descriptionKey = "game_88_desc", rulesKey = "game_88_rules", difficulty = Difficulty.MEDIUM, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.GROSS, gameType = GameType.AUFWAERMEN),

                // --- Neue Spiele: GROSS + KRAFT (89–100) ---
                GameEntity(nameKey = "game_89_name", descriptionKey = "game_89_desc", rulesKey = "game_89_rules", difficulty = Difficulty.HARD, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.GROSS, gameType = GameType.KRAFT),
                GameEntity(nameKey = "game_90_name", descriptionKey = "game_90_desc", rulesKey = "game_90_rules", difficulty = Difficulty.HARD, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.GROSS, gameType = GameType.KRAFT),
                GameEntity(nameKey = "game_91_name", descriptionKey = "game_91_desc", rulesKey = "game_91_rules", difficulty = Difficulty.HARD, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.GROSS, gameType = GameType.KRAFT),
                GameEntity(nameKey = "game_92_name", descriptionKey = "game_92_desc", rulesKey = "game_92_rules", difficulty = Difficulty.HARD, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.GROSS, gameType = GameType.KRAFT),
                GameEntity(nameKey = "game_93_name", descriptionKey = "game_93_desc", rulesKey = "game_93_rules", difficulty = Difficulty.HARD, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.GROSS, gameType = GameType.KRAFT),
                GameEntity(nameKey = "game_94_name", descriptionKey = "game_94_desc", rulesKey = "game_94_rules", difficulty = Difficulty.HARD, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.GROSS, gameType = GameType.KRAFT),
                GameEntity(nameKey = "game_95_name", descriptionKey = "game_95_desc", rulesKey = "game_95_rules", difficulty = Difficulty.MEDIUM, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.GROSS, gameType = GameType.KRAFT),
                GameEntity(nameKey = "game_96_name", descriptionKey = "game_96_desc", rulesKey = "game_96_rules", difficulty = Difficulty.HARD, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.GROSS, gameType = GameType.KRAFT),
                GameEntity(nameKey = "game_97_name", descriptionKey = "game_97_desc", rulesKey = "game_97_rules", difficulty = Difficulty.MEDIUM, locationIndoor = true, locationOutdoor = true, ageGroup = AgeGroup.GROSS, gameType = GameType.KRAFT),
                GameEntity(nameKey = "game_98_name", descriptionKey = "game_98_desc", rulesKey = "game_98_rules", difficulty = Difficulty.HARD, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.GROSS, gameType = GameType.KRAFT),
                GameEntity(nameKey = "game_99_name", descriptionKey = "game_99_desc", rulesKey = "game_99_rules", difficulty = Difficulty.HARD, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.GROSS, gameType = GameType.KRAFT),
                GameEntity(nameKey = "game_100_name", descriptionKey = "game_100_desc", rulesKey = "game_100_rules", difficulty = Difficulty.HARD, locationIndoor = true, locationOutdoor = false, ageGroup = AgeGroup.GROSS, gameType = GameType.KRAFT),
            )
            gameDao.insertAll(games)
        }
    }
}
