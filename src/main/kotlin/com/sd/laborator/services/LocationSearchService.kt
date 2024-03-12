package com.sd.laborator.services

import com.sd.laborator.interfaces.LocationSearchInterface
import com.sd.laborator.pojo.LocationData
import org.json.JSONArray
import org.springframework.stereotype.Service
import java.net.URL
import org.json.JSONObject
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Service
class LocationSearchService : LocationSearchInterface {
    override fun getLocation(locationName: String): LocationData {
        // codificare parametru URL (deoarece poate conţine caractere speciale)
        val encodedLocationName = URLEncoder.encode(locationName, StandardCharsets.UTF_8.toString())

        // construire obiect de tip URL
        val locationSearchURL = URL("https://api.api-ninjas.com/v1/geocoding?city=$encodedLocationName&X-Api-Key=SwAaROkFAHxe9eH/K3Gvcg==8L2AWCq3nNQBfM53")

        // preluare raspuns HTTP (se face cerere GET şi se preia conţinutul răspunsului sub formă de text)
        val rawResponse: String = locationSearchURL.readText()
        //print(rawResponse)

        // parsare obiect JSON
        var responseRootObject = JSONObject("{\"data\": ${rawResponse}}")
        //val raspuns = responseRootObject.takeUnless { it.isEmpty }

        val data = responseRootObject.get("data")
        if(data is JSONArray && data.length() == 0){
            return LocationData(
                "null",
                0.0,
                0.0,
                "null"
            )
        }

        responseRootObject = responseRootObject.getJSONArray("data").getJSONObject(0)

        val name: String = responseRootObject.getString("name")
        val latitude: Double = responseRootObject.getDouble("latitude")
        val longitude: Double = responseRootObject.getDouble("longitude")
        val country: String = responseRootObject.getString("country")

        val locationData = LocationData(name, latitude, longitude, country)

        return locationData
    }
}