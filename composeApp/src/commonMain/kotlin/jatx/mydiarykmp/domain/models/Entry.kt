package jatx.mydiarykmp.domain.models

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.char

class Entry(
    val id: Long? = null,
    val type: Int,
    val time: Long
)

fun Entry.formatTimeList(): String {
    val dateTimeFormat = DateTimeComponents.Format {
        year(); char('/'); monthNumber(); char('/'); dayOfMonth()
        char(' ')
        hour(); char(':'); minute()
    }
    return Instant.fromEpochMilliseconds(time).format(dateTimeFormat)
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