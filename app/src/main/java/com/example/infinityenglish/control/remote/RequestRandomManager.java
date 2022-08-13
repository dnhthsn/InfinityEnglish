package com.example.infinityenglish.control.remote;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class RequestRandomManager {
    private Retrofit retrofit;
    private OkHttpClient okHttpClient;

    public RequestRandomManager() {
        this.okHttpClient = new OkHttpClient.Builder()
                .callTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(new CustomInterceptor())
                .addNetworkInterceptor(new CustomInterceptor())
                .build();
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://random-word-api.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public void getRandomWord(OnFetchRandomDataListener listener) {
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

    private static class CustomInterceptor implements Interceptor {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {

            Request request = chain.request();

            okhttp3.Response response = chain.proceed(request);

            return response;
        }
    }
}
