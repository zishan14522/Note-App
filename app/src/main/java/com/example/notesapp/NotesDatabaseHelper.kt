package com.example.notesapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NotesDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_VERSION) {
    companion object{


        private const val DATABASE_NAME ="noteApp.db"
        private const val DATABASE_VERSION =2
        private const val TABLE_NAME ="allNotes"
        private const val COLUMN_ID="id"
        private const val COLUMN_TITLE="title"
        private const val COLUMN_CONTENT="content"
        private const val COLUMN_TEXT_COLOR="textColor"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery="CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY,$COLUMN_TITLE TEXT,$COLUMN_TEXT_COLOR INTEGER ,$COLUMN_CONTENT TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        val dropTableQuery="DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertNote(notes: Notes){
        val db=writableDatabase
        val values=ContentValues().apply {
            put(COLUMN_TITLE,notes.title)
            put(COLUMN_CONTENT,notes.content)
            put(COLUMN_TEXT_COLOR,notes.textColor)
        }
        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    fun getAllData(): List<Notes>{
        val notesList= mutableListOf<Notes>()
        val db =readableDatabase
        val query ="SELECT * FROM $TABLE_NAME"
        val cursor=db.rawQuery(query,null)
        while (cursor.moveToNext()){
            val id=cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
            val textColor=cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TEXT_COLOR))
            val note=Notes(id,title,content,textColor)
            notesList.add(note)
        }
        cursor.close()
        db.close()
        return notesList
    }



    //update
    fun updateNote(notes: Notes){
        val db =writableDatabase
        val values=ContentValues().apply {
            put(COLUMN_TITLE,notes.title)
            put(COLUMN_CONTENT,notes.content)
            put(COLUMN_TEXT_COLOR,notes.textColor)
        }
        val whereClause="$COLUMN_ID = ?"
        val whereArgs= arrayOf(notes.id.toString())
        db.update(TABLE_NAME,values,whereClause,whereArgs)
        db.close()
    }
    fun getNoteBuId(noteId: Int) : Notes{
        val db=readableDatabase
        val query="SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID=$noteId"
        val cursor=db.rawQuery(query,null)
        cursor.moveToFirst()
        val id=cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
        val textColor=cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TEXT_COLOR))
        cursor.close()
        db.close()
        return Notes(id, title, content,textColor)
    }




    //delete
  fun deleteNotes(noteId: Int){
    val db=writableDatabase
      val whereClause="$COLUMN_ID = ?"
      val whereArgs= arrayOf(noteId.toString())
      db.delete(TABLE_NAME,whereClause,whereArgs)
      db.close()
   }



}