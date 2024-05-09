package com.pedro.environmentreader.imageanalysis

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.pedro.environmentreader.common.image.GetImageFileUseCase
import com.pedro.environmentreader.data.repository.ImageAnalysisRepository
import com.pedro.environmentreader.data.response.ImageAnalysisResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

@HiltViewModel
class ImageAnalysisViewModel @Inject constructor(
    private val imageAnalysisRepository: ImageAnalysisRepository,
    private val getImageFileUseCase: GetImageFileUseCase,
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _imageAnalysisViewStateStream = BehaviorSubject.create<ImageAnalysisViewState>()
    val imageAnalysisViewStateStream: Observable<ImageAnalysisViewState> =
        _imageAnalysisViewStateStream.hide()

    init {
        imageAnalysisRepository
            .analyzeImage()
            .subscribe { response ->
                _imageAnalysisViewStateStream.onNext(buildViewState(response))
            }
            .apply { disposables.add(this) }
    }

    private fun buildViewState(
        imageAnalysisResponse: ImageAnalysisResponse,
    ): ImageAnalysisViewState {
        return ImageAnalysisViewState.Loaded(
            imageRoot = getImageFileUseCase.getImageFile().absolutePath,
            identifiedObjects = imageAnalysisResponse.identifiedObjects.mapIndexed { index, identifiedObjectResponse ->
                val coordinatesResponse = identifiedObjectResponse.coordinates
                IdentifiedObjectViewState(
                    name = identifiedObjectResponse.name,
                    borderColor = getIdentifiedObjectBorderColor(index),
                    topLeftCoordinates = CoordinatesViewState(
                        x = coordinatesResponse.x1,
                        y = coordinatesResponse.y1,
                    ),
                    bottomLeftCoordinates = CoordinatesViewState(
                        x = coordinatesResponse.x2,
                        y = coordinatesResponse.y2,
                    )
                )
            }
        )
    }

    private fun getIdentifiedObjectBorderColor(
        index: Int
    ): Color {
        return when (index % 7) {
            0 -> Color.Black
            1 -> Color.Blue
            2 -> Color.Cyan
            3 -> Color.Gray
            4 -> Color.Green
            5 -> Color.Magenta
            else -> Color.Red
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (!disposables.isDisposed) disposables.clear()
    }
}