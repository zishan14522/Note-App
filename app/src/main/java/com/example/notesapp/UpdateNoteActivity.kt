package com.example.notesapp

import android.graphics.Color
import android.graphics.ColorFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.notesapp.databinding.ActivityUpdateNoteBinding
import yuku.ambilwarna.AmbilWarnaDialog


class UpdateNoteActivity : AppCompatActivity() {
    private var selectedColor: Int = Color.rgb (129,57,149)// Default color
    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var db:NotesDatabaseHelper
    private var noteId : Int =-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db=NotesDatabaseHelper(this)

        noteId=intent.getIntExtra("note_id",-1)
        if (noteId==-1){
            finish()
            return
        }
        val notes=db.getNoteBuId(noteId)
        binding.updateTitleEdittext.setText(notes.title)
        binding.updateContentEdittext.setText(notes.content)


        binding.upDateData.setOnClickListener {
            val newTitle=binding.updateTitleEdittext.text.toString()
            val newContent=binding.updateContentEdittext.text.toString()



            if (TextUtils.isEmpty(newTitle)) {
                binding.updateTitleEdittext.error = "Title Field is empty"
            }else if (TextUtils.isEmpty(newContent)){
                binding.updateContentEdittext.error = "Content Field is empty"

            }
            else {

                val updateNote = Notes(noteId, newTitle, newContent, selectedColor)
                db.updateNote(updateNote)
                finish()
                Toast.makeText(this, "Update Saved", Toast.LENGTH_SHORT).show()
            }
        }

        binding.textColor2.setOnClickListener { showColorPickerDialog() }

    }

    private fun showColorPickerDialog() {
        val colorPicker = AmbilWarnaDialog(this, selectedColor, object : AmbilWarnaDialog.OnAmbilWarnaListener {
            override fun onCancel(dialog: AmbilWarnaDialog?) {
                // Handle dialog cancellation
            }

            override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {

                // Handle the selected color
                selectedColor = color
                // Use the selected color as needed (e.g., set it as background color)
                binding.updateTitleEdittext.setTextColor(color)
            }
        })

        colorPicker.show()
    }
    fun onClick(view: View) {
        onBackPressed()
    }
}