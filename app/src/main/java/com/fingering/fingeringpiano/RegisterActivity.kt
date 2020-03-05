package com.fingering.fingeringpiano

import com.fingering.fingeringpiano.API.ApiClient
import com.fingering.fingeringpiano.API.UserInterface
import com.fingering.fingeringpiano.Models.login
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.*
import com.github.ybq.android.spinkit.SpinKitView
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_update_profile.address_etxt
import kotlinx.android.synthetic.main.activity_update_profile.birthday_etxt
import kotlinx.android.synthetic.main.activity_update_profile.city_etxt
import kotlinx.android.synthetic.main.activity_update_profile.email_etxt
import kotlinx.android.synthetic.main.activity_update_profile.name_etxt
import kotlinx.android.synthetic.main.activity_update_profile.phone_etxt
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity() {
    var apiInterface = ApiClient.getApiClient().create(
        UserInterface::class.java)
    var myCalendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var apiInterface = ApiClient.getApiClient().create(
            UserInterface::class.java)
        var myCalendar = Calendar.getInstance()

        var name_etxt = findViewById<EditText>(R.id.name_etxt)
        var email_etxt = findViewById<EditText>(R.id.email_etxt)
        var password_etxt = findViewById<EditText>(R.id.password_etxt)
        var confirm_password_etxt = findViewById<EditText>(R.id.confirm_etxt)
        var birthday_etxt = findViewById<EditText>(R.id.birthday_etxt)
        var address_etxt = findViewById<EditText>(R.id.address_etxt)
        var gender_radio = findViewById<RadioGroup>(R.id.radioGender)
        var phone_etxt = findViewById<EditText>(R.id.phone_etxt)
        var city_etxt = findViewById<EditText>(R.id.city_etxt)
        var spin_kit = findViewById<SpinKitView>(R.id.spin_kit)

        spin_kit.visibility = View.INVISIBLE

        var date = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            var myFormat = "yyyy-MM-dd"
            var dateFormat = SimpleDateFormat(myFormat, Locale.US)

//            Toast.makeText(applicationContext, dateFormat.format(myCalendar.time) , Toast.LENGTH_LONG).show()

            birthday_etxt.setText(dateFormat.format(myCalendar.time))
        }

        birthday_etxt.setOnClickListener {
            DatePickerDialog(
                this@RegisterActivity, date, myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        var cancel_btn = findViewById<Button>(R.id.cancel_btn)
        cancel_btn.setOnClickListener {
            //            finish()
            if (etxtIsEmpty() == true) {
                finish()
            } else {
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

            var radio_button = findViewById<RadioButton>(gender_radio.checkedRadioButtonId)
            var male_radio = findViewById<RadioButton>(R.id.radioMale)
            var female_radio = findViewById<RadioButton>(R.id.radioFemale)

            Log.d("save_btn", "clicked")

            name_etxt.isEnabled = false
            email_etxt.isEnabled = false
            password_etxt.isEnabled = false
            birthday_etxt.isEnabled = false
            address_etxt.isEnabled = false
            male_radio.isEnabled = false
            female_radio.isEnabled = false
            phone_etxt.isEnabled = false
            city_etxt.isEnabled = false
            cancel_btn.isEnabled = false
            save_btn.isEnabled = false
            spin_kit.visibility = View.VISIBLE

            if (!male_radio.isChecked && !female_radio.isChecked) {
                Toast.makeText(applicationContext, "Jenis Kelamin belum dipilih!", Toast.LENGTH_LONG).show()

                name_etxt.isEnabled = true
                email_etxt.isEnabled = true
                password_etxt.isEnabled = true
                birthday_etxt.isEnabled = true
                address_etxt.isEnabled = true
                male_radio.isEnabled = true
                female_radio.isEnabled = true
                phone_etxt.isEnabled = true
                city_etxt.isEnabled = true
                cancel_btn.isEnabled = true
                save_btn.isEnabled = true
                spin_kit.visibility = View.INVISIBLE

            } else if (password_etxt.text.toString() != confirm_password_etxt.text.toString()) {
                Toast.makeText(applicationContext, "Password tidak sama", Toast.LENGTH_LONG).show()

                name_etxt.isEnabled = true
                email_etxt.isEnabled = true
                password_etxt.isEnabled = true
                birthday_etxt.isEnabled = true
                address_etxt.isEnabled = true
                male_radio.isEnabled = true
                female_radio.isEnabled = true
                phone_etxt.isEnabled = true
                city_etxt.isEnabled = true
                cancel_btn.isEnabled = true
                save_btn.isEnabled = true
                spin_kit.visibility = View.INVISIBLE

            } else if (etxtIsEmpty()) {
                Toast.makeText(applicationContext, "Field harus terisi semua!", Toast.LENGTH_LONG).show()

                name_etxt.isEnabled = true
                email_etxt.isEnabled = true
                password_etxt.isEnabled = true
                birthday_etxt.isEnabled = true
                address_etxt.isEnabled = true
                male_radio.isEnabled = true
                female_radio.isEnabled = true
                phone_etxt.isEnabled = true
                city_etxt.isEnabled = true
                cancel_btn.isEnabled = true
                save_btn.isEnabled = true
                spin_kit.visibility = View.INVISIBLE

            } else {

                val call = apiInterface.registerUser(
                    name_etxt.text.toString(),
                    email_etxt.text.toString(),
                    password_etxt.text.toString(),
                    birthday_etxt.text.toString(),
                    address_etxt.text.toString(),
                    radio_button.text.toString(),
                    phone_etxt.text.toString(),
                    city_etxt.text.toString()
                )

                call.enqueue(object : Callback<login> {
                    override fun onFailure(call: Call<login>, t: Throwable) {
                        Toast.makeText(applicationContext,
                        "Terjadi kesalahan saat memasukkan data,pastikan email anda sudah benar dan blm pernah terdaftar sebelumnya"
                        , Toast.LENGTH_LONG).show()

                        Log.e("onFailure", t.message)

                        name_etxt.isEnabled = true
                        email_etxt.isEnabled = true
                        password_etxt.isEnabled = true
                        birthday_etxt.isEnabled = true
                        address_etxt.isEnabled = true
                        male_radio.isEnabled = true
                        female_radio.isEnabled = true
                        phone_etxt.isEnabled = true
                        city_etxt.isEnabled = true
                        cancel_btn.isEnabled = true
                        save_btn.isEnabled = true
                        spin_kit.visibility = View.INVISIBLE
                    }

                    override fun onResponse(call: Call<login>, response: Response<login>) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                applicationContext,
                                "Login sebagai " + response.body()?.role,
                                Toast.LENGTH_LONG
                            ).show()
                            Log.d("success", response.body().toString())

                            //inserting to shared preferences
                            var sp = getSharedPreferences("login", Context.MODE_PRIVATE)
                            var ed: SharedPreferences.Editor = sp!!.edit()
                            ed.putString("token", response.body()!!.access_token)
                            ed.putInt("id_user", response.body()!!.id)
                            ed.apply()

                            startActivity(Intent(applicationContext, MainActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(applicationContext,
                                "Terjadi kesalahan saat memasukkan data,pastikan email anda sudah benar dan blm pernah terdaftar sebelumnya"
                                , Toast.LENGTH_LONG).show()
                            Log.e("isFailure", response.toString())

                            name_etxt.isEnabled = true
                            email_etxt.isEnabled = true
                            password_etxt.isEnabled = true
                            birthday_etxt.isEnabled = true
                            address_etxt.isEnabled = true
                            male_radio.isEnabled = true
                            female_radio.isEnabled = true
                            phone_etxt.isEnabled = true
                            city_etxt.isEnabled = true
                            cancel_btn.isEnabled = true
                            save_btn.isEnabled = true
                            spin_kit.visibility = View.INVISIBLE
                        }
                    }
                })
            }
        }
    }

    fun etxtIsEmpty(): Boolean {
        return name_etxt.text.toString().trim().length == 0 ||
                email_etxt.text.toString().trim().length == 0 ||
                password_etxt.text.toString().trim().length == 0 ||
                confirm_etxt.text.toString().trim().length == 0 ||
                address_etxt.text.toString().trim().length == 0 ||
                phone_etxt.text.toString().trim().length == 0 ||
                city_etxt.text.toString().trim().length == 0
    }

}
