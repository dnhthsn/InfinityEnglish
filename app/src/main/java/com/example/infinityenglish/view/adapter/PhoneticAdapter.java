package com.example.infinityenglish.view.adapter;

import android.annotation.SuppressLint;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinityenglish.R;
import com.example.infinityenglish.databinding.ItemPhoneticsBinding;
import com.example.infinityenglish.models.Phonetics;
import com.example.infinityenglish.util.Utility;

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

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull PhoneticViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (!TextUtils.isEmpty(phonetics.get(position).getText())){
            holder.binding.phonetic.setText(phonetics.get(position).getText());
            holder.binding.playAudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String audioUrl = phonetics.get(position).getAudio();
                    Utility.Player.playAudio(audioUrl, holder.binding.getRoot());
                }
            });
        } else {
            holder.binding.playAudio.getLayoutParams().height = 0;
            holder.binding.phonetic.getLayoutParams().height = 0;
            holder.binding.phoneticsLayout.getLayoutParams().height = 0;
        }
    }

    @Override
    public int getItemCount() {
        if (phonetics != null){
            return phonetics.size();
        }
        return 0;
    }

    public class PhoneticViewHolder extends RecyclerView.ViewHolder {
        private ItemPhoneticsBinding binding;

        public PhoneticViewHolder(ItemPhoneticsBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
