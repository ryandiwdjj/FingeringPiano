package com.fingering.fingeringpiano.DialogFragment

import com.fingering.fingeringpiano.API.ApiClient
import com.fingering.fingeringpiano.API.UserInterface
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.android.volley.VolleyLog.e
import com.fingering.fingeringpiano.LandingPage
import com.fingering.fingeringpiano.Models.userResponse
import com.fingering.fingeringpiano.R
import com.fingering.fingeringpiano.UpdateProfileActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserDialog : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.dialog_detailuser, container)
        var apiInterface = ApiClient.getApiClient().create(
            UserInterface::class.java)
        var spinkit = v.findViewById<View>(R.id.spin_kit)
        spinkit.visibility = View.VISIBLE

        //make dialog fragment rounded and transparent
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE)
        }

        var email_etxt = v.findViewById(R.id.email_etxt) as EditText
        var name_etxt = v.findViewById(R.id.name_etxt) as EditText
        var edit_btn = v.findViewById<Button>(R.id.edit_btn)
        email_etxt.isEnabled = false
        name_etxt.isEnabled = false

        //login token handler
        var login_token: String = "null"
        var sp  = activity!!.getSharedPreferences("login", Context.MODE_PRIVATE)

        var call = apiInterface.showUser("Bearer " + sp.getString("token", null))
        Log.e("token on detail user",sp.getString("token", null) )
        call.enqueue(object: Callback<userResponse> {
            override fun onFailure(call: Call<userResponse>, t: Throwable) {
                e("onFailure", t.message.toString())

                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show()
                spinkit.visibility = View.INVISIBLE
            }

            override fun onResponse(call: Call<userResponse>, response: Response<userResponse>) {
                Log.e("onResponse", response.body()?.role)
//                Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show()

                spinkit.visibility = View.INVISIBLE

                if(response.isSuccessful){
                    name_etxt.setText(response.body()!!.userdata.name)
                    email_etxt.setText(response.body()!!.userdata.email)
                }
                else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show()
                }
            }
        })

        var logout_btn = v.findViewById<Button>(R.id.logout_btn)
        logout_btn.setOnClickListener {

            var builder = context?.let { it1 -> AlertDialog.Builder(it1) }
            builder?.setTitle("Anda yakin ingin logout?")

            ?.setNegativeButton("Tidak", { dialog, which -> })

            ?.setPositiveButton("Ya", { dialog, which ->
                var ed: SharedPreferences.Editor = sp.edit()
                ed.putString("token", "null")
                ed.apply()

                login_token = sp.getString("token", null)

                Toast.makeText(context, "Berhasil Logout!", Toast.LENGTH_LONG).show()

                startActivity(Intent(context, LandingPage::class.java))
                activity?.finish()
            })
            ?.show()


        }

        edit_btn.setOnClickListener {
            startActivity(Intent(context, UpdateProfileActivity::class.java))
        }

        return v
    }

    override fun onResume() {
        dialog?.window?.setLayout((LinearLayout.LayoutParams.WRAP_CONTENT), (LinearLayout.LayoutParams.WRAP_CONTENT))

        super.onResume()
    }
}