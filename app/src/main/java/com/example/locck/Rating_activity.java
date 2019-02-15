package com.example.locck;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.common.internal.service.Common;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Rating_activity extends AppCompatActivity {

    RatingBar rate;
    Button btn;
    private FirebaseAuth mAuth;
    private String user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_rating_activity);


        mAuth = FirebaseAuth.getInstance ();
        user = mAuth.getCurrentUser ().getUid ();



        rate = (RatingBar) findViewById (R.id.ratingBar2);



        rate.setOnRatingBarChangeListener (new RatingBar.OnRatingBarChangeListener () {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {


                final DatabaseReference dbRef = FirebaseDatabase.getInstance ().getReference ()
                        .child (user).child ("Rating");

                double dbRating = v;
                dbRef.setValue (dbRating);
                submitRating ();
                Toast.makeText (Rating_activity.this, "You rated" + v, Toast.LENGTH_SHORT).show ();

            }
        });



    }

    private void submitRating() {

            final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

            dbRef.addValueEventListener(new ValueEventListener () {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    double total = 0.0;
                    double count = 0.0;
                    double average = 0.0;
                    for(DataSnapshot ds: dataSnapshot.getChildren()) {
                        //double rating = dataSnapshot.getValue(Double.class);
                        double rating = (double) Double.parseDouble(ds.child("Rating").getValue().toString ());

                        total = total + rating;
                        count = count + 1;
                        average = total / count;
                    }

                    final DatabaseReference newRef = dbRef.child(user);
                    newRef.child("averageRating").setValue(average);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    throw databaseError.toException();
                }
            });

    }
}
