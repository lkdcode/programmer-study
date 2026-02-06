package dev.lkdcode.infrastructure.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("kiwi")
data class Kiwi(
    @Id
    var id: Long? = null,
    var name: String,
    var price: Int,
)