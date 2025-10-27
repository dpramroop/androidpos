package com.example.pointofsale

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMenu(menu: Menu)

    @Delete
    suspend fun deleteMenu(menu: Menu)

    @Query("Select * From Menu")
    fun getAllMenu():Flow<List<Menu>>
}