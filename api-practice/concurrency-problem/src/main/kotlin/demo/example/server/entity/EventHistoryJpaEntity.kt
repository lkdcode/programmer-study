package demo.example.server.entity

import jakarta.persistence.*


@Entity
@Table(name = "event_history")
class EventHistoryJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "user_id")
    var userId: Long,

    @Column(name = "event_id")
    var eventId: Long,
)