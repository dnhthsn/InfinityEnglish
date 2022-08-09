package com.example.infinityenglish.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infinityenglish.R;
import com.example.infinityenglish.databinding.ActivityStartBinding;
import com.example.infinityenglish.databinding.DialogLoginBinding;
import com.example.infinityenglish.databinding.DialogLogoutBinding;
import com.example.infinityenglish.view.base.BaseActivity;
import com.example.infinityenglish.viewmodel.UserViewModel;

public class StartActivity extends BaseActivity {
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
                Dialog dialog = new Dialog(StartActivity.this);
                dialog.setContentView(R.layout.dialog_login);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setGravity(Gravity.CENTER);

                DialogLoginBinding binding = DataBindingUtil.inflate(LayoutInflater.from(StartActivity.this), R.layout.dialog_login, null, false);
                dialog.setContentView(binding.getRoot());

                binding.clickYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userViewModel.removeCurrentUser();
                        MainActivity.starter(StartActivity.this);
                        dialog.dismiss();
                    }
                });

                binding.clickNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}