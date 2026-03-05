package org.primaresearch.page.content

import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlAttribute
import jakarta.xml.bind.annotation.XmlElement
import jakarta.xml.bind.annotation.XmlElements
import jakarta.xml.bind.annotation.XmlEnum
import jakarta.xml.bind.annotation.XmlEnumValue
import jakarta.xml.bind.annotation.XmlRootElement
import kotlinx.serialization.Serializable

//const val PAGE_NS = "http://schema.primaresearch.org/PAGE/gts/pagecontent/2019-07-15"
const val PAGE_NS = "http://schema.primaresearch.org/PAGE/gts/pagecontent/2013-07-15"

// ─────────────────────────────────────────────
// Root
// ─────────────────────────────────────────────

@Serializable
@XmlRootElement(name = "PcGts", namespace = PAGE_NS)
@XmlAccessorType(XmlAccessType.FIELD)
/** Page Content - Ground Truth and Storage */
//    http://www.primaresearch.org/schema/PAGE/gts/pagecontent/2019-07-15/pagecontent.xsd
data class PcGts(
    @field:XmlElement(name = "Metadata", namespace = PAGE_NS, required = true)
    val metadata: Metadata = Metadata(),

    @field:XmlElement(name = "Page", namespace = PAGE_NS, required = true)
    val page: Page = Page(),

    @field:XmlAttribute
    val pcGtsId: String? = null
)

// ─────────────────────────────────────────────
// Metadata
// ─────────────────────────────────────────────
@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class Metadata(
    @field:XmlElement(name = "Creator", namespace = PAGE_NS)
    val creator: String? = null,

    @field:XmlElement(name = "Created", namespace = PAGE_NS)
    // The timestamp has to be in UTC (Coordinated Universal Time) and not local time.
    val created: String? = null,           // xs:dateTime as String; parse with DateTimeFormatter

    @field:XmlElement(name = "LastChange", namespace = PAGE_NS)
    val lastChange: String? = null, // The timestamp has to be in UTC (Coordinated Universal Time) and not local time.

    @field:XmlElement(name = "Comments", namespace = PAGE_NS)
    val comments: String? = null,

    @field:XmlElement(name = "TranskribusMetadata", namespace = PAGE_NS)
    val transkribusMetadata: TranskribusMetadata? = null,

    @field:XmlAttribute
    val externalRef: String? = null
)

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class TranskribusMetadata(
    @field:XmlAttribute val docId: Int? = null,
    @field:XmlAttribute val pageId: Int? = null,
    @field:XmlAttribute val pageNr: Int? = null,
    @field:XmlAttribute val tsId: Int? = null,
    @field:XmlAttribute val prevTsId: Int? = null,
    @field:XmlAttribute val status: String? = null,
    @field:XmlAttribute val userId: Int? = null,
    @field:XmlAttribute val imgUrl: String? = null,
    @field:XmlAttribute val thumbUrl: String? = null
)

// ─────────────────────────────────────────────
// Page
// ─────────────────────────────────────────────
@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class Page(
    // Contains the image file name including the file extension.
    @field:XmlAttribute(required = true) val imageFilename: String = "",

    @field:XmlAttribute(required = true) val imageWidth: Int = 0,
    @field:XmlAttribute(required = true) val imageHeight: Int = 0,
    @field:XmlAttribute val imageColor: ImageColorSimpleType? = null,
    @field:XmlAttribute val imagePhotometricInterpretation: String? = null,
    @field:XmlAttribute val type: PageTypeSimpleType? = null,
    @field:XmlAttribute val orientation: Double? = null,
    @field:XmlAttribute val conf: Double? = null,
    @field:XmlAttribute val custom: String? = null,
    @field:XmlAttribute val comments: String? = null,

    @field:XmlElement(name = "AlternativeImage", namespace = PAGE_NS)
    val alternativeImages: MutableList<AlternativeImage> = mutableListOf(),

    @field:XmlElement(name = "Border", namespace = PAGE_NS)
    val border: Border? = null,

    @field:XmlElement(name = "PrintSpace", namespace = PAGE_NS)
    val printSpace: PrintSpace? = null,

    @field:XmlElement(name = "ReadingOrder", namespace = PAGE_NS)
    val readingOrder: ReadingOrder? = null,

    @field:XmlElement(name = "Layers", namespace = PAGE_NS)
    val layers: Layers? = null,

    // Region types — all share the same parent slot
    @field:XmlElement(name = "TextRegion", namespace = PAGE_NS)
    val textRegions: MutableList<TextRegion> = mutableListOf(),

    @field:XmlElement(name = "ImageRegion", namespace = PAGE_NS)
    val imageRegions: MutableList<ImageRegion> = mutableListOf(),

    @field:XmlElement(name = "LineDrawingRegion", namespace = PAGE_NS)
    val lineDrawingRegions: MutableList<LineDrawingRegion> = mutableListOf(),

    @field:XmlElement(name = "GraphicRegion", namespace = PAGE_NS)
    val graphicRegions: MutableList<GraphicRegion> = mutableListOf(),

    @field:XmlElement(name = "TableRegion", namespace = PAGE_NS)
    val tableRegions: MutableList<TableRegion> = mutableListOf(),

    @field:XmlElement(name = "ChartRegion", namespace = PAGE_NS)
    val chartRegions: MutableList<ChartRegion> = mutableListOf(),

    @field:XmlElement(name = "MapRegion", namespace = PAGE_NS)
    val mapRegions: MutableList<MapRegion> = mutableListOf(),

    @field:XmlElement(name = "SeparatorRegion", namespace = PAGE_NS)
    val separatorRegions: MutableList<SeparatorRegion> = mutableListOf(),

    @field:XmlElement(name = "MathsRegion", namespace = PAGE_NS)
    val mathsRegions: MutableList<MathsRegion> = mutableListOf(),

    @field:XmlElement(name = "ChemRegion", namespace = PAGE_NS)
    val chemRegions: MutableList<ChemRegion> = mutableListOf(),

    @field:XmlElement(name = "MusicRegion", namespace = PAGE_NS)
    val musicRegions: MutableList<MusicRegion> = mutableListOf(),

    @field:XmlElement(name = "AdvertRegion", namespace = PAGE_NS)
    val advertRegions: MutableList<AdvertRegion> = mutableListOf(),

    @field:XmlElement(name = "NoiseRegion", namespace = PAGE_NS)
    val noiseRegions: MutableList<NoiseRegion> = mutableListOf(),

    @field:XmlElement(name = "UnknownRegion", namespace = PAGE_NS)
    val unknownRegions: MutableList<UnknownRegion> = mutableListOf(),

    @field:XmlElement(name = "CustomRegion", namespace = PAGE_NS)
    val customRegions: MutableList<CustomRegion> = mutableListOf(),

    @field:XmlElement(name = "UserDefined", namespace = PAGE_NS)
    val userDefined: UserDefined? = null
)

