package com.example.expensemaster.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expensemaster.adapter.ExpenseAdapter
import com.example.expensemaster.data.AppDatabase
import com.example.expensemaster.data.Expense
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.expensemaster.R

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MonthlyExpensesActivity : AppCompatActivity() {

    private lateinit var rvMonthlyExpenses: RecyclerView
    private lateinit var tvTotalAmount: TextView
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monthly_expenses)

        rvMonthlyExpenses = findViewById(R.id.rvMonthlyExpenses)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)

        userId = intent.getIntExtra("USER_ID", 0)

        val month = SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(Date())

        CoroutineScope(Dispatchers.IO).launch {
            val expenseDao = AppDatabase.getDatabase(applicationContext).expenseDao()
            val expenses = expenseDao.getMonthlyExpenses(userId, month)
            val totalAmount = expenseDao.getTotalAmountForMonth(userId, month)
            withContext(Dispatchers.Main) {
                rvMonthlyExpenses.layoutManager = LinearLayoutManager(this@MonthlyExpensesActivity)
                rvMonthlyExpenses.adapter = ExpenseAdapter(this@MonthlyExpensesActivity, expenses,
                    onDelete = { expense ->
                        deleteExpense(expense)
                    }
                )
                tvTotalAmount.text = "Total: $totalAmount R"
            }
        }
    }

    private fun deleteExpense(expense: Expense) {
        CoroutineScope(Dispatchers.IO).launch {
            AppDatabase.getDatabase(applicationContext).expenseDao().delete(expense)
            val month = SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(Date())
            val expenses = AppDatabase.getDatabase(applicationContext).expenseDao().getMonthlyExpenses(userId, month)
            val totalAmount = AppDatabase.getDatabase(applicationContext).expenseDao().getTotalAmountForMonth(userId, month)
            withContext(Dispatchers.Main) {
                (rvMonthlyExpenses.adapter as ExpenseAdapter).apply {
                    this.expenses = expenses
                    notifyDataSetChanged()
                }
                tvTotalAmount.text = "Total: $totalAmount"
            }
        }
    }

//    private fun editExpense(expense: Expense) {
//        val fragment = AddExpenseFragment.newInstance(userId, expense.id)
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fragment_container, fragment)
//            .addToBackStack(null)
//            .commit()
//    }
}
