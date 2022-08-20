package com.example.infinityenglish.view.activity;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.infinityenglish.R;
import com.example.infinityenglish.databinding.ActivityUpdateNoteBinding;
import com.example.infinityenglish.models.Users;
import com.example.infinityenglish.util.Const;
import com.example.infinityenglish.util.Utility;
import com.example.infinityenglish.view.base.BaseActivity;
import com.example.infinityenglish.viewmodel.NoteViewModel;
import com.example.infinityenglish.viewmodel.UserViewModel;

public class UpdateNoteActivity extends BaseActivity {
    private ActivityUpdateNoteBinding binding;
    private NoteViewModel noteViewModel;
    private UserViewModel userViewModel;

    public static void starter(Context context) {
        Intent intent = new Intent(context, UpdateNoteActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_note);
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.init(this);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.init(this);

        String title = getIntent().getStringExtra(Const.Sender.noteTitle);
        String content = getIntent().getStringExtra(Const.Sender.noteContent);

        binding.inputNoteTitle.setText(title);
        binding.inputNoteContent.setText(content);

        binding.updateNoteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.Keyboard.hideKeyBoard(UpdateNoteActivity.this);
            }
        });

        binding.updateNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Users users = userViewModel.getCurrentUser();

                userViewModel.getState().observe(UpdateNoteActivity.this, new Observer<Const.State>() {
                    @Override
                    public void onChanged(Const.State state) {
                        int id = getIntent().getIntExtra(Const.Sender.noteId, 0);
                        String noteTitle = binding.inputNoteTitle.getText().toString();
                        String noteContent = binding.inputNoteContent.getText().toString();
                        switch (state) {
                            case Main:
                                noteViewModel.updateOnlineNote(id, noteTitle, noteContent, view, users);
                                NoteActivity.starter(UpdateNoteActivity.this);
                                finish();
                                break;
                            case Start:
                                noteViewModel.updateNote(id, noteTitle, noteContent, view);
                                NoteActivity.starter(UpdateNoteActivity.this);
                                finish();
                                break;
                            case none:
                                break;
                        }
                    }
                });
            }
        });

        binding.clickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteActivity.starter(UpdateNoteActivity.this);
                finish();
            }
        });
    }
}