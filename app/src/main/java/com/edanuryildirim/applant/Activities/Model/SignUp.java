package com.edanuryildirim.applant.Activities.Model;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.edanuryildirim.applant.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {

    EditText   edtNameEntry,edtEmailEntry,edtPasswordEntry;

    Button btnSignUp;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtNameEntry=(EditText)findViewById(R.id.edtNameEntry);
        edtEmailEntry=(EditText)findViewById(R.id.edtEmailEntry);
        edtPasswordEntry=(EditText)findViewById(R.id.edtPasswordEntry);

        btnSignUp=(Button)findViewById(R.id.btnSignUp);

        mAuth=FirebaseAuth.getInstance();

       // final FirebaseAuth mAuth=FirebaseAuth.getInstance();
      // final DatabaseReference table_user=mAuth.getReference("User");

    btnSignUp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final ProgressDialog mDialog=new ProgressDialog(SignUp.this);
            mDialog.setMessage("Please waiting...");
            mDialog.show();
            if (checkForm(edtEmailEntry.getText().toString(),edtPasswordEntry.getText().toString())) {

                mAuth.createUserWithEmailAndPassword(edtEmailEntry.getText().toString(),edtPasswordEntry.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                System.out.println(edtEmailEntry.getText().toString() + "" + edtPasswordEntry.getText().toString());
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignUp.this,
                                            "Sign up successfully !", Toast.LENGTH_LONG).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                } else {
                                    System.out.println("başarısız kayıt!!!!! : " + task.getException());
                                    Toast.makeText(SignUp.this,
                                            "E-mail already register", Toast.LENGTH_LONG).show();
                                }
                                mDialog.dismiss();
                            }
                        });

                //startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

            } else {
               Toast.makeText(SignUp.this,"Kayıt işlemi başarılı",Toast.LENGTH_SHORT).show();
               mDialog.dismiss();
            }

        }
    });
    }

    public Boolean checkForm(String... data) {
        for (String str : data) {
            if (str == null || str.trim().equals("")) {
                return false;
            }
        }
        return true;
    }
}
