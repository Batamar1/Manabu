package me.manabu.webapi.models

data class DeckLevelCardModel(var id: String, var name: String, var description: String){
    fun convert(card: DeckLevelCardModel){
        name = card.name
        description = card.description
    }
}