package com.example.pointofsale

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Menu::class],version = 1)
abstract class MenuDatabase: RoomDatabase() {
    abstract val dao: MenuDao
}