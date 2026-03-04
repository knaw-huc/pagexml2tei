package org.primaresearch.page.content

import java.io.File
import jakarta.xml.bind.JAXBContext
import jakarta.xml.bind.Unmarshaller

object PageXMLLoader {
    val pageXMLUnmarshaller: Unmarshaller = JAXBContext
        .newInstance(PcGts::class.java)
        .createUnmarshaller()

    fun loadFromPath(pagexmlPath: String): PcGts =
        pageXMLUnmarshaller
            .unmarshal(File(pagexmlPath)) as PcGts
}
