package com.pedro.environmentreader.requestpermission

import androidx.lifecycle.ViewModel
import com.pedro.environmentreader.R
import com.pedro.environmentreader.common.view.button.ButtonState
import com.pedro.environmentreader.common.view.button.ButtonViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class RequestPermissionViewModel @Inject constructor() : ViewModel() {

    private val _requestPermissionViewStateStream = BehaviorSubject.createDefault(
        buildViewState()
    )
    val requestPermissionViewStateStream: Observable<RequestPermissionViewState> =
        _requestPermissionViewStateStream.hide()

    private val _askForPermissionStream = PublishSubject.create<Unit>()
    val askForPermissionStream: Observable<Unit> = _askForPermissionStream

    fun onRequestPermissionTapped() {
        _requestPermissionViewStateStream.onNext(
            buildViewState(buttonState = ButtonState.LOADING)
        )
        _askForPermissionStream.onNext(Unit)
    }

    fun onPermissionRejected() {
        _requestPermissionViewStateStream.onNext(buildViewState())
    }

    private fun buildViewState(
        buttonState: ButtonState = ButtonState.ACTIVE,
    ): RequestPermissionViewState {
        return RequestPermissionViewState.Loaded(
            descriptionRes = R.string.request_permission_description,
            requestPermissionButtonViewState = ButtonViewState(
                state = buttonState,
                textRes = R.string.request_permission_request_permission_button_text,
            ),
        )
    }
}