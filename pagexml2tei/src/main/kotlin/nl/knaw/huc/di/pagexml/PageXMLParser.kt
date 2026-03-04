package nl.knaw.huc.di.pagexml

import kotlin.io.path.Path
import kotlin.io.path.readText
import nl.knaw.huygens.tei.Document

class PageXMLParser(val path: String) {

    val visitor = PageXMLVisitor()

    fun parse(): PxPcGts {
        val xml = Path(path).readText()
        val doc = Document.createFromXml(xml, false)
        doc.accept(visitor)
        return visitor.context.buildPcGts()
    }

}