// ─────────────────────────────────────────────
// Geometry primitives
// ─────────────────────────────────────────────

/** Space-separated "x,y" pairs, e.g. "100,200 300,400" */
@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class CoordsType(
    @field:XmlAttribute(required = true) val points: String = "",
    @field:XmlAttribute val conf: Double? = null
)

/** Same structure as CoordsType but semantically a baseline */
@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class BaselineType(
    @field:XmlAttribute(required = true) val points: String = "",
    @field:XmlAttribute val conf: Double? = null
)

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class AlternativeImage(
    @field:XmlAttribute(required = true) val filename: String = "",
    @field:XmlAttribute val comments: String? = null,
    @field:XmlAttribute val conf: Double? = null
)

// ─────────────────────────────────────────────
// Reading order
// ─────────────────────────────────────────────
@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class ReadingOrder(
    @field:XmlElement(name = "OrderedGroup", namespace = PAGE_NS) val orderedGroup: OrderedGroup? = null,
    @field:XmlElement(name = "UnorderedGroup", namespace = PAGE_NS) val unorderedGroup: UnorderedGroup? = null
)

@Serializable
sealed class OrderedGroupItem

@Serializable
sealed class UnorderedGroupItem

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class OrderedGroup(
    @field:XmlAttribute(required = true) val id: String = "",
    @field:XmlAttribute val caption: String? = null,
    @field:XmlAttribute val custom: String? = null,
    @field:XmlAttribute val comments: String? = null,

    @field:XmlElements(
        XmlElement(name = "RegionRefIndexed", namespace = PAGE_NS, type = RegionRefIndexed::class),
        XmlElement(name = "OrderedGroupIndexed", namespace = PAGE_NS, type = OrderedGroupIndexed::class),
        XmlElement(name = "UnorderedGroupIndexed", namespace = PAGE_NS, type = UnorderedGroupIndexed::class)
    )
    val items: MutableList<OrderedGroupItem> = mutableListOf()
) : UnorderedGroupItem()

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class UnorderedGroup(
    @field:XmlAttribute(required = true) val id: String = "",
    @field:XmlAttribute val caption: String? = null,
    @field:XmlAttribute val custom: String? = null,
    @field:XmlAttribute val comments: String? = null,

    @field:XmlElements(
        XmlElement(name = "RegionRef", namespace = PAGE_NS, type = RegionRef::class),
        XmlElement(name = "OrderedGroup", namespace = PAGE_NS, type = OrderedGroup::class),
        XmlElement(name = "UnorderedGroup", namespace = PAGE_NS, type = UnorderedGroup::class)
    )
    val items: MutableList<UnorderedGroupItem> = mutableListOf()
) : UnorderedGroupItem()

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class OrderedGroupIndexed(
    @field:XmlAttribute(required = true) val id: String = "",
    @field:XmlAttribute(required = true) val index: Int = 0,
    @field:XmlAttribute val caption: String? = null,
    @field:XmlAttribute val custom: String? = null,
    @field:XmlAttribute val comments: String? = null,
    @field:XmlElements(
        XmlElement(name = "RegionRefIndexed", namespace = PAGE_NS, type = RegionRefIndexed::class),
        XmlElement(name = "OrderedGroupIndexed", namespace = PAGE_NS, type = OrderedGroupIndexed::class),
        XmlElement(name = "UnorderedGroupIndexed", namespace = PAGE_NS, type = UnorderedGroupIndexed::class)
    )
    val items: MutableList<OrderedGroupItem> = mutableListOf()
) : OrderedGroupItem()

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class UnorderedGroupIndexed(
    @field:XmlAttribute(required = true) val id: String = "",
    @field:XmlAttribute(required = true) val index: Int = 0,
    @field:XmlAttribute val caption: String? = null,
    @field:XmlAttribute val custom: String? = null,
    @field:XmlAttribute val comments: String? = null,
    @field:XmlElements(
        XmlElement(name = "RegionRef", namespace = PAGE_NS, type = RegionRef::class),
        XmlElement(name = "OrderedGroup", namespace = PAGE_NS, type = OrderedGroup::class),
        XmlElement(name = "UnorderedGroup", namespace = PAGE_NS, type = UnorderedGroup::class)
    )
    val items: MutableList<UnorderedGroupItem> = mutableListOf()
) : OrderedGroupItem()

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class RegionRef(@field:XmlAttribute(required = true) val regionRef: String = "") : UnorderedGroupItem()

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class RegionRefIndexed(
    @field:XmlAttribute(required = true) val regionRef: String = "",
    @field:XmlAttribute(required = true) val index: Int = 0
) : OrderedGroupItem()

