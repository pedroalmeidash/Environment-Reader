package com.pedro.environmentreader.imageanalysis

sealed class ImageAnalysisViewState {
    data object Loading : ImageAnalysisViewState()
}