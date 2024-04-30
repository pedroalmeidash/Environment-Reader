package com.pedro.environmentreader.data.repository

import com.pedro.environmentreader.common.image.GetImageFileUseCase
import com.pedro.environmentreader.data.api.ImageAnalysisService
import com.pedro.environmentreader.data.response.ImageAnalysisResponse
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class ImageAnalysisRepository @Inject constructor(
    private val imageAnalysisService: ImageAnalysisService,
    private val getImageFileUseCase: GetImageFileUseCase,
) {

    fun analyzeImage(): Single<ImageAnalysisResponse> {
        val imageFile = getImageFileUseCase.getImageFile()
        return imageAnalysisService.analyzeImage(
            image = MultipartBody.Part.createFormData(
                "image",
                imageFile.name,
                imageFile.asRequestBody(),
            )
        )
    }
}