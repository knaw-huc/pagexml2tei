package nl.knaw.huc.di.editem.tei

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.atomic.AtomicInteger
import kotlinx.serialization.ExperimentalSerializationApi
import org.primaresearch.page.content.PcGts
import org.primaresearch.page.content.text
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.PrintOptions
import org.redundent.kotlin.xml.XmlVersion
import org.redundent.kotlin.xml.xml

@OptIn(ExperimentalSerializationApi::class)
class TEIBuilder() {

    val printOptions = PrintOptions(
        singleLineTextElements = true,
        indent = "  ",
        useSelfClosingTags = true
    )

    fun fromPageData(pcgtsList: List<PcGts>): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val currentDate = LocalDateTime.now().format(formatter)
//        val metadataMap = entry.metadata.associate { it.field to it.value }
//        val projectName = projectConfig.projectName
//        val title = entry.name
//        val editorName = conversionConfig.editor.name
//        val editorId = conversionConfig.editor.id
//        val editorUrl = conversionConfig.editor.url
//
//        val letterMetadata = conversionConfig.letterMetadata!!
        val graphicUrls = pcgtsList.map { it.page.imageFilename }
        return xml("TEI") {
            prologNodes("letter")
            xmlns = "http://www.tei-c.org/ns/1.0"
            teiHeaderNode(
                title = "title",
                editorId = "editorId",
                editorName = "editorName",
                editorUrl = "editorUrl",
                currentDate = currentDate,
                projectName = "projectName",
                metadataMap = mapOf()
            )
            facsimileNode(graphicUrls)
            textNode(pcgtsList)
            standOffNode()
        }.toString(printOptions = printOptions)
    }

    private fun Node.prologNodes(projectType: String) {
        globalProcessingInstruction("editem", Pair("template", projectType))
        globalProcessingInstruction(
            "xml-model",
            Pair("href", "http://xmlschema.huygens.knaw.nl/editem-$projectType.rng"),
            Pair("type", "application/xml"),
            Pair("schematypens", "http://relaxng.org/ns/structure/1.0"),
        )
        globalProcessingInstruction(
            "xml-model",
            Pair("href", "http://xmlschema.huygens.knaw.nl/editem-$projectType.rng"),
            Pair("type", "application/xml"),
            Pair("schematypens", "http://purl.oclc.org/dsdl/schematron"),
        )
        version = XmlVersion.V10
        encoding = "UTF-8"
    }

    private fun Node.teiHeaderNode(
        title: String,
        editorId: String,
        editorName: String,
        editorUrl: String,
        currentDate: String,
        projectName: String,
        metadataMap: Map<String, String>,
//        letterMetadata: LetterMetadataConfig
    ) {
        "teiHeader" {
            fileDesc(
                title,
                editorId,
                editorName,
                editorUrl,
                currentDate,
                projectName,
                metadataMap,
//                letterMetadata
            )
            profileDesc(metadataMap)
        }
    }

    private fun Node.fileDesc(
        title: String,
        editorId: String,
        editorName: String,
        editorUrl: String,
        currentDate: String,
        projectName: String,
        metadataMap: Map<String, String>,
//        letterMetadata: LetterMetadataConfig
    ) {
        "fileDesc" {
            "titleStmt" {
                "title" {
                    -title
                }
                "editor" {
                    attribute("xml:id", editorId)
                    -editorName
                }
            }
            "publicationStmt" {
                "publisher" {
                    "name" {
                        attribute("ref", "https://huygens.knaw.nl")
                        -"Huygens Institute for the History and Cultures of the Netherlands (KNAW)"
                    }
                }
                "date" {
                    attribute("when", currentDate)
                    -currentDate
                }
//                "ptr" {
//                    attribute("target", "https://$projectName.huygens.knaw.nl/edition/entry/${entry.id}")
//                }
            }
            "sourceDesc" {
                "msDesc" {
                    "msIdentifier" {
                        "country" {}
//                        "settlement" { metadataMap[letterMetadata.settlement] ?: "" }
//                        "institution" { metadataMap[letterMetadata.institution] ?: "" }
//                        //                                "repository" { }
//                        //                                { "collection" { -(metadataMap[conversionConfig.letterMetadata.collection] ?: "") } }
//                        "idno" { -(metadataMap[letterMetadata.idno] ?: "") }
                    }
                    "physDesc" {
                        "objectDesc" {
                            attribute("form", "letter")
                        }
                    }
                }
            }
        }
    }

    private fun Node.profileDesc(
        metadataMap: Map<String, String>,
    ) {
        "profileDesc" {
            "correspDesc" {
//                sentCorrespActionNode(metadataMap)
//
//                val receiveString = metadataMap[letterMetadata.recipient] ?: ""
//                val (firstReceivers, forwardReceivers) = receiveString.biSplit("-->")
//                correspActionNode(
//                    "received",
//                    firstReceivers,
//                    metadataMap[letterMetadata.recipientPlace]
//                )
//                forwardReceivers?.let {
//                    correspActionNode(
//                        "received",
//                        forwardReceivers,
//                        metadataMap[letterMetadata.recipientPlace]
//                    )
//                }
            }
        }
    }

    //
    private fun Node.facsimileNode(
        graphicUrls: List<String>,
    ) {
        if (graphicUrls.isNotEmpty()) {
            "facsimile" {
                graphicUrls.forEachIndexed { i, url ->
                    "surface" {
                        attribute("n", "${i + 1}")
                        attribute("xml:id", "s${i + 1}")
                        "graphic" {
                            attribute("url", url)
                        }
                    }
                }
            }
        }
    }

    private fun Node.textNode(pcgtsList: List<PcGts>) {
        val parCounter = AtomicInteger(1)
        "text" {
            "body" {
                attribute("divRole", "doc-sections")
                "div" {
                    attribute("type", "original")
                    attribute("xml:lang", "nl")
                    -"\n"
                    pcgtsList.map { it.page }
                        .forEachIndexed { pageIndex, page ->
                            val pageNum = pageIndex + 1
                            "pb" {
                                attribute("f", "$pageNum")
                                attribute("n", "$pageNum")
                                attribute("xml:id", "pb-orig-$pageNum")
                                attribute("facs", "#s$pageNum")
                            }
                            page.textRegions.forEach { tr ->
                                val parNum = parCounter.getAndIncrement()
                                "p" {
                                    attribute("n", parNum)
                                    attribute("xml:id", "p-orig-$parNum")
                                    -"\n"
                                    -tr.textLines.map { it.text }.joinToString("\n")
                                    -"\n"
                                }
                            }
                        }
                }
            }
        }
    }

    private fun Node.standOffNode() {
        "standOff" {
            "listAnnotation" {
                attribute("type", "notes")
                "note" {
                    attribute("xml:id", "note_1")
                    attribute("n", 1)
                    "p" { -"TODO" }
                }
            }
        }
    }

