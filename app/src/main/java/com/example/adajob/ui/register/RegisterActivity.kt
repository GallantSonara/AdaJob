package com.example.adajob.ui.register

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.adajob.R
import com.example.adajob.databinding.ActivityRegisterBinding
import com.example.adajob.ui.login.LoginActivity
import com.example.adajob.utils.BaseResponses
import com.example.adajob.ViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory(applicationContext, Application())
        viewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]

        supportActionBar?.hide()

        binding.btnRegister.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val view = currentFocus
            view?.let {
                imm.hideSoftInputFromWindow(it.windowToken, 0)
            }
            validateRegisterUser()
            registerSetup()
        }
        binding.btnLogin.setOnClickListener {
            navigateLogin()
        }
    }

    private fun registerSetup(){
        viewModel.registerResult.observe(this) {
            when (it) {
                is BaseResponses.Loading -> {
                    showLoading(true)
                }
                is BaseResponses.Success -> {
                    showLoading(false)
                    processMessage("${it.data?.msg}, you must login first")
                    navigateLogin()
                }
                is BaseResponses.Error -> {
                    processMessage(it.msg)
                    showLoading(false)
                }
                else -> {
                    showLoading(false)
                }
            }
        }
    }

    private fun validateRegisterUser() {
        val nameEditText = binding.edtUsernameInput
        val emailEditText = binding.edtEmailInput
        val passwordEditText = binding.edtPasswordInput
        val retypePassword = binding.edtRePasswordInput

        if (nameEditText.error != null) {
            nameEditText.requestFocus()
            return
        }
        if (emailEditText.error != null) {
            emailEditText.requestFocus()
            return
        }
        if (passwordEditText.error != null) {
            passwordEditText.requestFocus()
            return
        }
        if (retypePassword.error != null) {
            passwordEditText.requestFocus()
            return
        }

        val name = nameEditText.text.toString()
        val email = emailEditText.text.toString()
        val pass = passwordEditText.text.toString()
        val repass = passwordEditText.text.toString()

        when {
            name.isBlank() -> {
                nameEditText.requestFocus()
                nameEditText.error = getString(R.string.error_empty_name)
            }
            email.isBlank() -> {
                emailEditText.requestFocus()
                emailEditText.error = getString(R.string.error_empty_email)
            }
            pass.isBlank() -> {
                passwordEditText.requestFocus()
                passwordEditText.error = getString(R.string.error_empty_password)
            }
            pass.length < 6 -> {
                binding.edtPassword.requestFocus()
                binding.edtPassword.error = getString(R.string.error_password)
            }
            else -> viewModel.registerUser(name, email, pass, repass)
        }
    }

    private fun processMessage(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(state: Boolean) {
        if (state) binding.progressBar.visibility = View.VISIBLE else binding.progressBar.visibility = View.GONE
        if (state)binding.bgDim.visibility = View.VISIBLE else binding.bgDim.visibility = View.GONE
        binding.apply {
            btnRegister.isClickable = !state
            btnRegister.isEnabled = !state
            btnLogin.isClickable = !state
            btnLogin.isEnabled = !state
        }
    }

    private fun navigateLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        finish()
        startActivity(intent)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        navigateLogin()
    }
}