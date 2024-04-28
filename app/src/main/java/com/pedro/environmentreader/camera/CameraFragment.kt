package com.pedro.environmentreader.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import java.util.Optional
import kotlin.jvm.optionals.getOrNull

@AndroidEntryPoint
class CameraFragment : Fragment() {

    private val viewModel: CameraViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val viewState by viewModel.cameraViewStateStream.subscribeAsState(
                    initial = Optional.empty(),
                )
                viewState.getOrNull()?.let {
                    CameraView(
                        viewState = it,
                        onViewEvent = viewModel::handleViewEvent,
                    )
                }
            }
        }
    }
}