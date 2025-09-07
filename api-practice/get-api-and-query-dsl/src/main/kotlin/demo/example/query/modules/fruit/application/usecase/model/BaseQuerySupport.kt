package demo.example.query.modules.fruit.application.usecase.model

import com.querydsl.core.types.Expression
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.EntityPathBase
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service


@Service
abstract class BaseQuerySupport(
    protected val factory: JPAQueryFactory
) {

    fun getTotalSize(
        conditions: ParamConditionList,
        vararg addConditions: BooleanExpression?
    ): Long = factory
        .select(qEntity().count())
        .from(qEntity())
        .where(*whereCondition(conditions, *addConditions))
        .fetchOne() ?: 0L

    fun whereCondition(
        conditions: ParamConditionList,
        vararg addConditions: BooleanExpression?
    ): Array<BooleanExpression> = (conditions.list
        .filter { true }
        .filter { it.isNotValue() }
        .mapNotNull(paramCondition())
            + addConditions.filterNotNull())
        .toTypedArray()

    fun creteOrderSpecifier(
        pageable: Pageable
    ): Array<OrderSpecifier<*>> = pageable.sort
        .filterNotNull()
        .map {
            val direction = if (it.isAscending) Order.ASC else Order.DESC
            val property = it.property
            OrderSpecifier(direction, sortingCondition().invoke(property))
        }
        .filter { true }
        .toTypedArray()

    fun creteOrderSpecifier(
        sort: Sort
    ): Array<OrderSpecifier<*>> = sort
        .filterNotNull()
        .map {
            val direction = if (it.isAscending) Order.ASC else Order.DESC
            val property = it.property
            OrderSpecifier(direction, sortingCondition().invoke(property))
        }
        .filter { true }
        .toTypedArray()

    abstract fun qEntity(): EntityPathBase<*>
    abstract fun paramCondition(): (ParamCondition) -> BooleanExpression?
    abstract fun sortingCondition(): (String) -> (Expression<out Comparable<*>>)?
}