package com.example.beat_box;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class Harrissongs extends Fragment {
    private RecyclerView r;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.harris_main,container,false);
        r=v.findViewById(R.id.reuse);
        r.setLayoutManager(new LinearLayoutManager(getContext()));
        List<String> s= Arrays.asList("Aathangara orathil","Anbe Anbe","Enna thedivantha Anjala","Ennamo yeadho",
                "Pala Palakura","Roja kadalae","Vennam Machan","Vizhi Moodi","Venmathi Venmathiye","Yamma Yamma");
        List<Integer> i=Arrays.asList(R.drawable.aathangara,R.drawable.anbe,R.drawable.others,R.drawable.ennamo
        ,R.drawable.pala,R.drawable.roja,R.drawable.venaam,R.drawable.vizhi,R.drawable.venmathi,R.drawable.yamma);
        songadp adp=new songadp(s,i,getContext());
        r.setAdapter(adp);
        return v;
    }
}
