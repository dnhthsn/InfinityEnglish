package com.example.infinityenglish.control.remote;

import android.content.Context;
import android.widget.Toast;

import com.example.infinityenglish.models.Notes;
import com.example.infinityenglish.models.Users;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class RequestNoteManager {
    private Retrofit retrofit;

    public RequestNoteManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.29.153:9000")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    public void getNoteAPI(com.example.infinityenglish.control.rest.Callback callback) {
        CallNote callNote = retrofit.create(CallNote.class);
        callNote.getAllNote().enqueue(new Callback<List<Notes>>() {
            @Override
            public void onResponse(Call<List<Notes>> call, Response<List<Notes>> response) {
                callback.getNotes(response.body());
            }

            @Override
            public void onFailure(Call<List<Notes>> call, Throwable t) {

            }
        });
    }

    public void saveNoteAPI(Notes notes) {
        CallNote callNote = retrofit.create(CallNote.class);
        callNote.save(notes).enqueue(new Callback<Notes>() {
            @Override
            public void onResponse(Call<Notes> call, Response<Notes> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                //listener.onFetchData(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<Notes> call, Throwable t) {
                //listener.onError("Request Failed");
            }
        });
    }

    public interface CallNote {
        @GET("/note/get-all-note")
        Call<List<Notes>> getAllNote();

        @POST("/note/save-note")
        Call<Notes> save(@Body Notes notes);
    }
}
