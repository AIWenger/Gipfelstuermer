package com.gipfelstuermer

import android.app.Application
import com.gipfelstuermer.data.local.AppDatabase

class GipfelStuermerApp : Application() {

    val database: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }

    override fun onCreate() {
        super.onCreate()
        // Trigger database creation to populate data
        database
    }
}
