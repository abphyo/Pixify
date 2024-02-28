package com.biho.pixify.core.model.util

import java.time.Clock
import java.time.Duration
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


fun String.formatTimeAgo(): String {

    return try {

        val fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS-ZZ:ZZ").withZone(ZoneOffset.UTC)
        val date = fmt.parse(this)
        val time = Instant.from(date)
        val now = Instant.now(Clock.systemUTC())

        val duration = Duration.between(time, now)
        val minDiff = duration.toMinutes()

        val fmtOut = DateTimeFormatter.ofPattern("dd-mm-yyyy hh:mm:ss").withZone(ZoneOffset.UTC)
        val fallBackString = fmtOut.format(time)

        if (minDiff > 1440) {
            val dayAgo = minDiff / 1440
            return dayAgo.toInt().toString().plus(" day ago")
        } else if (minDiff > 60) {
            val hour = minDiff / 60
            return hour.toInt().toString().plus(" hours ago")
        } else if (minDiff > 5) {
            return minDiff.toInt().toString().plus(" mins ago")
        } else if (minDiff > 0) {
            return "Just now"
        }

        return fallBackString

    } catch (e: Exception) {
        ""
    }

}