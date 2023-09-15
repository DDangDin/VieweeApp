package com.capstone.vieweeapp.utils

import android.content.Context
import android.content.SharedPreferences

class CustomSharedPreference(context: Context) {
    private val prefsName = "user_info"
    private val prefs: SharedPreferences = context.getSharedPreferences(prefsName,
        Context.MODE_PRIVATE
    )

    fun setUserPrefs(key: String, value: String) {
        prefs
            .edit()
            .putString(key, value)
            .apply()
    }

    fun getUserPrefs(key: String): String = prefs.getString(key, "") ?: ""

    fun deleteUserPrefs(key: String) {
        prefs
            .edit()
            .remove(key)
            .apply()
    }

    fun deleteAllUserPrefs() {
        prefs
            .edit()
            .clear()
            .apply()
    }

    fun isContain(key: String) = prefs.contains(key)
}