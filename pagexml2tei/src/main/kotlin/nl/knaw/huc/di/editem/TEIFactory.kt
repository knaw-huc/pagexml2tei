package nl.knaw.huc.di.editem

import javax.xml.namespace.NamespaceContext
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPath
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory
import org.apache.logging.log4j.kotlin.logger
import org.w3c.dom.Node
import org.w3c.dom.NodeList

class TEIFactory {
    object PageXMLNamespaceContext : NamespaceContext {
        val namespaceMap = mapOf(
            "px" to "http://schema.primaresearch.org/PAGE/gts/pagecontent/2013-07-15",
            "xsi" to "http://www.w3.org/2001/XMLSchema-instance",
        )
        val reverseMap = namespaceMap.map { it.value to it.key }.toMap()

        override fun getNamespaceURI(prefix: String?): String? =
            when (prefix) {
                in namespaceMap -> namespaceMap[prefix]
                else -> null
            }

        override fun getPrefix(namespaceURI: String?): String? =
            when (namespaceURI) {
                in reverseMap -> reverseMap[namespaceURI]
                else -> null
            }

        override fun getPrefixes(namespaceURI: String?): Iterator<String?>? =
            when (namespaceURI) {
                in reverseMap -> listOf(reverseMap[namespaceURI]).iterator()
                else -> null
            }
    }

    val xpath: XPath = XPathFactory.newInstance().newXPath().apply {
        namespaceContext = PageXMLNamespaceContext
    }
    val builder: DocumentBuilder = DocumentBuilderFactory
        .newInstance()
        .apply { this.isNamespaceAware = true }
        .newDocumentBuilder()

    fun fromPageXML(path: String): List<PageData> {
//        val errors = mutableListOf<String>()
        val pages = mutableListOf<PageData>()
        logger.info { "<= $path" }
        builder.parse(path).let { doc ->
            doc.getNodeSequence("//px:Page")
                .forEach { itemNode ->
                    val lines = mutableListOf<String>()
                    val imageFileName = itemNode.getAttributeValue("imageFilename")
//                    logger.info { "imageFileName=$imageFileName" }
//                    lines.add("<graphic url=\"$imageFileName\"/>")
//                    lines.add("<pb/>")
                    itemNode.getNodeSequence("px:TextRegion")
                        .forEach { textRegion ->
                            val id = textRegion.getAttributeValue("id")
//                            logger.info { "textregion id=$id" }
//                            lines.add("<p id=\"$id\">")
                            textRegion.getNodeSequence("px:TextLine")
                                .forEach { textLine ->
                                    val id = textLine.getAttributeValue("id")
                                    val text = textLine.getString("px:TextEquiv/px:Unicode")
                                    lines.add(text)
//                                    logger.info { "textLine $id=$text" }
                                }
//                            lines.add("</p>")
                        }
                    val pd = PageData(imageFileName ?: "", lines)
                    pages.add(pd)
                }
        }

        return pages
    }

    private fun Node.getNodeSequence(xpathExpression: String): Sequence<Node> =
        this.getNodeList(xpathExpression).asSequence()

    private fun Node.getNodeList(xpathExpression: String): NodeList =
        xpath.compile(xpathExpression)
            .evaluate(this, XPathConstants.NODESET) as NodeList

//    private fun Node.getNode(xpathExpression: String): Node =
//        xpath.compile(xpathExpression)
//            .evaluate(this, XPathConstants.NODE) as Node
//
//    private fun Node.getNumber(xpathExpression: String): Number =
//        xpath.compile(xpathExpression)
//            .evaluate(this, XPathConstants.NUMBER) as Number

    private fun Node.getString(xpathExpression: String): String =
        xpath.compile(xpathExpression)
            .evaluate(this, XPathConstants.STRING) as String

//    private fun Node.getBoolean(xpathExpression: String): Boolean =
//        xpath.compile(xpathExpression)
//            .evaluate(this, XPathConstants.BOOLEAN) as Boolean

}