// ─────────────────────────────────────────────
// Layers
// ─────────────────────────────────────────────

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class Layers(
    @field:XmlElement(name = "Layer", namespace = PAGE_NS)
    val layers: MutableList<Layer> = mutableListOf()
)

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class Layer(
    @field:XmlAttribute(required = true) val id: String = "",
    @field:XmlAttribute val caption: String? = null,
    @field:XmlAttribute val custom: String? = null,
    @field:XmlAttribute val comments: String? = null,
    @field:XmlElement(name = "RegionRef", namespace = PAGE_NS)
    val regionRefs: MutableList<RegionRef> = mutableListOf()
)

// ─────────────────────────────────────────────
// Shared region interface
// ─────────────────────────────────────────────

interface Region {
    val id: String
    val custom: String?
    val comments: String?
    val coords: CoordsType
    val userDefined: UserDefined?
    val roles: Roles?
    val alternativeImages: MutableList<AlternativeImage>
}

// ─────────────────────────────────────────────
// Text region & sub-elements
// ─────────────────────────────────────────────
@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class TextRegion(
    @field:XmlAttribute(required = true) override val id: String = "",
    @field:XmlAttribute override val custom: String? = null,
    @field:XmlAttribute override val comments: String? = null,
    @field:XmlElement(
        name = "Coords",
        namespace = PAGE_NS,
        required = true
    ) override val coords: CoordsType = CoordsType(),
    @field:XmlElement(name = "UserDefined", namespace = PAGE_NS) override val userDefined: UserDefined? = null,
    @field:XmlElement(name = "Roles", namespace = PAGE_NS) override val roles: Roles? = null,
    @field:XmlElement(
        name = "AlternativeImage",
        namespace = PAGE_NS
    ) override val alternativeImages: MutableList<AlternativeImage> = mutableListOf(),

    @field:XmlAttribute val type: TextTypeSimpleType? = null,
    @field:XmlAttribute val orientation: Double? = null,
    @field:XmlAttribute val readingDirection: ReadingDirectionSimpleType? = null,
    @field:XmlAttribute val textLineOrder: TextLineOrderSimpleType? = null,
    @field:XmlAttribute val indentLeft: Double? = null,
    @field:XmlAttribute val indentRight: Double? = null,
    @field:XmlAttribute val indentTop: Double? = null,
    @field:XmlAttribute val indentBottom: Double? = null,
    @field:XmlAttribute val readingOrientation: Double? = null,
    @field:XmlAttribute val primaryLanguage: LanguageSimpleType? = null,
    @field:XmlAttribute val secondaryLanguage: LanguageSimpleType? = null,
    @field:XmlAttribute val primaryScript: ScriptSimpleType? = null,
    @field:XmlAttribute val secondaryScript: ScriptSimpleType? = null,
    @field:XmlAttribute val production: ProductionSimpleType? = null,
    @field:XmlAttribute val conf: Double? = null,

    @field:XmlElement(name = "TextRegion", namespace = PAGE_NS)
    val nestedTextRegions: MutableList<TextRegion> = mutableListOf(),

    @field:XmlElement(name = "TextLine", namespace = PAGE_NS) val textLines: MutableList<TextLine> = mutableListOf(),
    @field:XmlElement(name = "TextStyle", namespace = PAGE_NS) val textStyle: TextStyle? = null,
    @field:XmlElement(name = "TextEquiv", namespace = PAGE_NS) val textEquiv: MutableList<TextEquiv> = mutableListOf()
) : Region

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class TextLine(
    @field:XmlAttribute(required = true) val id: String = "",
    @field:XmlAttribute val custom: String? = null,
    @field:XmlAttribute val comments: String? = null,
    @field:XmlAttribute val index: Int? = null,
    @field:XmlAttribute val readingDirection: ReadingDirectionSimpleType? = null,
    @field:XmlAttribute val primaryLanguage: LanguageSimpleType? = null,
    @field:XmlAttribute val secondaryLanguage: LanguageSimpleType? = null,
    @field:XmlAttribute val primaryScript: ScriptSimpleType? = null,
    @field:XmlAttribute val secondaryScript: ScriptSimpleType? = null,
    @field:XmlAttribute val production: ProductionSimpleType? = null,
    @field:XmlAttribute val conf: Double? = null,

    @field:XmlElement(name = "Coords", namespace = PAGE_NS, required = true) val coords: CoordsType = CoordsType(),
    @field:XmlElement(name = "Baseline", namespace = PAGE_NS) val baseline: BaselineType? = null,
    @field:XmlElement(name = "Word", namespace = PAGE_NS) val words: MutableList<Word> = mutableListOf(),
    @field:XmlElement(name = "TextStyle", namespace = PAGE_NS) val textStyle: TextStyle? = null,
    @field:XmlElement(name = "TextEquiv", namespace = PAGE_NS) val textEquiv: MutableList<TextEquiv> = mutableListOf(),
    @field:XmlElement(name = "UserDefined", namespace = PAGE_NS) val userDefined: UserDefined? = null,
    @field:XmlElement(
        name = "AlternativeImage",
        namespace = PAGE_NS
    ) val alternativeImages: MutableList<AlternativeImage> = mutableListOf()
)

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class Word(
    @field:XmlAttribute(required = true) val id: String = "",
    @field:XmlAttribute val custom: String? = null,
    @field:XmlAttribute val comments: String? = null,
    @field:XmlAttribute val language: LanguageSimpleType? = null,
    @field:XmlAttribute val script: ScriptSimpleType? = null,
    @field:XmlAttribute val production: ProductionSimpleType? = null,
    @field:XmlAttribute val conf: Double? = null,

    @field:XmlElement(name = "Coords", namespace = PAGE_NS, required = true) val coords: CoordsType = CoordsType(),
    @field:XmlElement(name = "Glyph", namespace = PAGE_NS) val glyphs: MutableList<Glyph> = mutableListOf(),
    @field:XmlElement(name = "TextStyle", namespace = PAGE_NS) val textStyle: TextStyle? = null,
    @field:XmlElement(name = "TextEquiv", namespace = PAGE_NS) val textEquiv: MutableList<TextEquiv> = mutableListOf(),
    @field:XmlElement(name = "UserDefined", namespace = PAGE_NS) val userDefined: UserDefined? = null,
    @field:XmlElement(
        name = "AlternativeImage",
        namespace = PAGE_NS
    ) val alternativeImages: MutableList<AlternativeImage> = mutableListOf()
)

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class Glyph(
    @field:XmlAttribute(required = true) val id: String = "",
    @field:XmlAttribute val custom: String? = null,
    @field:XmlAttribute val comments: String? = null,
    @field:XmlAttribute val ligature: Boolean? = null,
    @field:XmlAttribute val symbol: Boolean? = null,
    @field:XmlAttribute val script: ScriptSimpleType? = null,
    @field:XmlAttribute val production: ProductionSimpleType? = null,
    @field:XmlAttribute val conf: Double? = null,

    @field:XmlElement(name = "Coords", namespace = PAGE_NS, required = true) val coords: CoordsType = CoordsType(),
    @field:XmlElement(name = "TextStyle", namespace = PAGE_NS) val textStyle: TextStyle? = null,
    @field:XmlElement(name = "TextEquiv", namespace = PAGE_NS) val textEquiv: MutableList<TextEquiv> = mutableListOf(),
    @field:XmlElement(name = "UserDefined", namespace = PAGE_NS) val userDefined: UserDefined? = null,
    @field:XmlElement(
        name = "AlternativeImage",
        namespace = PAGE_NS
    ) val alternativeImages: MutableList<AlternativeImage> = mutableListOf()
)

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class TextEquiv(
    @field:XmlAttribute val index: Int? = null,
    @field:XmlAttribute val conf: Double? = null,
    @field:XmlAttribute val dataType: String? = null,
    @field:XmlAttribute val dataTypeDetails: String? = null,
    @field:XmlAttribute val comments: String? = null,
    @field:XmlElement(name = "PlainText", namespace = PAGE_NS) val plainText: String? = null,
    @field:XmlElement(name = "Unicode", namespace = PAGE_NS) val unicode: String? = null
)

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class TextStyle(
    @field:XmlAttribute val fontFamily: String? = null,
    @field:XmlAttribute val serif: Boolean? = null,
    @field:XmlAttribute val monospace: Boolean? = null,
    @field:XmlAttribute val fontSize: Double? = null,
    @field:XmlAttribute val xHeight: Double? = null,
    @field:XmlAttribute val kerning: Int? = null,
    @field:XmlAttribute val textColour: String? = null,
    @field:XmlAttribute val textColourRgb: Int? = null,
    @field:XmlAttribute val bgColour: String? = null,
    @field:XmlAttribute val bgColourRgb: Int? = null,
    @field:XmlAttribute val reverseVideo: Boolean? = null,
    @field:XmlAttribute val bold: Boolean? = null,
    @field:XmlAttribute val italic: Boolean? = null,
    @field:XmlAttribute val underlined: Boolean? = null,
    @field:XmlAttribute val underlineStyle: String? = null,
    @field:XmlAttribute val strikethrough: Boolean? = null,
    @field:XmlAttribute val subscript: Boolean? = null,
    @field:XmlAttribute val superscript: Boolean? = null,
    @field:XmlAttribute val smallCaps: Boolean? = null,
    @field:XmlAttribute val letterSpaced: Boolean? = null,
    @field:XmlAttribute val conf: Double? = null
)

