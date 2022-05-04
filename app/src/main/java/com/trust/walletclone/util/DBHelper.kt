package com.trust.walletclone.util

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(context: Context) :
    SQLiteOpenHelper(
        context,
        context.getExternalFilesDir(null)!!.path + "/coin",
        null,
        DATABASE_VERSION
    ) {

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = (SQL_CREATE_ENTRIES)
        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertCoin(coin: Coin): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(COL_NAME, coin.name)
        values.put(COL_SYMBOL, coin.symbol)
        values.put(COL_ICON, coin.icon)
        values.put(COL_URL, coin.url)
        values.put(COL_TYPE, coin.type)

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert(TABLE_NAME, null, values)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteCoin(userid: String): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = "$COL_ID LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(userid)
        // Issue SQL statement.
        db.delete(TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun readCoin(coinId: Coin): Coin? {
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from $TABLE_NAME WHERE $COL_ID='$coinId'", null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return null
        }



        return Coin(
            cursor.getString(cursor.getColumnIndex(COL_NAME).toInt()),
            cursor.getString(cursor.getColumnIndex(COL_SYMBOL).toInt()),
            cursor.getString(cursor.getColumnIndex(COL_ICON).toInt()),
            0.0f, 0.0f, 0.0f,
            cursor.getString(cursor.getColumnIndex(COL_TYPE).toInt()),
            cursor.getString(cursor.getColumnIndex(COL_URL).toInt())
        )
    }

    fun readAllCoins(): ArrayList<Coin> {
        val coins = ArrayList<Coin>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from $TABLE_NAME", null)
        } catch (e: SQLiteException) {
            Log.e("error", e.toString())
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }
        cursor!!.moveToFirst()
        while (!cursor.isAfterLast) {
            coins.add(
                Coin(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    0.0f, 0.0f, 0.0f,
                    cursor.getString(5),
                    cursor.getString(4),
                    )
            )
            cursor.moveToNext()
        }
        return coins
    }

    companion object {
        private const val DATABASE_NAME = "coin"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "coins"
        const val COL_ID = "id"
        const val COL_NAME = "name"
        const val COL_SYMBOL = "age"
        const val COL_ICON = "icon"
        const val COL_URL = "url"
        const val COL_TYPE = "type"
        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY, $COL_SYMBOL TEXT,$COL_ICON TEXT,$COL_URL TEXT,$COL_TYPE TEXT)"
    }
}
