package com.sd.laborator.services

import org.json.JSONObject
import org.springframework.stereotype.Service
import java.net.URL
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpUtils

@Service
class IPService {
    fun getIP(request: HttpServletRequest): String {
        //val ip = request.remoteAddr

        // demonstratie de test, folosesc ipv4 serverului in loc de cel al clientului
        // pt a testa functionalitatea.
        // (nu reusesc sa iau ip ul clientului deoarece sunt pe localhost!!!)
        var ip = request.remoteAddr

        val url = URL("https://api.myip.com/")
        val resp = url.readText()
        val obj = JSONObject(resp)

        println("IP: " + obj["ip"])
        ip = obj["ip"].toString()

        return ip
    }
}