// ─────────────────────────────────────────────
// Table region
// ─────────────────────────────────────────────
@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class TableRegion(
    @field:XmlAttribute(required = true) override val id: String = "",
    @field:XmlAttribute override val custom: String? = null,
    @field:XmlAttribute override val comments: String? = null,
    @field:XmlElement(
        name = "Coords",
        namespace = PAGE_NS,
        required = true
    ) override val coords: CoordsType = CoordsType(),
    @field:XmlElement(name = "UserDefined", namespace = PAGE_NS) override val userDefined: UserDefined? = null,
    @field:XmlElement(name = "Roles", namespace = PAGE_NS) override val roles: Roles? = null,
    @field:XmlElement(
        name = "AlternativeImage",
        namespace = PAGE_NS
    ) override val alternativeImages: MutableList<AlternativeImage> = mutableListOf(),

    @field:XmlAttribute val rows: Int? = null,
    @field:XmlAttribute val columns: Int? = null,
    @field:XmlAttribute val lineColour: String? = null,
    @field:XmlAttribute val lineSeparators: Boolean? = null,
    @field:XmlAttribute val embText: Boolean? = null,
    @field:XmlAttribute val bgColour: String? = null,
    @field:XmlAttribute val orientation: Double? = null,
    @field:XmlAttribute val conf: Double? = null,

    @field:XmlElement(name = "TableCell", namespace = PAGE_NS)
    val tableCells: MutableList<TableCell> = mutableListOf(),

    @field:XmlElement(name = "TextEquiv", namespace = PAGE_NS)
    val textEquiv: MutableList<TextEquiv> = mutableListOf()
) : Region

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class TableCell(
    @field:XmlAttribute(required = true) val id: String = "",
    @field:XmlAttribute(required = true) val row: Int = 0,
    @field:XmlAttribute(required = true) val col: Int = 0,
    @field:XmlAttribute val rowSpan: Int? = null,
    @field:XmlAttribute val colSpan: Int? = null,
    @field:XmlAttribute val cornerPts: String? = null,
    @field:XmlAttribute val custom: String? = null,
    @field:XmlAttribute val comments: String? = null,
    @field:XmlAttribute val leftBorderVisible: Boolean? = null,
    @field:XmlAttribute val rightBorderVisible: Boolean? = null,
    @field:XmlAttribute val topBorderVisible: Boolean? = null,
    @field:XmlAttribute val bottomBorderVisible: Boolean? = null,
    @field:XmlAttribute val conf: Double? = null,

    @field:XmlElement(name = "Coords", namespace = PAGE_NS, required = true) val coords: CoordsType = CoordsType(),
    @field:XmlElement(name = "CornerPts", namespace = PAGE_NS) val cornerPtsElement: CoordsType? = null,
    @field:XmlElement(name = "TextLine", namespace = PAGE_NS) val textLines: MutableList<TextLine> = mutableListOf(),
    @field:XmlElement(name = "TextEquiv", namespace = PAGE_NS) val textEquiv: MutableList<TextEquiv> = mutableListOf(),
    @field:XmlElement(name = "UserDefined", namespace = PAGE_NS) val userDefined: UserDefined? = null,
    @field:XmlElement(
        name = "AlternativeImage",
        namespace = PAGE_NS
    ) val alternativeImages: MutableList<AlternativeImage> = mutableListOf()
)

