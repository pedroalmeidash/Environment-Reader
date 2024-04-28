package com.pedro.environmentreader.camera

import android.content.ContentValues
import android.content.Context
import android.provider.MediaStore
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.pedro.environmentreader.common.view.button.Button
import com.pedro.environmentreader.common.view.button.ButtonViewState
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Composable
fun CameraView(
    viewState: CameraViewState,
    onViewEvent: (CameraViewEvent) -> Unit,
) {
    val lensFacing = CameraSelector.LENS_FACING_BACK
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }
    val cameraxSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
    val imageCapture = remember { ImageCapture.Builder().build() }

    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(lifecycleOwner, cameraxSelector, preview, imageCapture)
        preview.setSurfaceProvider(previewView.surfaceProvider)
    }
    HandleCameraAction(
        viewState = viewState,
        imageCapture = imageCapture,
        onViewEvent = onViewEvent,
    )
    CameraContent(
        previewView = previewView,
        takePictureButtonViewState = viewState.takePictureButtonViewState,
        onViewEvent = onViewEvent,
    )
}

@Composable
private fun HandleCameraAction(
    viewState: CameraViewState,
    imageCapture: ImageCapture,
    onViewEvent: (CameraViewEvent) -> Unit,
) {
    val context = LocalContext.current
    val cameraAction = viewState.cameraAction
    LaunchedEffect(key1 = cameraAction) {
        if (cameraAction == CameraAction.TAKE_PICTURE) {
            captureImage(
                context = context,
                imageCapture = imageCapture,
                saveImageContentValues = viewState.saveImageContentValues,
                onViewEvent = onViewEvent,
            )
        }
    }
}

@Composable
private fun CameraContent(
    previewView: PreviewView,
    takePictureButtonViewState: ButtonViewState,
    onViewEvent: (CameraViewEvent) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter,
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { previewView },
        )
        Button(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            buttonViewState = takePictureButtonViewState,
        ) { onViewEvent(CameraViewEvent.PictureButtonClicked) }
    }
}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener({
                continuation.resume(cameraProvider.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }

private fun captureImage(
    context: Context,
    imageCapture: ImageCapture,
    saveImageContentValues: Map<String, String>,
    onViewEvent: (CameraViewEvent) -> Unit,
) {
    val contentValues = ContentValues()
    saveImageContentValues.forEach(contentValues::put)

    val outputOptions = ImageCapture.OutputFileOptions.Builder(
        context.contentResolver,
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        contentValues
    ).build()

    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                onViewEvent(CameraViewEvent.PictureSuccessfullySaved)
            }

            override fun onError(exception: ImageCaptureException) {
                onViewEvent(CameraViewEvent.PictureSaveFailure)
            }
        }
    )
}