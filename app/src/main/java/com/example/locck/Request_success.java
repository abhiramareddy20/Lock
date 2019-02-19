package com.example.locck;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    private String SenderId,currentState,reciverId;
    private DatabaseReference myRef,bookRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_request_success);

        mAuth = FirebaseAuth.getInstance ();
        SenderId = mAuth.getCurrentUser ().getUid ();
        myRef = FirebaseDatabase.getInstance ().getReference ().child ("Users");
        bookRequest = FirebaseDatabase.getInstance ().getReference ().child ("Users").child (SenderId).child ("Booking Requests");

        b1 = (Button)findViewById (R.id.next);
        currentState = "new";


        b1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                if(currentState.equals ("new"))
                {
                    SendBookingRequest();
                }
                //startActivity (new Intent (Request_success.this,Start_Activity.class));
            }
        });

    }

    private void SendBookingRequest() {
        bookRequest.child ("Request_Type").setValue ("sent")
                .addOnCompleteListener (new OnCompleteListener<Void> () {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful ())
                        {
                            startActivity (new Intent (Request_success.this,Start_Activity.class));
                            Toast.makeText (Request_success.this, "Booking Confirmed", Toast.LENGTH_SHORT).show ();
                        }
                        else
                        {
                            Toast.makeText (Request_success.this, "Payment Failed", Toast.LENGTH_SHORT).show ();
                        }

                    }
                });

    }


}
