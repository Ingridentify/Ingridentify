package com.ingridentify.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "histories")
data class HistoryEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val cuisine: String,
    val recipes: String,
    val imageUrl: String
)