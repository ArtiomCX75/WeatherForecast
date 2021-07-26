package com.faa1192.weatherforecast.weather

import android.content.Context
import android.util.Log
import com.faa1192.weatherforecast.R
import com.faa1192.weatherforecast.preferred.PrefCitiesActivity
import org.json.JSONObject
import org.json.JSONTokener
import java.util.*

//класс объекта "Погодные данные". Служит для десериализации json в java object
class WeatherData {
    var jsonString = ""
        get() = if (field.isEmpty()) "" else field
    var cityName = "" //название города ПРИХОДЯЩЕЕ С СЕРВЕРА
    var humidity = "" //влажность
        get() = if (field.isEmpty()) hm[noData]!! else "$field%"
    var pressure = "" //давление
        get() = if (field.isEmpty()) hm[noData]!! else (java.lang.Double.valueOf(field) * 0.7500637554192).toString()
            .substring(0, 6) + context!!.resources.getString(R.string.mm)
    var temp = "" //температура
        get() = if (field.isEmpty()) hm[noData]!! else "$field°C"
    var sunrise = "" //восход
        get() = stringToTime(field)
    var sunset = "" //закат
        get() = stringToTime(field)
    var windSpeed = "" //скорость ветра
        get() = if (field.isEmpty()) hm[noData]!! else field + context!!.resources.getString(R.string.m_per_sec)
    var windDeg = "" //направление ветра
        get() {
            return if (field.isEmpty()) hm[noData]!! else {
                val response: String
                var angle = java.lang.Double.valueOf(field)
                angle += 22.5
                val side = angle.toInt() / 45
                response = when (side) {
                    0 -> context!!.resources.getString(R.string.N)
                    1 -> context!!.resources.getString(R.string.NE)
                    2 -> context!!.resources.getString(R.string.E)
                    3 -> context!!.resources.getString(R.string.SE)
                    4 -> context!!.resources.getString(R.string.S)
                    5 -> context!!.resources.getString(R.string.SW)
                    6 -> context!!.resources.getString(R.string.W)
                    7 -> context!!.resources.getString(R.string.NW)
                    else -> context!!.resources.getString(R.string.N)
                }
                response
            }
        }
    var weatherDescription = "" //описание погоды
        get() {
            if (field.isEmpty()) field = noData
            field = field.replace(" ", "_").lowercase(Locale.ROOT)
            return if (hm[field] == null || hm[field]!!
                    .isEmpty()
            ) field else hm[field]!!
        }
    var weatherMain = "" //краткое описание погоды
        get() {
            if (field.isEmpty()) field = noData
            field = field.replace(" ", "_").lowercase(Locale.getDefault())
            return if (hm[field] == null || hm[field]!!
                    .isEmpty()
            ) field else hm[field]!!
        }
    private var time = 0L //актуальное время сведений о погоде (приходит с сервера)
    val noData = "no_data"

    companion object {
        private val hm = HashMap<String, String?>()
        var context: Context? = null

        init {
            context = PrefCitiesActivity.context
            val key = context!!.resources.getStringArray(R.array.wd_k)
            val value = context!!.resources.getStringArray(R.array.wd_v)
            for (i in key.indices) {
                hm[key[i]] =
                    value[i]
            }
        }
    }

    constructor()
    constructor(jsonString: String) {
        this.jsonString = jsonString
        val qwe = JSONTokener(jsonString)
        try {
            val jsonTokener = JSONTokener(jsonString)
            val baseJsonObject = JSONObject(jsonTokener)
            try {
                try {
                    cityName = baseJsonObject.getString("name")
                } catch (e: Exception) {
                }
                var mainJsonObject = JSONObject()
                try {
                    mainJsonObject = baseJsonObject.getJSONObject("main")
                } catch (e: Exception) {
                }
                try {
                    humidity = mainJsonObject.getString("humidity")
                } catch (e: Exception) {
                }
                try {
                    pressure = mainJsonObject.getString("pressure")
                } catch (e: Exception) {
                }
                try {
                    temp = mainJsonObject.getString("temp")
                } catch (e: Exception) {
                }
                var sysJsonObject = JSONObject()
                try {
                    sysJsonObject = baseJsonObject.getJSONObject("sys")
                } catch (e: Exception) {
                }
                try {
                    sunrise = sysJsonObject.getString("sunrise")
                } catch (e: Exception) {
                }
                try {
                    sunset = sysJsonObject.getString("sunset")
                } catch (e: Exception) {
                }
                var windJsonObject = JSONObject()
                try {
                    windJsonObject = baseJsonObject.getJSONObject("wind")
                } catch (e: Exception) {
                }
                try {
                    windSpeed = windJsonObject.getString("speed")
                } catch (e: Exception) {
                }
                try {
                    windDeg = windJsonObject.getString("deg")
                } catch (e: Exception) {
                }
                var weatherJsonObject = JSONObject()
                try {
                    weatherJsonObject = baseJsonObject.getJSONArray("weather").getJSONObject(0)
                } catch (e: Exception) {
                }
                try {
                    weatherDescription = weatherJsonObject.getString("description")
                } catch (e: Exception) {
                }
                try {
                    weatherMain = weatherJsonObject.getString("main")
                } catch (e: Exception) {
                }
                try {
                    time = baseJsonObject.getLong("dt")
                } catch (e: Exception) {
                }
            } catch (e: Exception) {
                Log.e("my", "Данные о погоде некорректные или получены частично")
                throw e
            }
        } catch (e: Exception) {
            Log.e("my", "Данные о погоде некорректные")
            var i = 0
            while (i < e.stackTrace.size) {
                Log.e("my", e.stackTrace[i].toString())
                i++
            }
            Log.e("my", "======================================")
            Log.e("my", "start" + jsonString + "stop")
            Log.e("my", "start1" + qwe + "stop2")
        }
    }

    //for debug
    override fun toString(): String {
        return ("cityName " + cityName + "  humidity " + humidity
                + "  pressure " + pressure + "  temp " + temp
                + "  sunrise " + sunrise + "  sunset " + sunset
                + "  windSpeed " + windSpeed + "  windDeg "
                + windDeg + "  weatherDescription " + weatherDescription
                + "  weatherMain " + weatherMain)
    }


    //Данные считаются старыми, если им больше часа. Сравнение со временем приходящим с сервера, а не со временем фактического получения данных
    val isActualData: Boolean
        get() {
            val curenttime = Date().time / 1000
            //Log.e("my", "TIME:" + (curenttime - time) + ""); //fordebug
            return curenttime - time < 3600
        }

    //Преобразование строки со временем в читабельный вид с поправкой на часовой пояс +3
    private fun stringToTime(timeString: String): String {
        if (temp == noData) return hm[noData]!!
        val timeInt = Integer.valueOf("0$timeString")
        val hour = timeInt / 60 / 60 % 24
        val min = timeInt / 60 % 60
        val sec = timeInt % 60
        val timeZone = +3
        return ((hour + timeZone) % 24).toString() + context!!.resources.getString(R.string.hour) + " " + min + context!!.resources.getString(
            R.string.minute
        ) + " " + sec + context!!.resources.getString(R.string.second)
    }

    fun getTime() = stringToTime(time.toString()) + ""
}