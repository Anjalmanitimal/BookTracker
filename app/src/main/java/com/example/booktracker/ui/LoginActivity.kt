
package com.example.booktracker.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.booktracker.databinding.ActivityLoginBinding
import com.example.booktracker.ui.HomeActivity
import com.example.booktracker.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Correctly initialize ViewBinding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            authViewModel.login(email, password)
        }

        // ✅ Fix the `collect` issue
        lifecycleScope.launch {
            authViewModel.isAuthenticated.collect { isAuthenticated ->
                if (isAuthenticated) {
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    finish()
                }
            }
        }

        binding.tvSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
}
