package com.biho.pixify.core.domain.usecases

import com.biho.pixify.core.model.danbooru.model.profile.ProfileEditField
import com.biho.pixify.core.model.danbooru.repository.ImageBoardRepository
import com.biho.pixify.core.model.danbooru.repository.UserRepository
import com.biho.pixify.core.model.util.mapSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EditProfileSettings(
    private val userRepository: UserRepository,
    private val imageBoardRepository: ImageBoardRepository
) {

    operator fun invoke(field: ProfileEditField, isNeededToLoginAgain: Boolean): Flow<Result<Unit>> {
        return when(isNeededToLoginAgain) {
            false -> flow {
                val result = imageBoardRepository.updateProfileSettings(field)
                    .mapSuccess { _, statusCode ->
                        if (statusCode == 200) imageBoardRepository.getProfile()
                            .mapSuccess { profile, _ ->
                                userRepository.updateUser(profile)
                            }
                    }
                emit(result)
            }
            true -> flow {
                val result = imageBoardRepository.login(name = field.username, apiKey = field.apiKey)
                    .mapSuccess { _, loginStatusCode ->
                        if (loginStatusCode == 200) imageBoardRepository.updateProfileSettings(field)
                            .mapSuccess { _, updateStatusCode ->
                                if (updateStatusCode == 200) imageBoardRepository.getProfile()
                                    .mapSuccess { profile, finalStatusCode ->
                                        if (finalStatusCode == 200) userRepository.updateUser(profile)
                                    }
                            }
                    }
                emit(result)
            }
        }
    }

}