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

public class ARsongs extends Fragment {
    private RecyclerView r;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.ar_main,container,false);
        r=v.findViewById(R.id.reuse);
        r.setLayoutManager(new LinearLayoutManager(getContext()));
        List<String> s= Arrays.asList("Aalaporan_Thamizhan","Balleilakka","EnnaSollaPogirai","Maduraikku Pogatadi","Mukkala Mukkabala","Nee Singham Dhan","Oruvan oruvan","usurey Poguthu","Ella Pugazham","Urvasi Urvasi");
        List<Integer> i=Arrays.asList(R.drawable.alapora,R.drawable.balleilakka,R.drawable.enna_solla_pogiraai,R.drawable.maduraikku,R.drawable.muqabla,R.drawable.neesingham,R.drawable.oruvan,R.drawable.usure,R.drawable.ellapuga,R.drawable.urvasi);
        songadp adp=new songadp(s,i,getContext());
        r.setAdapter(adp);
        return v;
    }
}
