package com.example.expensemaster.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.expensemaster.R

class MainActivity : AppCompatActivity() {
    private var userId: Int = 0
    private lateinit var fabAddExpense: FloatingActionButton
    private lateinit var btnMonthlyExpenses: Button
    private lateinit var btnLogout: Button
    private lateinit var tvWelcome: TextView
    private lateinit var ivCenterImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fabAddExpense = findViewById(R.id.fabAddExpense)
        btnMonthlyExpenses = findViewById(R.id.btnMonthlyExpenses)
        btnLogout = findViewById(R.id.btnLogout)
        tvWelcome = findViewById(R.id.tvWelcome)
        ivCenterImage = findViewById(R.id.ivCenterImage)

        userId = intent.getIntExtra("USER_ID", 0)
        val userName = intent.getStringExtra("USER_NAME")

        if (userId == 0) {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        } else {
            tvWelcome.text = "Welcome, $userName"
        }

        fabAddExpense.setOnClickListener {
            val fragment = AddExpenseFragment.newInstance(userId)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        btnMonthlyExpenses.setOnClickListener {
            val intent = Intent(this, MonthlyExpensesActivity::class.java)
            intent.putExtra("USER_ID", userId)
            startActivity(intent)
        }

        btnLogout.setOnClickListener {
            userId = 0
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
