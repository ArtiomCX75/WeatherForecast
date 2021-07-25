package com.faa1192.weatherforecast.cities

import android.os.Bundle
import com.faa1192.weatherforecast.weather.WeatherData
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener

// класс объекта "Город". Содержит ид города (в базе, на сервере), название, и объект с информаций о погоде в городе
class City {
    @JvmField
    var id = -1

    @JvmField
    var name = ""

    @JvmField
    var country: String? = ""

    @JvmField
    var lon: String? = ""

    @JvmField
    var lat: String? = ""

    @JvmField
    var data = WeatherData()

    internal constructor(id: Int, name: String, country: String?, lon: String?, lat: String?) {
        this.id = id
        this.name = name
        this.country = country
        this.lon = lon
        this.lat = lat
    }

    constructor(
        id: Int,
        name: String?,
        country: String?,
        lon: String?,
        lat: String?,
        data: WeatherData
    ) : this(id, name!!, country, lon, lat) {
        this.data = data
    }

    internal constructor(json: String?) {
        val jt = JSONTokener(json)
        var jo: JSONObject? = null
        try {
            jo = JSONObject(jt)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        try {
            id = jo!!.getInt("_id")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        try {
            name = jo!!.getString("name")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        try {
            country = jo!!.getString("country")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        try {
            jo = jo!!.getJSONObject("coord")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        try {
            lon = jo!!.getString("lon")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        try {
            lat = jo!!.getString("lat")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    override fun toString(): String {
        return name
    }

    fun toBundle(): Bundle {
        val bundle = Bundle()
        bundle.putInt("id", id)
        bundle.putString("name", name)
        bundle.putString("country", country)
        bundle.putString("lon", lon)
        bundle.putString("lat", lat)
        bundle.putString("data", data.jsonString)
        return bundle
    }

    val shortName: String
        get() = if (name.contains("(")) {
            name.substring(0, name.indexOf("("))
        } else name
    val extraName: String
        get() = if (shortName.length == name.length) "" else name.substring(
            shortName.length + 1,
            name.length - 1
        )

    companion object {
        @JvmStatic
        fun fromBundle(bundle: Bundle): City {
            return City(
                bundle.getInt("id"),
                bundle.getString("name"),
                bundle.getString("country"),
                bundle.getString("lon"),
                bundle.getString("lat"),
                WeatherData(bundle.getString("data") ?: "")
            )
        }
    }
}