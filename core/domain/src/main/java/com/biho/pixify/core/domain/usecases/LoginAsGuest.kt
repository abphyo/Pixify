package com.biho.pixify.core.domain.usecases

import com.biho.pixify.core.model.danbooru.model.profile.Profile
import com.biho.pixify.core.model.danbooru.repository.UserRepository

class LoginAsGuest(private val userRepository: UserRepository) {

    suspend operator fun invoke() {
        userRepository.addUser(Profile(isGuest = true))
    }

}