package com.example.infinityenglish.control.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.infinityenglish.models.Histories;
import com.example.infinityenglish.models.Notes;

public class Database extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "english_db";
    private final static int VERSION = 1;

    private final String TABLE_USER = "users";
    private final String NAME = "name";
    private final String PASSWORD = "password";
    private final String ADDRESS = "address";
    private final String EMAIL = "email";
    private final String PHONE = "phone";
    private final String GENDER = "gender";
    private final String AVATAR = "avatar";

    private final String TABLE_HISTORY = "histories";
    private final String WORD = "word";

    private final String TABLE_NOTE = "notes";
    private final String ID = "id";
    private final String TITLE = "title";
    private final String CONTENT = "content";

    private final String createTableHistory = "CREATE TABLE " + TABLE_HISTORY + " ( " + WORD + " TEXT PRIMARY KEY UNIQUE)";

    private final String insertUser = "INSERT INTO " + TABLE_USER + " VAlUES ('1','1','1','1','1','male', null)";

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableUser = "CREATE TABLE " + TABLE_USER + " ( " + NAME + " TEXT PRIMARY KEY UNIQUE, "
                + PASSWORD + " TEXT, "
                + ADDRESS + " TEXT, "
                + EMAIL + " TEXT, "
                + PHONE + " TEXT, "
                + GENDER + " TEXT, "
                + AVATAR + " TEXT)";
        sqLiteDatabase.execSQL(createTableUser);
        sqLiteDatabase.execSQL(createTableHistory);
        String createTableNote = "CREATE TABLE " + TABLE_NOTE + " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE + " TEXT, "
                + CONTENT + " TEXT)";
        sqLiteDatabase.execSQL(createTableNote);

        sqLiteDatabase.execSQL(insertUser);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public Cursor getHistory() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_HISTORY, null);
    }

    public Cursor getNote() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NOTE, null);
    }

    public void addHistory(Histories histories) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WORD, histories.getWordInput());

        db.insert(TABLE_HISTORY, null, values);
        db.close();
    }

    public void addNote(Notes notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, notes.getTitle());
        values.put(CONTENT, notes.getContent());

        db.insert(TABLE_NOTE, null, values);
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

    public void updateNote(Notes notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, notes.getTitle());
        values.put(CONTENT, notes.getContent());

        db.update(TABLE_NOTE, values, ID + " = " + notes.getId(), null);
        db.close();
    }

    public boolean deleteHistory(String word) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete(TABLE_HISTORY, WORD + "=?", new String[]{word}) > 0;
    }

    public int deleteNote(Integer i) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete(TABLE_NOTE, ID + " = " + i, null);
    }

    public void deleteAllHistory() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " + TABLE_HISTORY);
    }
}
