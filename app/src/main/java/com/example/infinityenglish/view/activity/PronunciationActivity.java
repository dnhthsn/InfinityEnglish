package com.example.infinityenglish.view.activity;

import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.infinityenglish.R;
import com.example.infinityenglish.databinding.ActivityPronunciationBinding;
import com.example.infinityenglish.util.Const;
import com.example.infinityenglish.view.base.BaseActivity;

public class PronunciationActivity extends BaseActivity {
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

        binding.webLesson.loadUrl(String.format(Const.webUrl.lesson, result));

        binding.clickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}