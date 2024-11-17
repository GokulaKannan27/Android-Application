package com.example.beat_box;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Realtime extends AppCompatActivity {
    private EditText taskin;
    private EditText desc;
    private Button add,show;
    private FirebaseDatabase db;
    private DatabaseReference reference;
    private task t;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.realtime);

        taskin=findViewById(R.id.editTextText);
        add=findViewById(R.id.button3);
        show=findViewById(R.id.button4);
        desc=findViewById(R.id.editTextText1);

        db= FirebaseDatabase.getInstance();
        reference=db.getReference("Tasks");

        t=new task();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String task1=taskin.getText().toString();
                String description=desc.getText().toString();

                if(!task1.isEmpty()&&!description.isEmpty()){
                    task t=new task(task1,description);

                    reference.child("Tasks").child(task1).setValue(t).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                taskin.setText("");
                                desc.setText("");
                                Toast.makeText(getApplicationContext(), "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                String error=task.getException()!= null ? task.getException().getMessage() : "Unknown error";
                                Toast.makeText(getApplicationContext(),"Upload failed"+error,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(),"Task Cannot be empty",Toast.LENGTH_SHORT).show();
                }
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdata();
            }
        });

    }

    private void showdata() {
        reference.child("Tasks").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    StringBuilder tasklist=new StringBuilder();
                    for(DataSnapshot tasksnap:snapshot.getChildren()){
                        task t=tasksnap.getValue(task.class);
                        if(t!=null){
                            tasklist.append("Task : ").append(t.getTaskinput()).append("\nDescription : ").append(t.getDescription())
                                    .append("\n\n");
                        }
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(Realtime.this);
                    builder.setTitle("User Tasks");
                    builder.setMessage(tasklist.toString());
                    builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(getApplicationContext(), "No tasks found", Toast.LENGTH_SHORT).show();
                }
                }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
