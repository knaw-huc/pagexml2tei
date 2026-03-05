package org.primaresearch.page.content

import org.junit.jupiter.api.Test
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.apache.logging.log4j.kotlin.logger

class PageXMLLoaderTest {
    @Test
    fun `import globalise example`() {
        val pagexmlPath = "data/NL-HaNA_1.04.02_3599_0072.xml"
        val pcGts = PageXMLLoader.loadFromPath(pagexmlPath)
        val json = prettyJson.encodeToString(pcGts)
        logger.info { json }
        assert(json.isNotBlank())

        logger.info { pcGts.page.textRegions.first().points }
        pcGts.page.textRegions
            .asSequence()
            .filter { it.hasUnicodeTextEquiv() }
            .forEach {
                println("${it.id} ${it.structureType}")
                println("-".repeat(it.id.length))
                it.textLines
                    .flatMap { textLine -> textLine.textEquiv }
                    .mapNotNull { textEquiv -> textEquiv.unicode }
                    .forEach { unicode -> println(unicode) }
                println()
            }
    }

    @Test
    fun `parse 0002_p002`() {
        val pagexmlPath = "data/0002_p002.xml"
        val pcGts = PageXMLLoader.loadFromPath(pagexmlPath)
        val json = prettyJson.encodeToString(pcGts)
        logger.info { json }
        assert(json.isNotBlank())
    }

    @OptIn(ExperimentalSerializationApi::class)
    val prettyJson = Json { // this returns the JsonBuilder
        prettyPrint = true
        // optional: specify indent
        prettyPrintIndent = "  "
    }

}