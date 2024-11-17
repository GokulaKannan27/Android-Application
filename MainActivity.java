package com.example.beat_box;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        bn=findViewById(R.id.bottom_navigation);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

       bn.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id1 = item.getItemId();
                Fragment selected = new HomeActivity();
                if (id1 == R.id.home) {
                    selected = new HomeActivity();
                } else if (id1 == R.id.search1) {
                    selected = new SearchActivity();
                } else if (id1 == R.id.library) {
                    selected = new LibraryActivity();
                } else if (id1 == R.id.profile) {
                    selected=new ProfileActivity();
                }
                  if (selected != null) {
                    fragment(selected);
                }
                return true;

            }
        });
        if(savedInstanceState==null){
            fragment(new HomeActivity());
        }
    }

    private void fragment(Fragment selected) {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction t=fm.beginTransaction();
        t.replace(R.id.fragmentContainerView,selected);
        t.commit();
    }
}