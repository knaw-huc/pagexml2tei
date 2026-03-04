package nl.knaw.huc.di.pagexml

import org.junit.jupiter.api.Test
import org.apache.logging.log4j.kotlin.logger

class PageXMLParserTest {
    @Test
    fun `parse 0002_p002`() {
        val pagexmlPath = "data/0002_p002.xml"
        val result = PageXMLParser(pagexmlPath).parse()
        logger.info { result }
        assert(result.pages.size == 1)
        val wordText =
            result.pages
                .flatMap { it.textRegions }
                .flatMap { it.textLines }
                .map { it.text }
        println(wordText.joinToString("\n"))
    }

    @Test
    fun `parse 0005_p005`() {
        val pagexmlPath = "data/0005_p005.xml"
        val result = PageXMLParser(pagexmlPath).parse()
        logger.info { result }
        assert(result.pages.size == 1)
    }

    @Test
    fun `parse 0008_p008`() {
        val pagexmlPath = "data/0008_p008.xml"
        val result = PageXMLParser(pagexmlPath).parse()
        logger.info { result }
        assert(result.pages.size == 1)
    }

    @Test
    fun `parse 3599_0072`() {
        val pagexmlPath = "data/NL-HaNA_1.04.02_3599_0072.xml"
        val result = PageXMLParser(pagexmlPath).parse()
        logger.info { result }
        assert(result.pages.size == 1)
    }

}