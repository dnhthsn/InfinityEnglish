package com.example.infinityenglish.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.infinityenglish.R;
import com.example.infinityenglish.databinding.ActivityPracticeSpeakingBinding;

import java.util.Locale;

public class PracticeSpeakingActivity extends AppCompatActivity {
    private ActivityPracticeSpeakingBinding binding;

    private float pitch = -1;
    private float speechRate = -1;
    private TextToSpeech ttobj;

    public static void starter(Context context) {
        Intent intent = new Intent(context, PracticeSpeakingActivity.class);
        context.startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_speaking);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_practice_speaking);

        ttobj = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    public void onInit(int status) {
                        if (status != TextToSpeech.ERROR) {
                            ttobj.setLanguage(Locale.US);
                        }
                    }
                });
        binding.clickBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }

        });

        binding.clickClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                binding.inputText.setText("");
                ttobj.stop();
            }
        });

        binding.clickSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ttobj.setPitch(pitch);
                ttobj.setSpeechRate(speechRate);
                String toSpeak = binding.inputText.getText().toString();
                ttobj.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    public void onPause() {
        if (ttobj != null) {
            ttobj.stop();
        }
        super.onPause();
    }
}