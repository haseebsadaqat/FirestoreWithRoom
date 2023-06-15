package com.example.task2kotlin.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(@PrimaryKey(autoGenerate = true)
                    val id: Long = 0,
                    val text: String)
