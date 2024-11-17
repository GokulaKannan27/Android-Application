package com.example.beat_box;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends Fragment {
    private View v;
    private FirebaseFirestore db;
    private SearchView searchv;
    private RecyclerView recycle;
    private EditText searchEdit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.search_main,container,false);

        db=FirebaseFirestore.getInstance();
        searchv=v.findViewById(R.id.searchview);
        recycle=v.findViewById(R.id.recyclerview1);
        searchEdit=searchv.findViewById(androidx.appcompat.R.id.search_src_text);
     //   searchEdit.setTextColor(Color.parseColor("#FF000000"));

        //List<String> searchresult= Arrays.asList("Music1","Music2","Music3");
        //SearchAdapter adp=new SearchAdapter(searchresult,getChildFragmentManager());
        recycle.setLayoutManager(new LinearLayoutManager(getContext()));
        //recycle.setAdapter(adp);

        searchv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(!TextUtils.isEmpty(s)){
                    searchmusic(s);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!TextUtils.isEmpty(s)){
                    searchmusic(s);
                }
                return false;
            }
        });
        return v;
    }

    private void searchmusic(String s) {
        db.collection("songs")
                .whereEqualTo("songTitle", s)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Song> resultList = new ArrayList<>();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        //resultList.add(doc.getString("songTitle"));
                        String title=doc.getString("songTitle");
                        String url=doc.getString("Link");
                        if(title!=null&&url!=null) {
                            resultList.add(new Song(title, url));
                        }
                    }
                    // Update RecyclerView with search results
                    SearchAdapter adp=new SearchAdapter(resultList,getChildFragmentManager());
                    //SearchAdapter adp = new SearchAdapter(resultList);
                    recycle.setAdapter(adp);

                });
    ///
    }
}
