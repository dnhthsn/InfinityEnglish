package com.example.infinityenglish.control.remote;

import android.content.Context;
import android.widget.Toast;

import com.example.infinityenglish.models.Users;
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

public class RequestUserManager {
    private Retrofit retrofit;

    public RequestUserManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.28.72:9000")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    public void getUserAPI(OnFetchDataListener listener) {
        CallUser callUser = retrofit.create(CallUser.class);
        Call<List<Users>> call = callUser.getAllUser();
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

    public interface CallUser {
        @GET("/user/get-all")
        Call<List<Users>> getAllUser();

        @POST("/user/save")
        Call<Users> save(@Body Users users);
    }

    public interface OnFetchDataListener {
        void onFetchData(Object listWords, String message);

        void onError(String message);
    }
}
