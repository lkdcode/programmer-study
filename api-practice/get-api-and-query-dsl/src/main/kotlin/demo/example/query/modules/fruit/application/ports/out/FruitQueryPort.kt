package demo.example.query.modules.fruit.application.ports.out

import demo.example.query.modules.fruit.application.usecase.model.ParamConditionList
import demo.example.query.modules.fruit.application.usecase.query.FruitPage
import org.springframework.data.domain.Pageable

interface FruitQueryPort {
    fun fetch(paramConditionList: ParamConditionList, pageable: Pageable): FruitPage
}