package com.example.database.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.database.User

val VERSION = 1
val DB_NAME = "UserInfo"
val TABLE_NAME = "users"
val COL_NAME = "name_surname"
val COL_AGE = "age"
val COL_ID = "id"

class DatabaseHelper(var context: Context) : SQLiteOpenHelper(context, DB_NAME, null, VERSION) {
    override fun onCreate(p0: SQLiteDatabase?) {
        //runs once when database is created
        var createTable = " CREATE TABLE " + TABLE_NAME + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME + " VARCHAR(256)," +
                COL_AGE + " INTEGER)"
        p0?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }


    //function for save data

    fun insertData(user: User) {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_NAME, user.name)
        cv.put(COL_AGE, user.age)

        var sonuc = db.insert(TABLE_NAME, null, cv)
        if (sonuc == (-1).toLong()) {
            Toast.makeText(context, "MISSION FAILED", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "MISSION  SUCCESSFUL", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("Range")
    fun readData(): MutableList<User> {
        val userList = mutableListOf<User>()
        val sqliteDB = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val result = sqliteDB.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val user = User("", 0)
                user.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                user.name = result.getString(result.getColumnIndex(COL_NAME))
                user.age = result.getString(result.getColumnIndex(COL_AGE)).toInt()
                userList.add(user)
            } while (result.moveToNext())
        }
        result.close()
        sqliteDB.close()
        return userList
    }
    @SuppressLint("Range")
    fun updateData(){
      val db=writableDatabase
        var query="SELECT * FROM $TABLE_NAME"
        var result=db.rawQuery(query,null)
        if(result.moveToFirst()){
            do {
                var cv=ContentValues()
                cv.put(COL_AGE,(result.getInt(result.getColumnIndex(COL_AGE)))+1)
                cv.put(COL_NAME,(result.getString(result.getColumnIndex(COL_NAME)))+" guncellendi")
                db.update(TABLE_NAME,cv,"$COL_ID=? AND $COL_NAME=?",
                arrayOf(result.getString(result.getColumnIndex(COL_ID)),
                    result.getString(result.getColumnIndex(COL_NAME))))
            }while (result.moveToNext())
        }
        result.close()
        db.close()
    }
    @SuppressLint("Range")
    fun delete(){
        val db=writableDatabase
        db.delete(TABLE_NAME,null,null)
        db.close()
    }
}