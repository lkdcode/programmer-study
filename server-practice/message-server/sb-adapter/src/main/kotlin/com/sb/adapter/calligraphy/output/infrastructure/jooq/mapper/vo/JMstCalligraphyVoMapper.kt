package com.sb.adapter.calligraphy.output.infrastructure.jooq.mapper.vo

import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.Prompt
import com.sb.domain.calligraphy.value.Seed
import com.sb.domain.calligraphy.value.StyleType
import com.sb.domain.calligraphy.value.Text
import com.sb.jooq.tables.records.JMstCalligraphyRecord


val JMstCalligraphyRecord.toCalligraphyIdVo: Calligraphy.CalligraphyId
    get() = Calligraphy.CalligraphyId(this.id!!)

val JMstCalligraphyRecord.toSeedVo: Seed
    get() = Seed.of(this.seed)

val JMstCalligraphyRecord.toTextVo: Text
    get() = Text.of(this.text)

val JMstCalligraphyRecord.toPromptVo: Prompt?
    get() = this.prompt?.let { Prompt.of(this.prompt!!) }

val JMstCalligraphyRecord.toStyleVo: StyleType
    get() = StyleType.valueOf(this.style)