package org.clevercastle.saas.base

import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*


class TimeUtils {
    companion object {
        fun now(): OffsetDateTime = OffsetDateTime.now()

        // convert Date to OffsetDateTime
        fun from(date: Date): OffsetDateTime = date.toInstant().atOffset(ZoneOffset.UTC)

    }
}