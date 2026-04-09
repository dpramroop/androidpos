package com.example.pointofsale

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Farm::class, Pen::class, Batch::class, Mortality_Record::class, Feed_Record::class, User::class],
    version = 4
)
abstract class MenuDatabase: RoomDatabase() {
    abstract val dao: FarmDao
    abstract val userDao: UserDao
    abstract val penDao: PenDao
    abstract val batchDao: BatchDao
    abstract val mortalityDao: MortalityDao
    abstract val feedDao: FeedDao
}