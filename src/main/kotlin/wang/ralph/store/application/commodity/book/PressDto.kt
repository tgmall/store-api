package wang.ralph.store.application.commodity.book

import wang.ralph.store.models.commodity.book.Press

fun Press.toDto(): PressDto = PressDto(id.toString(), name)

data class PressDto(val id: String, val name: String)
