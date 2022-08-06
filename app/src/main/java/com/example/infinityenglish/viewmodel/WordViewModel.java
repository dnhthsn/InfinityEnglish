package com.example.infinityenglish.viewmodel;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.infinityenglish.control.Repository;
import com.example.infinityenglish.control.local.SharedPreference;
import com.example.infinityenglish.control.remote.RequestEnglishManager;
import com.example.infinityenglish.control.remote.RequestEnglishManager.OnFetchDataListener;
import com.example.infinityenglish.control.remote.RequestRandomManager;
import com.example.infinityenglish.control.remote.RequestRandomManager.OnFetchRandomDataListener;
import com.example.infinityenglish.control.rest.Callback;
import com.example.infinityenglish.models.Histories;
import com.example.infinityenglish.view.adapter.HistoryAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordViewModel extends ViewModel {
    private RequestEnglishManager requestEnglishManager;
    private RequestRandomManager requestRandomManager;
    private Repository repository;
    private MutableLiveData<List<Histories>> histories = new MutableLiveData<>();
    private SharedPreference sharedPreference;

    public void init(Context context){
        requestEnglishManager = new RequestEnglishManager();
        requestRandomManager = new RequestRandomManager();
        repository = new Repository(context);
        sharedPreference = new SharedPreference(context);
    }

    public void getWordMeanings(OnFetchDataListener listener, String query){
        requestEnglishManager.getWordMeanings(listener, query);
    }

    public void addWordSearched(String query){
        Histories histories = new Histories(query);
        repository.addHistory(histories);
    }

    public void deleteAllHistory(View view, HistoryAdapter historyAdapter){
        historyAdapter.setHistories(new ArrayList<>());
        repository.deleteAllHistory(view);
    }

    public MutableLiveData<List<Histories>> getHistory() {
        repository.getHistory(new Callback() {
            @Override
            public void getHistory(List<Histories> list) {
                super.getHistory(list);
                histories.setValue(list);
            }
        });
        return histories;
    }

    public void getRandomWord(OnFetchRandomDataListener listener, String word) {
        requestRandomManager.getRandomWord(listener, word);
    }
}
