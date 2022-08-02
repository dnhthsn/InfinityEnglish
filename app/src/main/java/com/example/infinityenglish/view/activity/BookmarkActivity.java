package com.example.infinityenglish.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.infinityenglish.R;

public class BookmarkActivity extends AppCompatActivity {

    public static void starter(Context context) {
        Intent intent = new Intent(context, BookmarkActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
    }
}