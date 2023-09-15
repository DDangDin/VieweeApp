package com.capstone.vieweeapp.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val profileImage: String
)
