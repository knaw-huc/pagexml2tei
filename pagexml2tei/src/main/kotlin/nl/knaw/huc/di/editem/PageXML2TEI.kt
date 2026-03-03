package nl.knaw.huc.di.editem

import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli

@OptIn(ExperimentalCli::class)
fun main(args: Array<String>) {
    val parser = ArgParser("pagexml2tei")
    val jsonldPath by parser.argument(
        type = ArgType.String,
        fullName = "pagexml_file",
        description = "The pagexml file to convert"
    )
    val arguments = if (args.isEmpty()) arrayOf("-h") else args
    parser.parse(arguments)
    val tei = Path(jsonldPath).readText()
}
