package com.gipfelstuermer.util

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object LanguageManager {
    private const val PREFS_NAME = "language_prefs"
    private const val KEY_LANGUAGE = "language"
    const val GERMAN = "de"
    const val ENGLISH = "en"

    fun getLanguage(context: Context): String {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_LANGUAGE, GERMAN) ?: GERMAN
    }

    fun setLanguage(context: Context, language: String) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_LANGUAGE, language)
            .apply()
    }

    fun applyLocale(context: Context): Context {
        val language = getLanguage(context)
        val locale = Locale.forLanguageTag(language)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }

    @Suppress("DEPRECATION")
    fun updateApplicationResources(context: Context, language: String) {
        val locale = Locale.forLanguageTag(language)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}
