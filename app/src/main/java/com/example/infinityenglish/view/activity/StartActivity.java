package com.example.infinityenglish.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.infinityenglish.R;
import com.example.infinityenglish.databinding.ActivityStartBinding;
import com.example.infinityenglish.viewmodel.UserViewModel;

public class StartActivity extends AppCompatActivity {
    private ActivityStartBinding binding;
    private UserViewModel userViewModel;

    public static void starter(Context context) {
        Intent intent = new Intent(context, StartActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_start);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.init(this);

        binding.loginAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.starter(StartActivity.this);
            }
        });

        binding.loginWithoutAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userViewModel.removeCurrentUser();
                MainActivity.starter(StartActivity.this);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}