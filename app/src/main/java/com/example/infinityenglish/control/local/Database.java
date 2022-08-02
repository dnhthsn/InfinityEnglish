package com.example.infinityenglish.control.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.infinityenglish.models.Histories;
import com.example.infinityenglish.models.Notes;
import com.example.infinityenglish.models.Users;

public class Database extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "english_db";
    private final static int VERSION = 1;

    private String TABLE_USER = "users";
    private String NAME = "name";
    private String PASSWORD = "password";
    private String ADDRESS = "address";
    private String EMAIL = "email";
    private String PHONE = "phone";
    private String GENDER = "gender";
    private String AVATAR = "avatar";

    private String TABLE_HISTORY = "histories";
    private String WORD = "word";

    private String TABLE_NOTE = "notes";
    private String ID = "id";
    private String TITLE = "title";
    private String CONTENT = "content";

    private String TABLE_DELETED_NOTE = "deletednotes";
    private String ID_DEL = "id";
    private String TITLE_DEL = "title";
    private String CONTENT_DEL = "content";

    private String createTableUser = "CREATE TABLE " + TABLE_USER + " ( " + NAME + " TEXT PRIMARY KEY UNIQUE, "
            + PASSWORD + " TEXT, "
            + ADDRESS + " TEXT, "
            + EMAIL + " TEXT, "
            + PHONE + " TEXT, "
            + GENDER + " TEXT, "
            + AVATAR + " TEXT)";

    private String createTableHistory = "CREATE TABLE " + TABLE_HISTORY + " ( " + WORD + " TEXT PRIMARY KEY UNIQUE)";

    private String createTableNote = "CREATE TABLE " + TABLE_NOTE + " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TITLE + " TEXT, "
            + CONTENT + " TEXT)";

    private String createTableDeletedNote = "CREATE TABLE " + TABLE_DELETED_NOTE + " ( " + ID_DEL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TITLE_DEL + " TEXT, "
            + CONTENT_DEL + " TEXT)";

    private String insertUser = "INSERT INTO " + TABLE_USER + " VAlUES ('1','1','1','1','1','male', null)";

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createTableUser);
        sqLiteDatabase.execSQL(createTableHistory);
        sqLiteDatabase.execSQL(createTableNote);
        sqLiteDatabase.execSQL(createTableDeletedNote);

        sqLiteDatabase.execSQL(insertUser);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public Cursor getUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_USER, null);
        return res;
    }

    public Cursor getHistory() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_HISTORY, null);
        return res;
    }

    public Cursor getNote() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NOTE, null);
        return res;
    }

    public Cursor getDeletedNote() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_DELETED_NOTE, null);
        return res;
    }

    public void addUser(Users users) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, users.getName());
        values.put(PASSWORD, users.getPassword());
        values.put(ADDRESS, users.getAddress());
        values.put(EMAIL, users.getEmail());
        values.put(PHONE, users.getPhone());
        values.put(GENDER, users.getGender());
        values.put(AVATAR, users.getAvatar());

        db.insert(TABLE_USER, null, values);
        db.close();
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

    public void addDeletedNote(Notes notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE_DEL, notes.getTitle());
        values.put(CONTENT_DEL, notes.getContent());

        db.insert(TABLE_DELETED_NOTE, null, values);
        db.close();
    }

    public void updateUser(Users users) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, users.getName());
        values.put(PASSWORD, users.getPassword());
        values.put(ADDRESS, users.getAddress());
        values.put(EMAIL, users.getEmail());
        values.put(PHONE, users.getPhone());
        values.put(GENDER, users.getGender());
        values.put(AVATAR, users.getAvatar());

        db.update(TABLE_USER, values, NAME + " = " + users.getName(), null);
        db.close();
    }

    public void updateUserPassword(Users users) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PASSWORD, users.getPassword());

        db.update(TABLE_USER, values, NAME + " = " + users.getName(), null);
        db.close();
    }


    public boolean checkUser(String username) {
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + NAME + " = ?";
        String[] whereArgs = {username};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, whereArgs);

        int count = cursor.getCount();

        cursor.close();

        return count >= 1;
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

    public boolean checkPhoneNumber(String phone) {
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + PHONE + " = ?";
        String[] whereArgs = {phone};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, whereArgs);

        int count = cursor.getCount();

        cursor.close();

        return count >= 1;
    }

    public boolean checkUserAndPhone(String phone, String username) {
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + PHONE + " = ?" + " AND " + NAME + " = ?";
        String[] whereArgs = {phone, username};

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
        int res = db.delete(TABLE_NOTE, ID + " = " + i, null);
        return res;
    }

    public int deleteNotePermanently(Integer i) {
        SQLiteDatabase db = this.getReadableDatabase();
        int res = db.delete(TABLE_DELETED_NOTE, ID + " = " + i, null);
        return res;
    }

    public void deleteAllHistory() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " + TABLE_HISTORY);
    }

    public void deleteAllNote() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " + TABLE_DELETED_NOTE);
    }
}
