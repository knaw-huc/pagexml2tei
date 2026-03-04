package org.primaresearch.page.content

import nl.knaw.huc.di.pagexml.Point

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

const val STRUCTURE_PREFIX = "structure {type:"

val Region.structureType: String?
    get() = custom
        ?.takeIf { it.startsWith(STRUCTURE_PREFIX) }
        ?.removePrefix(STRUCTURE_PREFIX)
        ?.removeSuffix(";}")