package com.sb.adapter.calligraphy.output.infrastructure.jooq.mapper

import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.Prompt
import com.sb.domain.calligraphy.value.Seed
import com.sb.domain.calligraphy.value.StyleType
import com.sb.domain.calligraphy.value.Text
import com.sb.jooq.tables.records.JMstCalligraphyRecord


fun JMstCalligraphyRecord.toCalligraphyIdVo(): Calligraphy.CalligraphyId =
    Calligraphy.CalligraphyId(this.id!!)

fun JMstCalligraphyRecord.toSeedVo(): Seed =
    Seed.of(this.seed)

fun JMstCalligraphyRecord.toTextVo(): Text =
    Text.of(this.text)

fun JMstCalligraphyRecord.toPromptVo(): Prompt? =
    this.prompt?.let { Prompt.of(this.prompt!!) }

fun JMstCalligraphyRecord.toStyleVo(): StyleType =
    StyleType.valueOf(this.style)