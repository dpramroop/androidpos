package com.example.pointofsale

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Menu::class,User::class],version = 2)
abstract class MenuDatabase: RoomDatabase() {
    abstract val dao: MenuDao
    abstract val userDao: UserDao
}