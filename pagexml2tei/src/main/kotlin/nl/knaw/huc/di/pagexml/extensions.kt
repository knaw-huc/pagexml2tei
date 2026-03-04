package nl.knaw.huc.di.pagexml

import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.util.Date

fun String.toDate(): Date {
    val formatter = DateTimeFormatterBuilder()
        .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        .optionalStart()
        .appendOffsetId()
        .optionalEnd()
        .toFormatter()
        .withZone(ZoneOffset.UTC) // fallback zone when offset is absent

    val odt = OffsetDateTime.parse(this, formatter)
    return Date.from(odt.toInstant())
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
