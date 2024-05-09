package com.pedro.environmentreader.imageanalysis

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import java.io.File

@Composable
fun ImageAnalysisView(
    viewState: ImageAnalysisViewState,
) {
    Scaffold(
        content = { paddingValues ->
            val modifier = Modifier.padding(paddingValues)
            when (viewState) {
                is ImageAnalysisViewState.Loading -> CircularProgressIndicator(modifier = modifier)
                is ImageAnalysisViewState.Loaded -> LoadedImageAnalysisView(
                    modifier = modifier,
                    viewState = viewState,
                )
            }
        }
    )
}

@Composable
private fun LoadedImageAnalysisView(
    modifier: Modifier = Modifier,
    viewState: ImageAnalysisViewState.Loaded,
) {
    // TODO add content description
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = rememberAsyncImagePainter(model = File(viewState.imageRoot)),
            contentDescription = null,
        )
        IdentifiedObjectsView(viewState.identifiedObjects)
    }
}

@Composable
private fun IdentifiedObjectsView(
    identifiedObjects: List<IdentifiedObjectViewState>,
) = with(LocalDensity.current) {
    identifiedObjects.forEach { identifiedObjectViewState ->
        Box(
            modifier = Modifier
                .offset(
                    x = identifiedObjectViewState.topLeftCoordinates.x.toDp(),
                    y = identifiedObjectViewState.topLeftCoordinates.y.toDp(),
                )
                .width((identifiedObjectViewState.bottomLeftCoordinates.x - identifiedObjectViewState.topLeftCoordinates.x).toDp())
                .height((identifiedObjectViewState.bottomLeftCoordinates.y - identifiedObjectViewState.topLeftCoordinates.y).toDp())
                .border(5.dp, identifiedObjectViewState.borderColor)
                .semantics {
                    contentDescription = identifiedObjectViewState.name
                }
        )
    }

}