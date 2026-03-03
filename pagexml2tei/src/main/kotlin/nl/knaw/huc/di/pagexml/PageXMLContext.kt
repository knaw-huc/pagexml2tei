package nl.knaw.huc.di.pagexml

import nl.knaw.huygens.tei.XmlContext

class PageXMLContext : XmlContext() {
    val metadataBuilder = PxMetadata.Builder()
    val pages = mutableListOf<PxPage>()

    val pageBuilder = PxPage.Builder()
    val readingOrderBuilder = ReadingOrder.Builder()
    val orderedGroupBuilder = OrderedGroup.Builder()
    val textRegionBuilder = PxTextRegion.Builder()
    val textLineBuilder = PxTextLine.Builder()
    val wordBuilder = PxWord.Builder()

    val unicodeStack = ArrayDeque<String>()
    val coordsStack = ArrayDeque<Coords>()
    val baselineStack = ArrayDeque<Baseline>()

    val unhandledElements = mutableSetOf<String>()

    fun buildPcGts(): PcGts {
        return PcGts(
            metadata = metadataBuilder.build(),
            pages = pages
        )
    }
}