package com.example.infinityenglish.view.activity;

import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;

import com.example.infinityenglish.R;
import com.example.infinityenglish.databinding.ActivityPracticeSpeakingBinding;
import com.example.infinityenglish.util.Utility;
import com.example.infinityenglish.view.base.BaseActivity;

import java.util.Locale;

public class PracticeSpeakingActivity extends BaseActivity {
    private final float pitch = -1;
    private final float speechRate = -1;

    private ActivityPracticeSpeakingBinding binding;

    private TextToSpeech textToSpeech;

    public static void starter(Context context) {
        Intent intent = new Intent(context, PracticeSpeakingActivity.class);
        context.startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_speaking);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_practice_speaking);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });

        binding.speakingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.Keyboard.hideKeyBoard(PracticeSpeakingActivity.this);
            }
        });

        binding.clickBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity.starter(PracticeSpeakingActivity.this);
                finish();
            }
        });

        binding.clickClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                binding.inputText.setText("");
                textToSpeech.stop();
            }
        });

        binding.clickSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech.setPitch(pitch);
                textToSpeech.setSpeechRate(speechRate);
                String toSpeak = binding.inputText.getText().toString();
                textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    public void onPause() {
        if (textToSpeech != null) {
            textToSpeech.stop();
        }
        super.onPause();
    }
}