package demo.example.server.entity

import jakarta.persistence.*


@Entity
@Table(name = "event_stock")
class EventStockJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "event_name")
    var eventName: String,

    @Column(name = "stock")
    var stock: Int,
)