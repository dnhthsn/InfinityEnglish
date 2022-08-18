package com.example.infinityenglish.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinityenglish.databinding.ItemDefinitionsBinding;
import com.example.infinityenglish.models.Definitions;
import com.example.infinityenglish.util.Const;

import java.util.List;

public class DefinitionAdapter extends RecyclerView.Adapter<DefinitionAdapter.DefinitionViewHolder> {
    private List<Definitions> definitions;

    public void setDefinitions(List<Definitions> definitions) {
        this.definitions = definitions;
    }

    @NonNull
    @Override
    public DefinitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDefinitionsBinding binding = ItemDefinitionsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DefinitionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DefinitionViewHolder holder, int position) {
        holder.binding.definition.setText(definitions.get(position).getDefinition());
        holder.binding.example.setText(definitions.get(position).getExample());
        StringBuilder synonym = new StringBuilder();
        StringBuilder antonym = new StringBuilder();

        if (definitions.get(position).getSynonyms().isEmpty()) {
            synonym.append(Const.Error.noSynonym);
        } else {
            synonym.append(definitions.get(position).getSynonyms());
        }

        if (definitions.get(position).getAntonyms().isEmpty()) {
            antonym.append(Const.Error.noAntonym);
        } else {
            antonym.append(definitions.get(position).getAntonyms());
        }

        holder.binding.synonyms.setText(synonym);

        holder.binding.antonyms.setText(antonym);

        holder.binding.synonyms.setSelected(true);
        holder.binding.antonyms.setSelected(true);
    }

    @Override
    public int getItemCount() {
        return definitions.size();
    }

    public class DefinitionViewHolder extends RecyclerView.ViewHolder {
        private ItemDefinitionsBinding binding;

        public DefinitionViewHolder(ItemDefinitionsBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
