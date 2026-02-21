package com.gipfelstuermer.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.gipfelstuermer.data.local.AgeGroup
import com.gipfelstuermer.data.local.AppDatabase
import com.gipfelstuermer.data.local.Difficulty
import com.gipfelstuermer.data.local.GameEntity
import com.gipfelstuermer.data.local.GameType
import com.gipfelstuermer.data.repository.GameRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class LocationFilter {
    ALL, INDOOR, OUTDOOR
}

enum class AgeFilter {
    ALL_AGES, KLEIN, MITTEL, GROSS
}

enum class TypeFilter {
    ALL_TYPES, KLETTERN, AUFWAERMEN, KRAFT
}

data class GameUiState(
    val games: List<GameEntity> = emptyList(),
    val filteredGames: List<GameEntity> = emptyList(),
    val selectedFilter: LocationFilter = LocationFilter.ALL,
    val ageFilter: AgeFilter = AgeFilter.ALL_AGES,
    val typeFilter: TypeFilter = TypeFilter.ALL_TYPES,
    val searchQuery: String = "",
    val timerMillis: Long = 0L,
    val timerRunning: Boolean = false
)

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GameRepository
    private val context: Context = application.applicationContext

    private val _selectedFilter = MutableStateFlow(LocationFilter.ALL)
    private val _ageFilter = MutableStateFlow(AgeFilter.ALL_AGES)
    private val _typeFilter = MutableStateFlow(TypeFilter.ALL_TYPES)
    private val _searchQuery = MutableStateFlow("")
    private val _timerMillis = MutableStateFlow(0L)
    private val _timerRunning = MutableStateFlow(false)

    val selectedFilter: StateFlow<LocationFilter> = _selectedFilter.asStateFlow()
    val ageFilter: StateFlow<AgeFilter> = _ageFilter.asStateFlow()
    val typeFilter: StateFlow<TypeFilter> = _typeFilter.asStateFlow()
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    val timerMillis: StateFlow<Long> = _timerMillis.asStateFlow()
    val timerRunning: StateFlow<Boolean> = _timerRunning.asStateFlow()

    private var timerJob: Job? = null

    val filteredGames: StateFlow<List<GameEntity>>

    init {
        val database = AppDatabase.getDatabase(application)
        repository = GameRepository(database.gameDao())

        filteredGames = combine(
            repository.getAllGames(),
            _selectedFilter,
            _ageFilter,
            _typeFilter,
            _searchQuery
        ) { allGames, locFilter, ageFilter, typeFilter, query ->
            var result = when (locFilter) {
                LocationFilter.ALL -> allGames
                LocationFilter.INDOOR -> allGames.filter { it.locationIndoor }
                LocationFilter.OUTDOOR -> allGames.filter { it.locationOutdoor }
            }
            result = when (ageFilter) {
                AgeFilter.ALL_AGES -> result
                AgeFilter.KLEIN -> result.filter { it.ageGroup == AgeGroup.KLEIN }
                AgeFilter.MITTEL -> result.filter { it.ageGroup == AgeGroup.MITTEL }
                AgeFilter.GROSS -> result.filter { it.ageGroup == AgeGroup.GROSS }
            }
            result = when (typeFilter) {
                TypeFilter.ALL_TYPES -> result
                TypeFilter.KLETTERN -> result.filter { it.gameType == GameType.KLETTERN }
                TypeFilter.AUFWAERMEN -> result.filter { it.gameType == GameType.AUFWAERMEN }
                TypeFilter.KRAFT -> result.filter { it.gameType == GameType.KRAFT }
            }
            if (query.isNotBlank()) {
                val lowerQuery = query.lowercase()
                result = result.filter { game ->
                    val name = getStringByKey(game.nameKey).lowercase()
                    val desc = getStringByKey(game.descriptionKey).lowercase()
                    val difficultyText = getDifficultyText(game.difficulty).lowercase()
                    name.contains(lowerQuery) || desc.contains(lowerQuery) || difficultyText.contains(lowerQuery)
                }
            }
            result
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }

    fun setFilter(filter: LocationFilter) {
        _selectedFilter.value = filter
    }

    fun setAgeFilter(filter: AgeFilter) {
        _ageFilter.value = filter
    }

    fun setTypeFilter(filter: TypeFilter) {
        _typeFilter.value = filter
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun toggleTimer() {
        if (_timerRunning.value) {
            pauseTimer()
        } else {
            startTimer()
        }
    }

    private fun startTimer() {
        _timerRunning.value = true
        timerJob = viewModelScope.launch {
            val startTime = System.currentTimeMillis() - _timerMillis.value
            while (_timerRunning.value) {
                _timerMillis.value = System.currentTimeMillis() - startTime
                delay(50L)
            }
        }
    }

    private fun pauseTimer() {
        _timerRunning.value = false
        timerJob?.cancel()
    }

    fun resetTimer() {
        pauseTimer()
        _timerMillis.value = 0L
    }

    fun toggleFavorite(gameId: Int, currentFavorite: Boolean) {
        viewModelScope.launch {
            repository.toggleFavorite(gameId, !currentFavorite)
        }
    }

    fun getGameById(id: Int) = repository.getGameById(id)

    fun getStringByKey(key: String): String {
        val resId = context.resources.getIdentifier(key, "string", context.packageName)
        return if (resId != 0) context.getString(resId) else key
    }

    private fun getDifficultyText(difficulty: Difficulty): String {
        val resId = when (difficulty) {
            Difficulty.EASY -> context.resources.getIdentifier("difficulty_easy", "string", context.packageName)
            Difficulty.MEDIUM -> context.resources.getIdentifier("difficulty_medium", "string", context.packageName)
            Difficulty.HARD -> context.resources.getIdentifier("difficulty_hard", "string", context.packageName)
        }
        return if (resId != 0) context.getString(resId) else difficulty.name
    }
}
