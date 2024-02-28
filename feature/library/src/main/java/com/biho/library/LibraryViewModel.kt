package com.biho.library

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biho.pixify.core.domain.usecases.DeleteUserFromDatabase
import com.biho.pixify.core.domain.usecases.GetUsersFromDatabase
import com.biho.pixify.core.domain.usecases.SwitchActiveUserToDatabase
import com.biho.pixify.core.model.danbooru.model.profile.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LibraryViewModel(
    private val getUsersFromDatabase: GetUsersFromDatabase,
    private val deleteUserFromDatabase: DeleteUserFromDatabase,
    private val switchActiveUserToDatabase: SwitchActiveUserToDatabase
) : ViewModel() {

    private val _profiles = MutableStateFlow<List<Profile>>(emptyList())
    val profiles: StateFlow<List<Profile>> get() = _profiles.asStateFlow()

    var isDeleteSuccess = mutableStateOf(false)
        private set

    init {
        getUsers()
    }

    private fun getUsers() {
        viewModelScope.launch {
            getUsersFromDatabase.invoke().collectLatest { list ->
                println("usersFromViewModel: $list")
                _profiles.update { list }
            }
        }
    }

    fun switchToProfile(roomId: Int?) {
        viewModelScope.launch {
            switchActiveUserToDatabase.invoke(roomId ?: 0)
        }
    }

    fun deleteProfile(user: Profile) {
        viewModelScope.launch {
            deleteUserFromDatabase.invoke(user)
                .onSuccess {
                    isDeleteSuccess.value = true
                }
                .onFailure {
                    println("deleting account: ${it.localizedMessage}")
                    isDeleteSuccess.value = false
                }
        }
    }

}