package com.example.infinityenglish.view.activity;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.infinityenglish.R;
import com.example.infinityenglish.databinding.ActivityWriteNoteBinding;
import com.example.infinityenglish.models.Notes;
import com.example.infinityenglish.models.Users;
import com.example.infinityenglish.view.base.BaseActivity;
import com.example.infinityenglish.viewmodel.NoteViewModel;
import com.example.infinityenglish.viewmodel.UserViewModel;

import java.util.List;

public class WriteNoteActivity extends BaseActivity {
    private ActivityWriteNoteBinding binding;
    private NoteViewModel noteViewModel;
    private UserViewModel userViewModel;

    public static void starter(Context context) {
        Intent intent = new Intent(context, WriteNoteActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_note);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_write_note);
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.init(WriteNoteActivity.this);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.init(WriteNoteActivity.this);

        Users users = userViewModel.getCurrentUser();
        boolean state = userViewModel.getStateLogin();

        binding.clickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = binding.inputNoteTitle.getText().toString();
                String content = binding.inputNoteContent.getText().toString();

                if (state) {
                    noteViewModel.addOnlineNote(title, content, users, view);
                    finish();
                } else {
                    noteViewModel.addNote(title, content);
                    finish();
                }
            }
        });
    }
}