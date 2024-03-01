package com.biho.database

import com.biho.database.daos.UserDao
import com.biho.database.entity.User
import com.biho.pixify.core.model.danbooru.model.profile.Profile
import com.biho.pixify.core.model.danbooru.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.runBlocking

class UserRepoImpl(
    private val ioScope: CoroutineScope,
    private val userDao: UserDao
) : UserRepository {

    override suspend fun addUser(user: Profile): Result<Int> {
        return try {
            ioScope.async {
                Result.success(userDao.addUser(user.toUser()).toInt())
            }.await()
        } catch (e: Exception) {
            Result.failure(e)
        }.onSuccess { roomId: Int ->
            switchUser(roomId)
        }.onFailure { throwable ->
            throwable.printStackTrace()
        }
    }

    override suspend fun isUserExists(name: String, apiKey: String): Boolean {
        return userDao.isUserExists(name = name, apiKey = apiKey) != 0
    }

    override suspend fun updateUser(user: Profile, roomId: Int) {
        println("updated user: $user")
        userDao.updateUser(user.toUser().copy(roomId = roomId))
    }

    override suspend fun deleteUser(user: Profile): Result<Unit> {
        return try {
            ioScope.async {
                Result.success(
                    userDao.switchUser(newActiveUserId = 1)
                )
            }.await().onSuccess {
                userDao.deleteUser(roomId = user.roomId!!)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getUsers(): Flow<List<Profile>> {
        return userDao.getUsers().transform { list ->
            println("usersFromRepo: $list")
            emit(list.map { it.toProfile() })
        }
    }

    override val activeUser: StateFlow<Profile> = userDao.getActiveUser(active = true)
        .transform<User, Profile> { user ->
            user.toProfile()
        }.stateIn(
            scope = ioScope,
            started = SharingStarted.Eagerly,
            initialValue = runBlocking {
                userDao.getActiveUser(active = true).first().toProfile()
            }
        )

    override suspend fun switchUser(roomId: Int) {
        try {
            ioScope.async {
                Result.success(
                    userDao.switchUser(newActiveUserId = roomId)
                )
            }.await()
        } catch (e: Exception) {
            Result.failure(e)
        }

//        try {
//            ioScope.async {
//                Result.success(
//                    userDao.updateUser(
//                        roomId = activeUser.value.roomId!!,
//                        active = false
//                    )
//                )
//            }.await()
//        } catch (e: Exception) {
//            Result.failure(e)
//        }.onSuccess {
//            userDao.updateUser(
//                roomId = activeUser.value.roomId!!,
//                active = false
//            )
//            userDao.updateUser(roomId = roomId, active = true)
//        }.onFailure { throwable ->
//            throwable.printStackTrace()
//        }

    }

}