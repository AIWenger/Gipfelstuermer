package com.gipfelstuermer

import android.app.Application
import android.content.Context
import com.gipfelstuermer.data.local.AppDatabase
import com.gipfelstuermer.util.LanguageManager

class GipfelStuermerApp : Application() {

    val database: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LanguageManager.applyLocale(base))
    }

    override fun onCreate() {
        super.onCreate()
        // Trigger database creation to populate data
        database
    }

    fun updateLocale(language: String) {
        LanguageManager.setLanguage(this, language)
        LanguageManager.updateApplicationResources(this, language)
    }
}
