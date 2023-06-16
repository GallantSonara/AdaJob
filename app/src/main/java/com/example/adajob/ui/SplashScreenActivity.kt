package com.example.adajob.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.adajob.MainActivity
import com.example.adajob.databinding.ActivitySplashScreenBinding
import com.example.adajob.ui.login.LoginActivity
import com.example.adajob.utils.UserPreferences

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var tokenManager: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        tokenManager = UserPreferences(this)
        navigate()
    }

    private fun navigate(){
        if (tokenManager.getToken() != null){
            val intent = Intent(this, MainActivity::class.java)
            navigate(intent)
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            navigate(intent)
        }
    }

    private fun navigate(intent: Intent) {
        val splashTimer: Long = 3000
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(intent)
            finish()
        }, splashTimer)
    }

}