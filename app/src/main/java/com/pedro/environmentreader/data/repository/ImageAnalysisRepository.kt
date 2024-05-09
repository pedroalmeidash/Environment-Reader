package com.pedro.environmentreader.data.repository

import com.pedro.environmentreader.common.image.GetImageFileUseCase
import com.pedro.environmentreader.data.api.ImageAnalysisService
import com.pedro.environmentreader.data.response.Coordinates
import com.pedro.environmentreader.data.response.IdentifiedObject
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

        if (1 + 1 == 2) {
            return Single.just(
                ImageAnalysisResponse(
                    identifiedObjects = listOf(
                        IdentifiedObject(
                            name = "Placa",
                            coordinates = Coordinates(
                                x1 = 10f,
                                x2 = 200f,
                                y1 = 10f,
                                y2 = 200f,
                            ),
                        ),
                        IdentifiedObject(
                            name = "Refeitorio",
                            coordinates = Coordinates(
                                x1 = 250f,
                                x2 = 450f,
                                y1 = 300f,
                                y2 = 800f,
                            ),
                        ),
                        IdentifiedObject(
                            name = "Sala de aula",
                            coordinates = Coordinates(
                                x1 = 800f,
                                x2 = 1000f,
                                y1 = 400f,
                                y2 = 600f,
                            ),
                        ),
                        IdentifiedObject(
                            name = "Teste",
                            coordinates = Coordinates(
                                x1 = 400f,
                                x2 = 700f,
                                y1 = 900f,
                                y2 = 1000f,
                            ),
                        ),
                        IdentifiedObject(
                            name = "Ponto de onibus",
                            coordinates = Coordinates(
                                x1 = 0f,
                                x2 = 100f,
                                y1 = 1200f,
                                y2 = 1300f,
                            ),
                        ),
                    )
                )
            )
        }

        return imageAnalysisService.analyzeImage(
            image = MultipartBody.Part.createFormData(
                "image",
                imageFile.name,
                imageFile.asRequestBody(),
            )
        )
    }
}