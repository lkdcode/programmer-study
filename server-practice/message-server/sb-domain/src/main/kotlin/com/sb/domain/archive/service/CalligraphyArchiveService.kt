package com.sb.domain.archive.service

import com.sb.domain.archive.aggregate.CalligraphyArchiveAggregate
import com.sb.domain.archive.repository.CalligraphyArchiveQueryRepository
import com.sb.domain.archive.repository.CalligraphyArchiveRepository
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.User

class CalligraphyArchiveService(
    private val repository: CalligraphyArchiveRepository,
    private val queryRepository: CalligraphyArchiveQueryRepository,
) {
    fun archive(calligraphyId: Calligraphy.CalligraphyId, user: User): CalligraphyArchiveAggregate {
        require(!queryRepository.existsBy(calligraphyId, user)) { "이미 보관한 캘리그래피입니다." }

        val aggregate = CalligraphyArchiveAggregate.create(calligraphyId, user)
        return repository.save(aggregate)
    }

    fun unarchive(calligraphyId: Calligraphy.CalligraphyId, user: User) {
        require(queryRepository.existsBy(calligraphyId, user)) { "보관하지 않은 캘리그래피입니다." }
        repository.deleteBy(calligraphyId, user)
    }
}


