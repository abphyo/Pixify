package com.biho.login

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.datastore.core.DataStore
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.biho.login.state.LoginState
import com.biho.pixify.core.domain.usecases.UpdateProfileSettings
import com.biho.pixify.core.domain.usecases.GetUsersFromDatabase
import com.biho.pixify.core.model.danbooru.model.post.MediaType
import com.biho.pixify.core.model.danbooru.model.profile.ContentFilter
import com.biho.pixify.core.model.danbooru.model.profile.PostScreenImageType
import com.biho.pixify.core.model.danbooru.model.profile.Profile
import com.biho.pixify.core.model.danbooru.model.profile.ProfileEditField
import com.biho.pixify.core.model.danbooru.model.profile.getProfileEditField
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(SavedStateHandleSaveableApi::class)
class EditProfileViewModel(
    savedStateHandle: SavedStateHandle,
    private val sessionCache: DataStore<ProfileEditField>,
    private val updateProfileSettings: UpdateProfileSettings,
    getUsersFromDatabase: GetUsersFromDatabase,
) : ViewModel() {

    private val activeUser: Profile = getUsersFromDatabase.activeUser.value

    val profileEditField: Flow<ProfileEditField> = sessionCache.data

    private val _loginStatus = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginStatus: StateFlow<LoginState> get() = _loginStatus.asStateFlow()

    var isApiKeyVisible by savedStateHandle.saveable {
        mutableStateOf(false)
    }
        private set

    init {
        viewModelScope.launch {
            sessionCache.updateData {
                println("activeUser: $activeUser")
                activeUser.getProfileEditField()
            }
        }
    }

    fun confirmSettings(field: ProfileEditField) {
        viewModelScope.launch {
            when {
                activeUser.name != field.username -> updateProfileSettings.loginAndUpdate(
                    field = field
                ).onSuccess {
                    _loginStatus.update { LoginState.Success }
                }.onFailure { throwable ->
                    _loginStatus.update { LoginState.Failure(throwable.message) }
                }

                activeUser.apiKey != field.apiKey -> updateProfileSettings.loginAndUpdate(
                    field = field
                ).onSuccess {
                    _loginStatus.update { LoginState.Success }
                }.onFailure { throwable ->
                    _loginStatus.update { LoginState.Failure(throwable.message) }
                }

                activeUser.name == field.username && activeUser.apiKey == field.apiKey -> {
                    println("tried to update profile")
                    updateProfileSettings.update(
                        field = field
                    )
                }

            }
        }
    }

    fun updateUsername(value: TextFieldValue) {
        viewModelScope.launch {
            sessionCache.updateData {
                it.copy(username = value.text)
            }
        }
    }

    fun updateApiKey(value: TextFieldValue) {
        viewModelScope.launch {
            sessionCache.updateData {
                it.copy(apiKey = value.text)
            }
        }
    }

    fun setContentFilter(contentFilter: ContentFilter) {
        viewModelScope.launch {
            sessionCache.updateData {
                it.copy(contentFilter = contentFilter)
            }
        }
    }

    fun setPostScreenImageType(postScreenImageType: PostScreenImageType) {
        viewModelScope.launch {
            sessionCache.updateData {
                it.copy(postScreenImageType = postScreenImageType)
            }
        }
    }

    fun toggleSafeMode(enabled: Boolean) {
        viewModelScope.launch {
            sessionCache.updateData {
                it.copy(enabledSafeMode = enabled)
            }
        }
    }

    fun toggleHideDeletedPost(hide: Boolean) {
        viewModelScope.launch {
            sessionCache.updateData {
                it.copy(hideDeletedPosts = hide)
            }
        }
    }

    fun setHomeScreenImageType(homeScreenImageType: MediaType) {
        viewModelScope.launch {
            sessionCache.updateData {
                it.copy(homeScreenImageType = homeScreenImageType)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("edit viewModel clear")
        viewModelScope.launch {
            sessionCache.updateData {
                ProfileEditField()
            }
        }
    }

}