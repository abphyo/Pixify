package com.biho.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.biho.login.state.LoginState
import com.biho.pixify.core.domain.usecases.LoginAsGuest
import com.biho.pixify.core.domain.usecases.LoginToHost
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(SavedStateHandleSaveableApi::class)
class LoginViewModel(
    savedStateHandle: SavedStateHandle,
    private val loginUseCase: LoginToHost,
    private val loginAsGuestUseCase: LoginAsGuest
) : ViewModel() {

    var loginStatus by mutableStateOf<LoginState>(LoginState.Idle)
        private set

    var isApiKeyVisible by savedStateHandle.saveable {
        mutableStateOf(false)
    }
        private set

    var username by savedStateHandle.saveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }

    var apiKey by savedStateHandle.saveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }

    fun updateUsername(value: TextFieldValue) {
        username = value
    }

    fun updateApiKey(value: TextFieldValue) {
        apiKey = value
    }

    fun performLogin() {
        viewModelScope.launch {
            loginUseCase.invoke(
                name = username.text,
                apiKey = apiKey.text
            ).collectLatest { result ->
                result.onSuccess {
                    loginStatus = LoginState.Success
                }.onFailure { throwable ->
                    loginStatus = LoginState.Failure(throwable.message)
                }
            }
        }
    }

}