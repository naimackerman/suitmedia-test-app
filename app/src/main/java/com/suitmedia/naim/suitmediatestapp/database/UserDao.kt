package com.suitmedia.naim.suitmediatestapp.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.suitmedia.naim.suitmediatestapp.network.UserResponseItem

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: List<UserResponseItem>)

    @Query("SELECT * FROM user")
    fun getAllUser(): PagingSource<Int, UserResponseItem>

    @Query("DELETE FROM user")
    suspend fun deleteAll()
}