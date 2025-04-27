package run.moku.framework.common.jpa

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant

@MappedSuperclass
abstract class BaseJpaEntity(

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false,

    @Column(name = "deleted_at", nullable = true)
    var deletedAt: Instant? = null,

    @CreatedBy
    @Column(name = "created_by", nullable = false)
    var createdBy: Long? = 0L,

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    var createdAt: Instant = Instant.now(),

    @Column(name = "updated_by", nullable = false)
    var updatedBy: Long? = 0L,

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant = Instant.now(),
) {

    fun softDelete() {
        this.isDeleted = true
        this.deletedAt = Instant.now()
    }
}