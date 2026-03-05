package nl.knaw.huc.di.editem

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.vararg
import org.apache.logging.log4j.kotlin.logger
import org.primaresearch.page.content.PageXMLLoader
import org.primaresearch.page.content.hasUnicodeTextEquiv
import org.primaresearch.page.content.text

val logger = logger("PageXML2TEI")

@OptIn(ExperimentalCli::class)
fun main(args: Array<String>) {
    val parser = ArgParser("pagexml2tei")
    val jsonldPath by parser.argument(
        type = ArgType.String,
        fullName = "pagexml_file",
        description = "The pagexml file to convert"
    ).vararg()
    val arguments = if (args.isEmpty()) arrayOf("-h") else args
    parser.parse(arguments)

    val pageData = jsonldPath.map { path -> PageXMLLoader.loadFromPath(path) }
    pageData
        .map { it.page }
        .forEach { page ->
            println(page.imageFilename)
            println("=".repeat(page.imageFilename.length))
            page.textRegions
                .filter { it.hasUnicodeTextEquiv() }
                .forEach { textRegion ->
                    println(textRegion.id)
                    println("-".repeat(textRegion.id.length))
                    textRegion.textLines.forEach { textLine -> println(textLine.text) }
                    println()
                }
            println()
        }

}
