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
import com.example.infinityenglish.control.remote.RequestNoteManager;
import com.example.infinityenglish.control.remote.RequestUserManager;
import com.example.infinityenglish.control.rest.Callback;
import com.example.infinityenglish.models.Notes;
import com.example.infinityenglish.models.Users;
import com.example.infinityenglish.util.Const;
import com.example.infinityenglish.util.Utility;

import java.util.List;

public class NoteViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<List<Notes>> notes = new MutableLiveData<>();
    private MutableLiveData<String> message = new MutableLiveData<>();
    private RequestUserManager requestNoteManager;

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public void init(Context context) {
        this.repository = new Repository(context);
        this.requestNoteManager = new RequestUserManager();
    }

    public void addNote(String title, String content, int idUser, Context context) {
        Notes notes = new Notes(title, content, idUser);
        requestNoteManager.saveNoteAPI(notes, context);
    }

    public void backupNote(List<Notes> notes, Users users, View view) {
        repository.syncNote(notes, users, view);
    }

    public MutableLiveData<List<Notes>> getNotes(int id) {
        requestNoteManager.getNoteByIdAPI(id, new Callback() {
            @Override
            public void getNotes(List<Notes> list) {
                super.getNotes(list);
                notes.setValue(list);
            }
        });
//        requestNoteManager.getNoteAPI(new Callback() {
//            @Override
//            public void getNotes(List<Notes> list) {
//                super.getNotes(list);
//                notes.setValue(list);
//            }
//        });
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

    public void updateNote(Notes notes) {
        repository.updateNote(notes);
        message.setValue(Const.Success.update);
    }

    public void updateOnlineNote(int id, String title, String content, View view, Users users) {

        //chưa set user
        Notes notes = new Notes(id, title, content, 1);
        repository.updateSyncNote(notes, users);
    }
}
