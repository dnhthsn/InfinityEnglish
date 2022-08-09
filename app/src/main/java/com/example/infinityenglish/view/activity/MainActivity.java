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

public class MainActivity extends BaseActivity implements RequestRandomManager.OnFetchRandomDataListener {
    private ActivityMainBinding binding;
    private UserViewModel userViewModel;
    private WordViewModel wordViewModel;

    private ProgressDialog progressDialog;
    private Bundle bundle;

    public static void starter(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        overridePendingTransition(R.anim.animation_intent_enter, R.anim.animation_intent_exit);

        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.init(this);

        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        wordViewModel.init(this);

        progressDialog = new ProgressDialog(this);

        bundle = new Bundle();

        Users users = userViewModel.getCurrentUser();
        String uri;
        if (users != null) {
            uri = users.getAvatar();
            String imageUri = uri == null ? String.valueOf(R.drawable.avatar) : uri;
            binding.userAvatar.setImageURI(Uri.parse(imageUri));
            binding.userName.setText("Hi, " + users.getName() + " !");
        }

        binding.inputSearch.setFocusable(false);
        binding.inputSearch.clearFocus();

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

        wordViewModel.getRandomWord(MainActivity.this);

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

    private void showData(Object listWords) {
        String word = listWords.toString();
        word = word.replaceAll(Const.Regex.randomWord, "");
        bundle.putString(Const.Sender.randomWord, word);
    }

    @Override
    public void onFetchRandomData(Object listWords, String message) {
        progressDialog.dismiss();
        if (listWords == null) {
            Utility.Notice.snack(getCurrentFocus(), Const.Error.noData);
            return;
        }

        showData(listWords);
    }

    @Override
    public void onRandomError(String message) {
        progressDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
    }
}