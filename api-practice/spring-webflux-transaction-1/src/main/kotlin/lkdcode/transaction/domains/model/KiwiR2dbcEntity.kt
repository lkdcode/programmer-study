package lkdcode.transaction.domains.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("kiwi")
data class KiwiR2dbcEntity(
    @Id
    val id: Long? = null,

    @Column("name")
    val name: String,
)