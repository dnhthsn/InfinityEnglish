package com.example.infinityenglish.control.remote;

import androidx.annotation.NonNull;

import com.example.infinityenglish.models.APIResponse;
import com.example.infinityenglish.models.MessageModel;

import java.io.IOException;
import java.util.List;
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
import retrofit2.http.Url;

public class RequestChatBotManager {
    private Retrofit retrofit;
    private OkHttpClient okHttpClient;

    public RequestChatBotManager() {
        this.okHttpClient = new OkHttpClient.Builder()
                .callTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(new CustomInterceptor())
                .addNetworkInterceptor(new CustomInterceptor())
                .build();
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://api.brainshop.ai/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public void getChatMessage(OnFetchDataListener listener, String message) {
        String url = "http://api.brainshop.ai/get?bid=160167&key=8h8vRUhkZo5zyBrO&uid=[uid]&msg="+message;
        RetroFitApi retroFitApi = retrofit.create(RetroFitApi.class);
        Call<MessageModel> call = retroFitApi.getMessage(url);
        call.enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                if(response.isSuccessful()){
                    MessageModel messageModel = response.body();
                    listener.onFetchData(messageModel, response.message());
                }
            }

            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {
                listener.onError("No response");
            }
        });
    }

    public interface RetroFitApi {
        @GET
        Call<MessageModel> getMessage(@Url String url);
    }

    public interface OnFetchDataListener {
        void onFetchData(MessageModel messageModel, String message);

        void onError(String message);
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
