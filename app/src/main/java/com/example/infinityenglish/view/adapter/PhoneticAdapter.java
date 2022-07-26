package com.example.infinityenglish.view.adapter;

import android.annotation.SuppressLint;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinityenglish.R;
import com.example.infinityenglish.databinding.ItemPhoneticsBinding;
import com.example.infinityenglish.models.Phonetics;

import java.io.IOException;
import java.util.List;

public class PhoneticAdapter extends RecyclerView.Adapter<PhoneticAdapter.PhoneticViewHolder> {
    private List<Phonetics> phonetics;

    public void setPhonetics(List<Phonetics> phonetics) {
        this.phonetics = phonetics;
    }

    @NonNull
    @Override
    public PhoneticViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPhoneticsBinding binding = ItemPhoneticsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PhoneticViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneticViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.phonetic.setText(phonetics.get(position).getText());
        holder.binding.playAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String audioUrl = phonetics.get(position).getAudio();
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaPlayer.setDataSource(audioUrl);
                    mediaPlayer.prepare();
                    mediaPlayer.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return phonetics.size();
    }

    public class PhoneticViewHolder extends RecyclerView.ViewHolder {
        private ItemPhoneticsBinding binding;
        public PhoneticViewHolder(ItemPhoneticsBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
