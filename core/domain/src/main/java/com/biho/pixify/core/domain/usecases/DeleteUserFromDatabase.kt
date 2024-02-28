package com.biho.pixify.core.domain.usecases

import com.biho.pixify.core.model.danbooru.model.profile.Profile
import com.biho.pixify.core.model.danbooru.repository.UserRepository

class DeleteUserFromDatabase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: Profile): Result<Unit> {
        return userRepository.deleteUser(user)
    }
}