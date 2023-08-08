package com.example.todok_example.domain.category

import androidx.annotation.ColorInt
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    @ColorInt
    val colorInt: Int,
)