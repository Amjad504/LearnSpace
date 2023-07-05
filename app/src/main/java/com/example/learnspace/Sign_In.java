package com.example.learnspace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Sign_In extends AppCompatActivity {

    LinearLayout login_btn;
    LinearLayout login_layout;
    LinearLayout login_verify_layout;

    Button Cancel_login;

    TextView signup;

    String email;

    TextView error_email,forget_pass;
    EditText Email,Password;
    Button login_button;

    FirebaseAuth mAuth;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();

        signup = findViewById(R.id.singup_btn);
        forget_pass  = findViewById(R.id.forget_pass);
        Email = findViewById(R.id.login_email);
        Password = findViewById(R.id.login_password);
        login_button = findViewById(R.id.login_btn);



        mAuth = FirebaseAuth.getInstance();


        forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sign_In.this, ResetPassword.class);
                startActivity(intent);

            }
        });



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Sign_In.this, Sign_Up.class);
                startActivity(intent);
                finish();

            }
        });


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginuser();
            }
        });
    }

    private void loginuser() {


        // Take the value of two edit texts in Strings

        email = Email.getText().toString();
        String password = Password.getText().toString();

        // Validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Email.setError("Email can not be Empty");
            Email.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(password)) {
            Password.setError("Password can not be Empty");
            Password.requestFocus();
            return;
        }else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),
                                        "Login Successful!",
                                        Toast.LENGTH_LONG)
                                .show();
                        Intent i = new Intent(Sign_In.this, MainActivity.class);
                        startActivity(i);
                    }else {

                        // Registration failed
                        Toast.makeText(
                                        getApplicationContext(),
                                        "Wrong Credentials"
                                                + task.getException().getMessage(),
                                        Toast.LENGTH_LONG)
                                .show();


                    }


                }
            });
        }
    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            Intent i = new Intent(Sign_In.this, MainActivity.class);
            i.putExtra("email",(email));
            startActivity(i);

        }
    }
}