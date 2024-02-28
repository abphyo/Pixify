package com.biho.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.biho.login.state.LoginState
import com.biho.pixify.core.domain.usecases.EditProfileSettings
import com.biho.pixify.core.domain.usecases.GetUsersFromDatabase
import com.biho.pixify.core.model.danbooru.model.profile.Profile
import com.biho.pixify.core.model.danbooru.model.profile.ProfileEditField
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(SavedStateHandleSaveableApi::class)
class EditProfileViewModel(
    savedStateHandle: SavedStateHandle,
    private val sessionCache: DataStore<ProfileEditField>,
    private val editProfileSettings: EditProfileSettings,
    private val getUsersFromDatabase: GetUsersFromDatabase,
) : ViewModel() {

    private val activeUser: Profile = getUsersFromDatabase.invoke(active = true)

    var profileEditField: ProfileEditField by mutableStateOf(
        with(activeUser) {
            ProfileEditField(
                userId = id ?: 0,
                username = name ?: "",
                apiKey = apiKey ?: "",
                enabledSafeMode = profileSettings.enabledSafeMode,
                showDeletedPosts = !profileSettings.hideDeletedPosts,
                defaultImageSize = profileSettings.postScreenImagePref,
                blacklistedTags = profileSettings.blackListTags.toPersistentList()
            )
        }
    )
        private set

    private val _loginStatus = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginStatus: StateFlow<LoginState> get() = _loginStatus.asStateFlow()

    var isApiKeyVisible by savedStateHandle.saveable {
        mutableStateOf(false)
    }
        private set

    init {
        viewModelScope.launch {
            sessionCache.updateData {
                profileEditField
            }
        }
    }

    fun confirmSettings() {
        viewModelScope.launch {
            _loginStatus.update { LoginState.Loading }
            sessionCache.data.collectLatest { field ->
                when {
                    activeUser.name != field.username -> editProfileSettings.invoke(
                        field = field,
                        isNeededToLoginAgain = true
                    ).collectLatest { result ->
                        result.onSuccess {
                            _loginStatus.update { LoginState.Success }
                        }.onFailure { throwable ->
                            _loginStatus.update { LoginState.Failure(throwable.message) }
                        }
                    }
                    activeUser.apiKey != field.apiKey -> editProfileSettings.invoke(
                        field = field,
                        isNeededToLoginAgain = true
                    ).collectLatest { result ->
                        result.onSuccess {
                            _loginStatus.update { LoginState.Success }
                        }.onFailure { throwable ->
                            _loginStatus.update { LoginState.Failure(throwable.message) }
                        }
                    }
                    else -> editProfileSettings.invoke(field = field, isNeededToLoginAgain = false)
                }
            }
        }
    }

    fun updateUsername(text: String) {
        viewModelScope.launch {
            sessionCache.updateData {
                it.copy(username = text)
            }
        }
    }

    fun updateApiKey(text: String) {
        viewModelScope.launch {
            sessionCache.updateData {
                it.copy(apiKey = text)
            }
        }
    }

}