// ─────────────────────────────────────────────
// Other region types
// ─────────────────────────────────────────────
@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class ImageRegion(
    @field:XmlAttribute(required = true) override val id: String = "",
    @field:XmlAttribute override val custom: String? = null,
    @field:XmlAttribute override val comments: String? = null,
    @field:XmlElement(
        name = "Coords",
        namespace = PAGE_NS,
        required = true
    ) override val coords: CoordsType = CoordsType(),
    @field:XmlElement(name = "UserDefined", namespace = PAGE_NS) override val userDefined: UserDefined? = null,
    @field:XmlElement(name = "Roles", namespace = PAGE_NS) override val roles: Roles? = null,
    @field:XmlElement(
        name = "AlternativeImage",
        namespace = PAGE_NS
    ) override val alternativeImages: MutableList<AlternativeImage> = mutableListOf(),

    @field:XmlAttribute val colourDepth: ColourDepthSimpleType? = null,
    @field:XmlAttribute val bgColour: String? = null,
    @field:XmlAttribute val orientation: Double? = null,
    @field:XmlAttribute val embText: Boolean? = null,
    @field:XmlAttribute val conf: Double? = null,
    @field:XmlElement(name = "TextEquiv", namespace = PAGE_NS) val textEquiv: MutableList<TextEquiv> = mutableListOf()
) : Region

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class LineDrawingRegion(
    @field:XmlAttribute(required = true) override val id: String = "",
    @field:XmlAttribute override val custom: String? = null,
    @field:XmlAttribute override val comments: String? = null,
    @field:XmlElement(
        name = "Coords",
        namespace = PAGE_NS,
        required = true
    ) override val coords: CoordsType = CoordsType(),
    @field:XmlElement(name = "UserDefined", namespace = PAGE_NS) override val userDefined: UserDefined? = null,
    @field:XmlElement(name = "Roles", namespace = PAGE_NS) override val roles: Roles? = null,
    @field:XmlElement(
        name = "AlternativeImage",
        namespace = PAGE_NS
    ) override val alternativeImages: MutableList<AlternativeImage> = mutableListOf(),

    @field:XmlAttribute val orientation: Double? = null,
    @field:XmlAttribute val penColour: String? = null,
    @field:XmlAttribute val bgColour: String? = null,
    @field:XmlAttribute val embText: Boolean? = null,
    @field:XmlAttribute val conf: Double? = null,
    @field:XmlElement(name = "TextEquiv", namespace = PAGE_NS) val textEquiv: MutableList<TextEquiv> = mutableListOf()
) : Region

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class GraphicRegion(
    @field:XmlAttribute(required = true) override val id: String = "",
    @field:XmlAttribute override val custom: String? = null,
    @field:XmlAttribute override val comments: String? = null,
    @field:XmlElement(
        name = "Coords",
        namespace = PAGE_NS,
        required = true
    ) override val coords: CoordsType = CoordsType(),
    @field:XmlElement(name = "UserDefined", namespace = PAGE_NS) override val userDefined: UserDefined? = null,
    @field:XmlElement(name = "Roles", namespace = PAGE_NS) override val roles: Roles? = null,
    @field:XmlElement(
        name = "AlternativeImage",
        namespace = PAGE_NS
    ) override val alternativeImages: MutableList<AlternativeImage> = mutableListOf(),

    @field:XmlAttribute val type: GraphicTypeSimpleType? = null,
    @field:XmlAttribute val orientation: Double? = null,
    @field:XmlAttribute val embText: Boolean? = null,
    @field:XmlAttribute val bgColour: String? = null,
    @field:XmlAttribute val penColour: String? = null,
    @field:XmlAttribute val conf: Double? = null,
    @field:XmlElement(name = "TextEquiv", namespace = PAGE_NS) val textEquiv: MutableList<TextEquiv> = mutableListOf()
) : Region

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class ChartRegion(
    @field:XmlAttribute(required = true) override val id: String = "",
    @field:XmlAttribute override val custom: String? = null,
    @field:XmlAttribute override val comments: String? = null,
    @field:XmlElement(
        name = "Coords",
        namespace = PAGE_NS,
        required = true
    ) override val coords: CoordsType = CoordsType(),
    @field:XmlElement(name = "UserDefined", namespace = PAGE_NS) override val userDefined: UserDefined? = null,
    @field:XmlElement(name = "Roles", namespace = PAGE_NS) override val roles: Roles? = null,
    @field:XmlElement(
        name = "AlternativeImage",
        namespace = PAGE_NS
    ) override val alternativeImages: MutableList<AlternativeImage> = mutableListOf(),

    @field:XmlAttribute val type: ChartTypeSimpleType? = null,
    @field:XmlAttribute val orientation: Double? = null,
    @field:XmlAttribute val embText: Boolean? = null,
    @field:XmlAttribute val bgColour: String? = null,
    @field:XmlAttribute val conf: Double? = null,
    @field:XmlElement(name = "TextEquiv", namespace = PAGE_NS) val textEquiv: MutableList<TextEquiv> = mutableListOf()
) : Region

