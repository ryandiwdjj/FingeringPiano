package DialogFragment

import API.ApiClient
import API.UserInterface
import Models.login
import Models.user
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import com.example.fingeringpiano.MainActivity
import com.example.fingeringpiano.R
import com.github.ybq.android.spinkit.SpinKitView
import kotlinx.android.synthetic.main.dialog_login.*
import kotlinx.android.synthetic.main.dialog_login.view.*
import retrofit2.Call
import retrofit2.Response


class LoginDialog: DialogFragment() {
    var apiInterface =ApiClient().getApiClient().create(UserInterface::class.java)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.dialog_login, container)

        //make dialog fragment rounded and transparent
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        val email_etxt = v.findViewById(R.id.email_etxt) as EditText
        val password_etxt = v.findViewById(R.id.password_etxt) as EditText
        val spin_kit = v.findViewById<SpinKitView>(R.id.spin_kit)
        val login_btn = v.findViewById<Button>(R.id.login_btn)
        val regis_btn = v.findViewById<Button>(R.id.register_btn)

        spin_kit.visibility = View.INVISIBLE

        email_etxt.setText("student@app.com")
        password_etxt.setText("password")



        login_btn!!.setOnClickListener {
            Log.d("login_btn", "clicked")
            Log.d("email", email_etxt.text.toString())

            email_etxt.isEnabled = false
            password_etxt.isEnabled = false
            login_btn.isEnabled = false
            regis_btn.isEnabled = false
            isCancelable = false

            spin_kit.visibility = View.VISIBLE


            val call =apiInterface.loginUser(email_etxt.text.toString(), password_etxt.text.toString())
            call.enqueue(object: retrofit2.Callback<login>{
                override fun onFailure(call: Call<login>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                    Log.e("onFailure", t.message)

                    email_etxt.isEnabled = true
                    password_etxt.isEnabled = true
                    login_btn.isEnabled = true
                    regis_btn.isEnabled = true
                    isCancelable = true
                }

                override fun onResponse(call: Call<login>, response: Response<login>) {
                    if(response.isSuccessful) {
                        Toast.makeText(context, "Login sebagai " + response.body()?.role, Toast.LENGTH_LONG).show()
                        Log.d("success", response.body()!!.access_token.length.toString())

                        //inserting to shared preferences
                        var sp  = activity?.getSharedPreferences("login", Context.MODE_PRIVATE)
                        var ed :SharedPreferences.Editor = sp!!.edit()
                        ed.putString("token", response.body()!!.access_token)
                        ed.apply()

                        (activity as MainActivity).onDialogResult()

                        dismiss()
                    }
                    else {
                        Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show()
                        Log.e("isFailure", response.toString())

                        spin_kit.visibility = View.INVISIBLE
                    }

                    email_etxt.isEnabled = true
                    password_etxt.isEnabled = true
                    login_btn.isEnabled = true
                    regis_btn.isEnabled = true
                    isCancelable = true
                }
            })

        }

        return v
    }

    override fun onResume() {
        dialog?.window?.setLayout((LinearLayout.LayoutParams.MATCH_PARENT + 1250), (LinearLayout.LayoutParams.MATCH_PARENT + 1000))

        super.onResume()
    }
}