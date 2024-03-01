package com.biho.pixify.core.domain.usecases

import com.biho.pixify.core.model.danbooru.model.profile.Profile
import com.biho.pixify.core.model.danbooru.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID

class LoginAsGuest(private val userRepository: UserRepository) {

    suspend operator fun invoke(): Flow<Result<Int>> {
        return flow {
            userRepository.addUser(Profile(isGuest = true, uuid = UUID.randomUUID().toString()))
                .onSuccess {
                    emit(Result.success(it))
                }
                .onFailure {
                    emit(Result.failure(it))
                }
        }
    }

}