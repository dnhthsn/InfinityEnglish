package com.example.infinityenglish.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinityenglish.databinding.ItemMeaningsBinding;
import com.example.infinityenglish.models.Meanings;

import java.util.List;

public class MeaningAdapter extends RecyclerView.Adapter<MeaningAdapter.MeaningViewHolder> {
    private List<Meanings> meanings;

    public void setMeanings(List<Meanings> meanings) {
        this.meanings = meanings;
    }

    @NonNull
    @Override
    public MeaningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMeaningsBinding binding = ItemMeaningsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MeaningViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MeaningViewHolder holder, int position) {
        holder.binding.partOfSpeech.setText("Parts of speech: " + meanings.get(position).getPartOfSpeech());
        holder.binding.definitionList.setHasFixedSize(true);
        holder.binding.definitionList.setLayoutManager(new GridLayoutManager(holder.binding.getRoot().getContext(), 1, GridLayoutManager.VERTICAL, false));
        DefinitionAdapter definitionAdapter = new DefinitionAdapter();
        definitionAdapter.setDefinitions(meanings.get(position).getDefinitions());
        holder.binding.definitionList.setAdapter(definitionAdapter);
    }

    @Override
    public int getItemCount() {
        return meanings.size();
    }

    public class MeaningViewHolder extends RecyclerView.ViewHolder {
        private ItemMeaningsBinding binding;
        public MeaningViewHolder(ItemMeaningsBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
