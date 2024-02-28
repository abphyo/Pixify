package com.biho.login.state

sealed class LoginState(val error: String?) {
    data object Idle: LoginState(error = null)
    data object Loading: LoginState(error = null)
    data object Success: LoginState(error = null)
    class Failure(message: String?): LoginState(error = message)
}