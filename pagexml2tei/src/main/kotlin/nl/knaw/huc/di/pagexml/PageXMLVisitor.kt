package nl.knaw.huc.di.pagexml

import org.apache.logging.log4j.kotlin.logger
import nl.knaw.huygens.tei.Comment
import nl.knaw.huygens.tei.CommentHandler
import nl.knaw.huygens.tei.DelegatingVisitor
import nl.knaw.huygens.tei.Element
import nl.knaw.huygens.tei.ElementHandler
import nl.knaw.huygens.tei.Traversal
import nl.knaw.huygens.tei.Traversal.NEXT
import nl.knaw.huygens.tei.handlers.DefaultElementHandler
import nl.knaw.huygens.tei.handlers.XmlTextHandler

class PageXMLVisitor : DelegatingVisitor<PageXMLContext>(PageXMLContext()) {


    init {
        setTextHandler(XmlTextHandler())
        setCommentHandler(IgnoreCommentHandler())
        setDefaultElementHandler(UnhandledElementHandler())
        addElementHandler(DefaultElementHandler(), "PcGts", "Metadata", "TextEquiv")
        addElementHandler(CreatorHandler(), "Creator")
        addElementHandler(CreatedHandler(), "Created")
        addElementHandler(LastChangeHandler(), "LastChange")
        addElementHandler(TranskribusMetadataHandler(), "TranskribusMetadata")
        addElementHandler(UnicodeHandler(), "Unicode")
        addElementHandler(CoordsHandler(), "Coords")
        addElementHandler(BaselineHandler(), "Baseline")
        addElementHandler(PageHandler(), "Page")
        addElementHandler(ReadingOrderHandler(), "ReadingOrder")
        addElementHandler(OrderedGroupHandler(), "OrderedGroup")
        addElementHandler(RegionRefIndexedHandler(), "RegionRefIndexed")
        addElementHandler(TextRegionHandler(), "TextRegion")
        addElementHandler(TextLineHandler(), "TextLine")
        addElementHandler(WordHandler(), "Word")
    }

    internal class CreatorHandler : ElementHandler<PageXMLContext> {
        override fun enterElement(element: Element, context: PageXMLContext): Traversal {
            context.openLayer()
            return NEXT
        }

        override fun leaveElement(element: Element, context: PageXMLContext): Traversal {
            context.metadataBuilder.creator = context.closeLayer()
            return NEXT
        }
    }

    internal class CreatedHandler : ElementHandler<PageXMLContext> {
        override fun enterElement(element: Element, context: PageXMLContext): Traversal {
            context.openLayer()
            return NEXT
        }

        override fun leaveElement(element: Element, context: PageXMLContext): Traversal {
            context.metadataBuilder.created = context.closeLayer().toDate()
            return NEXT
        }
    }

    class LastChangeHandler : ElementHandler<PageXMLContext> {
        override fun enterElement(element: Element, context: PageXMLContext): Traversal {
            context.openLayer()
            return NEXT
        }

        override fun leaveElement(element: Element, context: PageXMLContext): Traversal {
            context.metadataBuilder.lastChange = context.closeLayer().toDate()
            return NEXT
        }
    }

    class UnicodeHandler : ElementHandler<PageXMLContext> {
        override fun enterElement(element: Element, context: PageXMLContext): Traversal {
            context.openLayer()
            return NEXT
        }

        override fun leaveElement(element: Element, context: PageXMLContext): Traversal {
            context.unicodeStack.addFirst(context.closeLayer())
            return NEXT
        }
    }

    class CoordsHandler : ElementHandler<PageXMLContext> {
        override fun enterElement(element: Element, context: PageXMLContext): Traversal {
            context.coordsStack.addFirst(element.getAttribute("points").toCoords())
            return NEXT
        }

        override fun leaveElement(element: Element, context: PageXMLContext): Traversal {
            return NEXT
        }
    }

    class BaselineHandler : ElementHandler<PageXMLContext> {
        override fun enterElement(element: Element, context: PageXMLContext): Traversal {
            context.baselineStack.addFirst(element.getAttribute("points").toBaseline())
            return NEXT
        }

        override fun leaveElement(element: Element, context: PageXMLContext): Traversal {
            return NEXT
        }
    }

    internal class TranskribusMetadataHandler : ElementHandler<PageXMLContext> {
        override fun enterElement(element: Element, context: PageXMLContext): Traversal {
            return NEXT
        }

        override fun leaveElement(element: Element, context: PageXMLContext): Traversal {
            context.metadataBuilder.transkribusMetadata = TranskribusMetadata(
                docId = element.getAttribute("docId"),
                pageId = element.getAttribute("pageId"),
                pageNr = element.getAttribute("pageNr"),
                tsid = element.getAttribute("tsid"),
                status = element.getAttribute("status"),
                userId = element.getAttribute("userId"),
                imgUrl = element.getAttribute("imgUrl"),
                xmlUrl = element.getAttribute("xmlUrl"),
                imageID = element.getAttribute("imageId")
            )
            return NEXT
        }
    }

