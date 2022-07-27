package com.example.infinityenglish.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.infinityenglish.R;
import com.example.infinityenglish.databinding.ActivityWriteNoteBinding;
import com.example.infinityenglish.util.Const;
import com.example.infinityenglish.viewmodel.NoteViewModel;

public class WriteNoteActivity extends AppCompatActivity {
    private ActivityWriteNoteBinding binding;
    private NoteViewModel noteViewModel;

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
        noteViewModel.init(this);

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
                noteViewModel.addNote(title, content);
                NoteActivity.starter(WriteNoteActivity.this);
                finish();
            }
        });
    }
}