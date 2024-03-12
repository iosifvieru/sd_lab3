package com.sd.laborator.services

import com.sd.laborator.interfaces.LocationSearchInterface
import com.sd.laborator.pojo.LocationData
import org.json.JSONObject
import org.springframework.stereotype.Service
import java.net.URL

@Service
class IPtoLocationService: LocationSearchInterface {
    override fun getLocation(locationName: String): LocationData {

        val loc = URL("https://api.findip.net/$locationName/?token=47886753a3744561845b6192acd67d2b")
        val resp = loc.readText()
        val city = JSONObject(resp).getJSONObject("city").getJSONObject("names")

        val country = JSONObject(resp).getJSONObject("country").getJSONObject("names")

        println("City: " + city.getString("en"))

        return LocationData(city.getString("en"), 0.0, 0.0, country.getString("en"))
    }
}