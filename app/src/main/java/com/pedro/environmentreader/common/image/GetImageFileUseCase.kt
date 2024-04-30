package com.pedro.environmentreader.common.image

import android.os.Environment
import java.io.File
import java.lang.StringBuilder
import javax.inject.Inject

class GetImageFileUseCase @Inject constructor() {

    fun getImageFile(): File {
        val picturesPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val filePath = StringBuilder()
            .append(picturesPath)
            .append("/")
            .append(ImageConstants.IMAGE_FILE_PATH)
            .append("/")
            .append(ImageConstants.IMAGE_FILE_NAME)
        return File(filePath.toString())
    }
}