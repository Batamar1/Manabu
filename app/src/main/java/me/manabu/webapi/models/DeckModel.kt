package me.manabu.webapi.models

import com.google.gson.annotations.SerializedName

data class DeckModel(
        var name: String,
        @SerializedName("public") var isPublic: Int = 0,
        var id1: CardModel,
        var id2: CardModel
)