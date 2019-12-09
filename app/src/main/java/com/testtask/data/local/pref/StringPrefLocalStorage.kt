package com.testtask.data.local.pref

import android.content.Context
import com.testtask.data.local.LocalStorage

class StringPrefLocalStorage(private val appContext: Context) : LocalStorage<String> {

    private val prefName = "${appContext.packageName}.prefs"

    override fun save(key: String, value: String) {
        appContext.getSharedPreferences(prefName, MODE)
            .edit().putString("$prefName.$key", value).apply()

    }

    override fun get(key: String) =
        appContext.getSharedPreferences(prefName, MODE)
            .getString("$prefName.$key",null)

    companion object{
        private const val MODE = Context.MODE_PRIVATE
    }
}