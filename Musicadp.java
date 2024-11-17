package com.example.beat_box;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Musicadp extends RecyclerView.Adapter<Musicadp.MusicViewHolder> {
    private List<String> files;
    private Context context;
    private OnItemClickListener li;
    private List<Integer> filesimage;
    public interface OnItemClickListener{
        void onItemClick(int pos,String file);
    }
    public void setOnItemClickListener(OnItemClickListener li){
        this.li=li;
    }
    public Musicadp(List<String> files, List<Integer> filesimage, Context context) {
        this.files=files;
        this.filesimage=filesimage;
        this.context=context;
    }

    @NonNull
    @Override
    public Musicadp.MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.music_main,parent,false);
        return new MusicViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Musicadp.MusicViewHolder holder, int position) {
        String s=files.get(position);
        holder.text.setText(s);
        int imageid=filesimage.get(position);
        holder.img.setImageResource(imageid);

        //Imagebutton fixing
        SharedPreferences shared = context.getSharedPreferences("Music", Context.MODE_PRIVATE);
        boolean isLiked = shared.getBoolean("liked_" + s, false);

        if (isLiked) {
            holder.imgbtn.setImageResource(R.drawable.fav2);
        } else {
            holder.imgbtn.setImageResource(R.drawable.fav1);
        }

        // Handle click events for the like button
        holder.imgbtn.setOnClickListener(view -> {
            boolean currentState = shared.getBoolean("liked_" + s, false);
            SharedPreferences.Editor editor = shared.edit();

            if (currentState) {
                holder.imgbtn.setImageResource(R.drawable.fav1);
                editor.putBoolean("liked_" + s, false);
            } else {
                // Like
                holder.imgbtn.setImageResource(R.drawable.fav2);
                editor.putBoolean("liked_" + s, true);
            }
            editor.apply();
        });

        //
        holder.itemView.setOnClickListener(v->{
           // SharedPreferences shared=context.getSharedPreferences("Music",Context.MODE_PRIVATE);
            String urls=shared.getString(String.valueOf(files.get(position)),null);
          // Toast.makeText(context,String.valueOf(files.get(position)),Toast.LENGTH_SHORT).show();
            String title=String.valueOf(files.get(position));

            Toast.makeText(context,title,Toast.LENGTH_SHORT).show();

            if(title=="YuvanSongs"){
                u1songs u5=new u1songs();
                FragmentTransaction t=((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                t.replace(R.id.fragmentContainerView,u5);
                t.addToBackStack(null);
                t.commit();
            }
            else if(title=="AR Songs"){
                ARsongs u1=new ARsongs();
                FragmentTransaction t=((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                t.replace(R.id.fragmentContainerView,u1);
                t.addToBackStack(null);
                t.commit();
            }
            else if(title=="Anirudh Songs"){
                Anirudhsongs u2=new Anirudhsongs();
                FragmentTransaction t=((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                t.replace(R.id.fragmentContainerView,u2);
                t.addToBackStack(null);
                t.commit();
            }
            else if(title=="Harris"){
                Harrissongs u3=new Harrissongs();
                FragmentTransaction t=((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                t.replace(R.id.fragmentContainerView,u3);
                t.addToBackStack(null);
                t.commit();

            }
            else{
                othersongs u4=new othersongs();
                FragmentTransaction t=((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                t.replace(R.id.fragmentContainerView,u4);
                t.addToBackStack(null);
                t.commit();
            }
           /*if(urls!=null){

                Bundle b=new Bundle();
                b.putString("title",title);
                b.putString("key",urls);
                PlayerActivity player=new PlayerActivity();
                player.setArguments(b);

                FragmentTransaction t=((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                t.replace(R.id.fragmentContainerView,player);
                t.addToBackStack(null);
                t.commit();*/

              /*Intent in=new Intent(context,PlayerActivity.class);
                in.putExtra("key",urls);
                context.startActivity(in);*/
            //}
            /*else{
                Toast.makeText(context,"Not Found",Toast.LENGTH_SHORT).show();
            }*/
            RecyclerView recyclerView=(RecyclerView)holder.itemView.getParent();
            int itemwidth=recyclerView.getWidth()/4;
            holder.itemView.getLayoutParams().width=itemwidth;
        });

    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public class MusicViewHolder extends RecyclerView.ViewHolder {
        private TextView text;
        private ImageView img;
        private ImageButton imgbtn;
        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);
            text=itemView.findViewById(R.id.textView);
            img=itemView.findViewById(R.id.item_image);
            imgbtn=itemView.findViewById(R.id.imgbt);
            itemView.setOnClickListener(this::onClick);
        }

        private void onClick(View v) {
            if (li != null) {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    li.onItemClick(pos,files.get(pos));
                }
            }

        }
    }
}