    class PageHandler : ElementHandler<PageXMLContext> {
        override fun enterElement(element: Element, context: PageXMLContext): Traversal {
            context.pageBuilder.apply {
                reset()
                imageFilename = element.getAttribute("imageFilename")
                imageWidth = element.getAttribute("imageWidth").toInt()
                imageHeight = element.getAttribute("imageHeight").toInt()
            }
            return NEXT
        }

        override fun leaveElement(element: Element, context: PageXMLContext): Traversal {
            context.pages.add(context.pageBuilder.build())
            return NEXT
        }
    }

    internal class ReadingOrderHandler : ElementHandler<PageXMLContext> {
        override fun enterElement(element: Element, context: PageXMLContext): Traversal {
            context.readingOrderBuilder.reset()
            return NEXT
        }

        override fun leaveElement(element: Element, context: PageXMLContext): Traversal {
            context.pageBuilder.readingOrder = context.readingOrderBuilder.build()
            return NEXT
        }
    }

    internal class OrderedGroupHandler : ElementHandler<PageXMLContext> {
        override fun enterElement(element: Element, context: PageXMLContext): Traversal {
            context.orderedGroupBuilder.apply {
                reset()
                id = element.getAttribute("id")
                caption = element.getAttribute("caption")
            }

            return NEXT
        }

        override fun leaveElement(element: Element, context: PageXMLContext): Traversal {
            context.readingOrderBuilder.orderedGroups.add(context.orderedGroupBuilder.build())
            return NEXT
        }
    }

    internal class RegionRefIndexedHandler : ElementHandler<PageXMLContext> {
        override fun enterElement(element: Element, context: PageXMLContext): Traversal {
            context.orderedGroupBuilder.regionRefs.add(
                RegionRefIndexed(
                    index = element.getAttribute("index").toInt(),
                    regionRef = element.getAttribute("regionRef")
                )
            )
            return NEXT
        }

        override fun leaveElement(element: Element, context: PageXMLContext): Traversal {
            return NEXT
        }
    }

    internal class TextRegionHandler : ElementHandler<PageXMLContext> {
        override fun enterElement(element: Element, context: PageXMLContext): Traversal {
            context.textRegionBuilder.apply {
                reset()
                id = element.getAttribute("id")
                orientation = element.getAttribute("orientation")
                custom = element.getAttribute("custom")
            }
            return NEXT
        }

        override fun leaveElement(element: Element, context: PageXMLContext): Traversal {
            context.apply {
                textRegionBuilder.coords = coordsStack.removeFirst()
                textRegionBuilder.text = unicodeStack.removeFirst()
                pageBuilder.textRegions.add(textRegionBuilder.build())
            }
            return NEXT
        }
    }

    internal class TextLineHandler : ElementHandler<PageXMLContext> {
        override fun enterElement(element: Element, context: PageXMLContext): Traversal {
            context.textLineBuilder.apply {
                reset()
                id = element.getAttribute("id")
                custom = element.getAttribute("custom")
            }
            return NEXT
        }

        override fun leaveElement(element: Element, context: PageXMLContext): Traversal {
            context.apply {
                textLineBuilder.coords = coordsStack.removeFirst()
                textLineBuilder.text = unicodeStack.removeFirst()
                textLineBuilder.baseline = baselineStack.removeFirst()
                textRegionBuilder.textLines.add(textLineBuilder.build())
            }

            return NEXT
        }
    }

    internal class WordHandler : ElementHandler<PageXMLContext> {
        override fun enterElement(element: Element, context: PageXMLContext): Traversal {
            context.wordBuilder.apply {
                reset()
                id = element.getAttribute("id")
                custom = element.getAttribute("custom")
            }
            return NEXT
        }

        override fun leaveElement(element: Element, context: PageXMLContext): Traversal {
            context.apply {
                wordBuilder.coords = coordsStack.removeFirst()
                wordBuilder.text = unicodeStack.removeFirst()
                textLineBuilder.words.add(wordBuilder.build())
            }

            return NEXT
        }
    }

    internal class IgnoreCommentHandler : CommentHandler<PageXMLContext> {
        override fun visitComment(comment: Comment?, context: PageXMLContext?): Traversal = NEXT
    }

    internal class UnhandledElementHandler : ElementHandler<PageXMLContext> {
        override fun enterElement(element: Element, context: PageXMLContext): Traversal {
            logger.error { "Unhandled element: $element" }
            context.unhandledElements.add(element.name)
            return NEXT
        }

        override fun leaveElement(element: Element, context: PageXMLContext): Traversal {
            return NEXT
        }
    }
}

