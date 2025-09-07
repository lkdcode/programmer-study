package demo.example.query.modules.fruit.application.usecase.query

import com.querydsl.core.annotations.QueryProjection
import demo.example.query.modules.fruit.adapter.out.infrastructure.jpa.entity.Currency
import demo.example.query.modules.fruit.adapter.out.infrastructure.jpa.entity.Grade
import demo.example.query.modules.fruit.adapter.out.infrastructure.jpa.entity.Unit
import demo.example.query.modules.fruit.application.usecase.model.Pagination
import java.math.BigDecimal
import java.time.LocalDate

data class FruitPage(
    val items: List<FruitSummary>,
    val pagination: Pagination,
)

data class FruitSummary @QueryProjection constructor(
    var fruitId: Long,
    var variety: String,
    var sweetnessBrix: BigDecimal,
    var brand: String,
    var grade: Grade,
    var harvestedAt: LocalDate,
    var price: BigDecimal,
    var currency: Currency,
    var unit: Unit,
)