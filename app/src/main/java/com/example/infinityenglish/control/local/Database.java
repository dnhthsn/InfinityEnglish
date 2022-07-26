package com.example.infinityenglish.control.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.infinityenglish.models.Histories;

public class Database extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "english_db";
    private final static int VERSION = 1;

    private String TABLE_HISTORY = "histories";
    private String WORD = "word";

    private String createTableHistory = "CREATE TABLE " + TABLE_HISTORY + " ( " + WORD + " TEXT PRIMARY KEY UNIQUE)";

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createTableHistory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Cursor getHistory() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_HISTORY, null);
        return res;
    }

    public void addHistory(Histories histories) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WORD, histories.getWordInput());

        db.insert(TABLE_HISTORY, null, values);
        db.close();
    }

    public boolean checkHistory(String word) {
        String query = "SELECT * FROM " + TABLE_HISTORY + " WHERE " + WORD + " = ?";
        String[] whereArgs = {word};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, whereArgs);

        int count = cursor.getCount();

        cursor.close();

        return count >= 1;
    }

    public boolean deleteHistory(String word) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete(TABLE_HISTORY, WORD + "=?", new String[]{word}) > 0;
    }

    public void deleteAllHistory() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_HISTORY);;
    }
}
