package com.example.infinityenglish.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.infinityenglish.R;
import com.example.infinityenglish.databinding.ActivityHistoryBinding;
import com.example.infinityenglish.models.Histories;
import com.example.infinityenglish.view.adapter.HistoryAdapter;
import com.example.infinityenglish.viewmodel.WordViewModel;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private ActivityHistoryBinding binding;
    private HistoryAdapter historyAdapter;
    private WordViewModel wordViewModel;

    public static void starter(Context context) {
        Intent intent = new Intent(context, HistoryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_history);
        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        wordViewModel.init(this);

        historyAdapter = new HistoryAdapter();

        wordViewModel.getHistory().observe(HistoryActivity.this, new Observer<List<Histories>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Histories> histories) {
                historyAdapter.setHistories(histories);
                historyAdapter.notifyDataSetChanged();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.historyList.setLayoutManager(layoutManager);
        binding.historyList.setAdapter(historyAdapter);

        binding.clearAll.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                wordViewModel.deleteAllHistory(view, historyAdapter);
                historyAdapter.notifyDataSetChanged();
            }
        });
    }
}