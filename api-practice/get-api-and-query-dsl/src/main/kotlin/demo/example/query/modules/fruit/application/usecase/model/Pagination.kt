package demo.example.query.modules.fruit.application.usecase.model

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.data.domain.Page

@JsonPropertyOrder("totalItems", "totalPages", "currentPage", "perPate")
class Pagination private constructor(
    val totalItems: Long,
    val totalPages: Int,
    val currentPage: Int,
    val perPage: Int,
) {

    companion object {
        fun of(page: Page<*>): Pagination =
            Pagination(
                totalItems = page.totalElements,
                totalPages = page.totalPages,
                currentPage = page.number + 1,
                perPage = page.size,
            )
    }
}