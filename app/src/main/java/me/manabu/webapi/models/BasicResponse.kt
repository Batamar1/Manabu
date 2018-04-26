package me.manabu.webapi.models

data class BasicResponse<E>(var status: Status, var data: E, var errors: String)

enum class Status { SUCCESS, FAIL }
