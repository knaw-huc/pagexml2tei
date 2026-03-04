package org.primaresearch.page.content

import nl.knaw.huc.di.pagexml.Point

fun CoordsType.points(): List<Point> =
    points.trim()
        .split(" ")
        .filter { it.isNotEmpty() }
        .map {
            val (x, y) = it.split(",")
            Point(x.toInt(), y.toInt())
        }

fun Region.points(): List<Point> = coords.points()

fun TextRegion.hasUnicodeTextEquiv(): Boolean =
    textEquiv
        .mapNotNull { it.unicode }
        .joinToString("")
        .isNotBlank()
            ||
            textLines.any { it.hasUnicodeTextEquiv() }

fun TextLine.hasUnicodeTextEquiv(): Boolean =
    textEquiv
        .mapNotNull { it.unicode }
        .joinToString("")
        .isNotBlank()