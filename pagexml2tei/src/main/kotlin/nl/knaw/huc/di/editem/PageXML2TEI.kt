package nl.knaw.huc.di.editem

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.vararg
import org.apache.logging.log4j.kotlin.logger

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
    val tf = TEIFactory()
    jsonldPath.forEach { path ->
        val tei = tf.fromPageXML(path)
        logger.info { tei }
    }
}
