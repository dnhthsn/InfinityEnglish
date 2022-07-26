package com.example.infinityenglish.control.rest;

import android.graphics.Bitmap;

import com.example.infinityenglish.models.ChatsModel;
import com.example.infinityenglish.models.Histories;
import com.example.infinityenglish.models.Notes;
import com.example.infinityenglish.models.Users;

import java.util.List;

public abstract class Callback {
    public void getUser(List<Users> list) {
    }

    public void getHistory(List<Histories> list) {
    }

    public void getNotes(List<Notes> list) {
    }

    public void getAvatar(Bitmap bitmap) {
    }

    public void getChatBotMessage(List<ChatsModel> chatsModel) {
    }
}
