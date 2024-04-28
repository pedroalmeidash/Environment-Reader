package com.pedro.environmentreader.requestpermission

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.pedro.environmentreader.R
import com.pedro.environmentreader.camera.CameraFragment
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlin.concurrent.fixedRateTimer

@AndroidEntryPoint
class RequestPermissionFragment : Fragment() {

    private val viewModel: RequestPermissionViewModel by viewModels()
    private val cameraPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        ::handleGrantPermissionResult,
    )
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA,
            ) -> { navigateToCameraFragment() }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel
            .askForPermissionStream
            .subscribe { cameraPermissionRequest.launch(Manifest.permission.CAMERA) }
            .apply { disposables.add(this) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val viewState by viewModel.requestPermissionViewStateStream.subscribeAsState(
                    RequestPermissionViewState.Loading
                )
                RequestPermissionView(
                    viewState = viewState,
                    onRequestPermissionTapped = viewModel::onRequestPermissionTapped,
                )
            }
        }
    }

    private fun handleGrantPermissionResult(isPermissionGranted: Boolean) {
        if (isPermissionGranted) {
            navigateToCameraFragment()
        } else {
            viewModel.onPermissionRejected()
        }
    }

    private fun navigateToCameraFragment() {
        parentFragmentManager.commit {
            replace(R.id.fragment_container, CameraFragment())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!disposables.isDisposed) {
            disposables.dispose()
            disposables.clear()
        }
    }
}