package com.example.infinityenglish.control.remote;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.infinityenglish.models.Notes;
import com.example.infinityenglish.models.Users;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class RequestUserManager {
    private Retrofit retrofit;

    public RequestUserManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.29.153:9000")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    public void getUserAPI(com.example.infinityenglish.control.rest.Callback callback) {
        CallUser callUser = retrofit.create(CallUser.class);
        callUser.getAllUser().enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                callback.getUser(response.body());
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {

            }
        });
    }

    public void saveUserAPI(Users users, Context context) {
        CallUser callUser = retrofit.create(CallUser.class);
        callUser.save(users).enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                //listener.onFetchData(response.body(), response.message());
                Toast.makeText(context, "add success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                //listener.onError("Request Failed");
            }
        });
    }

    public void getNoteAPI(com.example.infinityenglish.control.rest.Callback callback) {
        CallUser callNote = retrofit.create(CallUser.class);
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

    public void saveNoteAPI(Notes notes, Context context) {
        CallUser callNote = retrofit.create(CallUser.class);
        callNote.save(notes).enqueue(new Callback<Notes>() {
            @Override
            public void onResponse(Call<Notes> call, Response<Notes> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                //listener.onFetchData(response.body(), response.message());
                Toast.makeText(context, "add success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Notes> call, Throwable t) {
                //listener.onError("Request Failed");
            }
        });
    }

    public void deleteNoteAPI(Notes notes, Context context) {
        CallUser callNote = retrofit.create(CallUser.class);
        callNote.deleteNote(notes.getId()).enqueue(new Callback<Notes>() {
            @Override
            public void onResponse(Call<Notes> call, Response<Notes> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                //listener.onFetchData(response.body(), response.message());
                Toast.makeText(context, "delete success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Notes> call, Throwable t) {
                //listener.onError("Request Failed");
            }
        });
    }

    public void getNoteByIdAPI(int id, com.example.infinityenglish.control.rest.Callback callback) {
        CallUser callNote = retrofit.create(CallUser.class);
        callNote.getNoteById(id).enqueue(new Callback<List<Notes>>() {
            @Override
            public void onResponse(Call<List<Notes>> call, Response<List<Notes>> response) {
                callback.getNotes(response.body());
            }

            @Override
            public void onFailure(Call<List<Notes>> call, Throwable t) {

            }
        });
    }

    public interface CallUser {
        @GET("/user/get-all")
        Call<List<Users>> getAllUser();

        @POST("/user/save")
        Call<Users> save(@Body Users users);

        @GET("/note/get-all")
        Call<List<Notes>> getAllNote();

        @POST("/note/save")
        Call<Notes> save(@Body Notes notes);

        @GET("/note/get-by-id/{id}")
        Call<List<Notes>> getNoteById(@Path("id") int id);

        @POST("/note/delete/{id}")
        Call<Notes> deleteNote(@Path("id") int id);
    }
}
