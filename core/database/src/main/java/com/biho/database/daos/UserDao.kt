package com.biho.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.TypeConverters
import androidx.room.Update
import com.biho.database.UserTypeConvertors
import com.biho.database.entity.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

@Dao
@TypeConverters(UserTypeConvertors::class)
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User): Long

    @Query("select count(*) from users where name = :name and api_key = :apiKey")
    suspend fun isUserExists(name: String, apiKey: String): Int

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("select * from users")
    fun getUsers(): Flow<List<User>>

    @Query("select * from users where is_active = :active limit 1")
    fun getActiveUser(active: Boolean): Flow<User>

    @Query("update users set is_active = :active where room_id = :roomId")
    suspend fun updateUser(roomId: Int, active: Boolean)

    @Transaction
    suspend fun switchUser(newActiveUserId: Int) {
        val currentActiveUser = getActiveUser(active = true).first()
        updateUser(roomId = currentActiveUser.roomId, active = false)
        updateUser(roomId = newActiveUserId, active = true)
    }

}