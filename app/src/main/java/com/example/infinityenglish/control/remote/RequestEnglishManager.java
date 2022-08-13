package com.example.infinityenglish.control.remote;

import androidx.annotation.NonNull;

import com.example.infinityenglish.models.APIResponse;

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
import retrofit2.http.Path;

public class RequestEnglishManager {
    private Retrofit retrofit;
    private OkHttpClient okHttpClient;

    public RequestEnglishManager() {
        this.okHttpClient = new OkHttpClient.Builder()
                .callTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(new CustomInterceptor())
                .addNetworkInterceptor(new CustomInterceptor())
                .build();
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://api.dictionaryapi.dev/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
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

    private static class CustomInterceptor implements Interceptor {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {

            Request request = chain.request();

            okhttp3.Response response = chain.proceed(request);

            return response;
        }
    }
}
