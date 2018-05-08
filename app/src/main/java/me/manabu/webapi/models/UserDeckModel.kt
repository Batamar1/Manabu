package me.manabu.webapi.models

import com.google.gson.annotations.SerializedName

data class UserDeckModel(@SerializedName("_id") val id: String, val originalId: String, val name: String)