package com.example.infinityenglish.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import com.example.infinityenglish.control.remote.RequestManager;
import com.example.infinityenglish.databinding.ActivitySearchBinding;
import com.example.infinityenglish.models.APIResponse;
import com.example.infinityenglish.models.Histories;
import com.example.infinityenglish.util.Const;
import com.example.infinityenglish.util.Utility;
import com.example.infinityenglish.view.adapter.MeaningAdapter;
import com.example.infinityenglish.view.adapter.PhoneticAdapter;
import com.example.infinityenglish.viewmodel.WordViewModel;

public class SearchActivity extends AppCompatActivity implements RequestManager.OnFetchDataListener {
    private ActivitySearchBinding binding;
    private WordViewModel wordViewModel;

    private ProgressDialog progressDialog;

    private PhoneticAdapter phoneticAdapter;
    private MeaningAdapter meaningAdapter;
    private boolean isBookmarked;

    public static void starter(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
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
        String word1 = word==null ? "hello":word;

        wordViewModel.getWordMeanings(SearchActivity.this, word1);

        isBookmarked = wordViewModel.getStateBookmarked();

        binding.clickBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBookmarked){
                    binding.clickBookmark.setImageResource(R.drawable.bookmarked);
                    wordViewModel.saveStateBookmark(isBookmarked);
                } else {
                    binding.clickBookmark.setImageResource(R.drawable.unbookmark);
                    wordViewModel.removeBookmarkedState();
                }
                isBookmarked = !isBookmarked;
            }
        });

        binding.inputSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressDialog.setTitle("Fetching response for: " + query);
                progressDialog.show();

                wordViewModel.getWordMeanings(SearchActivity.this, query);
                wordViewModel.addWordSearched(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        binding.clickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookmarkActivity.starter(SearchActivity.this);
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
    public void onFetchData(APIResponse apiResponse, String message) {
        progressDialog.dismiss();
        if (apiResponse == null){
            Utility.Notice.snack(getCurrentFocus(), Const.Error.noData);
            return;
        }
        showData(apiResponse);
    }

    @Override
    public void onError(String message) {
        progressDialog.dismiss();
        Utility.Notice.snack(getCurrentFocus(), message);
    }
}