package com.sd.laborator.controllers

import com.sd.laborator.interfaces.BlacklistInterface
import com.sd.laborator.interfaces.LocationSearchInterface
import com.sd.laborator.interfaces.WeatherForecastInterface
import com.sd.laborator.pojo.LocationData
import com.sd.laborator.pojo.WeatherForecastData
import com.sd.laborator.services.IPService
import com.sd.laborator.services.IPtoLocationService
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import java.net.InetAddress
import java.net.URL
import javax.servlet.http.HttpServletRequest

@Controller
class WeatherAppController {
    @Autowired
    private lateinit var locationSearchService: LocationSearchInterface

    @Autowired
    private lateinit var weatherForecastService: WeatherForecastInterface

    @Autowired
    private lateinit var blacklistService: BlacklistInterface

    @Autowired
    private lateinit var ipService: IPService

    @Autowired
    private lateinit var ipToLocationService: IPtoLocationService

    @RequestMapping("/getforecast/{location}", method = [RequestMethod.GET])
    @ResponseBody
    fun getForecast(@PathVariable location: String, request: HttpServletRequest): String {
        val ip = ipService.getIP(request)
        val clientLocation = ipToLocationService.getLocation(ip)

        //testing
        if(blacklistService.isAllowed(clientLocation.city)){
            return "Nu poti afla informatii din locatia in care te afli."
        }

        // se incearca preluarea lat. si long. a locatiei
        val locationData: LocationData = locationSearchService.getLocation(location)
        println(locationData)

        // dacă locaţia nu a fost găsită, răspunsul va fi corespunzător
        if (locationData.city == "null") {
            return "Nu s-au putut gasi date meteo pentru cuvantul cheie \"$location\"!"
        }

        // pe baza lat. si long. a locaţiei, se interoghează al doilea serviciu care returnează datele meteo
        // încapsulate într-un obiect POJO
        val rawForecastData: WeatherForecastData = weatherForecastService.getForecastData(locationData.longitude, locationData.latitude)

        rawForecastData.weatherState = locationData.country
        rawForecastData.location = locationData.city

        // fiind obiect POJO, funcţia toString() este suprascrisă pentru o afişare mai prietenoasă
        return rawForecastData.toString()
    }
}