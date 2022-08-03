package com.example.infinityenglish.viewmodel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import androidx.lifecycle.ViewModel;

import com.example.infinityenglish.R;
import com.example.infinityenglish.control.Repository;
import com.example.infinityenglish.control.local.SharedPreference;
import com.example.infinityenglish.control.rest.Callback;
import com.example.infinityenglish.models.Users;
import com.example.infinityenglish.util.Const;
import com.example.infinityenglish.util.Utility;
//import com.example.infinityenglish.view.activity.EditInformationActivity;
import com.example.infinityenglish.view.activity.LoginActivity;
import com.example.infinityenglish.view.activity.MainActivity;
import com.example.infinityenglish.view.activity.SignUpActivity;

import java.io.IOException;
import java.util.List;

public class UserViewModel extends ViewModel {
    private Repository repository;
    private SharedPreference sharedPreference;
    private String message;

    public void init(Context context){
        this.repository = new Repository(context);
        sharedPreference = new SharedPreference(context);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setStateLogin(boolean stateLogin){
        sharedPreference.setStateLogin(stateLogin);
    }

    public boolean getStateLogin(){
        boolean stateLogin = sharedPreference.getStateLogin();
        return stateLogin;
    }

    public void addUser(String name, String password, String address, String email, String phone, String gender, String avatar, View view) {
        if (TextUtils.isEmpty(name)) {
            Utility.Notice.snack(view, Const.Error.name);
        } else if (TextUtils.isEmpty(phone)) {
            Utility.Notice.snack(view, Const.Error.phone);
        } else if (TextUtils.isEmpty(password)) {
            Utility.Notice.snack(view, Const.Error.password);
        } else if (TextUtils.isEmpty(address)) {
            Utility.Notice.snack(view, Const.Error.address);
        } else if (TextUtils.isEmpty(email)) {
            Utility.Notice.snack(view, Const.Error.email);
        } else if (avatar == "null") {
            Utility.Notice.snack(view, Const.Error.avatar);
        }else {
            Users users = new Users(name, password, address, email, phone, gender, avatar);
            LoginActivity.starter(view.getContext());
            repository.addUser(users, view);
        }
    }

    public void updateUser(String name, String password, String address, String email, String phone, String gender, String avatar, View view) {
        if (TextUtils.isEmpty(name)) {
            Utility.Notice.snack(view, Const.Error.name);
        } else if (TextUtils.isEmpty(phone)) {
            Utility.Notice.snack(view, Const.Error.phone);
        } else if (TextUtils.isEmpty(password)) {
            Utility.Notice.snack(view, Const.Error.password);
        } else if (TextUtils.isEmpty(address)) {
            Utility.Notice.snack(view, Const.Error.address);
        } else if (TextUtils.isEmpty(email)) {
            Utility.Notice.snack(view, Const.Error.email);
        } else {
            Users users = new Users(name, password, address, email, phone, gender, avatar);
            repository.updateUser(users);
            LoginActivity.starter(view.getContext());
            Utility.Notice.snack(view, Const.Success.update);
        }
    }

    public void updatePassword(String name, String password, String phone, View view, Context context) {
        if (TextUtils.isEmpty(name)) {
            Utility.Notice.snack(view, Const.Error.name);
        } else if (TextUtils.isEmpty(password)) {
            Utility.Notice.snack(view, Const.Error.password);
        } else {
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.timo);
            mediaPlayer.start();

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(LayoutInflater.from(context).inflate(R.layout.dialog_update, null));
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
                    Users users = new Users();
                    users.setName(name);
                    users.setPassword(password);
                    users.setPhone(phone);

                    repository.updatePassword(users, view);
                }
            });

            AlertDialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.round_corner_white);
            dialog.show();
        }
    }

    public Users getCurrentUser(){
        Users users = sharedPreference.getCurrentUser();
        return users;
    }

    public void saveUserAfterLogout(String name, String password){
        sharedPreference.saveUser(name, password);
    }

    public void removeUser(){
        sharedPreference.removeUser();
    }

    public void removeStateLogin(){
        sharedPreference.removeStateLogin();
    }

    public void removeCurrentUser(){
        sharedPreference.removeCurrentUser();
    }

    public void checkUser(String name, String password, View view) {
        if (TextUtils.isEmpty(name)) {
            Utility.Notice.snack(view, Const.Error.name);
        } else if (TextUtils.isEmpty(password)) {
            Utility.Notice.snack(view, Const.Error.password);
        } else {
            repository.getUser(new Callback() {
                @Override
                public void getUser(List<Users> list) {
                    super.getUser(list);
                    for (Users user : list) {
                        if (user.getName().equals(name) && user.getPassword().equals(password)) {
                            sharedPreference.saveCurrentUser(user);
                            MainActivity.starter(view.getContext());
                            setMessage("");
                            break;
                        } else {
                            setMessage(Const.Error.information);
                        }
                    }
                }
            });
        }
    }
}
