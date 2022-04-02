package com.example.register;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.app.ProgressDialog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView image;
    Button select, upload;
    EditText name,pass,confirm;
    Bitmap bitmap;
    ProgressDialog progressDialog;
    static final String USER_KEY = "USER";
    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    image = findViewById(R.id.image);
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        Uri filePath = intent.getData();
                        try {
                            //getting image from gallery
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                            //Setting image to ImageView
                            image.setImageBitmap(bitmap);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                }
           });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = findViewById(R.id.image);
        select = findViewById(R.id.select);
        upload = findViewById(R.id.upload);
        name=findViewById(R.id.name);

        //opening image chooser option
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
//                Intent intent = new Intent(MainActivity.this, SuccessActivity.class);
//                String nameuser = name.getText().toString();
//                intent.putExtra(USER_KEY, nameuser);
                mStartForResult.launch(Intent.createChooser(intent, "Select Image"));
            }
        });

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
//            Uri filePath = data.getData();
//
//            try {
//                //getting image from gallery
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//
//                //Setting image to ImageView
//                image.setImageBitmap(bitmap);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        //}
}