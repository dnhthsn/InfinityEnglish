package com.example.infinityenglish.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.infinityenglish.R;
import com.example.infinityenglish.databinding.ActivityPronunciationListBinding;
import com.example.infinityenglish.view.adapter.LessonsAdapter;
import com.example.infinityenglish.view.base.BaseActivity;

import java.util.Arrays;

public class PronunciationListActivity extends BaseActivity {
    private ActivityPronunciationListBinding binding;
    private LessonsAdapter lessonsAdapter;

    public static void starter(Context context) {
        Intent intent = new Intent(context, PronunciationListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pronunciation_list);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_pronunciation_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.lessonList.setLayoutManager(layoutManager);
        lessonsAdapter = new LessonsAdapter();
        String[] lessons = getResources().getStringArray(R.array.lessons);
        lessonsAdapter.setLessons(Arrays.asList(lessons));
        binding.lessonList.setAdapter(lessonsAdapter);

        binding.clickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.starter(PronunciationListActivity.this);
                finish();
            }
        });
    }
}