package com.example.infinityenglish.view.activity;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.infinityenglish.R;
import com.example.infinityenglish.databinding.ActivityLoginBinding;
import com.example.infinityenglish.view.base.BaseActivity;
import com.example.infinityenglish.viewmodel.NoteViewModel;
import com.example.infinityenglish.viewmodel.UserViewModel;

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding binding;
    private UserViewModel userViewModel;
    private NoteViewModel noteViewModel;

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

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.init(this);

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
                userViewModel.setStateLogin(true);
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

    @Override
    public void onBackPressed() {
        StartActivity.starter(LoginActivity.this);
        super.onBackPressed();
    }
}