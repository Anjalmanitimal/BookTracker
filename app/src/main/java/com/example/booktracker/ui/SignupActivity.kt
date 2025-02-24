package com.example.booktracker.ui
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.booktracker.R
import com.example.booktracker.databinding.ActivitySignupBinding
import com.example.booktracker.ui.LoginActivity
import com.example.booktracker.viewmodel.AuthViewModel
import com.example.booktracker.viewmodel.SignupViewModel


class SignupActivity : AppCompatActivity() {

    private lateinit var signupViewModel: SignupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        signupViewModel = ViewModelProvider(this).get(SignupViewModel::class.java)

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val signupButton = findViewById<Button>(R.id.signupButton)

        signupViewModel.signupStatus.observe(this, Observer { isSuccess ->
            if (isSuccess) {
                // Redirect to LoginActivity after successful signup
                Toast.makeText(this, "Signup Successful! Please log in.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish() // Close SignupActivity
            } else {
                Toast.makeText(this, "Signup Failed. Try again.", Toast.LENGTH_SHORT).show()
            }
        })

        signupButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            signupViewModel.signup(email, password)
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
