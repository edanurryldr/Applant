package com.edanuryildirim.applant.Activities.Model;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.edanuryildirim.applant.Activities.Model.Common.Common;
import com.edanuryildirim.applant.Activities.Model.Model.User;
import com.edanuryildirim.applant.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    Button btnSignIn;
    EditText edtUserName,edtPassword;

    private FirebaseAuth mAuth;
    private String TAG = "SignIn";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        final String email = edtUserName.getText().toString();
        final String password = edtPassword.getText().toString();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(edtUserName.getText() + "" + edtPassword.getText());
                final ProgressDialog mDialog=new ProgressDialog(SignIn.this);
                mDialog.setMessage("Please waiting");
                mDialog.show();

                mAuth.signInWithEmailAndPassword(edtUserName.getText().toString(), edtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            FirebaseUser currentFirebaseUser  = FirebaseAuth.getInstance().getCurrentUser();
                            saveUser(currentFirebaseUser.getUid(),edtUserName.getText().toString());

                        }
                        else{
                            System.out.println("Failed " + task.getException());

                        }
                        mDialog.dismiss();
                    }
                });
            }
        });
    }

    void saveUser(final String id, final String username){
        final FirebaseDatabase mAuth=FirebaseDatabase.getInstance();
        final DatabaseReference table_user=mAuth.getReference("User");
        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user =new User(id,username);
                    table_user.child(id).setValue(user);

                Common.currentUser = user;
                Intent i = new Intent(SignIn.this, Home.class);
                startActivity(i);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}