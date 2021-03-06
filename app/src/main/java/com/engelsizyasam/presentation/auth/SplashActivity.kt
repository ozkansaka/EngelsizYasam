package com.engelsizyasam.presentation.auth

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.engelsizyasam.databinding.ActivitySplashBinding
import com.engelsizyasam.presentation.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        val currentUser = auth.currentUser

        val login = Intent(this, LoginActivity::class.java)
        val main = Intent(this, MainActivity::class.java)

        var i = 0
        val timer = object : CountDownTimer(2000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                binding.splashProgress.progress = i * 100 / (2000 / 1000)
                i++
            }

            override fun onFinish() {
                i++
                binding.splashProgress.progress = 100
                if (isInternetOn(baseContext)) {

                    if (currentUser != null) {
                        startActivity(main)
                        finish()
                    } else {
                        startActivity(login)
                        finish()
                    }
                } else {
                    Toast.makeText(baseContext, "İnternet bağlantınızı kontrol ediniz...", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
        timer.start()
    }

    fun isInternetOn(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val activeNetwork = cm?.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }
}