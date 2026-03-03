package nl.knaw.huc.di.pagexml

import java.util.Date

data class PcGts(
    val metadata: PxMetadata,
    val pages: List<PxPage>
)

data class PxMetadata(
    val creator: String,
    val created: Date,
    val lastChange: Date,
    val transkribusMetadata: TranskribusMetadata
) {
    class Builder {
        var creator: String? = null
        var created: Date? = null
        var lastChange: Date? = null
        var transkribusMetadata: TranskribusMetadata? = null

        fun build(): PxMetadata = PxMetadata(
            creator = creator!!,
            created = created!!,
            lastChange = lastChange!!,
            transkribusMetadata = transkribusMetadata!!
        )
    }
}

data class TranskribusMetadata(
    val docId: String,
    val pageId: String,
    val pageNr: String,
    val tsid: String,
    val status: String,
    val userId: String,
    val imgUrl: String,
    val xmlUrl: String,
    val imageID: String,
)

data class PxPage(
    val imageFilename: String,
    val imageWidth: Int,
    val imageHeight: Int,
    val readingOrder: ReadingOrder?,
    val textRegions: List<PxTextRegion>,
) {
    class Builder {
        var imageFilename: String? = null
        var imageWidth: Int? = null
        var imageHeight: Int? = null
        var readingOrder: ReadingOrder? = null
        var textRegions: MutableList<PxTextRegion> = mutableListOf()

        fun build(): PxPage = PxPage(
            imageFilename = imageFilename!!,
            imageWidth = imageWidth!!,
            imageHeight = imageHeight!!,
            readingOrder = readingOrder,
            textRegions = textRegions
        )

        fun reset() {
            imageFilename = null
            imageWidth = null
            imageHeight = null
            readingOrder = null
            textRegions = mutableListOf()
        }
    }
}

data class ReadingOrder(
    val orderedGroups: List<OrderedGroup>
) {
    class Builder {
        var orderedGroups: MutableList<OrderedGroup> = mutableListOf()

        fun build(): ReadingOrder = ReadingOrder(orderedGroups)

        fun reset() {
            orderedGroups = mutableListOf()
        }
    }
}

data class OrderedGroup(
    val id: String,
    val caption: String,
    val regionRefs: List<RegionRefIndexed>,
) {
    class Builder {
        var id: String? = null
        var caption: String? = null
        var regionRefs: MutableList<RegionRefIndexed> = mutableListOf()

        fun build(): OrderedGroup = OrderedGroup(
            id = id!!,
            caption = caption!!,
            regionRefs = regionRefs
        )

        fun reset() {
            id = null
            caption = null
            regionRefs = mutableListOf()
        }
    }
}

data class RegionRefIndexed(
    val index: Int,
    val regionRef: String
)

data class PxTextRegion(
    val id: String,
    val orientation: String,
    val custom: String,
    val coords: Coords,
    val textLines: List<PxTextLine>,
    val text: String,
) {
    class Builder {
        var id: String? = null
        var orientation: String? = null
        var custom: String? = null
        var coords: Coords? = null
        var textLines = mutableListOf<PxTextLine>()
        var text: String? = null

        fun build(): PxTextRegion = PxTextRegion(
            id = id!!,
            orientation = orientation!!,
            custom = custom!!,
            coords = coords!!,
            textLines = textLines,
            text = text!!
        )

        fun reset() {
            id = null
            orientation = null
            custom = null
            coords = null
            textLines = mutableListOf()
            text = null
        }
    }
}

data class Coords(
    val points: List<Point>
)

data class Point(
    val x: Int,
    val y: Int
)

data class PxTextLine(
    val id: String,
    val custom: String,
    val coords: Coords,
    val baseline: Baseline,
    val words: List<PxWord>,
    val text: String,
) {
    class Builder {
        var id: String? = null
        var custom: String? = null
        var coords: Coords? = null
        var baseline: Baseline? = null
        var words: MutableList<PxWord> = mutableListOf()
        var text: String? = null

        fun build(): PxTextLine = PxTextLine(
            id = id!!,
            custom = custom!!,
            coords = coords!!,
            baseline = baseline!!,
            words = words,
            text = text!!
        )

        fun reset() {
            id = null
            custom = null
            coords = null
            baseline = null
            words = mutableListOf()
            text = null
        }
    }
}

data class Baseline(
    val points: List<Point>
)

data class PxWord(
    val id: String,
    val custom: String,
    val coords: Coords,
    val text: String,
) {
    class Builder {
        var id: String? = null
        var custom: String? = null
        var coords: Coords? = null
        var text: String? = null

        fun build(): PxWord = PxWord(
            id = id!!,
            custom = custom!!,
            coords = coords!!,
            text = text!!
        )

        fun reset() {
            id = null
            custom = null
            coords = null
            text = null
        }
    }
}
