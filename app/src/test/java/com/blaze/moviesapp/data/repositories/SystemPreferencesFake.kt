package com.blaze.moviesapp.data.repositories

import com.blaze.moviesapp.data.local.ISystemPreferences

class SystemPreferencesFake: ISystemPreferences {

    private val map = mutableMapOf<String, String>()

    override fun getValue(key: String): String {
        return map[key] ?: ""
    }

    override fun putValue(key: String, value: String) {
        map[key] = value
    }
}