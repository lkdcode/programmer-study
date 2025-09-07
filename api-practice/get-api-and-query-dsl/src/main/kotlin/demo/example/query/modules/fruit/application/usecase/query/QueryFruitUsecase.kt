package demo.example.query.modules.fruit.application.usecase.query

import demo.example.query.modules.fruit.application.usecase.model.ParamConditionList
import org.springframework.data.domain.Pageable

interface QueryFruitUsecase {
    fun fetchV1(paramConditionList: ParamConditionList, pageable: Pageable): FruitPage
    fun fetchV2(paramConditionList: ParamConditionList, pageable: Pageable): FruitPage
}