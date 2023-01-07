package com.blaze.moviesapp.data.local

import android.content.SharedPreferences

interface ISystemPreferences {
    fun getValue(key: String): String

    fun putValue(key: String, value: String)
}

class SystemPreferences(
    private val sharedPreferences: SharedPreferences
) : ISystemPreferences {

    override fun getValue(key: String): String {
        return sharedPreferences.getString(key, "") ?: ""
    }

    override fun putValue(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

}