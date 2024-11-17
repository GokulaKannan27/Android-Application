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

public class othersongs extends Fragment {
    private RecyclerView r;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.other_main,container,false);
        r=v.findViewById(R.id.reuse);
        r.setLayoutManager(new LinearLayoutManager(getContext()));
        List<String> s= Arrays.asList("Aathichoodi","AyeAyeAye","Thala Kothum","Kathi","Ram life","Manithan","Nee Kavithaigala","otha Solaala","Vaadi Pulla Vaadi");
        List<Integer> i=Arrays.asList(R.drawable.aathi,R.drawable.aye,R.drawable.thala,R.drawable.katthi,R.drawable.ram,R.drawable.aval,R.drawable.nee,R.drawable.solla,R.drawable.vaadi);
        songadp adp=new songadp(s,i,getContext());
        r.setAdapter(adp);
        return v;
    }
}
