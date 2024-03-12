package com.sd.laborator.interfaces

interface BlacklistInterface {
    fun isAllowed(location: String): Boolean
}