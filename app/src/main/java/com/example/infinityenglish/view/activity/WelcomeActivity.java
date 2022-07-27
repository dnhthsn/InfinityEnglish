package com.example.infinityenglish.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;

import com.example.infinityenglish.R;
import com.example.infinityenglish.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends AppCompatActivity {
    private ActivityWelcomeBinding binding;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);

        new Handler().postDelayed(() -> {
            StartActivity.starter(WelcomeActivity.this);
        }, 8000);
    }
}