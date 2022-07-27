package com.example.infinityenglish.viewmodel;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.infinityenglish.control.Repository;
import com.example.infinityenglish.control.local.Database;
import com.example.infinityenglish.control.local.SharedPreference;
import com.example.infinityenglish.control.remote.RequestManager;
import com.example.infinityenglish.control.remote.RequestManager.OnFetchDataListener;
import com.example.infinityenglish.control.rest.Callback;
import com.example.infinityenglish.models.Histories;
import com.example.infinityenglish.view.adapter.HistoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class WordViewModel extends ViewModel {
    private RequestManager requestManager;
    private Repository repository;
    private MutableLiveData<List<Histories>> histories = new MutableLiveData<>();
    private SharedPreference sharedPreference;

    public void init(Context context){
        requestManager = new RequestManager();
        repository = new Repository(context);
        sharedPreference = new SharedPreference(context);
    }

    public void getWordMeanings(OnFetchDataListener listener, String query){
        requestManager.getWordMeanings(listener, query);
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

    public void saveStateBookmark(boolean state){
        sharedPreference.saveStateBookmark(state);
    }

    public boolean getStateBookmarked(){
        return sharedPreference.getStateBookmarked();
    }
    public void removeBookmarkedState(){
        sharedPreference.removeBookmarkState();
    }
}
