package com.example.infinityenglish.control;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.infinityenglish.R;
import com.example.infinityenglish.control.local.Database;
import com.example.infinityenglish.control.rest.Callback;
import com.example.infinityenglish.models.Histories;
import com.example.infinityenglish.models.Notes;
import com.example.infinityenglish.models.Users;
import com.example.infinityenglish.util.Const;
import com.example.infinityenglish.util.Utility;
import com.example.infinityenglish.view.activity.ForgetPasswordActivity;
import com.example.infinityenglish.view.activity.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Repository {
    private Database database;
    private FirebaseDatabase db;
    private DatabaseReference databaseReference;

    public Repository(Context context) {
        this.database = new Database(context);
        this.db = FirebaseDatabase.getInstance("https://test-b2d47-default-rtdb.asia-southeast1.firebasedatabase.app/");
        this.databaseReference = db.getReference();
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

    public void getUser(Callback callback) {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Users> list = new ArrayList<>();
                Users users;
                for (DataSnapshot data : snapshot.getChildren()) {
                    users = data.getValue(Users.class);
                    list.add(users);
                }
                callback.getUser(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference.child(Const.Database.user).addValueEventListener(postListener);
    }

    public void addUser(Users users, View view) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.child(Const.Database.user).child(users.getName()).exists()) {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put(Const.Database.name, users.getName());
                    userdataMap.put(Const.Database.password, users.getPassword());
                    userdataMap.put(Const.Database.address, users.getAddress());
                    userdataMap.put(Const.Database.email, users.getEmail());
                    userdataMap.put(Const.Database.phone, users.getPhone());
                    userdataMap.put(Const.Database.gender, users.getGender());
                    userdataMap.put(Const.Database.avatar, users.getAvatar());

                    databaseReference.child(Const.Database.user)
                            .child(users.getName())
                            .updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Utility.Notice.snack(view, Const.Success.created);
                                    } else {
                                        Utility.Notice.snack(view, Const.Error.network);
                                    }
                                }
                            });
                } else {
                    Utility.Notice.snack(view, Const.Error.existed);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void syncNote(List<Notes> notes, Users users, View view) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (Notes note : notes) {
                    if (!snapshot.child(Const.Database.user)
                            .child(users.getName())
                            .child(Const.Database.notes)
                            .child(String.valueOf(note.getId()))
                            .exists()) {
                        HashMap<String, Object> userdataMap = new HashMap<>();
                        userdataMap.put(Const.Database.id, note.getId());
                        userdataMap.put(Const.Database.title, note.getTitle());
                        userdataMap.put(Const.Database.content, note.getContent());

                        databaseReference.child(Const.Database.user)
                                .child(users.getName())
                                .child(Const.Database.notes)
                                .child(String.valueOf(note.getId()))
                                .updateChildren(userdataMap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Utility.Notice.snack(view, Const.Success.created);
                                        } else {
                                            Utility.Notice.snack(view, Const.Error.network);
                                        }
                                    }
                                });
                    } else {
                        updateSyncNote(note, users);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateSyncNote(Notes notes, Users users) {
        databaseReference.child(Const.Database.user)
                .child(users.getName())
                .child(Const.Database.notes)
                .child(String.valueOf(notes.getId()))
                .setValue(notes);
    }

    public void updateUser(Users users) {
        databaseReference.child(Const.Database.user).child(users.getName()).child(Const.Database.password).setValue(users.getPassword());
        databaseReference.child(Const.Database.user).child(users.getName()).child(Const.Database.email).setValue(users.getEmail());
        databaseReference.child(Const.Database.user).child(users.getName()).child(Const.Database.phone).setValue(users.getPhone());
        databaseReference.child(Const.Database.user).child(users.getName()).child(Const.Database.address).setValue(users.getAddress());
        databaseReference.child(Const.Database.user).child(users.getName()).child(Const.Database.avatar).setValue(users.getAvatar());
        databaseReference.child(Const.Database.user).child(users.getName()).child(Const.Database.gender).setValue(users.getGender());
    }

    public void updatePassword(Users users, View view) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(Const.Database.user).child(users.getName()).exists()) {
                    if (snapshot.child(Const.Database.user)
                            .child(users.getName())
                            .child(Const.Database.phone)
                            .getValue()
                            .equals(users.getPhone())) {
                        Utility.Notice.snack(view, Const.Success.update);
                        databaseReference.child(Const.Database.user)
                                .child(users.getName())
                                .child(Const.Database.password)
                                .setValue(users.getPassword());
                        LoginActivity.starter(view.getContext());
                    } else {
                        Utility.Notice.snack(view, Const.Error.wrongPhone);
                    }
                } else {
                    Utility.Notice.snack(view, Const.Error.notexisted);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
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

    public void getOnlineNote(Users users, Callback callback) {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Notes> list = new ArrayList<>();
                Notes notes;
                for (DataSnapshot data : snapshot.getChildren()) {
                    notes = data.getValue(Notes.class);
                    list.add(notes);
                }
                callback.getNotes(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference.child(Const.Database.user)
                .child(users.getName())
                .child(Const.Database.notes)
                .addValueEventListener(postListener);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

        builder.setView(LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_delete_permanent, null));
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.deleteNote(id);
                Utility.Notice.snack(view, Const.Success.deleted);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.round_corner_white);
        dialog.show();
    }

    public void deleteOnlineNote(Users users, Integer id, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

        builder.setView(LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_delete_permanent, null));
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                databaseReference.child(Const.Database.user)
                        .child(users.getName())
                        .child(Const.Database.notes)
                        .child(String.valueOf(id)).removeValue();
                Utility.Notice.snack(view, Const.Success.deleted);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.round_corner_white);
        dialog.show();
    }

    public void deleteAllHistory(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

        builder.setView(LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_delete, null));
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.deleteAllHistory();
                Utility.Notice.snack(view, Const.Success.deleted);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.round_corner_white);
        dialog.show();
    }
}
