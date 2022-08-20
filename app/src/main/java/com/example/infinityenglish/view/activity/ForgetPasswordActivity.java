package com.example.infinityenglish.view.activity;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.infinityenglish.R;
import com.example.infinityenglish.databinding.ActivityForgetPasswordBinding;
import com.example.infinityenglish.models.Users;
import com.example.infinityenglish.util.Utility;
import com.example.infinityenglish.view.base.BaseActivity;
import com.example.infinityenglish.viewmodel.UserViewModel;

public class ForgetPasswordActivity extends BaseActivity {
    private ActivityForgetPasswordBinding binding;
    private UserViewModel userViewModel;

    public static void starter(Context context) {
        Intent intent = new Intent(context, ForgetPasswordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_forget_password);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.init(this);

        binding.clickConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.inputName.getText().toString();
                String password = binding.inputPassword.getText().toString();
                String phone = binding.inputPhone.getText().toString();
                String rePassword = binding.inputRepassword.getText().toString();

                Users users = new Users();
                users.setName(name);
                users.setPassword(password);
                users.setPhone(phone);

                userViewModel.updatePassword(users, rePassword, view);

                userViewModel.getMessage().observe(ForgetPasswordActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        if (!s.equals("")){
                            Utility.Notice.snack(view, s);
                        }
                    }
                });
            }
        });

        binding.forgetPasswordLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.Keyboard.hideKeyBoard(ForgetPasswordActivity.this);
            }
        });
    }
}