package com.sd.laborator.services

import com.sd.laborator.interfaces.WeatherForecastInterface
import com.sd.laborator.pojo.WeatherForecastData
import org.json.JSONObject
import org.springframework.stereotype.Service
import java.net.URL
import kotlin.math.roundToInt

@Service
class WeatherForecastService (private val timeService: TimeService) : WeatherForecastInterface {
    override fun getForecastData(longitude: Double, latitude: Double): WeatherForecastData {
        // ID-ul locaţiei nu trebuie codificat, deoarece este numeric
        //val forecastDataURL = URL("https://www.metaweather.com/api/location/$locationId/")
        val forecastDataURL = URL("https://api.api-ninjas.com/v1/weather?lat=$latitude&lon=$longitude&X-Api-Key=SwAaROkFAHxe9eH/K3Gvcg==8L2AWCq3nNQBfM53")

        // preluare conţinut răspuns HTTP la o cerere GET către URL-ul de mai sus
        val rawResponse: String = forecastDataURL.readText()

        // parsare obiect JSON primit
        val responseRootObject = JSONObject(rawResponse)
        //val weatherDataObject = responseRootObject.getJSONArray("consolidated_weather").getJSONObject(0)

        // construire şi returnare obiect POJO care încapsulează datele meteo
        return WeatherForecastData(
            location = "",
            date = timeService.getCurrentTime(),
            weatherState = "",
            windDirection = responseRootObject.getInt("wind_degrees").toString(),
            windSpeed = responseRootObject.getFloat("wind_speed").roundToInt(),
            minTemp = responseRootObject.getFloat("min_temp").roundToInt(),
            maxTemp = responseRootObject.getFloat("max_temp").roundToInt(),
            humidity = responseRootObject.getFloat("humidity").roundToInt()
        )
    }
}