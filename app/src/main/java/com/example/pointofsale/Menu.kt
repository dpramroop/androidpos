package com.example.pointofsale

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "farm")
data class Farm(

    val farm_name:String,
    val address:String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

@Entity (tableName = "pen")
data class Pen(
    val farm_id:Int,
    val pen_name:String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)


@Entity(tableName = "batch")
data class Batch(
    val pen_id: Int,
    val chick_amt:Int,
    val startdate:Long = System.currentTimeMillis(),
    val enddate: Long?,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

@Entity(tableName = "mortality_record")
data class Mortality_Record(
    val batch_id: Int,
    val chick_amt:Int,
    val collecteddate:Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

@Entity (tableName = "feed_record")
data class Feed_Record(
    val batch_id: Int,
    val feed_amt:Int,
    val collecteddate:Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

@Entity (tableName = "user")
data class User(

    val user:String,
    val email:String,
    val password: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)