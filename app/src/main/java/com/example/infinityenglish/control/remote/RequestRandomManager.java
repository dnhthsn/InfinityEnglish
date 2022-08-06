package com.example.infinityenglish.control.remote;

import com.example.infinityenglish.models.ListWords;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class RequestRandomManager {
    private Retrofit retrofit;

    public RequestRandomManager() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://random-word-api.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void getRandomWord(OnFetchRandomDataListener listener, String word) {
        CallRandomWord callRandomWord = retrofit.create(CallRandomWord.class);
        Call<Object> call = callRandomWord.callWords();

        try {
            call.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    if (!response.isSuccessful()) {
                        return;
                    }
                    listener.onFetchRandomData(response.body(), response.message());
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {

                    listener.onRandomError("Request Failed");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface CallRandomWord {
        @GET("word")
        Call<Object> callWords();
    }

    public interface OnFetchRandomDataListener {
        void onFetchRandomData(Object listWords, String message);

        void onRandomError(String message);
    }
}
