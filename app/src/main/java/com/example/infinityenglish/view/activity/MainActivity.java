package com.example.infinityenglish.view.activity;

import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.infinityenglish.R;
import com.example.infinityenglish.control.remote.RequestRandomManager;
import com.example.infinityenglish.databinding.ActivityMainBinding;
import com.example.infinityenglish.models.Users;
import com.example.infinityenglish.util.Const;
import com.example.infinityenglish.util.Utility;
import com.example.infinityenglish.view.base.BaseActivity;
import com.example.infinityenglish.viewmodel.UserViewModel;
import com.example.infinityenglish.viewmodel.WordViewModel;

import java.io.IOException;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private UserViewModel userViewModel;
    private WordViewModel wordViewModel;

    private Bundle bundle;

    public static void starter(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.init(MainActivity.this);

        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        wordViewModel.init(MainActivity.this);

        bundle = new Bundle();
        Users users = userViewModel.getCurrentUser();
        if (users != null) {
            userViewModel.getUserAvatar(users, binding.mainLayout, binding.userAvatar);
        }

        binding.inputSearch.setFocusable(false);
        binding.inputSearch.clearFocus();

        binding.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.Keyboard.hideKeyBoard(MainActivity.this);
            }
        });

        binding.userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsActivity.starter(MainActivity.this);
            }
        });

        binding.history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HistoryActivity.starter(MainActivity.this);
            }
        });

        binding.note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteActivity.starter(MainActivity.this);
            }
        });

        binding.speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PracticeSpeakingActivity.starter(MainActivity.this);
            }
        });

        binding.pronunciationGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PronunciationListActivity.starter(MainActivity.this);
            }
        });

        binding.random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchActivity.starter(MainActivity.this, bundle);
            }
        });

        binding.inputSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Bundle bundle = new Bundle();
                bundle.putString(Const.Sender.searchQuery, query);

                SearchActivity.starter(MainActivity.this, bundle);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}