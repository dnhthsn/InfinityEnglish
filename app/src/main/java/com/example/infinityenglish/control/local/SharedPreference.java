package com.example.infinityenglish.control.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.infinityenglish.models.Users;
import com.example.infinityenglish.util.Const;
import com.google.gson.Gson;

public class SharedPreference {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    public SharedPreference(Context context) {
        this.sharedPreferences = context.getSharedPreferences(Const.Sender.SHARED_PREFERENCES,
                Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
        this.gson = new Gson();
    }

    public void saveUser(String name, String password) {
        editor.putString(Const.Sender.name, name);
        editor.putString(Const.Sender.password, password);
        editor.commit();
    }

    public void saveWord(String word){
        editor.putString(Const.Sender.word, word);
        editor.commit();
    }

    public String getWord(){
        String word = sharedPreferences.getString(Const.Sender.word, "");
        return word;
    }

    public void saveCurrentUser(Users users){
        String user = gson.toJson(users);
        editor.putString(Const.Sender.users, user);
        editor.commit();
    }

    public void removeCurrentUser(){
        editor.remove(Const.Sender.users);
        editor.commit();
    }

    public Users getCurrentUser(){
        String getUser = sharedPreferences.getString(Const.Sender.users, "");
        return gson.fromJson(getUser,Users.class);
    }

    public void removeUser() {
        editor.remove(Const.Sender.name);
        editor.remove(Const.Sender.password);
        editor.commit();
    }

    public void getUser(Users users){
        String name = sharedPreferences.getString(Const.Sender.name, "");
        String password = sharedPreferences.getString(Const.Sender.password, "");
        users.setName(name);
        users.setPassword(password);
    }

    public void setStateLogin(boolean stateLogin){
        editor.putBoolean(Const.Sender.loginState, true);
        editor.commit();
    }

    public boolean getStateLogin(){
        boolean stateLogin = sharedPreferences.getBoolean(Const.Sender.loginState, false);
        return stateLogin;
    }

    public void removeStateLogin(){
        editor.remove(Const.Sender.loginState);
        editor.commit();
    }
}
