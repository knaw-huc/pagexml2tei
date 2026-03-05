package org.primaresearch.page.content

import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.util.Date

data class Point(
    val x: Int,
    val y: Int,
)

fun CoordsType.pointList(): List<Point> =
    points.trim()
        .split(" ")
        .filter { it.isNotEmpty() }
        .map {
            val (x, y) = it.split(",")
            Point(x.toInt(), y.toInt())
        }

val Region.points: List<Point>
    get() = coords.pointList()

fun TextRegion.hasUnicodeTextEquiv(): Boolean =
    textEquiv
        .mapNotNull { it.unicode }
        .joinToString("")
        .isNotBlank()
            ||
            textLines.any { it.hasUnicodeTextEquiv }

val TextLine.hasUnicodeTextEquiv: Boolean
    get() = textEquiv
        .mapNotNull { it.unicode }
        .joinToString("")
        .isNotBlank()

val TextLine.text: String
    get() = textEquiv
        .mapNotNull { it.unicode }
        .joinToString("")

const val STRUCTURE_PREFIX = "structure {type:"

val Region.structureType: String?
    get() = custom
        ?.takeIf { it.startsWith(STRUCTURE_PREFIX) }
        ?.removePrefix(STRUCTURE_PREFIX)
        ?.removeSuffix(";}")

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
