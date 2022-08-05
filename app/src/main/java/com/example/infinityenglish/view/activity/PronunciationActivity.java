package com.example.infinityenglish.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.infinityenglish.R;
import com.example.infinityenglish.databinding.ActivityPronunciationBinding;
import com.example.infinityenglish.util.Const;

public class PronunciationActivity extends AppCompatActivity {
    private ActivityPronunciationBinding binding;

    public static void starter(Context context) {
        Intent intent = new Intent(context, PronunciationActivity.class);
        context.startActivity(intent);
    }

    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pronunciation);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_pronunciation);

        Bundle b = getIntent().getExtras();

        binding.webLesson.getSettings().setJavaScriptEnabled(true);

        binding.webLesson.getSettings().setAllowFileAccess(true);

        int result = Integer.parseInt(b.getString("pme1"));

        if (result == 1) {
            binding.webLesson.loadUrl(Const.webUrl.lesson1);
        } else if (result == 2) {
            binding.webLesson.loadUrl(Const.webUrl.lesson2);
        } else if (result == 3) {
            binding.webLesson.loadUrl(Const.webUrl.lesson3);
        } else if (result == 4) {
            binding.webLesson.loadUrl(Const.webUrl.lesson4);
        } else if (result == 5) {
            binding.webLesson.loadUrl(Const.webUrl.lesson5);
        } else if (result == 6) {
            binding.webLesson.loadUrl(Const.webUrl.lesson6);
        } else if (result == 7) {
            binding.webLesson.loadUrl(Const.webUrl.lesson7);
        } else if (result == 8) {
            binding.webLesson.loadUrl(Const.webUrl.lesson8);
        } else if (result == 9) {
            binding.webLesson.loadUrl(Const.webUrl.lesson9);
        } else if (result == 10) {
            binding.webLesson.loadUrl(Const.webUrl.lesson10);
        } else if (result == 11) {
            binding.webLesson.loadUrl(Const.webUrl.lesson11);
        } else if (result == 12) {
            binding.webLesson.loadUrl(Const.webUrl.lesson12);
        } else if (result == 13) {
            binding.webLesson.loadUrl(Const.webUrl.lesson13);
        } else if (result == 14) {
            binding.webLesson.loadUrl(Const.webUrl.lesson14);
        } else if (result == 15) {
            binding.webLesson.loadUrl(Const.webUrl.lesson15);
        }

        binding.clickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}