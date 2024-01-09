package com.example.notesapp.kotlinTutorial

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputBinding
import android.widget.Button
import com.example.notesapp.R

class SEND_APP : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_app)


        val sendApp: Button=findViewById(R.id.sendApp)
        sendApp.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Insert Subject here")

            val appUrl = "https://play.google.com/store/apps/details?id=my.example.javatpoint"
            shareIntent.putExtra(Intent.EXTRA_TEXT, appUrl)

            startActivity(Intent.createChooser(shareIntent, "Share via"))

        }

    }
}
