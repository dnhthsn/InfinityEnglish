package com.example.infinityenglish.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.infinityenglish.R;
import com.example.infinityenglish.databinding.ActivityDeletedNotesBinding;
import com.example.infinityenglish.models.Notes;
import com.example.infinityenglish.view.adapter.DeletedNotesAdapter;
import com.example.infinityenglish.view.base.BaseActivity;
import com.example.infinityenglish.viewmodel.NoteViewModel;

import java.util.List;

public class DeletedNotesActivity extends BaseActivity {
    private ActivityDeletedNotesBinding binding;
    private NoteViewModel noteViewModel;

    private DeletedNotesAdapter deletedNotesAdapter;

    public static void starter(Context context) {
        Intent intent = new Intent(context, DeletedNotesActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleted_notes);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_deleted_notes);
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.init(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.deletedNotesList.setLayoutManager(layoutManager);
        deletedNotesAdapter = new DeletedNotesAdapter();
        noteViewModel.getDeletedNotes().observe(DeletedNotesActivity.this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                deletedNotesAdapter.setNotes(notes);
                deletedNotesAdapter.notifyDataSetChanged();
            }
        });

        binding.deletedNotesList.setAdapter(deletedNotesAdapter);

        binding.clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteViewModel.deleteAllNote(view, deletedNotesAdapter);
                deletedNotesAdapter.notifyDataSetChanged();
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