package nl.knaw.huc.di.pagexml

import nl.knaw.huygens.tei.XmlContext

class PageXMLContext : XmlContext() {
    enum class SegmentLevel {
        TEXT_REGION,
        TEXT_LINE,
        WORD
    }

    val metadataBuilder = PxMetadata.Builder()
    val pages = mutableListOf<PxPage>()

    val pageBuilder = PxPage.Builder()
    val readingOrderBuilder = ReadingOrder.Builder()
    val orderedGroupBuilder = OrderedGroup.Builder()
    val textRegionBuilder = PxTextRegion.Builder()
    val textLineBuilder = PxTextLine.Builder()
    val wordBuilder = PxWord.Builder()
    val metadataItemBuilder = PxMetadataItem.Builder()

    val textRegionUnicodeStack = ArrayDeque<String>()
    val textLineUnicodeStack = ArrayDeque<String>()
    val wordUnicodeStack = ArrayDeque<String>()

    val textRegionCoordsStack = ArrayDeque<Coords>()
    val textLineCoordsStack = ArrayDeque<Coords>()
    val wordCoordsStack = ArrayDeque<Coords>()

    val baselineStack = ArrayDeque<Baseline>()

    val segmentLevel = ArrayDeque<SegmentLevel>()

    val unhandledElements = mutableSetOf<String>()

    fun buildPcGts(): PcGts {
        return PcGts(
            metadata = metadataBuilder.build(),
            pages = pages
        )
    }
}