package com.example.expensemaster.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ExpenseDao {
    @Insert
    suspend fun insert(expense: Expense)

    @Query("SELECT * FROM expenses WHERE userId = :userId")
    suspend fun getAllExpenses(userId: Int): List<Expense>

//    @Query("SELECT * FROM expenses WHERE userId = :userId AND date LIKE :month || '%'")
//    suspend fun getMonthlyExpenses(userId: Int, month: String): List<Expense>

    @Query("SELECT * FROM expenses WHERE userId = :userId AND strftime('%Y-%m', date) = :month")
    fun getMonthlyExpenses(userId: Int, month: String): List<Expense>

    @Query("SELECT SUM(amount) FROM expenses WHERE userId = :userId AND strftime('%Y-%m', date) = :month")
    fun getTotalAmountForMonth(userId: Int, month: String): Int

    @Delete
    suspend fun delete(expense: Expense)

    @Update
    suspend fun update(expense: Expense)

    @Query("SELECT * FROM expenses WHERE id = :id")
    suspend fun getExpenseById(id: Int): Expense
}
