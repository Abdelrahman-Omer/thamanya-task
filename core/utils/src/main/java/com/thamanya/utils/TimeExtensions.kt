package com.thamanya.utils
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.thamanya.presentation.R
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

fun getHoursFromMillis(millis: Int): Int {
    return (millis / (60 * 60)) % 24
}

fun getMinutesFromMillis(millis: Int): Int {
    return (millis / (60)) % 60
}

fun getSecondsFromMillis(millis: Int): Int {
    return (millis) % 60
}

@Composable
fun getTimeDescription(target: String): String {
    target.ifBlank { return "" }
    val targetDate = LocalDateTime.ofInstant(
        Instant.parse(target),
        ZoneId.systemDefault()
    )
    val now = LocalDateTime.now()

    val years = ChronoUnit.YEARS.between(targetDate, now)
    if (years > 0) return stringResource(R.string.before_years, years.toString())

    val months = ChronoUnit.MONTHS.between(targetDate, now)
    if (months > 0) return stringResource(R.string.before_months, months.toString())

    val weeks = ChronoUnit.WEEKS.between(targetDate, now)
    if (weeks > 0) return stringResource(R.string.before_weeks, weeks.toString())

    val days = ChronoUnit.DAYS.between(targetDate, now)
    if (days > 0) return stringResource(R.string.before_days, days.toString())

    val hours = ChronoUnit.HOURS.between(targetDate, now)
    if (hours > 0) return stringResource(R.string.before_hours, hours.toString())

    val minutes = ChronoUnit.MINUTES.between(targetDate, now)
    if (minutes > 0) return stringResource(R.string.before_minutes, minutes.toString())

    val seconds = ChronoUnit.SECONDS.between(targetDate, now)
    if (seconds > 0) return stringResource(R.string.before_seconds, seconds.toString())

    return stringResource(R.string.before_long_time)
}