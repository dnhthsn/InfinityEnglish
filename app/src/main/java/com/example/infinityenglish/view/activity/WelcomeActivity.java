package com.example.infinityenglish.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

import com.example.infinityenglish.R;
import com.example.infinityenglish.databinding.ActivityWelcomeBinding;
import com.example.infinityenglish.models.Users;
import com.example.infinityenglish.viewmodel.UserViewModel;

public class WelcomeActivity extends AppCompatActivity {
    private ActivityWelcomeBinding binding;
    private UserViewModel userViewModel;

    private String name, password;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.init(this);

        boolean state = userViewModel.getStateLogin();
        Users users = userViewModel.getCurrentUser();

        if(users != null){
            name = users.getName();
            password = users.getPassword();
        }

        if (state){
            new Handler().postDelayed(() -> {
                userViewModel.checkUser(name, password, binding.root);
            }, 8000);
        } else {
            new Handler().postDelayed(() -> {
                StartActivity.starter(WelcomeActivity.this);
            }, 8000);
        }
    }
}