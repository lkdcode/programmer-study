package dev.lkdcode.domain.value

interface Fruit

data class Apple(
    val id: Long,
) : Fruit

data class Tomato(
    val id: Long,
) : Fruit