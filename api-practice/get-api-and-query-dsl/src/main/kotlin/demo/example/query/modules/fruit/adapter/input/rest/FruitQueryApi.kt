package demo.example.query.modules.fruit.adapter.input.rest

import demo.example.query.modules.fruit.adapter.input.rest.dto.request.FruitQueryStringV1
import demo.example.query.modules.fruit.adapter.input.rest.dto.request.FruitQueryStringV2
import demo.example.query.modules.fruit.application.usecase.query.FruitPage
import demo.example.query.modules.fruit.application.usecase.query.QueryFruitUsecase
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort.Direction.ASC
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RestController

@RestController
class FruitQueryApi(
    private val queryFruitUsecase: QueryFruitUsecase,
) {

    @GetMapping("/api/v1/fruits")
    fun getListV1(
        @PageableDefault(size = 15, page = 0, sort = ["variety"], direction = ASC) pageable: Pageable,
        @ModelAttribute queryString: FruitQueryStringV1,
    ): ResponseEntity<FruitPage> {
        val response = queryFruitUsecase.fetchV1(queryString.convert(), pageable)

        return ResponseEntity.ok().body(response)
    }

    @GetMapping("/api/v2/fruits")
    fun getListV2(
        @PageableDefault(size = 15, page = 0, sort = ["variety"], direction = ASC) pageable: Pageable,
        @ModelAttribute queryString: FruitQueryStringV2,
    ): ResponseEntity<FruitPage> {
        val response = queryFruitUsecase.fetchV2(queryString.convert(), pageable)

        return ResponseEntity.ok().body(response)
    }
}