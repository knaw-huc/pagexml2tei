package nl.knaw.huc.di.pagexml

import kotlin.concurrent.atomics.ExperimentalAtomicApi
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
        addElementHandler(DefaultElementHandler(), "PcGts", "Metadata", "TextEquiv", "Labels")

        addElementHandler(CreatorHandler(), "Creator")
        addElementHandler(PxCommentHandler(), "Comment")
        addElementHandler(PxCommentHandler(), "Comment")
        addElementHandler(CreatedHandler(), "Created")
        addElementHandler(LastChangeHandler(), "LastChange")
        addElementHandler(TranskribusMetadataHandler(), "TranskribusMetadata")
        addElementHandler(MetadataItemHandler(), "MetadataItem")
        addElementHandler(LabelHandler(), "Label")

        addElementHandler(PageHandler(), "Page")
        addElementHandler(TextRegionHandler(), "TextRegion")
        addElementHandler(TextLineHandler(), "TextLine")
        addElementHandler(WordHandler(), "Word")

        addElementHandler(ReadingOrderHandler(), "ReadingOrder")
        addElementHandler(OrderedGroupHandler(), "OrderedGroup")
        addElementHandler(RegionRefIndexedHandler(), "RegionRefIndexed")

        addElementHandler(UnicodeHandler(), "Unicode")
        addElementHandler(CoordsHandler(), "Coords")
        addElementHandler(BaselineHandler(), "Baseline")
        addElementHandler(TextStyleHandler(), "TextStyle")
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

    internal class PxCommentHandler : ElementHandler<PageXMLContext> {
        override fun enterElement(element: Element, context: PageXMLContext): Traversal {
            context.openLayer()
            return NEXT
        }

        override fun leaveElement(element: Element, context: PageXMLContext): Traversal {
            context.metadataBuilder.comment = context.closeLayer()
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
        @OptIn(ExperimentalAtomicApi::class)
        override fun enterElement(element: Element, context: PageXMLContext): Traversal {
            val coords = element.getAttribute("points").toCoords()
            when (context.segmentLevel.load()) {
                PageXMLContext.SegmentLevel.TEXT_REGION -> context.textRegionCoordsStack.addFirst(coords)
                PageXMLContext.SegmentLevel.TEXT_LINE -> context.textLineCoordsStack.addFirst(coords)
                PageXMLContext.SegmentLevel.WORD -> context.wordCoordsStack.addFirst(coords)
            }
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

    internal class MetadataItemHandler : ElementHandler<PageXMLContext> {
        override fun enterElement(element: Element, context: PageXMLContext): Traversal {
            context.metadataItemBuilder.apply {
                reset()
                type = element.getAttribute("type")
                name = element.getAttribute("name")
                value = element.getAttribute("value")
            }
            return NEXT
        }

        override fun leaveElement(element: Element, context: PageXMLContext): Traversal {
            context.metadataBuilder.metadataItems.add(context.metadataItemBuilder.build())
            return NEXT
        }
    }

    internal class LabelHandler : ElementHandler<PageXMLContext> {
        override fun enterElement(element: Element, context: PageXMLContext): Traversal {
            val type = element.getAttribute("type")
            val value = element.getAttribute("value")
            context.metadataItemBuilder.labels[type] = value
            return NEXT
        }

        override fun leaveElement(element: Element, context: PageXMLContext): Traversal {
            context.metadataBuilder.metadataItems.add(context.metadataItemBuilder.build())
            return NEXT
        }
    }

    internal class PageHandler : ElementHandler<PageXMLContext> {
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
        @OptIn(ExperimentalAtomicApi::class)
        override fun enterElement(element: Element, context: PageXMLContext): Traversal {
            context.segmentLevel.store(PageXMLContext.SegmentLevel.TEXT_REGION)
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
                textRegionBuilder.coords = textRegionCoordsStack.removeFirst()
                textRegionBuilder.text = if (unicodeStack.isEmpty()) "" else unicodeStack.removeFirst()
                pageBuilder.textRegions.add(textRegionBuilder.build())
            }
            return NEXT
        }
    }

    internal class TextLineHandler : ElementHandler<PageXMLContext> {
        @OptIn(ExperimentalAtomicApi::class)
        override fun enterElement(element: Element, context: PageXMLContext): Traversal {
            context.segmentLevel.store(PageXMLContext.SegmentLevel.TEXT_LINE)
            context.textLineBuilder.apply {
                reset()
                id = element.getAttribute("id")
                custom = element.getAttribute("custom")
            }
            return NEXT
        }

        override fun leaveElement(element: Element, context: PageXMLContext): Traversal {
            context.apply {
                textLineBuilder.coords = textLineCoordsStack.removeFirst()
                textLineBuilder.text = if (unicodeStack.isEmpty()) "" else unicodeStack.removeFirst()
                textLineBuilder.baseline = baselineStack.removeFirst()
                textRegionBuilder.textLines.add(textLineBuilder.build())
            }

            return NEXT
        }
    }

    internal class WordHandler : ElementHandler<PageXMLContext> {
        @OptIn(ExperimentalAtomicApi::class)
        override fun enterElement(element: Element, context: PageXMLContext): Traversal {
            context.segmentLevel.store(PageXMLContext.SegmentLevel.WORD)
            context.wordBuilder.apply {
                reset()
                id = element.getAttribute("id")
                custom = element.getAttribute("custom")
            }
            return NEXT
        }

        override fun leaveElement(element: Element, context: PageXMLContext): Traversal {
            context.apply {
                wordBuilder.coords = wordCoordsStack.removeFirst()
                wordBuilder.text = unicodeStack.removeFirst()
                textLineBuilder.words.add(wordBuilder.build())
            }
            return NEXT
        }
    }

    internal class TextStyleHandler : ElementHandler<PageXMLContext> {
        override fun enterElement(element: Element, context: PageXMLContext): Traversal {
            context.textLineBuilder.textStyle = TextStyle(xHeight = element.getAttribute("xHeight").toInt())
            return NEXT
        }

        override fun leaveElement(element: Element, context: PageXMLContext): Traversal {
            return NEXT
        }
    }

    internal class IgnoreCommentHandler : CommentHandler<PageXMLContext> {
        override fun visitComment(comment: Comment, context: PageXMLContext): Traversal = NEXT
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

