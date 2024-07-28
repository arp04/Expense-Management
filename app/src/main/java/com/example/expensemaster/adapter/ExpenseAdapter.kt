package com.example.expensemaster.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expensemaster.R
import com.example.expensemaster.data.Expense

class ExpenseAdapter(
    private val context: Context,
    var expenses: List<Expense>,
    private val onDelete: (Expense) -> Unit
) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]
        holder.bind(expense)
    }

    override fun getItemCount(): Int {
        return expenses.size
    }

    inner class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvExpenseTitle: TextView = itemView.findViewById(R.id.tvExpenseTitle)
        private val tvExpenseAmount: TextView = itemView.findViewById(R.id.tvExpenseAmount)
        private val tvExpenseDate: TextView = itemView.findViewById(R.id.tvExpenseDate)
        private val btnDeleteExpense: Button = itemView.findViewById(R.id.btnDeleteExpense)

        fun bind(expense: Expense) {
            tvExpenseTitle.text = expense.title
            tvExpenseAmount.text = "Amount: ${expense.amount}"
            tvExpenseDate.text = "Date: ${expense.date}"

            btnDeleteExpense.setOnClickListener {
                onDelete(expense)
            }
        }
    }
}
