package com.example.fingeringpiano

import API.ApiClient
import API.UserInterface
import Models.user
import android.app.DatePickerDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_update_profile.*
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.DAY_OF_MONTH
import java.util.Calendar.MONTH

class UpdateProfileActivity : AppCompatActivity() {
    var myCalendar = Calendar.getInstance()
//    var name_etxt = findViewById<EditText>(R.id.name_etxt)
//    var email_etxt = findViewById<EditText>(R.id.email_etxt)
//    var password_etxt = findViewById<EditText>(R.id.password_etxt)
//    var confirm_password_etxt = findViewById<EditText>(R.id.confirm_etxt)
//    var birthday_etxt = findViewById<EditText>(R.id.birthday_etxt)
//    var address_etxt = findViewById<EditText>(R.id.address_etxt)
//    var phone_etxt = findViewById<EditText>(R.id.phone_etxt)
//    var city_etxt = findViewById<EditText>(R.id.city_etxt)
//    var spinkit = findViewById<View>(R.id.spin_kit)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)


        var myCalendar = Calendar.getInstance()
        var name_etxt = findViewById<EditText>(R.id.name_etxt)
        var email_etxt = findViewById<EditText>(R.id.email_etxt)
        var password_etxt = findViewById<EditText>(R.id.password_etxt)
        var confirm_password_etxt = findViewById<EditText>(R.id.confirm_etxt)
        var birthday_etxt = findViewById<EditText>(R.id.birthday_etxt)
        var address_etxt = findViewById<EditText>(R.id.address_etxt)
        var phone_etxt = findViewById<EditText>(R.id.phone_etxt)
        var city_etxt = findViewById<EditText>(R.id.city_etxt)
        var spinkit = findViewById<View>(R.id.spin_kit)

        var apiInterface = ApiClient.getApiClient().create(UserInterface::class.java)

        var sp  = getSharedPreferences("login", Context.MODE_PRIVATE)

        val call = apiInterface.showUser(sp.getInt("id_user", 0) , "bearer" + sp.getString("token", null))
        Log.d("token","bearer" + sp.getString("token", null) )
        call.enqueue(object: retrofit2.Callback<user> {
            override fun onFailure(call: Call<user>, t: Throwable) {
                Log.d("onFailure", t.message.toString())

                Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_LONG).show()
                spinkit.visibility = View.INVISIBLE
            }

            override fun onResponse(call: Call<user>, response: Response<user>) {
                Log.d("onResponse", response.message().toString())
                spinkit.visibility = View.INVISIBLE

                if(response.isSuccessful){
                    name_etxt.setText(response.body()?.name)
                    email_etxt.setText(response.body()?.email)
                }
                else {
                    Toast.makeText(applicationContext, response.message(), Toast.LENGTH_LONG).show()
                }
            }
        })

        var date = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(MONTH, month)
            myCalendar.set(DAY_OF_MONTH, dayOfMonth)
            updateLabel()
        }

        birthday_etxt.setOnClickListener {
            DatePickerDialog(this@UpdateProfileActivity, date, myCalendar.get(Calendar.YEAR),
                myCalendar.get(MONTH), myCalendar.get(DAY_OF_MONTH)).show()
        }

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
            else if(etxtIsEmpty()){
                Toast.makeText(applicationContext, "Field harus terisi semua!", Toast.LENGTH_LONG).show()
            }
            else{
//                val call =apiInterface.updateUser(email_etxt.text.toString(), password_etxt.text.toString())
            }
        }
    }


    fun etxtIsEmpty(): Boolean{
        return name_etxt.text.toString().trim().length == 0 ||
                email_etxt.text.toString().trim().length == 0 ||
                password_etxt.text.toString().trim().length == 0 ||
                confirm_etxt.text.toString().trim().length == 0 ||
                address_etxt.text.toString().trim().length == 0 ||
                phone_etxt.text.toString().trim().length == 0 ||
                city_etxt.text.toString().trim().length == 0
    }

    private fun updateLabel() {
        var myFormat = "dd/MM/yyyy"
        var dateFormat = SimpleDateFormat(myFormat, Locale.US)

        birthday_etxt.setText(dateFormat.format(myCalendar.time))
    }
}
