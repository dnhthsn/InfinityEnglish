package com.example.infinityenglish.view.activity;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;

import com.example.infinityenglish.R;
import com.example.infinityenglish.databinding.ActivityWelcomeBinding;
import com.example.infinityenglish.models.Users;
import com.example.infinityenglish.util.Const;
import com.example.infinityenglish.util.Utility;
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

        Users users = userViewModel.getCurrentUser();
        userViewModel.checkUser(users);

        userViewModel.getState().observe(this, new Observer<Const.State>() {
            @Override
            public void onChanged(Const.State state) {
                if (state.equals(Const.State.Main)) {
                    new Handler().postDelayed(() -> {
                        MainActivity.starter(WelcomeActivity.this);
                    }, 8000);
                } else if (state.equals(Const.State.Start)) {
                    new Handler().postDelayed(() -> {
                        StartActivity.starter(WelcomeActivity.this);
                    }, 8000);
                }
            }
        });
    }
}