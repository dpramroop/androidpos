package com.example.pointofsale

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FarmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertFarm(farm: Farm)

    @Delete
    suspend fun deleteFarm(farm: Farm)

    @Query("Select * From Farm")
    fun getAllFarm():Flow<List<Farm>>
}

@Dao
interface PenDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPen(pen: Pen)

    @Delete
    suspend fun deletePen(pen: Pen)

    @Query("Select * From Pen where farm_id=farm_id")
    fun getAllPens(farm_id:Int):Flow<List<Pen>>
}
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("Select * From User")
    fun getAllUser():Flow<List<User>>
}