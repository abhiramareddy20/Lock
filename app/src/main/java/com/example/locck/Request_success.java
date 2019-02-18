package com.example.locck;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class Request_success extends AppCompatActivity {

    Button b1;
    private FirebaseAuth mAuth;
    private String user;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_request_success);

        mAuth = FirebaseAuth.getInstance ();
        user = mAuth.getCurrentUser ().getUid ();
        myRef = FirebaseDatabase.getInstance ().getReference ("Users");



        b1 = (Button)findViewById (R.id.next);

        b1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                startActivity (new Intent (Request_success.this,Start_Activity.class));
            }
        });

    }




}
