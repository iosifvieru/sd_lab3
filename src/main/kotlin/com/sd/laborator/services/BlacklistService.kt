package com.sd.laborator.services

import com.sd.laborator.interfaces.BlacklistInterface
import org.json.JSONObject
import org.springframework.boot.json.GsonJsonParser
import org.springframework.stereotype.Service
import java.io.File

@Service
class BlacklistService: BlacklistInterface {
    // JSON? cu locatiile blacklisted?
    private val path = "src/main/kotlin/com/sd/laborator/blacklist.json"

    override fun isAllowed(location: String): Boolean {
        val jsonContent = File(path).readText()

        if(jsonContent.contains(location)){
            return true
        }

        return false
    }
}
