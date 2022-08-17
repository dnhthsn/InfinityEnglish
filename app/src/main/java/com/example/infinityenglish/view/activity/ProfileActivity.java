package com.example.infinityenglish.view.activity;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infinityenglish.R;
import com.example.infinityenglish.databinding.ActivityProfileBinding;
import com.example.infinityenglish.databinding.DialogEditInformationBinding;
import com.example.infinityenglish.models.Users;
import com.example.infinityenglish.util.Const;
import com.example.infinityenglish.util.Utility;
import com.example.infinityenglish.view.base.BaseActivity;
import com.example.infinityenglish.viewmodel.UserViewModel;

public class ProfileActivity extends BaseActivity {
    private ActivityProfileBinding binding;
    private UserViewModel userViewModel;

    public static void starter(Context context) {
        Intent intent = new Intent(context, ProfileActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.init(this);

        Users users = userViewModel.getCurrentUser();
        if (users != null) {
            userViewModel.getUserAvatar(users, binding.profile, binding.avatar);
            binding.userName.setText(users.getName());
            binding.email.setText(users.getEmail());
            binding.phoneNumber.setText(users.getPhone());
            binding.password.setText("********");
            binding.gender.setText(users.getGender());
            binding.address.setText(users.getAddress());
        }

        binding.clickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.clickEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(ProfileActivity.this);
                dialog.setContentView(R.layout.dialog_edit_information);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.round_corner);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setGravity(Gravity.CENTER);

                DialogEditInformationBinding binding1 = DataBindingUtil.inflate(LayoutInflater.from(ProfileActivity.this), R.layout.dialog_edit_information, null, false);
                dialog.setContentView(binding1.getRoot());


                binding1.clickConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (users != null) {
                            String pass = binding1.inputPassword.getText().toString();
                            if (users.getPassword().equals(pass)) {
                                dialog.dismiss();
                                ChangeInformationActivity.starter(ProfileActivity.this);
                            } else {
                                Utility.Notice.snack(view, Const.Error.information);
                            }
                        }
                    }
                });

                dialog.show();
            }
        });
    }
}