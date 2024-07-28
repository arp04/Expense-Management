package com.example.expensemaster.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.expensemaster.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.expensemaster.R


class LoginActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    val user = AppDatabase.getDatabase(applicationContext).userDao().getUser(username, password)
                    withContext(Dispatchers.Main) {
                        if (user != null) {
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.putExtra("USER_NAME", user.username)
                            intent.putExtra("USER_ID", user.id)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, "wrong credentials", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}
