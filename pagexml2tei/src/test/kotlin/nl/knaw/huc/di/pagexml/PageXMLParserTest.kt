package nl.knaw.huc.di.pagexml

import org.junit.jupiter.api.Test
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.apache.logging.log4j.kotlin.logger
import org.primaresearch.page.content.PageXMLLoader
import org.primaresearch.page.content.hasUnicodeTextEquiv
import org.primaresearch.page.content.points

class PageXMLParserTest {
    @OptIn(ExperimentalSerializationApi::class)
    val prettyJson = Json { // this returns the JsonBuilder
        prettyPrint = true
        // optional: specify indent
        prettyPrintIndent = "  "
    }

    @Test
    fun `parse 0002_p002`() {
        val pagexmlPath = "data/0002_p002.xml"
        val pcGts = PageXMLLoader.loadFromPath(pagexmlPath)
//        logger.info { pcGts }
        println(pcGts.pcGtsId)
        val json = prettyJson.encodeToString(pcGts)
        logger.info { json }
    }

    @Test
    fun `parse 0005_p005`() {
        val pagexmlPath = "data/0005_p005.xml"
        val result = PageXMLParser(pagexmlPath).parse()
        logger.info { result }
    }

    @Test
    fun `parse 0008_p008`() {
        val pagexmlPath = "data/0008_p008.xml"
        val result = PageXMLParser(pagexmlPath).parse()
        logger.info { result }
    }

    @Test
    fun `parse 3599_0072`() {
        val pagexmlPath = "data/NL-HaNA_1.04.02_3599_0072.xml"
        val result = PageXMLParser(pagexmlPath).parse()
        logger.info { result }
    }

    @Test
    fun alternative() {
        val pagexmlPath = "data/NL-HaNA_1.04.02_3599_0072.xml"
        val pcGts = PageXMLLoader.loadFromPath(pagexmlPath)
        logger.info { pcGts.page.textRegions.first().points() }
        pcGts.page.textRegions
            .asSequence()
            .filter { it.hasUnicodeTextEquiv() }
            .forEach {
                println(it.id)
                println("-".repeat(it.id.length))
                it.textLines
                    .flatMap { it.textEquiv }
                    .mapNotNull { it.unicode }
                    .forEach { println(it) }
                println()
            }
    }

}