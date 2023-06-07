package com.example.convidados.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.convidados.constansts.DataBaseConstants

class GuestDataBase(context: Context) : SQLiteOpenHelper(context, NAME, null, VERSION) {

    companion object {
        private const val NAME = "guestdb"
        private const val VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "create table " + DataBaseConstants.GUESTS.TABLE_NAME + " (" +
                    DataBaseConstants.GUESTS.COLUMNS.ID + " integer primary key autoincrement, " +
                    DataBaseConstants.GUESTS.COLUMNS.NAME + "text, " +
                    DataBaseConstants.GUESTS.COLUMNS.PRESENCE + " integer);"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

}