package com.example.infinityenglish.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.infinityenglish.R;
import com.example.infinityenglish.databinding.ActivityPronunciationBinding;

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

        binding.web1.getSettings().setJavaScriptEnabled(true);

        int result = Integer.parseInt(b.getString("pme1"));

        if (result == 1) {
            binding.web1.loadUrl(String.valueOf(R.string.lesson1));
        } else if (result == 2) {
            binding.web1.loadUrl(String.valueOf(R.string.lesson2));
        } else if (result == 3) {
            binding.web1.loadUrl(String.valueOf(R.string.lesson3));
        } else if (result == 4) {
            binding.web1.loadUrl(String.valueOf(R.string.lesson4));
        } else if (result == 5) {
            binding.web1.loadUrl(String.valueOf(R.string.lesson5));
        } else if (result == 6) {
            binding.web1.loadUrl(String.valueOf(R.string.lesson6));
        } else if (result == 7) {
            binding.web1.loadUrl(String.valueOf(R.string.lesson7));
        } else if (result == 8) {
            binding.web1.loadUrl(String.valueOf(R.string.lesson8));
        } else if (result == 9) {
            binding.web1.loadUrl(String.valueOf(R.string.lesson9));
        } else if (result == 10) {
            binding.web1.loadUrl(String.valueOf(R.string.lesson10));
        } else if (result == 11) {
            binding.web1.loadUrl(String.valueOf(R.string.lesson11));
        } else if (result == 12) {
            binding.web1.loadUrl(String.valueOf(R.string.lesson12));
        } else if (result == 13) {
            binding.web1.loadUrl(String.valueOf(R.string.lesson13));
        } else if (result == 14) {
            binding.web1.loadUrl(String.valueOf(R.string.lesson14));
        } else if (result == 15) {
            binding.web1.loadUrl(String.valueOf(R.string.lesson15));
        }

    }
}