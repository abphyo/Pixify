package com.biho.pixify.core.domain.usecases

import com.biho.pixify.core.model.danbooru.model.profile.ProfileEditField
import com.biho.pixify.core.model.danbooru.repository.ImageBoardRepository
import com.biho.pixify.core.model.danbooru.repository.UserRepository
import com.biho.pixify.core.model.util.mapSuccess

class UpdateProfileSettings(
    private val userRepository: UserRepository,
    private val imageBoardRepository: ImageBoardRepository
) {
    suspend fun update(
        field: ProfileEditField
    ): Result<Unit> {
        return imageBoardRepository.updateProfileSettings(field)
            .mapSuccess { _, statusCode ->
                if (statusCode == 204) imageBoardRepository.getProfile()
                    .mapSuccess { profile, _ ->
                        println("updating new profile")
                        try {
                            userRepository.updateUser(
                                user = profile,
                                roomId = field.roomId!!
                            )
                        } catch (e: Exception) {
                            if (e is NullPointerException)
                                println("local update failed, sth wrong with database!")
                        }
                    }
            }
    }

    suspend fun loginAndUpdate(
        field: ProfileEditField
    ): Result<Unit> {
        return imageBoardRepository.login(name = field.username, apiKey = field.apiKey)
            .mapSuccess { _, loginStatusCode ->
                if (loginStatusCode == 200) imageBoardRepository.updateProfileSettings(
                    field
                )
                    .mapSuccess { _, updateStatusCode ->
                        if (updateStatusCode == 200) imageBoardRepository.getProfile()
                            .mapSuccess { profile, finalStatusCode ->
                                if (finalStatusCode == 200) try {
                                    userRepository.updateUser(
                                        user = profile,
                                        roomId = field.roomId!!
                                    )
                                } catch (e: Exception) {
                                    if (e is NullPointerException)
                                        println("local update failed, sth wrong with database!")
                                }
                            }
                    }
            }
    }

}
