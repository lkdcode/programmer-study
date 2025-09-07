package demo.example.query.modules.fruit.application.ports.input

import demo.example.query.modules.fruit.application.ports.out.FruitQueryPort
import demo.example.query.modules.fruit.application.usecase.model.ParamConditionList
import demo.example.query.modules.fruit.application.usecase.query.FruitPage
import demo.example.query.modules.fruit.application.usecase.query.QueryFruitUsecase
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FruitQueryInput(
    private val port: FruitQueryPort,
) : QueryFruitUsecase {

    override fun fetchV1(paramConditionList: ParamConditionList, pageable: Pageable): FruitPage =
        port.fetch(paramConditionList, pageable)

    override fun fetchV2(paramConditionList: ParamConditionList, pageable: Pageable): FruitPage =
        port.fetch(paramConditionList, pageable)
}