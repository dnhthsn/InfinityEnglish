package com.example.infinityenglish.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.infinityenglish.R;
import com.example.infinityenglish.databinding.ActivityPronunciationListBinding;
import com.example.infinityenglish.view.adapter.LessonsAdapter;

import java.util.Arrays;

public class PronunciationListActivity extends AppCompatActivity {
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
    }
}