// Simple region types with minimal extra attributes
@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class SeparatorRegion(
    @field:XmlAttribute(required = true) override val id: String = "",
    @field:XmlAttribute override val custom: String? = null,
    @field:XmlAttribute override val comments: String? = null,
    @field:XmlElement(
        name = "Coords",
        namespace = PAGE_NS,
        required = true
    ) override val coords: CoordsType = CoordsType(),
    @field:XmlElement(name = "UserDefined", namespace = PAGE_NS) override val userDefined: UserDefined? = null,
    @field:XmlElement(name = "Roles", namespace = PAGE_NS) override val roles: Roles? = null,
    @field:XmlElement(
        name = "AlternativeImage",
        namespace = PAGE_NS
    ) override val alternativeImages: MutableList<AlternativeImage> = mutableListOf(),
    @field:XmlAttribute val orientation: Double? = null,
    @field:XmlAttribute val colour: String? = null,
    @field:XmlAttribute val conf: Double? = null,
    @field:XmlElement(name = "TextEquiv", namespace = PAGE_NS) val textEquiv: MutableList<TextEquiv> = mutableListOf()
) : Region

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class MathsRegion(
    @field:XmlAttribute(required = true) override val id: String = "",
    @field:XmlAttribute override val custom: String? = null,
    @field:XmlAttribute override val comments: String? = null,
    @field:XmlElement(
        name = "Coords",
        namespace = PAGE_NS,
        required = true
    ) override val coords: CoordsType = CoordsType(),
    @field:XmlElement(name = "UserDefined", namespace = PAGE_NS) override val userDefined: UserDefined? = null,
    @field:XmlElement(name = "Roles", namespace = PAGE_NS) override val roles: Roles? = null,
    @field:XmlElement(
        name = "AlternativeImage",
        namespace = PAGE_NS
    ) override val alternativeImages: MutableList<AlternativeImage> = mutableListOf(),
    @field:XmlAttribute val orientation: Double? = null,
    @field:XmlAttribute val conf: Double? = null,
    @field:XmlElement(name = "TextEquiv", namespace = PAGE_NS) val textEquiv: MutableList<TextEquiv> = mutableListOf()
) : Region

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class ChemRegion(
    @field:XmlAttribute(required = true) override val id: String = "",
    @field:XmlAttribute override val custom: String? = null,
    @field:XmlAttribute override val comments: String? = null,
    @field:XmlElement(
        name = "Coords",
        namespace = PAGE_NS,
        required = true
    ) override val coords: CoordsType = CoordsType(),
    @field:XmlElement(name = "UserDefined", namespace = PAGE_NS) override val userDefined: UserDefined? = null,
    @field:XmlElement(name = "Roles", namespace = PAGE_NS) override val roles: Roles? = null,
    @field:XmlElement(
        name = "AlternativeImage",
        namespace = PAGE_NS
    ) override val alternativeImages: MutableList<AlternativeImage> = mutableListOf(),
    @field:XmlAttribute val orientation: Double? = null,
    @field:XmlAttribute val conf: Double? = null,
    @field:XmlElement(name = "TextEquiv", namespace = PAGE_NS) val textEquiv: MutableList<TextEquiv> = mutableListOf()
) : Region

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class MusicRegion(
    @field:XmlAttribute(required = true) override val id: String = "",
    @field:XmlAttribute override val custom: String? = null,
    @field:XmlAttribute override val comments: String? = null,
    @field:XmlElement(
        name = "Coords",
        namespace = PAGE_NS,
        required = true
    ) override val coords: CoordsType = CoordsType(),
    @field:XmlElement(name = "UserDefined", namespace = PAGE_NS) override val userDefined: UserDefined? = null,
    @field:XmlElement(name = "Roles", namespace = PAGE_NS) override val roles: Roles? = null,
    @field:XmlElement(
        name = "AlternativeImage",
        namespace = PAGE_NS
    ) override val alternativeImages: MutableList<AlternativeImage> = mutableListOf(),
    @field:XmlAttribute val orientation: Double? = null,
    @field:XmlAttribute val conf: Double? = null,
    @field:XmlElement(name = "TextEquiv", namespace = PAGE_NS) val textEquiv: MutableList<TextEquiv> = mutableListOf()
) : Region

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class AdvertRegion(
    @field:XmlAttribute(required = true) override val id: String = "",
    @field:XmlAttribute override val custom: String? = null,
    @field:XmlAttribute override val comments: String? = null,
    @field:XmlElement(
        name = "Coords",
        namespace = PAGE_NS,
        required = true
    ) override val coords: CoordsType = CoordsType(),
    @field:XmlElement(name = "UserDefined", namespace = PAGE_NS) override val userDefined: UserDefined? = null,
    @field:XmlElement(name = "Roles", namespace = PAGE_NS) override val roles: Roles? = null,
    @field:XmlElement(
        name = "AlternativeImage",
        namespace = PAGE_NS
    ) override val alternativeImages: MutableList<AlternativeImage> = mutableListOf(),
    @field:XmlAttribute val orientation: Double? = null,
    @field:XmlAttribute val conf: Double? = null,
    @field:XmlElement(name = "TextEquiv", namespace = PAGE_NS) val textEquiv: MutableList<TextEquiv> = mutableListOf()
) : Region

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class NoiseRegion(
    @field:XmlAttribute(required = true) override val id: String = "",
    @field:XmlAttribute override val custom: String? = null,
    @field:XmlAttribute override val comments: String? = null,
    @field:XmlElement(
        name = "Coords",
        namespace = PAGE_NS,
        required = true
    ) override val coords: CoordsType = CoordsType(),
    @field:XmlElement(name = "UserDefined", namespace = PAGE_NS) override val userDefined: UserDefined? = null,
    @field:XmlElement(name = "Roles", namespace = PAGE_NS) override val roles: Roles? = null,
    @field:XmlElement(
        name = "AlternativeImage",
        namespace = PAGE_NS
    ) override val alternativeImages: MutableList<AlternativeImage> = mutableListOf(),
    @field:XmlAttribute val conf: Double? = null
) : Region

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class MapRegion(
    @field:XmlAttribute(required = true) override val id: String = "",
    @field:XmlAttribute override val custom: String? = null,
    @field:XmlAttribute override val comments: String? = null,
    @field:XmlElement(
        name = "Coords",
        namespace = PAGE_NS,
        required = true
    ) override val coords: CoordsType = CoordsType(),
    @field:XmlElement(name = "UserDefined", namespace = PAGE_NS) override val userDefined: UserDefined? = null,
    @field:XmlElement(name = "Roles", namespace = PAGE_NS) override val roles: Roles? = null,
    @field:XmlElement(
        name = "AlternativeImage",
        namespace = PAGE_NS
    ) override val alternativeImages: MutableList<AlternativeImage> = mutableListOf(),
    @field:XmlAttribute val orientation: Double? = null,
    @field:XmlAttribute val bgColour: String? = null,
    @field:XmlAttribute val embText: Boolean? = null,
    @field:XmlAttribute val conf: Double? = null,
    @field:XmlElement(name = "TextEquiv", namespace = PAGE_NS) val textEquiv: MutableList<TextEquiv> = mutableListOf()
) : Region

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class UnknownRegion(
    @field:XmlAttribute(required = true) override val id: String = "",
    @field:XmlAttribute override val custom: String? = null,
    @field:XmlAttribute override val comments: String? = null,
    @field:XmlElement(
        name = "Coords",
        namespace = PAGE_NS,
        required = true
    ) override val coords: CoordsType = CoordsType(),
    @field:XmlElement(name = "UserDefined", namespace = PAGE_NS) override val userDefined: UserDefined? = null,
    @field:XmlElement(name = "Roles", namespace = PAGE_NS) override val roles: Roles? = null,
    @field:XmlElement(
        name = "AlternativeImage",
        namespace = PAGE_NS
    ) override val alternativeImages: MutableList<AlternativeImage> = mutableListOf(),
    @field:XmlAttribute val conf: Double? = null
) : Region

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class CustomRegion(
    @field:XmlAttribute(required = true) override val id: String = "",
    @field:XmlAttribute override val custom: String? = null,
    @field:XmlAttribute override val comments: String? = null,
    @field:XmlElement(
        name = "Coords",
        namespace = PAGE_NS,
        required = true
    ) override val coords: CoordsType = CoordsType(),
    @field:XmlElement(name = "UserDefined", namespace = PAGE_NS) override val userDefined: UserDefined? = null,
    @field:XmlElement(name = "Roles", namespace = PAGE_NS) override val roles: Roles? = null,
    @field:XmlElement(
        name = "AlternativeImage",
        namespace = PAGE_NS
    ) override val alternativeImages: MutableList<AlternativeImage> = mutableListOf(),
    @field:XmlAttribute val type: String? = null,
    @field:XmlAttribute val orientation: Double? = null,
    @field:XmlAttribute val conf: Double? = null
) : Region

