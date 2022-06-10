package com.faa1192.weatherforecast.countries

import android.content.Context
import com.faa1192.weatherforecast.R

class Country(private val context: Context) {
    private val map: MutableMap<String, Int> = HashMap()
    fun getName(s: String): String {
        val i = map[s] ?: return s
        return context.resources.getString(i)
    }

    init {
        map["RU"] = R.string.RU
        map["UA"] = R.string.UA
    }
}