package com.example.infinityenglish.view.base;

import androidx.appcompat.app.AppCompatActivity;

import com.example.infinityenglish.util.Executor;

import java.util.concurrent.ExecutorService;

public class BaseActivity extends AppCompatActivity {
    protected ExecutorService executorService;

    public BaseActivity() {
        this.executorService = Executor.getInstance();
    }
}
