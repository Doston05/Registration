package com.myapps.registration.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myapps.registration.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert( user: User)

}