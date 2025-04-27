package run.moku.modules.users.adapter.infrastructure.jpa.entity

import jakarta.persistence.*
import run.moku.framework.common.jpa.BaseJpaEntity

@Entity
@Table(name = "tb_users")
class UserJpaEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "login_id", nullable = false, unique = true)
    var loginId: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "nickname", nullable = false, unique = true)
    var nickname: String,
) : BaseJpaEntity() {
}