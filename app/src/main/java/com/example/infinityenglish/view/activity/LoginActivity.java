package com.example.infinityenglish.view.activity;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.infinityenglish.R;
import com.example.infinityenglish.databinding.ActivityLoginBinding;
import com.example.infinityenglish.models.Users;
import com.example.infinityenglish.util.Const;
import com.example.infinityenglish.util.Utility;
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

        binding.loginLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.Keyboard.hideKeyBoard(LoginActivity.this);
            }
        });

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

                Users users = new Users();
                users.setName(name);
                users.setPassword(password);
                userViewModel.checkUser(users);

                userViewModel.getState().observe(LoginActivity.this, new Observer<Const.State>() {
                    @Override
                    public void onChanged(Const.State state) {
                        if (state.equals(Const.State.Main)){
                            MainActivity.starter(LoginActivity.this);
                        }
                    }
                });

                userViewModel.getMessage().observe(LoginActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String message) {
                        binding.wrongInfo.setText(message);
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        StartActivity.starter(LoginActivity.this);
        super.onBackPressed();
    }
}