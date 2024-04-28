package com.pedro.environmentreader.common.view.button

import androidx.annotation.StringRes

data class ButtonViewState(
    val state: ButtonState,
    @StringRes val textRes: Int
)