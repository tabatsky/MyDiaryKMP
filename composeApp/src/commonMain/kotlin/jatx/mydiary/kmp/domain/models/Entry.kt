@file:OptIn(ExperimentalTime::class)

package jatx.mydiary.kmp.domain.models

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class Entry(
    val id: Long? = null,
    val type: Int,
    val time: Long
)

fun Entry.formatTimeList(): String {
    val formatPattern = "dd/MM/yyyy HH:mm"

    @OptIn(FormatStringsInDatetimeFormats::class)
    val dateTimeFormat = LocalDateTime.Format {
        byUnicodePattern(formatPattern)
    }
    val instant = Instant.fromEpochMilliseconds(time)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return dateTimeFormat.format(localDateTime)
}

fun Entry.formatTimeTop(): String {
    val dt = Clock.System.now().toEpochMilliseconds() - time
    val secTotal = dt / 1000L
    val sec = secTotal % 60
    val minTotal = secTotal / 60
    val min = minTotal % 60
    val hoursTotal = minTotal / 60
    val hours = hoursTotal % 24
    val days = hoursTotal / 24
    return when {
        days > 0 -> {
            "$days д. $hours ч. назад"
        }
        hours > 0 -> {
            "$hours ч. $min м. назад"
        }
        else -> {
            "$min м. $sec с. назад"
        }
    }
}