package nl.knaw.huc.di.editem

import org.w3c.dom.Node
import org.w3c.dom.NodeList

fun NodeList.asSequence(): Sequence<Node> =
    sequence {
        for (i in 0..<this@asSequence.length) {
            yield(item(i))
        }
    }

fun Node.getAttributeValue(name: String): String? =
    this.attributes.getNamedItem(name)?.textContent
