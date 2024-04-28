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
import androidx.compose.ui.unit.dp
import com.pedro.environmentreader.common.view.button.Button

@Composable
fun RequestPermissionView(
    viewState: RequestPermissionViewState,
    onRequestPermissionTapped: () -> Unit,
) {
    if (viewState !is RequestPermissionViewState.Loaded) return
    
    Scaffold(
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = stringResource(id = viewState.descriptionRes))
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