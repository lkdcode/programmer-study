package demo.example.query.modules.fruit.adapter.out.query

import com.querydsl.core.types.Expression
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.EntityPathBase
import com.querydsl.jpa.impl.JPAQueryFactory
import demo.example.query.modules.fruit.adapter.out.infrastructure.jpa.entity.*
import demo.example.query.modules.fruit.adapter.out.infrastructure.jpa.entity.Unit
import demo.example.query.modules.fruit.application.ports.out.FruitQueryPort
import demo.example.query.modules.fruit.application.usecase.model.BaseQuerySupport
import demo.example.query.modules.fruit.application.usecase.model.Pagination
import demo.example.query.modules.fruit.application.usecase.model.ParamCondition
import demo.example.query.modules.fruit.application.usecase.model.ParamConditionList
import demo.example.query.modules.fruit.application.usecase.query.FruitPage
import demo.example.query.modules.fruit.application.usecase.query.QFruitSummary
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class FruitQueryAdapter(
    override val factory: JPAQueryFactory
) : FruitQueryPort, BaseQuerySupport(factory) {

    override fun fetch(paramConditionList: ParamConditionList, pageable: Pageable): FruitPage {
        val result = factory
            .select(
                QFruitSummary(
                    FRUIT.id.`as`("fruitId"),
                    FRUIT.variety,
                    FRUIT.sweetnessBrix,
                    FRUIT.brand,
                    FRUIT.grade,
                    FRUIT.harvestedAt,
                    PRICE.price,
                    PRICE.currency,
                    PRICE.unit,
                )
            )

            .from(FRUIT)
            .leftJoin(PRICE)
            .on(
                PRICE.fruitId.eq(FRUIT.id)
            )

            .where(*whereCondition(paramConditionList))

            .orderBy(*creteOrderSpecifier(pageable))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())

            .fetch()

        val totalSize = factory
            .select(FRUIT.count())
            .from(FRUIT)

            .leftJoin(PRICE)
            .on(
                PRICE.fruitId.eq(FRUIT.id)
            )

            .where(*whereCondition(paramConditionList))

            .fetchFirst() ?: 0L

        return FruitPage(
            result,
            Pagination.of(PageImpl(result, pageable, totalSize))
        )
    }

    override fun qEntity(): EntityPathBase<*> = FRUIT

    override fun paramCondition(): (ParamCondition) -> BooleanExpression? = { e ->
        when (e.key) {
            "fruitId" -> FRUIT.id.eq(e.valueToLong)
            "variety" -> FRUIT.variety.containsIgnoreCase(e.valueString)

            "sweetnessBrixGoe" -> FRUIT.sweetnessBrix.goe(e.valueToBigDecimal)
            "sweetnessBrixLoe" -> FRUIT.sweetnessBrix.loe(e.valueToBigDecimal)

            "brand" -> FRUIT.brand.containsIgnoreCase(e.valueString)

            "grade" -> FRUIT.grade.eq(Grade.valueOf(e.valueString))

            "harvestedAtGoe" -> FRUIT.harvestedAt.goe(e.valueToDate)
            "harvestedAtLoe" -> FRUIT.harvestedAt.goe(e.valueToDate)

            "priceGoe" -> PRICE.price.goe(e.valueToBigDecimal)
            "priceLoe" -> PRICE.price.loe(e.valueToBigDecimal)

            "currency" -> PRICE.currency.eq(Currency.valueOf(e.valueString))
            "unit" -> PRICE.unit.eq(Unit.valueOf(e.valueString))

            else -> null
        }
    }

    override fun sortingCondition(): (String) -> Expression<out Comparable<*>>? = { e ->
        when (e.trim()) {
            "fruitId" -> FRUIT.id
            "variety" -> FRUIT.variety

            "sweetnessBrix" -> FRUIT.sweetnessBrix
            "brand" -> FRUIT.brand
            "grade" -> FRUIT.grade

            "harvestedAt" -> FRUIT.harvestedAt
            "price" -> PRICE.price

            "currency" -> PRICE.currency
            "unit" -> PRICE.unit

            else -> FRUIT.id
        }
    }

    companion object {
        private val FRUIT = QFruitJpaEntity.fruitJpaEntity
        private val PRICE = QPriceJpaEntity.priceJpaEntity
    }
}