//    private fun Node.correspActionNode(
//        type: String,
//        correspondentString: String,
//        recipientPlace: String?
//    ) {
//        val (personReceivers, orgReceivers) = correspondentString.biSplit("#")
//        "correspAction" {
//            attribute("type", type)
//            personReceivers.split("/")
//                .forEach { personRsNode(it) }
//            orgReceivers?.let {
//                it.split("/").forEach { org -> orgRsNode(org) }
//            }
//            recipientPlace?.let { place ->
//                "placeName" {
//                    -place
//                }
//            }
//        }
//    }
//
//    private fun Node.sentCorrespActionNode(
//        metadataMap: Map<String, String>
//    ) {
//        val letterMetadata = conversionConfig.letterMetadata!!
//        val senders = (metadataMap[letterMetadata.sender] ?: "").split("/")
//        val date = metadataMap[letterMetadata.date] ?: ""
//        val place =
//            metadataMap[letterMetadata.senderPlace] ?: ""
//        "correspAction" {
//            attribute("type", "sent")
//            senders
//                .forEach { sender ->
//                    val (person, org) = sender.biSplit("#")
//                    personRsNode(person)
//                    org?.let { orgRsNode(org) }
//                }
//            "date" {
//                dateAttributeFactory?.getDateAttributes(date)?.forEach {
//                    attribute(it.key, it.value)
//                }
//                -date
//            }
//            "placeName" {
//                -place
//            }
//        }
//    }
//
//    private fun Node.personRsNode(
//        personName: String
//    ) {
//        val personId = projectConfig.personIds[personName] ?: ""
//        "rs" {
//            attribute("type", "person")
//            if (personId.isNotEmpty()) {
//                attribute("ref", "bio.xml#$personId")
//            }
//            -personName
//        }
//    }
//
//    private fun Node.orgRsNode(org: String) {
//        val orgId = ""
//        "rs" {
//            attribute("type", "org")
//            if (orgId.isNotEmpty()) {
//                attribute("ref", "orgs.xml#$orgId")
//            }
//            -org
//        }
//    }
//
//

}

