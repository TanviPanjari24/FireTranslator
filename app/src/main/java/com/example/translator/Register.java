package com.example.translator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText MName,MEmail,MPassword,MContact;
    TextView LogButton;
    Button MButton;
    FirebaseAuth fAuth;
    ProgressBar progressBar;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        MName = findViewById(R.id.RName);
        MEmail = findViewById(R.id.REmail);
        MContact = findViewById(R.id.Rcontact);
        MPassword = findViewById(R.id.Rpassword);
        MButton = findViewById(R.id.RegButton);
        LogButton = findViewById(R.id.LeButton);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);


        LogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this,Login.class));
            }
        });



        if(fAuth.getCurrentUser() !=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        MButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = MEmail.getText().toString().trim();
                String password = MPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    MEmail.setError("Email is Required.");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    MPassword.setError("Password is Required.");
                    return;

                }
                if(password.length() < 6){
                    MPassword.setError("Password must be >= 6 Characters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                // register the user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            Toast.makeText(Register.this, "Error!" +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });

            }
        });




    }
}