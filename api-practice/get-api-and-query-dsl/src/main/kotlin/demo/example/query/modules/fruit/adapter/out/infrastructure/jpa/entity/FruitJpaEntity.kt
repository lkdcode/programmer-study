package demo.example.query.modules.fruit.adapter.out.infrastructure.jpa.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(name = "fruit")
class FruitJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "variety")
    var variety: String,

    @Column(name = "sweetness_brix", precision = 4, scale = 1)
    var sweetnessBrix: BigDecimal,

    @Column(name = "brand")
    var brand: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "grade")
    var grade: Grade,

    @Column(name = "harvested_at")
    var harvestedAt: LocalDate,
)

enum class Grade {
    PREMIUM,
    HIGH,
    MEDIUM,
    LOW,
}