package DialogFragment

import API.ApiClient
import API.UserInterface
import Models.login
import Models.user
import android.content.Context
import android.content.Intent
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
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.example.fingeringpiano.LandingPage
import com.example.fingeringpiano.LoginActivity
import com.example.fingeringpiano.R
import retrofit2.Call
import retrofit2.Response

class DetailUserDialog : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.dialog_detailuser, container)
        var apiInterface = ApiClient().getApiClient().create(UserInterface::class.java)

        //make dialog fragment rounded and transparent
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        var email_etxt = v.findViewById(R.id.email_etxt) as EditText

        //login token handler
        var login_token: String = "null"
        var sp  = activity!!.getSharedPreferences("login", Context.MODE_PRIVATE)

        Log.d("data token on detail", sp.getString("token", null))
        Log.d("user on detail", sp.getInt("id_user", 0).toString())

        val call = apiInterface.showUser(sp.getInt("id_user", 0) , "bearer" + sp.getString("token", null))
        Log.d("token","bearer" + sp.getString("token", null) )
        call.enqueue(object: retrofit2.Callback<user> {
            override fun onFailure(call: Call<user>, t: Throwable) {
                Log.d("onFailure", t.message.toString())
            }

            override fun onResponse(call: Call<user>, response: Response<user>) {
                Log.d("onResponse", response.message().toString())

                email_etxt.setText(response.body()?.email)
            }
        })


        email_etxt.isEnabled = false

        var logout_btn = v.findViewById<Button>(R.id.logout_btn)
        logout_btn.setOnClickListener {
            var ed: SharedPreferences.Editor = sp.edit()
            ed.putString("token", "null")
            ed.apply()

            login_token = sp.getString("token", null)

            Toast.makeText(context, "Berhasil Logout!", Toast.LENGTH_LONG).show()

            startActivity(Intent(context, LandingPage::class.java))
            activity?.finish()
        }

        return v
    }

    override fun onResume() {
        dialog?.window?.setLayout((LinearLayout.LayoutParams.MATCH_PARENT + 1250), (LinearLayout.LayoutParams.MATCH_PARENT + 1000))

        super.onResume()
    }
}