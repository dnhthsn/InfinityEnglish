package com.example.infinityenglish.view.activity;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.infinityenglish.R;
import com.example.infinityenglish.control.remote.RequestEnglishManager;
import com.example.infinityenglish.control.remote.RequestRandomManager;
import com.example.infinityenglish.databinding.ActivityRandomBinding;
import com.example.infinityenglish.models.APIResponse;
import com.example.infinityenglish.models.ListWords;
import com.example.infinityenglish.util.Const;
import com.example.infinityenglish.util.Utility;
import com.example.infinityenglish.view.adapter.MeaningAdapter;
import com.example.infinityenglish.view.adapter.PhoneticAdapter;
import com.example.infinityenglish.view.base.BaseActivity;
import com.example.infinityenglish.viewmodel.WordViewModel;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RandomActivity extends BaseActivity implements RequestRandomManager.OnFetchRandomDataListener, RequestEnglishManager.OnFetchDataListener {
    private ActivityRandomBinding binding;
    private WordViewModel wordViewModel;

    private ProgressDialog progressDialog;

    private PhoneticAdapter phoneticAdapter;
    private MeaningAdapter meaningAdapter;

    public static void starter(Context context) {
        Intent intent = new Intent(context, RandomActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_random);
        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        wordViewModel.init(this);

        progressDialog = new ProgressDialog(this);

        progressDialog.setTitle("Loading...");
        progressDialog.show();

        wordViewModel.getRandomWord(RandomActivity.this, "");

        binding.clickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void showData(APIResponse apiResponse) {
        binding.word.setText("Word: " + apiResponse.getWord());

        binding.phoneticsList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        binding.phoneticsList.setLayoutManager(layoutManager);
        phoneticAdapter = new PhoneticAdapter();
        phoneticAdapter.setPhonetics(apiResponse.getPhonetics());
        binding.phoneticsList.setAdapter(phoneticAdapter);

        binding.meaningsList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        binding.meaningsList.setLayoutManager(layoutManager1);
        meaningAdapter = new MeaningAdapter();
        meaningAdapter.setMeanings(apiResponse.getMeanings());
        binding.meaningsList.setAdapter(meaningAdapter);
    }

    @Override
    public void onFetchRandomData(Object listWords, String message) {
        progressDialog.dismiss();
        if (listWords == null) {
            Utility.Notice.snack(getCurrentFocus(), Const.Error.noData);
            return;
        }

        String word = listWords.toString();
        word = word.replaceAll(Const.Regex.randomWord, "");
        wordViewModel.getWordMeanings(RandomActivity.this, word);
    }

    @Override
    public void onRandomError(String message) {
        progressDialog.dismiss();
    }

    @Override
    public void onFetchData(APIResponse apiResponse, String message) {
        progressDialog.dismiss();
        if (apiResponse == null) {
            Utility.Notice.snack(getCurrentFocus(), Const.Error.noData);
            return;
        }
        showData(apiResponse);
    }

    @Override
    public void onError(String message) {
        progressDialog.dismiss();
        Utility.Notice.snack(binding.randomLayout, message);
    }
}