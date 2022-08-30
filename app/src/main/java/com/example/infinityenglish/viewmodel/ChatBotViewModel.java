package com.example.infinityenglish.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.infinityenglish.control.remote.RequestChatBotManager;
import com.example.infinityenglish.control.remote.RequestEnglishManager;

public class ChatBotViewModel extends ViewModel {
    private RequestChatBotManager requestChatBotManager;

    public ChatBotViewModel() {
        this.requestChatBotManager = new RequestChatBotManager();
    }

    public void getChatMessage(RequestChatBotManager.OnFetchDataListener listener, String query){
        requestChatBotManager.getChatMessage(listener, query);
    }
}
