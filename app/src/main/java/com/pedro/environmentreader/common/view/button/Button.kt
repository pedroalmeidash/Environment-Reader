package com.pedro.environmentreader.common.view.button

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun Button(
    modifier: Modifier,
    buttonViewState: ButtonViewState,
    onClick: () -> Unit,
) {
    androidx.compose.material3.Button(
        modifier = modifier.height(64.dp),
        shape = RoundedCornerShape(5),
        enabled = buttonViewState.state == ButtonState.ACTIVE,
        content = {
            when (buttonViewState.state) {
                ButtonState.ACTIVE,
                ButtonState.DISABLED -> Text(text = stringResource(id = buttonViewState.textRes))
                ButtonState.LOADING -> CircularProgressIndicator()
            }
        },
        onClick = onClick,
    )
}