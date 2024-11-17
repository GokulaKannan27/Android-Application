package com.example.beat_box;

import static android.content.Context.MODE_PRIVATE;
import static android.view.Gravity.*;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TotpSecret;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends Fragment {
    private Toolbar tool;
    private ImageView profile;
    private View v;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private S3UploadManager uploadManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.home_main,container,false);
        tool=v.findViewById(R.id.toolbar);
        profile=v.findViewById(R.id.imageView);
        recyclerView=v.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Imageview
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null && user.getPhotoUrl()!=null){
            Uri profileuri=user.getPhotoUrl();
            Glide.with(this).load(profileuri).circleCrop().error(R.drawable.rounded_border).into(profile);
        }

       profile.setOnClickListener(view -> openCamera());

        //Recycler View
       // List<String> files= Arrays.asList("https://github.com/GokulaKannan27/Hackthon-Accus-for-Buccus/raw/refs/heads/main/Aye-Aye-Aye-MassTamilan.fm.mp3",
         //       "https://github.com/GokulaKannan27/Hackthon-Accus-for-Buccus/raw/refs/heads/main/Aaruyire.mp3");

       /* List<String> files=Arrays.asList("Oru Naalil","Arabu Naade","Kadhal Solla Neram Illai","kadhal vaithu","Kadhal Valarthen","Kanpesum Varthaigal","Loosu Penne");
        List<Integer> filesimage=Arrays.asList(R.drawable.oru_naalil,R.drawable.arabu_naade,R.drawable.en_kadhal_solla,R.drawable.kadhal_vaithu,R.drawable.kadhal_valarthen,R.drawable.kan_pesum_varthaigal,R.drawable.loosu_penne);
        */
        List<String> files=Arrays.asList("YuvanSongs","AR Songs","Anirudh Songs","Harris","Others");
        List<Integer> filesimage=Arrays.asList(R.drawable.u1,R.drawable.ar,R.drawable.anirudh,R.drawable.harris,R.drawable.others);
        Musicadp adp=new Musicadp(files,filesimage,getContext());

        SharedPreferences sp= getContext().getSharedPreferences("Music",MODE_PRIVATE);
        SharedPreferences.Editor edit=sp.edit();
        //U1 Songs


        edit.putString("Oru Devathai Parkum Neram","https://github.com/Vikramr16/U1-Songs/raw/refs/heads/main/Oru%20Devathai%20Parukum%20Neram%20Ithu%20Song.mp3");
        edit.putString("Oru Kal Oru Kannadi","https://github.com/Vikramr16/U1-Songs/raw/refs/heads/main/Oru%20Kal%20Oru%20Kannadi%20Song.mp3");
        edit.putString("venmegam Pannaga","https://github.com/Vikramr16/U1-Songs/raw/refs/heads/main/Venmegam%20Pennaga%20Song.mp3");
        //SOngs
        edit.putString("Oru Naalil","https://github.com/Vikramr16/U1-Songs/raw/refs/heads/main/Oru%20Naalil%20Song.mp3");
        edit.putString("Arabu Naade","https://github.com/Vikramr16/U1-Songs/raw/refs/heads/main/Arabu%20Naade%20Song.mp3");
        edit.putString("En Kadhal Solla Neram Illai","https://github.com/Vikramr16/U1-Songs/raw/refs/heads/main/En%20Kadhal%20Solla%20Neram%20Illai%20Song.mp3");
        edit.putString("Kadhal Vaithu","https://github.com/Vikramr16/U1-Songs/raw/refs/heads/main/Kadhal%20Vaithu%20Song.mp3");
        edit.putString("Kadhal Valarthen","https://github.com/Vikramr16/U1-Songs/raw/refs/heads/main/Kadhal%20Valarthen%20Song.mp3");
        edit.putString("Kanpesum Varthaigal","https://github.com/Vikramr16/U1-Songs/raw/refs/heads/main/Kanpesum%20Varthaigal%20Song.mp3");
        edit.putString("Loosu Penne","https://github.com/Vikramr16/U1-Songs/raw/refs/heads/main/Loosu%20Penne%20Song.mp3");
        edit.putString("Balleilakka","https://github.com/Vikramr16/ARR-Songs/raw/refs/heads/main/Balleilakka%20Song.mp3");
        edit.putString("Aalaporan_Thamizhan","https://github.com/Vikramr16/ARR-Songs/raw/refs/heads/main/Aalaporan%20Thamizhan%20Song.mp3");
        edit.putString("EnnaSollaPogirai","https://github.com/Vikramr16/ARR-Songs/raw/refs/heads/main/Enna%20Solla%20Pogirai%20Song.mp3");
        edit.putString("Maduraikku Pogatadi","https://github.com/Vikramr16/ARR-Songs/raw/refs/heads/main/Maduraikku%20Pogathadi%20Song.mp3");
        edit.putString("Mukkala Mukkabala","https://github.com/Vikramr16/ARR-Songs/raw/refs/heads/main/Mukkala%20Mukkabala%20Song.mp3");
        edit.putString("Nee Singham Dhan","https://github.com/Vikramr16/ARR-Songs/raw/refs/heads/main/Nee%20Singam%20Dhan%20Song.mp3");
        edit.putString("Oruvan oruvan","https://github.com/Vikramr16/ARR-Songs/raw/refs/heads/main/Oruvan%20Oruvan%20Song.mp3");
        edit.putString("usurey Poguthu","https://github.com/Vikramr16/ARR-Songs/raw/refs/heads/main/Usurey%20poguthu%20Song.mp3");
        edit.putString("Ella Pugazham","https://github.com/Vikramr16/ARR-Songs/raw/refs/heads/main/Ella%20Pugazhum%20Oruvanuke%20song.mp3");
        edit.putString("Urvasi Urvasi","https://github.com/Vikramr16/ARR-Songs/raw/refs/heads/main/Urvasi%20Urvasi%20Song.mp3");
        edit.putString("Boomi Enna Suthudhe","https://github.com/Vikramr16/Anirudh-Songs/raw/refs/heads/main/Boomi%20Enna%20Suthudhe%20Song.mp3");
        edit.putString(  "Dippam Dappam","https://github.com/Vikramr16/Anirudh-Songs/raw/refs/heads/main/Dippam%20Dappam%20Song.mp3");
        edit.putString("Hukum","https://github.com/Vikramr16/Anirudh-Songs/raw/refs/heads/main/Hukum%20Title%20Song.mp3");
        edit.putString(  "Illamai Thirubudhe","https://github.com/Vikramr16/Anirudh-Songs/raw/refs/heads/main/Ilamai%20Thirumbudhe%20Song.mp3");
        edit.putString("Donu Donu","https://github.com/Vikramr16/Anirudh-Songs/raw/refs/heads/main/Maari%20Donu%20Donu%20Donu%20Song.mp3");
        edit.putString("Manasilaayo","https://github.com/Vikramr16/Anirudh-Songs/raw/refs/heads/main/Manasilaayo%20Song.mp3");
        edit.putString( "Osaka Osaka","https://github.com/Vikramr16/Anirudh-Songs/raw/refs/heads/main/Osaka%20Osaka%20Song.mp3");
        edit.putString("Selfie pulla","https://github.com/Vikramr16/Anirudh-Songs/raw/refs/heads/main/Selfie%20Pulla%20Song.mp3");
        edit.putString( "vaathi Coming","https://github.com/Vikramr16/Anirudh-Songs/raw/refs/heads/main/Vaathi%20Coming%20Song.mp3");
        edit.putString("Why This Kolaveri","https://github.com/Vikramr16/Anirudh-Songs/raw/refs/heads/main/Why%20This%20Kolaveri%20Song.mp3");

        //Harris Playlist

        edit.putString("Aathangara orathil","https://github.com/Vikramr16/Harris-Songs/raw/refs/heads/main/Aathangara%20Orathil%20Song.mp3");
        edit.putString("Anbe Anbe","https://github.com/Vikramr16/Harris-Songs/raw/refs/heads/main/Anbe%20En%20Anbe%20Song.mp3");
        edit.putString("Enna thedivantha Anjala","https://github.com/Vikramr16/Harris-Songs/raw/refs/heads/main/Ava%20Enna%20Enna%20thedivantha%20Anjala%20Song.mp3");
        edit.putString("Ennamo yeadho","https://github.com/Vikramr16/Harris-Songs/raw/refs/heads/main/Ennamo%20Yeadho%20Song.mp3");
        edit.putString("Pala Palakura","https://github.com/Vikramr16/Harris-Songs/raw/refs/heads/main/Pala%20Palakkura%20Song.mp3");
        edit.putString("Roja Kadalae","https://github.com/Vikramr16/Harris-Songs/raw/refs/heads/main/Roja%20Kadale%20Song.mp3");
        edit.putString("Vennam Machan","https://github.com/Vikramr16/Harris-Songs/raw/refs/heads/main/Venaam%20Machan%20Vena%20Song.mp3");
        edit.putString("Vizhi Moodi","https://github.com/Vikramr16/Harris-Songs/raw/refs/heads/main/Vizhi%20Moodi%20Song.mp3");
        edit.putString("Venmathi Venmathiye","https://github.com/Vikramr16/Harris-Songs/raw/refs/heads/main/Venmathi%20Venmathiye%20Song.mp3");
        edit.putString("Yamma Yamma","https://github.com/Vikramr16/Harris-Songs/raw/refs/heads/main/Yamma%20Yamma%20Song.mp3");


       //Others
    edit.putString("Aathichoodi","https://github.com/Vikramr16/Others-Songs/raw/refs/heads/main/Aathichoodi%20Song.mp3");
    edit.putString("AyeAyeAye","https://github.com/Vikramr16/Others-Songs/raw/refs/heads/main/Aye%20Aye%20Aye%20Song.mp3");
    edit.putString("Thala Kothum","https://github.com/Vikramr16/Others-Songs/raw/refs/heads/main/Jai%20Bhim%20-%20Thala%20Kodhum%20Song.mp3");
    edit.putString("Kaathu Mela","https://github.com/Vikramr16/Others-Songs/raw/refs/heads/main/Kaathu%20Mela%20Song.mp3");
    edit.putString("Kathi","https://github.com/Vikramr16/Others-Songs/raw/refs/heads/main/Kathi%20Mela%20Kathi%20Album%20Song.mp3");
    edit.putString("Ram life","https://github.com/Vikramr16/Others-Songs/raw/refs/heads/main/Life%20of%20Ram%20Song.mp3");
    edit.putString("Manithan","https://github.com/Vikramr16/Others-Songs/raw/refs/heads/main/Manithan%20-%20Aval%20Song.mp3");
    edit.putString("Nee Kavithaigala","https://github.com/Vikramr16/Others-Songs/raw/refs/heads/main/Nee%20Kavithaigala%20Song.mp3");
    edit.putString("Otha Solaala","https://github.com/Vikramr16/Others-Songs/raw/refs/heads/main/Otha%20Sollaala%20Song.mp3");
    edit.putString("Vaadi Pulla Vaadi","https://github.com/Vikramr16/Others-Songs/raw/refs/heads/main/Vaadi%20Pulla%20Vaadi%20Song.mp3");
        edit.apply();


        recyclerView.setAdapter(adp);

        //Music Fet

        return v;
    }

    private void openCamera() {
        Intent in=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(in,100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
        Bitmap images=(Bitmap) data.getExtras().get("data");
        profile.setImageBitmap(images);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int it=item.getItemId();
        if(it==R.id.setting){
            //settings Fragment
        }

        return super.onOptionsItemSelected(item);
    }


}