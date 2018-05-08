package me.manabu.webapi.models

data class BasicResponse<E>(val status: ResponseStatus, val data: E, val errors: MutableList<String>)