// ─────────────────────────────────────────────
// Border & PrintSpace
// ─────────────────────────────────────────────
@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class Border(
    @field:XmlElement(name = "Coords", namespace = PAGE_NS, required = true) val coords: CoordsType = CoordsType()
)

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class PrintSpace(
    @field:XmlElement(name = "Coords", namespace = PAGE_NS, required = true) val coords: CoordsType = CoordsType()
)

// ─────────────────────────────────────────────
// Misc elements
// ─────────────────────────────────────────────

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class UserDefined(
    @field:XmlElement(name = "UserAttribute", namespace = PAGE_NS)
    val attributes: MutableList<UserAttribute> = mutableListOf()
)

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class UserAttribute(
    @field:XmlAttribute(required = true) val name: String = "",
    @field:XmlAttribute val type: String? = null,
    @field:XmlAttribute(required = true) val value: String = "",
    @field:XmlAttribute val description: String? = null
)

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class Roles(
    @field:XmlElement(name = "Role", namespace = PAGE_NS)
    val roles: MutableList<String> = mutableListOf()
)

// ─────────────────────────────────────────────
// Simple type enums
// ─────────────────────────────────────────────

@Serializable
@XmlEnum
enum class TextTypeSimpleType {
    @XmlEnumValue("paragraph")
    PARAGRAPH,

