package com.example.infinityenglish.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.infinityenglish.R;
import com.example.infinityenglish.databinding.ActivityQuizBinding;

public class QuizActivity extends AppCompatActivity {
    private ActivityQuizBinding binding;

    public static void starter(Context context) {
        Intent intent = new Intent(context, QuizActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_quiz);
    }
}