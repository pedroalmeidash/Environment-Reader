package com.pedro.environmentreader.imageanalysis

import androidx.lifecycle.ViewModel
import com.pedro.environmentreader.data.repository.ImageAnalysisRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

@HiltViewModel
class ImageAnalysisViewModel @Inject constructor(
    private val imageAnalysisRepository: ImageAnalysisRepository,
) : ViewModel() {

    private val _imageAnalysisViewStateStream = BehaviorSubject.create<ImageAnalysisViewState>()
    val imageAnalysisViewStateStream: Observable<ImageAnalysisViewState> =
        _imageAnalysisViewStateStream.hide()

    init {
        imageAnalysisRepository.analyzeImage().subscribe()
    }
}