    @XmlEnumValue("heading")
    HEADING,

    @XmlEnumValue("caption")
    CAPTION,

    @XmlEnumValue("header")
    HEADER,

    @XmlEnumValue("footer")
    FOOTER,

    @XmlEnumValue("page-number")
    PAGE_NUMBER,

    @XmlEnumValue("drop-capital")
    DROP_CAPITAL,

    @XmlEnumValue("credit")
    CREDIT,

    @XmlEnumValue("floating")
    FLOATING,

    @XmlEnumValue("signature")
    SIGNATURE,

    @XmlEnumValue("catch-word")
    CATCH_WORD,

    @XmlEnumValue("marginalia")
    MARGINALIA,

    @XmlEnumValue("footnote")
    FOOTNOTE,

    @XmlEnumValue("footnote-continued")
    FOOTNOTE_CONTINUED,

    @XmlEnumValue("endnote")
    ENDNOTE,

    @XmlEnumValue("TOC-entry")
    TOC_ENTRY,

    @XmlEnumValue("other")
    OTHER
}

@XmlEnum
enum class ReadingDirectionSimpleType {
    @XmlEnumValue("left-to-right")
    LEFT_TO_RIGHT,

    @XmlEnumValue("right-to-left")
    RIGHT_TO_LEFT,

    @XmlEnumValue("top-to-bottom")
    TOP_TO_BOTTOM,

    @XmlEnumValue("bottom-to-top")
    BOTTOM_TO_TOP
}

@XmlEnum
enum class TextLineOrderSimpleType {
    @XmlEnumValue("top-to-bottom")
    TOP_TO_BOTTOM,

    @XmlEnumValue("bottom-to-top")
    BOTTOM_TO_TOP,

    @XmlEnumValue("left-to-right")
    LEFT_TO_RIGHT,

    @XmlEnumValue("right-to-left")
    RIGHT_TO_LEFT
}

@XmlEnum
enum class GraphicTypeSimpleType {
    @XmlEnumValue("logo")
    LOGO,

    @XmlEnumValue("letterhead")
    LETTERHEAD,

    @XmlEnumValue("decoration")
    DECORATION,

    @XmlEnumValue("stamp")
    STAMP,

    @XmlEnumValue("signature")
    SIGNATURE,

    @XmlEnumValue("barcode")
    BARCODE,

    @XmlEnumValue("paper-grow")
    PAPER_GROW,

    @XmlEnumValue("punch-hole")
    PUNCH_HOLE,

    @XmlEnumValue("other")
    OTHER
}

@XmlEnum
enum class ChartTypeSimpleType {
    @XmlEnumValue("bar")
    BAR,

    @XmlEnumValue("line")
    LINE,

    @XmlEnumValue("pie")
    PIE,

    @XmlEnumValue("scatter")
    SCATTER,

    @XmlEnumValue("surface")
    SURFACE,

    @XmlEnumValue("other")
    OTHER
}

@XmlEnum
enum class ColourDepthSimpleType {
    @XmlEnumValue("bitonal")
    BITONAL,

    @XmlEnumValue("greyscale")
    GREYSCALE,

    @XmlEnumValue("8-bit colour")
    COLOUR_8BIT,

    @XmlEnumValue("16-bit colour")
    COLOUR_16BIT,

    @XmlEnumValue("24-bit colour")
    COLOUR_24BIT,

    @XmlEnumValue("32-bit colour")
    COLOUR_32BIT,

    @XmlEnumValue("other")
    OTHER
}

@XmlEnum
enum class ImageColorSimpleType {
    @XmlEnumValue("colour")
    COLOUR,

    @XmlEnumValue("greyscale")
    GREYSCALE,

    @XmlEnumValue("bitonal")
    BITONAL,

    @XmlEnumValue("other")
    OTHER
}

@XmlEnum
enum class PageTypeSimpleType {
    @XmlEnumValue("front-cover")
    FRONT_COVER,

    @XmlEnumValue("back-cover")
    BACK_COVER,

    @XmlEnumValue("title")
    TITLE,

    @XmlEnumValue("index")
    INDEX,

    @XmlEnumValue("dedication")
    DEDICATION,

    @XmlEnumValue("acknowledgement")
    ACKNOWLEDGEMENT,

    @XmlEnumValue("list-of-tables")
    LIST_OF_TABLES,

    @XmlEnumValue("list-of-figures")
    LIST_OF_FIGURES,

    @XmlEnumValue("list-of-abbreviations")
    LIST_OF_ABBREVIATIONS,

    @XmlEnumValue("preface")
    PREFACE,

    @XmlEnumValue("foreword")
    FOREWORD,

    @XmlEnumValue("introduction")
    INTRODUCTION,

    @XmlEnumValue("chapter")
    CHAPTER,

    @XmlEnumValue("contents")
    CONTENTS,

    @XmlEnumValue("undefined")
    UNDEFINED,

    @XmlEnumValue("other")
    OTHER
}

@XmlEnum
enum class ProductionSimpleType {
    @XmlEnumValue("typewritten")
    TYPEWRITTEN,

    @XmlEnumValue("handwritten-cursive")
    HANDWRITTEN_CURSIVE,

    @XmlEnumValue("handwritten-print")
    HANDWRITTEN_PRINT,

    @XmlEnumValue("printed")
    PRINTED,

    @XmlEnumValue("mixed")
    MIXED,

    @XmlEnumValue("other")
    OTHER
}

// Language & Script are large enumerations — represented as String to avoid verbosity,
// but you can expand them into full enum classes from the XSD if strict validation is needed.
typealias LanguageSimpleType = String
typealias ScriptSimpleType = String