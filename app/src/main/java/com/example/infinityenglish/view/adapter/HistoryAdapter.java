package com.example.infinityenglish.view.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinityenglish.control.Repository;
import com.example.infinityenglish.databinding.ItemHistoriesBinding;
import com.example.infinityenglish.models.Histories;
import com.example.infinityenglish.util.Const;
import com.example.infinityenglish.view.activity.SearchActivity;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private List<Histories> histories;

    public void setHistories(List<Histories> histories) {
        this.histories = histories;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHistoriesBinding binding = ItemHistoriesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new HistoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Repository repository = new Repository(holder.binding.getRoot().getContext());
        holder.binding.wordInput.setText(histories.get(position).getWordInput());
        holder.binding.deleteWordSearched.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                repository.deleteWordHistory(histories.get(position).getWordInput(), holder.binding.getRoot());
                histories.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.binding.wordInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(holder.binding.getRoot().getContext(), SearchActivity.class);
                i.putExtra(Const.Sender.word, histories.get(position).getWordInput());
                holder.binding.getRoot().getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        private ItemHistoriesBinding binding;
        public HistoryViewHolder(ItemHistoriesBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
