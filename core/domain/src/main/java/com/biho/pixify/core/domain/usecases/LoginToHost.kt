package com.biho.pixify.core.domain.usecases

import com.biho.pixify.core.model.danbooru.repository.ImageBoardRepository
import com.biho.pixify.core.model.danbooru.repository.UserRepository
import com.biho.pixify.core.model.util.mapSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginToHost(
    private val imageBoardRepository: ImageBoardRepository,
    private val userRepository: UserRepository
) {
    operator fun invoke(name: String, apiKey: String): Flow<Result<Int>> {
        return flow {
            when (userRepository.isUserExists(name = name, apiKey = apiKey)) {
                true -> emit(Result.failure(Throwable("User is already logged in.")))
                false -> emit(
                    imageBoardRepository
                        .login(name = name, apiKey = apiKey)
                        .mapSuccess { profile, statusCode ->
                            println("login success: $statusCode")
                            if (statusCode == 200)
                                userRepository.addUser(profile.copy(apiKey = apiKey))
                            statusCode
                        }
                )
            }

        }
    }
}