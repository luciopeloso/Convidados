package com.example.convidados.repository

import android.content.ContentValues
import android.content.Context
import com.example.convidados.constansts.DataBaseConstants
import com.example.convidados.model.GuestModel

class GuestRepository private constructor(context: Context) {

    private val guestDataBase = GuestDataBase(context)

    companion object {
        private lateinit var repository: GuestRepository

        fun getInstance(context: Context): GuestRepository {
            if (!::repository.isInitialized) {
                repository = GuestRepository(context)
            }
            return repository
        }
    }

    fun insert(guest: GuestModel): Boolean {
        return try {
            val db = guestDataBase.writableDatabase
            val presence = if (guest.presence) 1 else 0
            val values = ContentValues()

            values.put(DataBaseConstants.GUESTS.COLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUESTS.COLUMNS.PRESENCE, presence)
            db.insert(DataBaseConstants.GUESTS.TABLE_NAME, null, values)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun update(guest: GuestModel): Boolean {
        return try {
            val db = guestDataBase.writableDatabase
            val values = ContentValues()
            val presence = if (guest.presence) 1 else 0
            values.put(DataBaseConstants.GUESTS.COLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUESTS.COLUMNS.PRESENCE, presence)
            val selection = DataBaseConstants.GUESTS.COLUMNS.ID + "id = ?"
            val args = arrayOf(guest.id.toString())

            db.update(DataBaseConstants.GUESTS.TABLE_NAME, values, selection, args)
            true
        } catch (e: java.lang.Exception) {
            false
        }
    }

    fun delete(id: Int): Boolean {
        return try {
            val db = guestDataBase.writableDatabase

            val selection = DataBaseConstants.GUESTS.COLUMNS.ID + "id = ?"
            val args = arrayOf(id.toString())

            db.delete(DataBaseConstants.GUESTS.TABLE_NAME, selection, args)
            true
        } catch (e: java.lang.Exception) {
            false
        }
    }

    fun getAll(): List<GuestModel> {
        val list = mutableListOf<GuestModel>()

        try {
            val db = guestDataBase.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.GUESTS.COLUMNS.ID,
                DataBaseConstants.GUESTS.COLUMNS.NAME,
                DataBaseConstants.GUESTS.COLUMNS.PRESENCE
            )

            val cursor = db.query(
                DataBaseConstants.GUESTS.TABLE_NAME, projection, null, null,
                null, null, null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUESTS.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUESTS.COLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUESTS.COLUMNS.PRESENCE))

                    list.add(GuestModel(id, name, presence == 1))
                }
            }

            cursor.close()
        } catch (e: Exception) {
            return list
        }

        return list
    }

    fun get(id: Int): GuestModel? {
        var guest: GuestModel? = null

        try {
            val db = guestDataBase.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.GUESTS.COLUMNS.ID,
                DataBaseConstants.GUESTS.COLUMNS.NAME,
                DataBaseConstants.GUESTS.COLUMNS.PRESENCE
            )

            val selection = DataBaseConstants.GUESTS.COLUMNS.ID + "id = ?"
            val args = arrayOf(id.toString())

            val cursor = db.query(
                DataBaseConstants.GUESTS.TABLE_NAME, projection, selection, args,
                null, null, null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUESTS.COLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUESTS.COLUMNS.PRESENCE))

                    guest = GuestModel(id, name, presence == 1)
                }
            }

            cursor?.close()
            return guest
        } catch (e: Exception) {
            return guest
        }

        return guest
    }

    fun getPresent(): List<GuestModel> {
        val list = mutableListOf<GuestModel>()

        try {
            val db = guestDataBase.readableDatabase
            val cursor =
                db.rawQuery("SELECT id, name, presence FROM Guests WHERE presence = 1", null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUESTS.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUESTS.COLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUESTS.COLUMNS.PRESENCE))

                    list.add(GuestModel(id, name, presence == 1))
                }
            }

            cursor.close()
        } catch (e: Exception) {
            return list
        }

        return list
    }

    fun getAbsent(): List<GuestModel> {
        val list = mutableListOf<GuestModel>()

        try {
            val db = guestDataBase.readableDatabase
            val cursor =
                db.rawQuery("SELECT id, name, presence FROM Guests WHERE presence = 0", null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUESTS.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUESTS.COLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUESTS.COLUMNS.PRESENCE))

                    list.add(GuestModel(id, name, presence == 1))
                }
            }

            cursor.close()
        } catch (e: Exception) {
            return list
        }

        return list
    }

}