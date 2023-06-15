package com.example.adajob

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.adajob.databinding.ActivityMainBinding
import com.example.adajob.utils.UserPreferences

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var pref: UserPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = UserPreferences(this)

    }
}