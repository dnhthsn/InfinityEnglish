package com.example.infinityenglish.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.infinityenglish.R;
import com.example.infinityenglish.databinding.ActivityRandomBinding;

public class RandomActivity extends AppCompatActivity {
    private ActivityRandomBinding binding;

    public static void starter(Context context) {
        Intent intent = new Intent(context, RandomActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_random);
    }
}