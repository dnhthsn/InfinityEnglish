package com.example.infinityenglish.view.activity;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;

import com.example.infinityenglish.R;
import com.example.infinityenglish.databinding.ActivityWelcomeBinding;
import com.example.infinityenglish.models.Users;
import com.example.infinityenglish.view.base.BaseActivity;
import com.example.infinityenglish.viewmodel.UserViewModel;

public class WelcomeActivity extends BaseActivity {
    private ActivityWelcomeBinding binding;
    private UserViewModel userViewModel;

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

        if (state) {
            new Handler().postDelayed(() -> {
                if (users != null) {
                    MainActivity.starter(WelcomeActivity.this);
                }
            }, 8000);
        } else {
            new Handler().postDelayed(() -> {
                StartActivity.starter(WelcomeActivity.this);
            }, 8000);
        }
    }
}