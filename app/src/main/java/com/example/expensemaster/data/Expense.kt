package com.example.expensemaster.data


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amount: Double,
    val title: String,
    val description: String,
    val date: String,
    val time: String,
    val userId: Int
)
