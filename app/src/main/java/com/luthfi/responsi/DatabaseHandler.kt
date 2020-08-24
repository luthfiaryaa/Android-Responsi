package com.luthfi.responsi

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context,
    DB_NAME, null,
    DB_VERSION
) {
    companion object {
        private val DB_VERSION = 1
        private val DB_NAME = "responsi"
        private val TABLE_USER = "user"
        private val COL_ID = "id"
        private val COL_USERNAME = "username"
        private val COL_NAME = "name"
        private val COL_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE $TABLE_USER(" +
                    "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COL_USERNAME TEXT UNIQUE, " +
                    "$COL_NAME TEXT, " +
                    "$COL_PASSWORD TEXT)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, old: Int, new: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        onCreate(db)
    }

    fun register(name: String, username: String, password: String) : Boolean {
        if (check(username)) return false

        val write = this.writableDatabase
        val content = ContentValues()
        content.put(COL_NAME, name)
        content.put(COL_USERNAME, username)
        content.put(COL_PASSWORD, password)

        write.insert(TABLE_USER, null, content)
        write.close()

        return true
    }

    fun login(username: String, password: String) : Boolean {
        val read = this.readableDatabase
        val query = read.query(
            TABLE_USER,
            arrayOf(
                COL_USERNAME,
                COL_PASSWORD
            ),
            "$COL_USERNAME = ? AND $COL_PASSWORD = ?",
            arrayOf(username, password),
            null,
            null,
            null)
        val cursorCount = query.count
        query.close()
        read.close()

        if (cursorCount == 0) return false

        return true
    }

    private fun check(username: String) : Boolean {
        val read = this.readableDatabase
        val query = read.query(
            TABLE_USER,
            arrayOf(
                COL_ID,
                COL_USERNAME,
                COL_NAME
            ),
            "$COL_USERNAME = ?",
            arrayOf(username),
            null,
            null,
            null)
        val cursorCount = query.count
        query.close()
        read.close()

        if (cursorCount == 0) return false

        return true
    }
}