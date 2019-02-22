package com.example.locck;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Book extends AppCompatActivity {

    private static final String TAG = "GalleryActivity";
    ImageView imgageView;
    AlertDialog.Builder builder;
    Button b1;

    private FirebaseAuth mAuth;
    private DatabaseReference myref;
    private String user;
    int img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_book);

        mAuth = FirebaseAuth.getInstance ();
        user = mAuth.getCurrentUser ().getUid ();
        myref = FirebaseDatabase.getInstance ().getReference ().child ("Users").child (user);


        imgageView =(ImageView)findViewById (R.id.lock1);
       /* Bundle bundle =getIntent ().getExtras ();
        if(bundle!=null)
        {
             img = bundle.getInt ("image");
            imgageView.setImageResource (img);

        }
*/


        b1 = (Button)findViewById (R.id.book);
        builder = new AlertDialog.Builder (this);
        b1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {


                builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);


                builder.setPositiveButton("Pay 20% in advance", null);

                builder.setPositiveButton("Pay 20% in advance", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent (Book.this,Pay_Half.class);
                        startActivity (intent);
                    }
                });


                builder.setNegativeButton("Cancel", null);

                builder.setNegativeButton("Pay full service charges online", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent (Book.this,Pay_Full.class);
                        startActivity (intent);
                    }
                });



                AlertDialog alert = builder.create();
                alert.setTitle("Please choose payment option");
                alert.show();


            }
        });









    }



}
