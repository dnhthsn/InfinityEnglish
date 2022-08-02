package com.example.infinityenglish.viewmodel;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.infinityenglish.control.Repository;
import com.example.infinityenglish.control.rest.Callback;
import com.example.infinityenglish.models.Histories;
import com.example.infinityenglish.models.Notes;
import com.example.infinityenglish.util.Const;
import com.example.infinityenglish.util.Utility;
import com.example.infinityenglish.view.adapter.DeletedNotesAdapter;
import com.example.infinityenglish.view.adapter.HistoryAdapter;
import com.example.infinityenglish.view.adapter.NoteAdapter;

import java.util.ArrayList;
import java.util.List;

public class NoteViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<List<Notes>> notes = new MutableLiveData<>();
    private MutableLiveData<List<Notes>> deletedNotes = new MutableLiveData<>();

    public void init(Context context){
        this.repository = new Repository(context);
    }

    public void addNote(String title, String content){
        Notes notes = new Notes(title, content);
        repository.addNote(notes);
    }

    public void deleteAllNote(View view, DeletedNotesAdapter deletedNotesAdapter){
        deletedNotesAdapter.setNotes(new ArrayList<>());
        repository.deleteAllNote(view);
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

    public MutableLiveData<List<Notes>> getDeletedNotes() {
        repository.getDeletedNote(new Callback() {
            @Override
            public void getDeletedNotes(List<Notes> list) {
                super.getDeletedNotes(list);
                deletedNotes.setValue(list);
            }
        });
        return deletedNotes;
    }

    public void updateNote(int id, String title, String content, View view){
        Notes notes = new Notes(id, title, content);
        repository.updateNote(notes);
        Utility.Notice.snack(view, Const.Success.update);
    }
}
