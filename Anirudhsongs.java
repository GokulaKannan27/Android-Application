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

public class Anirudhsongs extends Fragment {
    private RecyclerView r;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.ani_main,container,false);
        r=v.findViewById(R.id.reuse);
        r.setLayoutManager(new LinearLayoutManager(getContext()));
        List<String> s= Arrays.asList("Boomi Enna Suthudhe","Dippam Dappam","Hukum","Illamai Thirubudhe","Donu Donu","Manasilaayo","Osaka Osaka","Selfie pulla","vaathi Coming","Why This Kolaveri");
        List<Integer> i=Arrays.asList(R.drawable.boomi,R.drawable.dippam,R.drawable.hukum,R.drawable.ilamai,R.drawable.don,R.drawable.manasu,R.drawable.osaka,R.drawable.selfie,R.drawable.vaathi,R.drawable.why);
        songadp adp=new songadp(s,i,getContext());
        r.setAdapter(adp);
        return v;
    }
}
