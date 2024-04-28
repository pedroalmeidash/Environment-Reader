package com.pedro.environmentreader.requestpermission

import androidx.annotation.StringRes
import com.pedro.environmentreader.common.view.button.ButtonViewState

sealed class RequestPermissionViewState {
    data object Loading : RequestPermissionViewState()

    data class Loaded(
        @StringRes val descriptionRes: Int,
        val requestPermissionButtonViewState: ButtonViewState,
    ) : RequestPermissionViewState()
}