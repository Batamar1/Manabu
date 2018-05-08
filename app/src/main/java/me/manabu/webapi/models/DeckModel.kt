package me.manabu.webapi.models

import com.google.gson.annotations.SerializedName

data class DeckModel(@SerializedName("_id") val id: String, val name: String)