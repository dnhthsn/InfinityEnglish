package com.example.infinityenglish.viewmodel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.infinityenglish.R;
import com.example.infinityenglish.control.Repository;
import com.example.infinityenglish.control.rest.Callback;
import com.example.infinityenglish.models.Notes;
import com.example.infinityenglish.models.Users;
import com.example.infinityenglish.util.Const;
import com.example.infinityenglish.util.Utility;

import java.util.ArrayList;
import java.util.List;

public class NoteViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<List<Notes>> notes = new MutableLiveData<>();

    public void init(Context context) {
        this.repository = new Repository(context);
    }

    public void addNote(String title, String content) {
        Notes notes = new Notes(title, content);
        repository.addNote(notes);
    }

    public void addOnlineNote(String title, String content, Users users, View view) {
        Notes notes = new Notes(title, content);
        repository.addNote(notes);
        repository.getNote(new Callback() {
            @Override
            public void getNotes(List<Notes> list) {
                super.getNotes(list);
                repository.syncNote(list, users, view);
            }
        });
    }

    public void backupNote(List<Notes> notes, Users users, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

        builder.setView(LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_backup, null));
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                repository.syncNote(notes, users, view);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.round_corner_white);
        dialog.show();
    }

    public MutableLiveData<List<Notes>> getNotes() {
        repository.getNote(new Callback() {
            @Override
            public void getNotes(List<Notes> list) {
                super.getNotes(list);
                notes.setValue(list);
            }
        });
        return notes;
    }

    public MutableLiveData<List<Notes>> getOnlineNotes(Users users) {
        repository.getOnlineNote(users, new Callback() {
            @Override
            public void getNotes(List<Notes> list) {
                super.getNotes(list);
                notes.setValue(list);
            }
        });
        return notes;
    }

    public void updateNote(int id, String title, String content, View view) {
        Notes notes = new Notes(id, title, content);
        repository.updateNote(notes);
        Utility.Notice.snack(view, Const.Success.update);
    }

    public void updateOnlineNote(int id, String title, String content, View view, Users users){
        Notes notes = new Notes(id, title, content);
        repository.updateSyncNote(notes, users);
    }
}
