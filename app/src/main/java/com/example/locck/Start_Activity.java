package com.example.locck;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class Start_Activity extends AppCompatActivity {
    private Button UploadPic,update;
    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private DatabaseReference UsersRef;
    private static final int GALLERY_PICK = 1;
    private StorageReference PostImagesRef;
    private ProgressDialog loadingBar;
    private ImageView img;
    private Uri ImageUri;
    private  String saveCurrentDate,saveCurrentTime,postRandomName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_start_);

        img = (ImageView)findViewById (R.id.imageView2);
        UploadPic = (Button) findViewById (R.id.button);
        update = (Button)findViewById (R.id.Update);

        loadingBar = new ProgressDialog (this);

        mAuth = FirebaseAuth.getInstance ();
        currentUserID = mAuth.getCurrentUser ().getUid ();
        /*RootRef = FirebaseDatabase.getInstance ().getReference ();
        UsersRef = FirebaseDatabase.getInstance ().getReference ().child ("Users");*/
        PostImagesRef = FirebaseStorage.getInstance ().getReference ().child ("Loksmith Images");


        img.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ();
                intent.setAction (Intent.ACTION_GET_CONTENT);
                intent.setType ("image/ ");
                startActivityForResult (intent, GALLERY_PICK);

            }
        });

        update.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                ValidatePostInfo();
            }
        });


    }

    private void ValidatePostInfo()
    {
            if(ImageUri == null){
                Toast.makeText (this, "Please Take the photo first", Toast.LENGTH_SHORT).show ();

            }else{
                loadingBar.setTitle ("Uploading Photo");
                loadingBar.setMessage ("Please wait, while we Upload Photo");
                loadingBar.setCanceledOnTouchOutside (false);
                loadingBar.show ();
                StoringImageToFirebase();

            }
    }

    private void StoringImageToFirebase() {

        Calendar calenderforDate = Calendar.getInstance ();
        SimpleDateFormat currentDate = new SimpleDateFormat ("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format (calenderforDate.getTime ());

        Calendar calenderforTime = Calendar.getInstance ();
        SimpleDateFormat currentTime = new SimpleDateFormat ("HH:mm");
        saveCurrentTime = currentTime.format (calenderforTime.getTime ());

        postRandomName = saveCurrentDate + saveCurrentTime;

        StorageReference Filepath =PostImagesRef.child ("Camera picture").child ( currentUserID + ImageUri.getLastPathSegment () + postRandomName + ".jpg");

        Filepath.putFile (ImageUri).addOnCompleteListener (new OnCompleteListener<UploadTask.TaskSnapshot> () {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful ()){
                    loadingBar.dismiss ();
                    Toast.makeText (Start_Activity.this, "Image Uploaded Successfully ", Toast.LENGTH_SHORT).show ();
                    Intent intent = new Intent (Start_Activity.this,Rating_activity.class);
                    startActivity (intent);
                    finish ();
                }else{
                    String msg = task.getException ().getMessage ();
                    Toast.makeText (Start_Activity.this, "Error occured" +msg , Toast.LENGTH_SHORT).show ();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult (requestCode, resultCode, data);


        if(requestCode == GALLERY_PICK && resultCode ==RESULT_OK && data!=null)
        {
                 ImageUri =data.getData ();
                img.setImageURI (ImageUri);
        }

    }
}







