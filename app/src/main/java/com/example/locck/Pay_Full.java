package com.example.locck;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Pay_Full extends AppCompatActivity {

    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_pay__full);

        b1 = (Button)findViewById (R.id.pDone);

        b1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                startActivity (new Intent (Pay_Full.this,Request_success.class));
                finish ();
            }
        });
    }
}
