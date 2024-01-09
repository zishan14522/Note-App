package com.example.notesapp
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import com.example.notesapp.databinding.ActivityAddNoteBinding
import yuku.ambilwarna.AmbilWarnaDialog

class AddNoteActivity : AppCompatActivity() {
    private var selectedColor: Int = Color.rgb (129,57,149)// Default color
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var db:NotesDatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db= NotesDatabaseHelper(this)

        binding.saveData.setOnClickListener {


            val title = binding.titleEdittext.text.toString().trim()
            val content = binding.contentEdittext.text.toString().trim()
            if (TextUtils.isEmpty(title)) {
                binding.titleEdittext.error = "Title Field is empty"
            }else if (TextUtils.isEmpty(content)){
                binding.contentEdittext.error = "Content Field is empty"

            }
            else {

                val note = Notes(0, title, content,selectedColor)
                db.insertNote(note)
                finish()
                Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()


            }

        }

        binding.colorchange.setOnClickListener { showColorPickerDialog() }

    }





    fun onClick(view: View) {
       onBackPressed()
    }

    private fun showColorPickerDialog() {
        val colorPicker = AmbilWarnaDialog(this, selectedColor, object : AmbilWarnaDialog.OnAmbilWarnaListener {
            override fun onCancel(dialog: AmbilWarnaDialog?) {
                // Handle dialog cancellation
            }

            override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {

                selectedColor = color
                binding.titleEdittext.setTextColor(color)
            }
        })

        colorPicker.show()
    }
}