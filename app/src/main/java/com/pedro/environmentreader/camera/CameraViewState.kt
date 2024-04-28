package com.pedro.environmentreader.camera

import com.pedro.environmentreader.common.view.button.ButtonViewState

data class CameraViewState(
    val cameraAction: CameraAction,
    val saveImageContentValues: Map<String, String>,
    val takePictureButtonViewState: ButtonViewState,
)

enum class CameraAction {
    TAKE_PICTURE,
    IDLE;
}