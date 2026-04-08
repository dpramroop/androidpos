package com.example.pointofsale

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Farm::class,User::class],version = 2)
abstract class MenuDatabase: RoomDatabase() {
    abstract val dao: FarmDao
    abstract val userDao: UserDao
    abstract val penDao: PenDao
}