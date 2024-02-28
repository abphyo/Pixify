package com.biho.pixify.core.domain.usecases

import com.biho.pixify.core.model.danbooru.repository.UserRepository

class SwitchActiveUserToDatabase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(roomId: Int) {
        userRepository.switchUser(roomId)
    }
}