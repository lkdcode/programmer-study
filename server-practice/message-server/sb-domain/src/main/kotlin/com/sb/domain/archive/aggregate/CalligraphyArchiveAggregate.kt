package com.sb.domain.archive.aggregate

import com.sb.domain.archive.entity.CalligraphyArchive
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.Author
import java.time.Instant

class CalligraphyArchiveAggregate private constructor(
    private val archive: CalligraphyArchive
) {
    val getArchive: CalligraphyArchive get() = archive

    companion object {
        fun restore(archive: CalligraphyArchive): CalligraphyArchiveAggregate = CalligraphyArchiveAggregate(archive)

        fun create(
            calligraphyId: Calligraphy.CalligraphyId,
            user: Author,
        ): CalligraphyArchiveAggregate = CalligraphyArchiveAggregate(
            CalligraphyArchive(
                id = generateArchiveId(),
                calligraphyId = calligraphyId,
                user = user,
                createdAt = Instant.now(),
            )
        )

        private fun generateArchiveId(): CalligraphyArchive.CalligraphyArchiveId =
            CalligraphyArchive.CalligraphyArchiveId(Instant.now().toEpochMilli())
    }
}