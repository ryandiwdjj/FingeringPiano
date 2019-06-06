package com.example.fingeringpiano

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.util.*
import kotlin.concurrent.schedule

class LandingPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)

        //login token handler
        var login_token: String = "null"
        var sp  = getSharedPreferences("login", Context.MODE_PRIVATE)

        Timer().schedule(500){

            try {
                login_token = sp.getString("token", null)

                if (login_token.equals("null")) { //kalo null masuk ke login
                    startActivity(Intent(applicationContext, LoginActivity::class.java))
                    finish()
                }
                else {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                }
            }
            catch (e: Throwable) {
                var ed: SharedPreferences.Editor = sp.edit()
                ed.putString("token", "null")
                ed.putInt("id_user", 0)
                ed.apply()

                login_token = sp.getString("token", null)

                startActivity(Intent(applicationContext, LoginActivity::class.java))
                finish()
            }

            Log.d("login_token", login_token)
        }
    }
}
