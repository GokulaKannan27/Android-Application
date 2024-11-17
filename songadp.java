package com.example.beat_box;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class songadp extends RecyclerView.Adapter<songadp.MyViewHolder> {
    private List<String > s;
    private Context context;
    private List<Integer> i;
    private OnItemClickListener li;
    public interface OnItemClickListener{
        void onItemClick(int pos,String file);
    }
    public void setOnItemClickListener(OnItemClickListener li){
        this.li=li;
    }
    public songadp(List<String> s, List<Integer> i, Context context) {
    this.context=context;
    this.i=i;
    this.s=s;
    }

    @NonNull
    @Override
    public songadp.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.u1_songs,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull songadp.MyViewHolder holder, int position) {
        String s1=s.get(position);
        holder.t.setText(s1);
        Integer in=i.get(position);
        holder.im.setImageResource(in);

        SharedPreferences shared = context.getSharedPreferences("Music", Context.MODE_PRIVATE);
        boolean isLiked = shared.getBoolean("liked_" + s1, false);

        if (isLiked) {
            holder.imgbtn.setImageResource(R.drawable.fav2);
        } else {
            holder.imgbtn.setImageResource(R.drawable.fav1);
        }

        // Handle click events for the like button
        holder.imgbtn.setOnClickListener(view -> {
            boolean currentState = shared.getBoolean("liked_" + s1, false);
            SharedPreferences.Editor editor = shared.edit();

            if (currentState) {
                holder.imgbtn.setImageResource(R.drawable.fav1);
                editor.putBoolean("liked_" + s1, false);
            } else {
                // Like
                holder.imgbtn.setImageResource(R.drawable.fav2);
                editor.putBoolean("liked_" + s1, true);
            }
            editor.apply();
        });
        //Toast.makeText(context,s1,Toast.LENGTH_SHORT).show();




        holder.itemView.setOnClickListener(view -> {
            //SharedPreferences shared=context.getSharedPreferences("Music",Context.MODE_PRIVATE);
            String urls=shared.getString(String.valueOf(s.get(position)),null);
            String title=String.valueOf(s.get(position));
            Integer img=i.get(position);

            if(urls!=null) {

                Bundle b = new Bundle();
                b.putInt("image",img);
                b.putString("title", title);
                b.putString("key", urls);
                PlayerActivity player = new PlayerActivity();
                player.setArguments(b);

                FragmentTransaction t = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                t.replace(R.id.fragmentContainerView, player);
                t.addToBackStack(null);
                t.commit();
            }
            else{
                Toast.makeText(context,"Not Found",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return s.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView t;
        private ImageView im;
        private ImageButton imgbtn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgbtn=itemView.findViewById(R.id.imgbt1);
            im=itemView.findViewById(R.id.item_image1);
            t=itemView.findViewById(R.id.textView2);


        }
    }
}
