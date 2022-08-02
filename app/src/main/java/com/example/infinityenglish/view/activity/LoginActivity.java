package com.example.infinityenglish.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.example.infinityenglish.R;
import com.example.infinityenglish.databinding.ActivityLoginBinding;
import com.example.infinityenglish.viewmodel.UserViewModel;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private UserViewModel userViewModel;

    public static void starter(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        overridePendingTransition(R.anim.animation_intent_enter, R.anim.animation_intent_exit);

        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.init(this);

        binding.forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgetPasswordActivity.starter(LoginActivity.this);
            }
        });

        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpActivity.starter(LoginActivity.this);
            }
        });

        binding.clickLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.inputName.getText().toString();
                String password = binding.inputPassword.getText().toString();

                if (binding.rememberUser.isChecked()) {
                    userViewModel.saveUserAfterLogout(name, password);
                } else {
                    userViewModel.removeUser();
                }

                userViewModel.checkUser(name, password, view);
                binding.wrongInfo.setText(userViewModel.getMessage());
            }
        });
    }
}