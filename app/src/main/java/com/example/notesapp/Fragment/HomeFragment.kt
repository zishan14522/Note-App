package com.example.notesapp.Fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapp.AddNoteActivity
import com.example.notesapp.NotesAdapter
import com.example.notesapp.NotesDatabaseHelper
import com.example.notesapp.NotesInterface
import com.example.notesapp.ViewActivity
import com.example.notesapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment(), NotesInterface {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var db: NotesDatabaseHelper
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root


        db = NotesDatabaseHelper(this.requireContext())
        notesAdapter = NotesAdapter(db.getAllData(), this, this.requireContext())
        val layoutManager: RecyclerView.LayoutManager=
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.layoutManager=layoutManager
        binding.recyclerView.adapter=notesAdapter

//        binding.recyclerView.layoutManager = LinearLayoutManager(this.requireContext())



        binding.addButton.setOnClickListener {
            val intent = Intent(this.requireContext(), AddNoteActivity::class.java)
            startActivity(intent)
        }

        // Set up the SearchView
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                notesAdapter.filter(newText.orEmpty())
                return true
            }
        })




        return view
    }
    override fun onResume() {
        super.onResume()
        notesAdapter.refreshData(db.getAllData())
    }

    override fun getDetails(title: String, content: String) {
        val intent=Intent(this.requireContext(), ViewActivity::class.java)
        intent.putExtra("title",title)
        intent.putExtra("content",content)
        startActivity(intent)
    }




}