package com.sb.framework.jooq.mapper.sort

import com.sb.application.common.input.query.sort.SortDirection
import org.jooq.Field
import org.jooq.SortField


fun <T> Field<T>.sortOption(direction: SortDirection): SortField<T> =
    if (direction == SortDirection.ASC) this.asc() else this.desc()