package com.application.game.NumberMergeApp.data


import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(
    name = "number_merge_preferences"
)

@Singleton
class ScoreRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private companion object {
        val BEST_SCORE = intPreferencesKey("best_score")
    }

    val bestScore: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[BEST_SCORE] ?: 0
    }

    suspend fun saveBestScore(score: Int) {
        context.dataStore.edit { preferences ->
            val previous = preferences[BEST_SCORE] ?: 0
            if (score > previous) {
                preferences[BEST_SCORE] = score
            }
        }
    }
}