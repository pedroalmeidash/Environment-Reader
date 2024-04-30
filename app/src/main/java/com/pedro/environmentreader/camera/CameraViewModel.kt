package com.pedro.environmentreader.camera

import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import com.pedro.environmentreader.R
import com.pedro.environmentreader.common.image.GetImageFileUseCase
import com.pedro.environmentreader.common.image.ImageConstants
import com.pedro.environmentreader.common.view.button.ButtonState
import com.pedro.environmentreader.common.view.button.ButtonViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.io.File
import java.lang.StringBuilder
import java.util.Optional
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val getImageFileUseCase: GetImageFileUseCase,
) : ViewModel() {

    private val _cameraViewStateStream = BehaviorSubject.createDefault(
        buildCameraViewState()
    )
    val cameraViewStateStream: Observable<Optional<CameraViewState>> =
        _cameraViewStateStream.hide()

    fun handleViewEvent(viewEvent: CameraViewEvent) {
        when (viewEvent) {
            is CameraViewEvent.PictureButtonClicked -> onTakePictureButtonClick()
            is CameraViewEvent.PictureSuccessfullySaved -> onPictureSuccessfullySaved()
            is CameraViewEvent.PictureSaveFailure -> onPictureSaveFailure()
        }
    }

    private fun onTakePictureButtonClick() {
        deleteLastPicture()
        _cameraViewStateStream.onNext(
            buildCameraViewState(
                buttonState = ButtonState.LOADING,
                cameraAction = CameraAction.TAKE_PICTURE,
            )
        )
    }

    private fun deleteLastPicture() {
        val pictureFile = getImageFileUseCase.getImageFile()
        if (pictureFile.exists()) {
            pictureFile.delete()
        }
    }

    private fun onPictureSuccessfullySaved() {
        // TODO go to screen to request process
        _cameraViewStateStream.onNext(
            buildCameraViewState(
                buttonState = ButtonState.ACTIVE,
                cameraAction = CameraAction.IDLE,
            )
        )
    }

    private fun onPictureSaveFailure() {
        _cameraViewStateStream.onNext(
            buildCameraViewState(
                buttonState = ButtonState.ACTIVE,
                cameraAction = CameraAction.IDLE,
            )
        )
    }

    private fun buildCameraViewState(
        buttonState: ButtonState = ButtonState.ACTIVE,
        cameraAction: CameraAction = CameraAction.IDLE,
    ): Optional<CameraViewState> {
        val viewState = CameraViewState(
            cameraAction = cameraAction,
            saveImageContentValues = getSaveImageContentValues(),
            takePictureButtonViewState = ButtonViewState(
                textRes = R.string.camera_take_picture_action,
                state = buttonState,
            ),
        )
        return Optional.of(viewState)
    }

    private fun getSaveImageContentValues(): Map<String, String> {
        return mutableMapOf(
            MediaStore.MediaColumns.DISPLAY_NAME to ImageConstants.IMAGE_FILE_NAME,
            MediaStore.MediaColumns.MIME_TYPE to ImageConstants.IMAGE_FILE_MIME_TYPE,
        ).apply {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, ImageConstants.IMAGE_FILE_RELATIVE_PATH)
            }
        }
    }
}