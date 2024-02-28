package com.biho.pixify.core.domain.usecases

import com.biho.pixify.core.model.danbooru.model.profile.Profile
import com.biho.pixify.core.model.danbooru.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class GetUsersFromDatabase(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<List<Profile>> {
        return userRepository.getUsers()
    }
    operator fun invoke(active: Boolean): Profile {
        return userRepository.activeUser.value
    }
}