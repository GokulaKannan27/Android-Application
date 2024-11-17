package com.example.beat_box;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private List<Song> searchresult;
    private FragmentManager fm;


    public SearchAdapter(List<Song> searchresult, FragmentManager fm) {
        this.searchresult=searchresult;
        this.fm=fm;
    }


    @NonNull
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent,false);
        return new SearchViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, int position) {
        Song result=searchresult.get(position);
        holder.text.setText(result.getTitle());
        holder.text.setTextColor(Color.parseColor("#FF000000"));

        String v1 = result.getUrl();

        holder.itemView.setOnClickListener(v -> {
            if (v1 == null || v1.isEmpty()) {
                Toast.makeText(v.getContext(), "URL is invalid", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                Bundle bundle = new Bundle();
                bundle.putString("key", v1);

                PlayerActivity player = new PlayerActivity(); // Ensure PlayerActivity is a Fragment
                player.setArguments(bundle);

                FragmentTransaction t = fm.beginTransaction();
                t.replace(R.id.fragmentContainerView, player);
                t.addToBackStack(null);
                t.commit();
            } catch (Exception e) {
                Toast.makeText(v.getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchresult.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        TextView texts;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            text=itemView.findViewById(android.R.id.text1);
            texts=itemView.findViewById(android.R.id.text2);
        }
    }
}
