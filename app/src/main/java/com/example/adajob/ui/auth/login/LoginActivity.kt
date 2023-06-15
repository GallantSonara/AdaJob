package com.example.adajob.ui.auth.login

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.adajob.MainActivity
import com.example.adajob.R
import com.example.adajob.databinding.ActivityLoginBinding
import com.example.adajob.ui.auth.register.RegisterActivity
import com.example.adajob.utils.BaseResponses
import com.example.adajob.utils.UserPreferences
import com.example.adajob.utils.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var tokenManager : UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory(applicationContext, Application())
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        tokenManager = UserPreferences(this)
        val token = tokenManager.getToken()
        if (!token.isNullOrBlank()) {
            navigateToHome()
        }

        supportActionBar?.hide()

        binding.loginbtn.setOnClickListener{
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val view = currentFocus
            view?.let {
                imm.hideSoftInputFromWindow(it.windowToken, 0)
            }
            validateLoginUser()
            loginSetup()
        }
        binding.btnRegister.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun loginSetup(){
        viewModel.loginResult.observe(this) {
            when (it) {
                is BaseResponses.Loading -> {
                    showLoading(true)
                }
                is BaseResponses.Success -> {
                    it.data?.accessToken?.let { it1 -> tokenManager.saveAuthToken(it1) }
                    if (it.data?.accessToken?.isNotEmpty() == true) navigateToHome()
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

    private fun validateLoginUser() {
        val email = binding.edtEmailInput.text.toString()
        val pass = binding.edtPasswordInput.text.toString()

        when{
            email.isBlank() -> {
                binding.edtEmail.requestFocus()
                binding.edtEmail.error = getString(R.string.error_empty_password)
            }
            pass.isBlank() -> {
                binding.edtPassword.requestFocus()
                binding.edtPassword.error = getString(R.string.error_empty_password)
            }
            pass.length < 6 -> {
                binding.edtPassword.requestFocus()
                binding.edtPassword.error = getString(R.string.error_password_more_8)
            }
            else -> viewModel.loginUser(email, pass)
        }
    }

    private fun processMessage(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun showLoading(state: Boolean) {
        if (state) binding.progressBar.visibility = View.VISIBLE else binding.progressBar.visibility = View.GONE
        if (state)binding.bgDim.visibility = View.VISIBLE else binding.bgDim.visibility = View.GONE
        binding.apply {
            btnRegister.isClickable = !state
            btnRegister.isEnabled = !state
            loginbtn.isClickable = !state
            loginbtn.isEnabled = !state
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finishAffinity()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}