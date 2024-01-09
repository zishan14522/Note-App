package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import com.example.notesapp.databinding.ActivityViewBinding

class ViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.TitleView.text=intent.getStringExtra("title")
        binding.ContentView.text=intent.getStringExtra("content")

    }

    fun onClick(view: View) {
        onBackPressed()
    }
}