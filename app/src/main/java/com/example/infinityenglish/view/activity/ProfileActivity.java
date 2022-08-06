package com.example.infinityenglish.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.infinityenglish.R;
import com.example.infinityenglish.databinding.ActivityProfileBinding;
import com.example.infinityenglish.models.Users;
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
        String uri;
        if (users != null){
            uri = users.getAvatar();
            String imageUri = uri==null ? String.valueOf(R.drawable.avatar) :uri;
            binding.avatar.setImageURI(Uri.parse(imageUri));
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
                ChangeInformationActivity.starter(ProfileActivity.this);
            }
        });
    }
}