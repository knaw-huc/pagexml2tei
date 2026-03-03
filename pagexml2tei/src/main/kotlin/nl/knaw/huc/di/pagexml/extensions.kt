package nl.knaw.huc.di.pagexml

import java.time.ZonedDateTime
import java.util.Date

fun String.toDate(): Date {
    // Parse as ZonedDateTime first
    val zdt = ZonedDateTime.parse(this) // ISO_ZONED_DATE_TIME is default

    // Convert to legacy java.util.Date if needed
    return Date.from(zdt.toInstant())
}

fun String.toCoords(): Coords =
    Coords(
        points = toPoints()
    )

fun String.toBaseline(): Baseline =
    Baseline(
        points = toPoints()
    )

private fun String.toPoints(): List<Point> =
    split(" ")
        .map { points -> points.split(",") }
        .map { Point(it[0].toInt(), it[1].toInt()) }
