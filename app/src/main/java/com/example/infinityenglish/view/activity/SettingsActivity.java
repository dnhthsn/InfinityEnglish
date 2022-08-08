package com.example.infinityenglish.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
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
import com.example.infinityenglish.databinding.ActivitySettingsBinding;
import com.example.infinityenglish.databinding.DialogLogoutBinding;
import com.example.infinityenglish.models.Notes;
import com.example.infinityenglish.models.Users;
import com.example.infinityenglish.util.Const;
import com.example.infinityenglish.util.Utility;
import com.example.infinityenglish.view.base.BaseActivity;
import com.example.infinityenglish.viewmodel.NoteViewModel;
import com.example.infinityenglish.viewmodel.UserViewModel;

import java.util.List;
import java.util.Set;

public class SettingsActivity extends BaseActivity {
    private ActivitySettingsBinding binding;
    private UserViewModel userViewModel;
    private NoteViewModel noteViewModel;

    public static void starter(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }

    public static void finishActivity(Activity activity){
        activity.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.init(this);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.init(this);

        Users users = userViewModel.getCurrentUser();
        String uri;
        if (users != null){
            uri = users.getAvatar();
            String imageUri = uri==null ? String.valueOf(R.drawable.avatar) :uri;
            binding.userAvatar.setImageURI(Uri.parse(imageUri));
            binding.userName.setText(users.getName());
        }

        binding.backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean state = userViewModel.getStateLogin();
                if (state){
                    noteViewModel.getNotes().observe(SettingsActivity.this, new Observer<List<Notes>>() {
                        @Override
                        public void onChanged(List<Notes> notes) {
                            noteViewModel.backupNote(notes, users, view);
                        }
                    });
                } else {
                    Utility.Notice.snack(view, Const.Error.login);
                }
            }
        });

        binding.clickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (users == null){
                    Utility.Notice.snack(view, Const.Error.profileClick);
                } else {
                    ProfileActivity.starter(SettingsActivity.this);
                }
            }
        });

        binding.clickLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(SettingsActivity.this);
                dialog.setContentView(R.layout.dialog_logout);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.round_corner_nocolor);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setGravity(Gravity.BOTTOM);

                DialogLogoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(SettingsActivity.this), R.layout. dialog_logout, null, false);
                dialog.setContentView(binding.getRoot());

                binding.clickLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        StartActivity.starter(SettingsActivity.this);
                        userViewModel.removeStateLogin();
                        SettingsActivity.finishActivity(SettingsActivity.this);
                    }
                });

                binding.clickCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });
    }
}