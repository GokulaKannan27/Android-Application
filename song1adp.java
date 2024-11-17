package com.example.beat_box;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class song1adp extends RecyclerView.Adapter<song1adp.My1ViewHolder> {
    private List<String > s;
    private Context context;
    private List<Integer> i;
    public song1adp(List<String> s, List<Integer> i, Context context) {
        this.context=context;
        this.i=i;
        this.s=s;
    }

    @NonNull
    @Override
    public song1adp.My1ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.ani_songs,parent,false);
        return new My1ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull song1adp.My1ViewHolder holder, int position) {
        String s1=s.get(position);
        holder.t1.setText(s1);
        Integer in=i.get(position);
        holder.im1.setImageResource(in);
        Toast.makeText(context,s1,Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getItemCount() {
        return s.size();
    }

    public class My1ViewHolder extends RecyclerView.ViewHolder {
        private TextView t1;
        private ImageView im1;
        public My1ViewHolder(@NonNull View itemView) {
            super(itemView);
            im1=itemView.findViewById(R.id.item_image1);
            t1=itemView.findViewById(R.id.textView2);
        }
    }
}
