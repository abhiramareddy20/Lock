package com.example.locck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Rating_activity extends AppCompatActivity {

    RatingBar rate;
    Button btn;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_rating_activity);


        mAuth = FirebaseAuth.getInstance ();
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hello, World!");

        rate = (RatingBar)findViewById (R.id.ratingBar2);
        btn = (Button)findViewById (R.id.submit);

        rate.setOnRatingBarChangeListener (new RatingBar.OnRatingBarChangeListener () {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                Toast.makeText (Rating_activity.this, "You rated" + v, Toast.LENGTH_SHORT).show ();

            }
        });

        btn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Toast.makeText (Rating_activity.this, "Thank you ", Toast.LENGTH_SHORT).show ();
            }
        });
    }
}
