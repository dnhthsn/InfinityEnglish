package com.example.infinityenglish.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.infinityenglish.R;
import com.example.infinityenglish.databinding.ActivityUpdateNoteBinding;
import com.example.infinityenglish.databinding.ActivityWriteNoteBinding;
import com.example.infinityenglish.util.Const;
import com.example.infinityenglish.viewmodel.NoteViewModel;

public class UpdateNoteActivity extends AppCompatActivity {
    private ActivityUpdateNoteBinding binding;
    private NoteViewModel noteViewModel;

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

        String title = getIntent().getStringExtra(Const.Sender.noteTitle);
        String content = getIntent().getStringExtra(Const.Sender.noteContent);

        binding.inputNoteTitle.setText(title);
        binding.inputNoteContent.setText(content);

        binding.updateNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = getIntent().getIntExtra(Const.Sender.noteId, 0);
                String noteTitle = binding.inputNoteTitle.getText().toString();
                String noteContent = binding.inputNoteContent.getText().toString();
                noteViewModel.updateNote(id, noteTitle, noteContent, view);

                NoteActivity.starter(UpdateNoteActivity.this);
                finish();
            }
        });

        binding.clickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}