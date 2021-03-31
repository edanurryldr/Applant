package com.edanuryildirim.applant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

private static final int FOTOGRAF_ISTEKKOD = 99;

CircleImageView iv_pp;

Button btn_profile_change;

TextView tv_profile_name,tv_profile_mail,tv_profile_password;

    private FirebaseAuth mAuth;
    private String TAG = "Profile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        iv_pp=(CircleImageView)findViewById(R.id.iv_pp);

        btn_profile_change=(Button)findViewById(R.id.btn_profile_change);

        tv_profile_name=(TextView)findViewById(R.id.tv_profile_name);
        tv_profile_password=(TextView)findViewById(R.id.tv_profile_password);
        tv_profile_mail=(TextView)findViewById(R.id.tv_profile_mail);


        final String name = tv_profile_name.getText().toString();
        final String mail = tv_profile_mail.getText().toString();
        final String password = tv_profile_password.getText().toString();

        mAuth = FirebaseAuth.getInstance();

        btn_profile_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoChange();
            }
        });

    }

    private void PhotoChange() {

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, FOTOGRAF_ISTEKKOD);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) return;

        if (requestCode == FOTOGRAF_ISTEKKOD) {

            if (data != null) {

                Bundle bundle = data.getExtras();
                iv_pp.setImageBitmap((Bitmap) bundle.get("data"));

            }

        }
    }
}