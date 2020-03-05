package com.fingering.fingeringpiano

import com.fingering.fingeringpiano.API.ApiClient
import com.fingering.fingeringpiano.API.UserInterface
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.*
import com.fingering.fingeringpiano.Models.updateResponse
import com.fingering.fingeringpiano.Models.userResponse
import kotlinx.android.synthetic.main.activity_update_profile.*
import retrofit2.Call
import retrofit2.Callback
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
        var birthday_etxt = findViewById<EditText>(R.id.birthday_etxt)
        var address_etxt = findViewById<EditText>(R.id.address_etxt)
        var gender_radio = findViewById<RadioGroup>(R.id.radioGender)
        var phone_etxt = findViewById<EditText>(R.id.phone_etxt)
        var city_etxt = findViewById<EditText>(R.id.city_etxt)
        var spinkit = findViewById<View>(R.id.spin_kit)

        var radio_button = findViewById<RadioButton>(gender_radio.checkedRadioButtonId)
        var male_radio = findViewById<RadioButton>(R.id.radioMale)
        var female_radio = findViewById<RadioButton>(R.id.radioFemale)

        var apiInterface = ApiClient.getApiClient().create(
            UserInterface::class.java
        )

        var sp = getSharedPreferences("login", Context.MODE_PRIVATE)

        val call = apiInterface.showUser("Bearer " + sp.getString("token", null))
        Log.d("token", "Bearer " + sp.getString("token", null))
        call.enqueue(object : Callback<userResponse> {
            override fun onFailure(call: Call<userResponse>, t: Throwable) {
                Log.e("onFailure get user", t.message.toString())

                Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_LONG).show()
                spinkit.visibility = View.INVISIBLE
            }

            override fun onResponse(call: Call<userResponse>, response: Response<userResponse>) {
                Log.d("onResponse get user", response.message().toString())
                spinkit.visibility = View.INVISIBLE

                if (response.isSuccessful) {
                    name_etxt.setText(response.body()?.userdata!!.name)
                    email_etxt.setText(response.body()?.userdata!!.email)
                    birthday_etxt.setText(response.body()?.userdata!!.detail?.dateofbirth)
                    address_etxt.setText(response.body()?.userdata!!.detail?.address)
                    if (response.body()?.userdata!!.detail?.gender!!.contains("L")) {
                        male_radio.performClick()
                    } else {
                        female_radio.performClick()
                    }
                    phone_etxt.setText(response.body()?.userdata!!.detail?.phoneNumber)
                    city_etxt.setText(response.body()?.userdata!!.detail?.city)

                } else {
                    Toast.makeText(applicationContext, response.message(), Toast.LENGTH_LONG).show()
                }
            }
        })

        var date = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(MONTH, month)
            myCalendar.set(DAY_OF_MONTH, dayOfMonth)

            var myFormat = "yyyy-MM-dd"
            var dateFormat = SimpleDateFormat(myFormat, Locale.US)

            birthday_etxt.setText(dateFormat.format(myCalendar.time))
        }

        birthday_etxt.setOnClickListener {
            DatePickerDialog(
                this@UpdateProfileActivity, date, myCalendar.get(Calendar.YEAR),
                myCalendar.get(MONTH), myCalendar.get(DAY_OF_MONTH)
            ).show()
        }

        var cancel_btn = findViewById<Button>(R.id.cancel_btn)
        cancel_btn.setOnClickListener {
            var builder = AlertDialog.Builder(this)
            builder.setTitle("Batalkan aksi?")
                .setPositiveButton("Ya", { dialog, which ->
                    finish()
                })
                .setNegativeButton("Tidak", { dialog, which ->

                })
                .show()
        }

        var save_btn = findViewById<Button>(R.id.save_btn)
        save_btn.setOnClickListener {

            var radio_button = findViewById<RadioButton>(gender_radio.checkedRadioButtonId)
            var male_radio = findViewById<RadioButton>(R.id.radioMale)
            var female_radio = findViewById<RadioButton>(R.id.radioFemale)

            name_etxt.isEnabled = false
            email_etxt.isEnabled = false
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
                Toast.makeText(
                    applicationContext,
                    "Jenis Kelamin belum dipilih!",
                    Toast.LENGTH_LONG
                ).show()

                name_etxt.isEnabled = true
                email_etxt.isEnabled = true
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
                Toast.makeText(applicationContext, "Field harus terisi semua!", Toast.LENGTH_LONG)
                    .show()

                name_etxt.isEnabled = true
                email_etxt.isEnabled = true
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

                val call = apiInterface.updateUser(
                    "Bearer " + sp.getString("token", null),
                    name_etxt.text.toString(),
                    email_etxt.text.toString(),
                    birthday_etxt.text.toString(),
                    address_etxt.text.toString(),
                    radio_button.text.toString(),
                    phone_etxt.text.toString(),
                    city_etxt.text.toString()
                )

                call.enqueue(object : Callback<updateResponse> {
                    override fun onFailure(call: Call<updateResponse>, t: Throwable) {
                        Toast.makeText(applicationContext,
                            "Terjadi kesalahan saat memasukkan data,pastikan email anda sudah benar dan blm pernah terdaftar sebelumnya"
                            , Toast.LENGTH_LONG).show()

                        Log.e("onFailure update", call.toString())
                        Log.e("onFailure update", t.localizedMessage)

                        name_etxt.isEnabled = true
                        email_etxt.isEnabled = true
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

                    override fun onResponse(call: Call<updateResponse>, response: Response<updateResponse>) {
                        if (response.isSuccessful) {
                            startActivity(Intent(applicationContext, MainActivity::class.java))
                            Toast.makeText(applicationContext,
                                "Berhasil update data!", Toast.LENGTH_LONG).show()
                            finish()
                        } else {
                            Toast.makeText(applicationContext,
                                "Terjadi kesalahan saat memasukkan data,pastikan email anda sudah benar dan blm pernah terdaftar sebelumnya"
                                , Toast.LENGTH_LONG).show()

                            Log.e("isFailure on update", response.toString())

                            name_etxt.isEnabled = true
                            email_etxt.isEnabled = true
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
                address_etxt.text.toString().trim().length == 0 ||
                phone_etxt.text.toString().trim().length == 0 ||
                city_etxt.text.toString().trim().length == 0
    }

}
