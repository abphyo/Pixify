package com.biho.pixify.core.model.danbooru.repository

import com.biho.pixify.core.model.danbooru.model.profile.Profile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
    suspend fun addUser(user: Profile)
    suspend fun isUserExists(name: String, apiKey: String): Boolean
    suspend fun updateUser(user: Profile)
    suspend fun deleteUser(user: Profile): Result<Unit>
    fun getUsers(): Flow<List<Profile>>
    val activeUser: StateFlow<Profile>
    suspend fun switchUser(roomId: Int)
}

