package com.pedro.environmentreader.requestpermission

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.pedro.environmentreader.R
import com.pedro.environmentreader.common.view.button.Button

@Composable
fun RequestPermissionView(
    viewState: RequestPermissionViewState,
    onRequestPermissionTapped: () -> Unit,
) {
    if (viewState !is RequestPermissionViewState.Loaded) return
    
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    text = stringResource(id = R.string.app_name),
                    fontSize = TextUnit(28f, TextUnitType.Sp),
                    fontFamily = FontFamily.Serif,
                    textAlign = TextAlign.Center,
                )
            }
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    text = stringResource(id = viewState.descriptionRes),
                    fontSize = TextUnit(20f, TextUnitType.Sp),
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center,
                )
            }
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                onClick = onRequestPermissionTapped,
                buttonViewState = viewState.requestPermissionButtonViewState,
            )
        }
    )
}