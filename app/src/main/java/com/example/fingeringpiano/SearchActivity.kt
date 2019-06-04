package com.example.fingeringpiano

import android.media.Image
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        var back_btn = findViewById<ImageButton>(R.id.back_btn)
        var search_etxt = findViewById<EditText>(R.id.search_etxt)
        var search_btn = findViewById<ImageButton>(R.id.search_btn)

        back_btn.setOnClickListener{
            finish()
        }

        search_btn.setOnClickListener{

        }
    }
}
