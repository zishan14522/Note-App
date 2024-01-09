package com.example.notesapp.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.notesapp.NotesInterface
import com.example.notesapp.R
import com.example.notesapp.ViewActivity
import com.example.notesapp.databinding.FragmentFavoriteBinding
import com.example.notesapp.databinding.FragmentHomeBinding


class FavoriteFragment : Fragment(),NotesInterface {
    private lateinit var binding: FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val view = binding.root


        return view
    }

    override fun getDetails(title: String, content: String) {
        TODO("Not yet implemented")
    }


}