package com.example.fingeringpiano

import API.ApiClient
import API.UserInterface
import Models.login
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.github.ybq.android.spinkit.SpinKitView
import retrofit2.Call
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var apiInterface = ApiClient().getApiClient().create(UserInterface::class.java)

        val email_etxt = findViewById(R.id.email_etxt) as EditText
        val password_etxt = findViewById(R.id.password_etxt) as EditText
        val spin_kit = findViewById<SpinKitView>(R.id.spin_kit)
        val login_btn = findViewById<Button>(R.id.login_btn)
        val regis_btn = findViewById<Button>(R.id.register_btn)

        spin_kit.visibility = View.INVISIBLE

        email_etxt.setText("student@app.com")
        password_etxt.setText("password")


        regis_btn.setOnClickListener {
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
        }

        login_btn.setOnClickListener {
            Log.d("login_btn", "clicked")
            Log.d("email", email_etxt.text.toString())

            email_etxt.isEnabled = false
            password_etxt.isEnabled = false
            login_btn.isEnabled = false
            regis_btn.isEnabled = false

            spin_kit.visibility = View.VISIBLE


            val call =apiInterface.loginUser(email_etxt.text.toString(), password_etxt.text.toString())
            call.enqueue(object: retrofit2.Callback<login>{
                override fun onFailure(call: Call<login>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    Log.e("onFailure", t.message)

                    email_etxt.isEnabled = true
                    password_etxt.isEnabled = true
                    login_btn.isEnabled = true
                    regis_btn.isEnabled = true
                }

                override fun onResponse(call: Call<login>, response: Response<login>) {
                    if(response.isSuccessful) {
                        Toast.makeText(applicationContext, "Login sebagai " + response.body()?.role, Toast.LENGTH_LONG).show()
                        Log.d("success", response.body().toString())

                        //inserting to shared preferences
                        var sp  = getSharedPreferences("login", Context.MODE_PRIVATE)
                        var ed : SharedPreferences.Editor = sp!!.edit()
                        ed.putString("token", response.body()!!.access_token)
                        ed.putInt("id_user", response.body()!!.id)
                        ed.apply()

                        startActivity(Intent(applicationContext, MainActivity::class.java))
                        finish()
                    }
                    else {
                        Toast.makeText(applicationContext, response.message(), Toast.LENGTH_LONG).show()
                        Log.e("isFailure", response.toString())

                        spin_kit.visibility = View.INVISIBLE
                    }

                    email_etxt.isEnabled = true
                    password_etxt.isEnabled = true
                    login_btn.isEnabled = true
                    regis_btn.isEnabled = true
                }
            })

        }
    }
}
