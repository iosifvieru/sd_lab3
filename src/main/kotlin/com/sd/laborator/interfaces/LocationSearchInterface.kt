package com.sd.laborator.interfaces

import com.sd.laborator.pojo.LocationData

interface LocationSearchInterface {
    fun getLocation(locationName: String): LocationData
}