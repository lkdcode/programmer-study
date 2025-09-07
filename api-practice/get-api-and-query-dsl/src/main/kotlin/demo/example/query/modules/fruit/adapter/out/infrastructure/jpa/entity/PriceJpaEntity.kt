package demo.example.query.modules.fruit.adapter.out.infrastructure.jpa.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "price")
class PriceJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "fruit_id")
    var fruitId: Long,

    @Column(name = "price", precision = 10, scale = 2)
    var price: BigDecimal,

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    var currency: Currency,

    @Enumerated(EnumType.STRING)
    @Column(name = "unit")
    var unit: Unit,
)

enum class Currency {
    KRW,
    USD,
}

enum class Unit {
    KG,
    BOX,
    EA,
}