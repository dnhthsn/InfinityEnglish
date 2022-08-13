package com.example.infinityenglish.view.activity;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.infinityenglish.R;
import com.example.infinityenglish.databinding.ActivityNoteBinding;
import com.example.infinityenglish.models.Notes;
import com.example.infinityenglish.models.Users;
import com.example.infinityenglish.util.Utility;
import com.example.infinityenglish.view.adapter.NoteAdapter;
import com.example.infinityenglish.view.base.BaseActivity;
import com.example.infinityenglish.viewmodel.NoteViewModel;
import com.example.infinityenglish.viewmodel.UserViewModel;

import java.util.List;

public class NoteActivity extends BaseActivity {
    private ActivityNoteBinding binding;
    private NoteViewModel noteViewModel;
    private UserViewModel userViewModel;

    private NoteAdapter noteAdapter;
    private long count;

    public static void starter(Context context) {
        Intent intent = new Intent(context, NoteActivity.class);
        context.startActivity(intent);
    }

    public static void finishActivity(Activity activity){
        activity.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_note);
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.init(this);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.init(this);

        binding.clickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.starter(NoteActivity.this);
                finish();
            }
        });

        noteAdapter = new NoteAdapter();

        boolean state = userViewModel.getStateLogin();

        Users users = userViewModel.getCurrentUser();

        if (state){
            noteViewModel.getOnlineNotes(users).observe(NoteActivity.this, new Observer<List<Notes>>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onChanged(List<Notes> notes) {
                    if (notes != null) {
                        count = notes.size();
                        noteAdapter.setNotes(notes);
                        noteAdapter.notifyDataSetChanged();
                    }
                }
            });
        } else {
            noteViewModel.getNotes().observe(NoteActivity.this, new Observer<List<Notes>>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onChanged(List<Notes> notes) {
                    if (notes != null) {
                        count = notes.size();
                        noteAdapter.setNotes(notes);
                        noteAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.noteList.setLayoutManager(layoutManager);
        binding.noteList.setAdapter(noteAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        binding.noteList.addItemDecoration(itemDecoration);

        binding.writeNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count > 49){
                    Utility.Notice.snack(view, "Max count");
                } else {
                    WriteNoteActivity.starter(NoteActivity.this);
                    finish();
                }
            }
        });
    }
}