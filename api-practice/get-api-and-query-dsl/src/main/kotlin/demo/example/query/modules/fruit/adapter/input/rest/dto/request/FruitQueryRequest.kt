package demo.example.query.modules.fruit.adapter.input.rest.dto.request

import demo.example.query.modules.fruit.application.usecase.model.BaseQueryString

data class FruitQueryStringV1(
    val variety: String? = "",

    val sweetnessBrixGoe: String? = "",
    val sweetnessBrixLoe: String? = "",

    val priceGoe: String? = "",
    val priceLoe: String? = "",
) : BaseQueryString()

data class FruitQueryStringV2(
    val fruitId: String? = "",
    val variety: String? = "",

    val sweetnessBrixGoe: String? = "",
    val sweetnessBrixLoe: String? = "",

    val priceGoe: String? = "",
    val priceLoe: String? = "",

    val brand: String? = "",
    val grade: String? = "",

    val harvestedAtGoe: String? = "",
    val harvestedAtLoe: String? = "",
) : BaseQueryString()