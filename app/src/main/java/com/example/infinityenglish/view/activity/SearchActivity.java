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
import com.example.infinityenglish.databinding.ActivitySearchBinding;
import com.example.infinityenglish.models.APIResponse;
import com.example.infinityenglish.util.Const;
import com.example.infinityenglish.util.Utility;
import com.example.infinityenglish.view.adapter.MeaningAdapter;
import com.example.infinityenglish.view.adapter.PhoneticAdapter;
import com.example.infinityenglish.view.base.BaseActivity;
import com.example.infinityenglish.viewmodel.WordViewModel;

public class SearchActivity extends BaseActivity implements RequestEnglishManager.OnFetchDataListener, RequestRandomManager.OnFetchRandomDataListener {
    private ActivitySearchBinding binding;
    private WordViewModel wordViewModel;

    private ProgressDialog progressDialog;

    private PhoneticAdapter phoneticAdapter;
    private MeaningAdapter meaningAdapter;

    public static void starter(Context context, Bundle bundle) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        binding = DataBindingUtil.setContentView(SearchActivity.this, R.layout.activity_search);

        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        wordViewModel.init(this);

        progressDialog = new ProgressDialog(this);

        progressDialog.setTitle("Loading...");
        progressDialog.show();

        String word = getIntent().getStringExtra(Const.Sender.word);
        String query = getIntent().getStringExtra(Const.Sender.searchQuery);
        String random = wordViewModel.getSavedWord();

        String word1 = query == null ? random : query;
        String word2 = word == null ? word1 : word;

        binding.word.setText(word2);
        wordViewModel.getWordMeanings(SearchActivity.this, word2);
        wordViewModel.getRandomWord(SearchActivity.this);

        if (query != null) {
            wordViewModel.addWordSearched(query);
        }

        binding.clickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.starter(SearchActivity.this);
                finish();
            }
        });
    }

    private void showData(APIResponse apiResponse) {
        binding.word.setText(apiResponse.getWord());

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
    public void onFetchData(APIResponse apiResponse, String message) {
        progressDialog.dismiss();
        if (apiResponse == null) {
            Utility.Notice.snack(binding.searchLayout, Const.Error.noData);
            return;
        } else {
            showData(apiResponse);
        }
    }

    @Override
    public void onError(String message) {
        progressDialog.dismiss();
        Utility.Notice.snack(binding.searchLayout, message);
    }

    private void showDataRandom(Object listWords) {
        String word = listWords.toString();
        word = word.replaceAll(Const.Regex.randomWord, "");
        wordViewModel.saveWord(word);
    }

    @Override
    public void onFetchRandomData(Object listWords, String message) {
        progressDialog.dismiss();
        if (listWords == null) {
            Utility.Notice.snack(getCurrentFocus(), Const.Error.noData);
            return;
        }

        showDataRandom(listWords);
    }

    @Override
    public void onRandomError(String message) {
        progressDialog.dismiss();
    }
}