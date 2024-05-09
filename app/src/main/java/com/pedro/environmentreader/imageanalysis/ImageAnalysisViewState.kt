package com.pedro.environmentreader.imageanalysis

import androidx.compose.ui.graphics.Color

sealed class ImageAnalysisViewState {
    data object Loading : ImageAnalysisViewState()
    data class Loaded(
        val imageRoot: String,
        val identifiedObjects: List<IdentifiedObjectViewState>,
    ) : ImageAnalysisViewState()
}

data class IdentifiedObjectViewState(
    val name: String,
    val borderColor: Color,
    val topLeftCoordinates: CoordinatesViewState,
    val bottomLeftCoordinates: CoordinatesViewState,
)

data class CoordinatesViewState(
    val x: Float,
    val y: Float,
)