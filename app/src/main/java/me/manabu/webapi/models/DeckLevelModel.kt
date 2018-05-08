package me.manabu.webapi.models

data class DeckLevelModel(val id: String, val name: String, val cards: List<DeckLevelCardModel>, val available: Boolean)