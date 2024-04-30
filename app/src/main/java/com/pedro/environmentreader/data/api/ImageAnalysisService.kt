package com.pedro.environmentreader.data.api

import com.pedro.environmentreader.data.response.ImageAnalysisResponse
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageAnalysisService {
    @Multipart
    @POST("/analyzeImage")
    fun analyzeImage(
        @Part image: MultipartBody.Part
    ): Single<ImageAnalysisResponse>
}