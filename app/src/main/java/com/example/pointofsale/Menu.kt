package com.example.pointofsale

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity
data class Farm(

    val farm_name:String,
    val address:String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

@Entity
data class Pen(
    val farm_id:Int,
    val pen_name:String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)


@Entity
data class Batch(
    val pen_id: Int,
    val chick_amt:Int,
    val startdate:Long = System.currentTimeMillis(),
    val enddate: Long?,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

@Entity
data class Mortality_Record(
    val batch_id: Int,
    val chick_amt:Int,
    val collecteddate:Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

@Entity
data class Feed_Record(
    val batch_id: Int,
    val feed_amt:Int,
    val collecteddate:Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

@Entity
data class User(

    val user:String,
    val email:String,
    val password: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)