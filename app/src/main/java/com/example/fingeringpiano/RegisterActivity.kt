package com.example.fingeringpiano

import API.ApiClient
import API.UserInterface
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {
    var apiInterface = ApiClient.getApiClient().create(UserInterface::class.java)

    var name_etxt = findViewById<EditText>(R.id.name_etxt)
    var email_etxt = findViewById<EditText>(R.id.email_etxt)
    var password_etxt = findViewById<EditText>(R.id.password_etxt)
    var confirm_password_etxt = findViewById<EditText>(R.id.confirm_etxt)
    var calendar_etxt = findViewById<EditText>(R.id.calendar_etxt)
    var address_etxt = findViewById<EditText>(R.id.address_etxt)
    var phone_etxt = findViewById<EditText>(R.id.phone_etxt)
    var city_etxt = findViewById<EditText>(R.id.city_etxt)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)



        var cancel_btn = findViewById<Button>(R.id.cancel_btn)
        cancel_btn.setOnClickListener {
//            finish()
            if(etxtIsEmpty() == true) {
                finish()
            }
            else {
                AlertDialog.Builder(applicationContext)
                    .setTitle("Batalkan aksi")
                    .setMessage("Anda yakin akan membatalkan aksi anda?")
                    .setPositiveButton("Ya") { _, which ->
                        finish()
                    }
                    .setNegativeButton("Tidak") { _, which ->

                    }
                    .show()
            }
        }

        var save_btn = findViewById<Button>(R.id.save_btn)
        save_btn.setOnClickListener {
            if(password_etxt.text.toString() != confirm_password_etxt.text.toString()) {
                Toast.makeText(applicationContext, "Password tidak sama", Toast.LENGTH_LONG).show()
            }
            else {
//                val call =apiInterface.loginUser(email_etxt.text.toString(), password_etxt.text.toString())
            }
        }
    }

    fun etxtIsEmpty(): Boolean{
        return name_etxt.text.toString().trim().length == 0 &&
                email_etxt.text.toString().trim().length == 0 &&
                password_etxt.text.toString().trim().length == 0 &&
                confirm_password_etxt.text.toString().trim().length == 0 &&
                address_etxt.text.toString().trim().length == 0 &&
                phone_etxt.text.toString().trim().length == 0 &&
                city_etxt.text.toString().trim().length == 0
    }
}
