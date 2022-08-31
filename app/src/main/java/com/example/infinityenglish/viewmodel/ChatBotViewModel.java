package com.example.infinityenglish.viewmodel;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.infinityenglish.control.Repository;
import com.example.infinityenglish.control.remote.RequestChatBotManager;
import com.example.infinityenglish.control.rest.Callback;
import com.example.infinityenglish.models.ChatsModel;
import com.example.infinityenglish.models.Users;

import java.util.List;

public class ChatBotViewModel extends ViewModel {
    private RequestChatBotManager requestChatBotManager;
    private Repository repository;
    private MutableLiveData<List<ChatsModel>> chatModels = new MutableLiveData<>();

    public void init(Context context){
        this.requestChatBotManager = new RequestChatBotManager();
        this.repository = new Repository(context);
    }

    public void getChatMessage(RequestChatBotManager.OnFetchDataListener listener, String query){
        requestChatBotManager.getChatMessage(listener, query);
    }

    public MutableLiveData<List<ChatsModel>> getChatModels(Users users) {
        repository.getChatBotMessage(users, new Callback() {
            @Override
            public void getChatBotMessage(List<ChatsModel> chatsModel) {
                super.getChatBotMessage(chatsModel);
                chatModels.setValue(chatsModel);
            }
        });
        return chatModels;
    }

    public void addChatBotMessage(List<ChatsModel> chatsModels, Users users, View view) {
        repository.addChatBot(chatsModels, users, view);
    }
}
