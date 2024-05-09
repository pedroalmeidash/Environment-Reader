package com.pedro.environmentreader.data.response

data class ImageAnalysisResponse(
    val identifiedObjects: List<IdentifiedObject>
)

data class IdentifiedObject(
    val name: String,
    val coordinates: Coordinates,
)

data class Coordinates(
    val x1: Float,
    val y1: Float,
    val x2: Float,
    val y2: Float,
)