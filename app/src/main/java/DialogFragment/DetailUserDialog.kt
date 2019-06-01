package DialogFragment

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.fingeringpiano.R

class DetailUserDialog : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.dialog_detailuser, container)

        var email_etxt = v.findViewById(R.id.email_etxt) as EditText
        email_etxt.isEnabled = false

        return v
    }
}