package com.example.expensemaster.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.expensemaster.data.AppDatabase
import com.example.expensemaster.data.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.expensemaster.R

class RegisterActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var btnGoToLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnRegister = findViewById(R.id.btnRegister)
        btnGoToLogin = findViewById(R.id.btnGoToLogin)

        btnRegister.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val user = User(username = username, password = password)
                CoroutineScope(Dispatchers.IO).launch {
                    AppDatabase.getDatabase(applicationContext).userDao().insert(user)
                    runOnUiThread {
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        finish()
                    }
                }
            }
        }

        btnGoToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
