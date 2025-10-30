package com.example.pointofsale

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Menu(

    val item_name:String,
    val item_type:String,
    val cost: Double,
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