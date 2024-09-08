package com.example.translator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText MEmail,MPassword;
    TextView ReButton;
    Button MButton;
    FirebaseAuth fAuth;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MEmail = findViewById(R.id.LEmail);
        MPassword = findViewById(R.id.LPassword);
        MButton = findViewById(R.id.LButton);
        ReButton = findViewById(R.id.RButton);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);




        ReButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Register.class));
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

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this,"Logged In Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }
                        else {
                            Toast.makeText(Login.this, "Error!" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }

        });




    }
}