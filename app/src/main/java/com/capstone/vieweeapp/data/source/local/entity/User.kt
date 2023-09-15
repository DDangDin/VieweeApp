package com.capstone.vieweeapp.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("user_db")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val profileImage: String
)
