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

public class u1songs extends Fragment {
    private RecyclerView r;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.u1_main,container,false);
        r=v.findViewById(R.id.recyclerview12);
        r.setLayoutManager(new LinearLayoutManager(getContext()));
        List<String> s= Arrays.asList("Arabu Naade","En Kadhal Solla Neram Illai","Kadhal Vaithu","Kadhal Valarthen","Kanpesum Varthaigal","Loosu Penne","Oru Devathai Parkum Neram","Oru kal Oru Kannadi","venmegam Pennaga");
        List<Integer> i=Arrays.asList(R.drawable.arabu_naade,R.drawable.en_kadhal_solla,R.drawable.kadhal_vaithu,R.drawable.kadhal_valarthen,R.drawable.kan_pesum_varthaigal,R.drawable.loosu_penne,R.drawable.oru_devathai,R.drawable.oru_kal_oru_kannadi,R.drawable.venmegam);
        songadp adp=new songadp(s,i,getContext());
        r.setAdapter(adp);
        return v;
    }
}
