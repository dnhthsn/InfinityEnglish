package com.example.infinityenglish.control;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;

import com.example.infinityenglish.R;
import com.example.infinityenglish.control.local.Database;
import com.example.infinityenglish.control.rest.Callback;
import com.example.infinityenglish.models.Histories;
import com.example.infinityenglish.models.Notes;
import com.example.infinityenglish.util.Const;
import com.example.infinityenglish.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    private Database database;

    public Repository(Context context) {
        this.database = new Database(context);
    }

    public void getHistory(Callback callback) {
        Cursor cursor = database.getHistory();
        Histories histories;
        List<Histories> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String word = cursor.getString(0);
            histories = new Histories(word);
            list.add(histories);
        }
        callback.getHistory(list);
        cursor.moveToFirst();
        cursor.close();
    }

    public void getNote(Callback callback) {
        Cursor cursor = database.getNote();
        Notes notes;
        List<Notes> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(0);
            String title = cursor.getString(1);
            String content = cursor.getString(2);
            notes = new Notes(id, title, content);
            list.add(notes);
        }
        callback.getNotes(list);
        cursor.moveToFirst();
        cursor.close();
    }

    public void addHistory(Histories histories) {
        if (!database.checkHistory(histories.getWordInput())) {
            database.addHistory(histories);
        }
    }

    public void addNote(Notes notes) {
        database.addNote(notes);
    }

    public void updateNote(Notes notes) {
        database.updateNote(notes);
    }

    public void deleteWordHistory(String word, View view) {
        database.deleteHistory(word);
        Utility.Notice.snack(view, Const.Success.deleted);
    }

    public void deleteNote(Integer id, View view) {
        database.deleteNote(id);
        Utility.Notice.snack(view, Const.Success.deleted);
    }

    public void deleteAllHistory(View view) {
        MediaPlayer mediaPlayer = MediaPlayer.create(view.getContext(), R.raw.timo);
        mediaPlayer.start();

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                mediaPlayer.stop();
                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setView(LayoutInflater.from(view.getContext()).inflate(R.layout.dialog, null));
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mediaPlayer.stop();
                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mediaPlayer.stop();
                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                database.deleteAllHistory();
                Utility.Notice.snack(view, Const.Success.deleted);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.round_corner_white);
        dialog.show();
    }
}
