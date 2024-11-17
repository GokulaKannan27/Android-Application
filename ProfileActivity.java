package com.example.beat_box;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends Fragment {
    private TextView bt;
    private TextView text1,text2;
    private FirebaseAuth mAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.profile_main,container,false);
        text1=v.findViewById(R.id.textView3);
        text2=v.findViewById(R.id.textView4);

        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info();
            }

        });

        bt=v.findViewById(R.id.button2);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder built=new AlertDialog.Builder(getContext());
            built.setTitle("SignOut");
            built.setMessage("Are Sure on it ?");
            built.setPositiveButton("SignOut", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    signOutUser();
                    dialogInterface.dismiss();
                }
            });
            built.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            AlertDialog alert=built.create();
            alert.show();
//                signOutUser();
            }
        });
        return v;
    }

    private void info() {
            FirebaseUser user=mAuth.getCurrentUser();
            String Email=user.getEmail();
            String name=user.getDisplayName();
            AlertDialog.Builder built=new AlertDialog.Builder(getContext());
            built.setNeutralButton("CONFIRM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            built.setMessage("EMAIL : "+Email+"\n"+"Name : "+name);
            AlertDialog alert=built.create();
            alert.show();

        }


    private void signOutUser() {

        mAuth.getInstance().signOut();
        Toast.makeText(getContext(),"Signed Out Successfully",Toast.LENGTH_LONG).show();
        GoogleSignInClient gsc= GoogleSignIn.getClient(getActivity(),new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build());

        gsc.signOut().addOnCompleteListener(getActivity(),task -> {
            if(task.isSuccessful()) {
                Intent in = new Intent(getActivity(), LoginActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
                getActivity().finish();
                Toast.makeText(getContext(), "SignOut Success", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getContext(),"Failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}
