package com.pedro.environmentreader.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.pedro.environmentreader.R
import com.pedro.environmentreader.imageanalysis.ImageAnalysisFragment
import com.pedro.environmentreader.navigation.NavigationModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.Optional
import kotlin.jvm.optionals.getOrNull

@AndroidEntryPoint
class CameraFragment : Fragment() {

    private val disposables = CompositeDisposable()
    private val viewModel: CameraViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigationRequestStream
            .subscribe {
                when (it) {
                    is NavigationModel.NavigateToImageAnalysisScreen -> {
                        navigateToImageAnalysisScreen()
                    }
                }
            }
            .apply { disposables.add(this) }
    }

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

    private fun navigateToImageAnalysisScreen() {
        parentFragmentManager.commit {
            add(R.id.fragment_container, ImageAnalysisFragment())
        }
    }
}