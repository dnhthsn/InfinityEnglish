package com.example.infinityenglish.control.remote;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.infinityenglish.models.APIResponse;
import com.example.infinityenglish.util.Utility;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class RequestEnglishManager {
    private Retrofit retrofit;

    public RequestEnglishManager() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://api.dictionaryapi.dev/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void getWordMeanings(OnFetchDataListener listener, String word) {
        CallDictionary callDictionary = retrofit.create(CallDictionary.class);
        Call<List<APIResponse>> call = callDictionary.callMeanings(word);

        try {
            call.enqueue(new Callback<List<APIResponse>>() {
                @Override
                public void onResponse(@NonNull Call<List<APIResponse>> call, @NonNull Response<List<APIResponse>> response) {
                    if (!response.isSuccessful()) {
                        listener.onError("No data for this word " + word);
                    } else {
                        listener.onFetchData(response.body().get(0), response.message());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<APIResponse>> call, Throwable t) {
                    listener.onError("Request failed !!!");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface CallDictionary {
        @GET("entries/en/{word}")
        Call<List<APIResponse>> callMeanings(
                @Path("word") String word
        );
    }

    public interface OnFetchDataListener {
        void onFetchData(APIResponse apiResponse, String message);

        void onError(String message);
    }
}
