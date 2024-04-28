package com.pedro.environmentreader.camera

sealed class CameraViewEvent {
    data object PictureButtonClicked : CameraViewEvent()
    data object PictureSuccessfullySaved : CameraViewEvent()
    data object PictureSaveFailure : CameraViewEvent()
}
