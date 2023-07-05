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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sign_Up extends AppCompatActivity {


    Button register;
    EditText Email,Pass,confrm_pass;

    TextView login;

    FirebaseAuth mAuth;

    RadioButton student_radio,teacher_radio;
    String user_role;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();





        mAuth = FirebaseAuth.getInstance();
        Email = (EditText) findViewById(R.id.reg_mail);
        Pass = (EditText) findViewById(R.id.reg_pass) ;
        confrm_pass = (EditText) findViewById(R.id.cnfrm_password);
        register = findViewById(R.id.reg_signup);
        login = findViewById(R.id.login_page);

        student_radio = findViewById(R.id.Student);
        teacher_radio = findViewById(R.id.Teacher);
        student_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student_radio.setError(null);
                teacher_radio.setError(null);
                if (!student_radio.isSelected()) {
                    student_radio.setChecked(true);
                    student_radio.setSelected(true);
                    teacher_radio.setChecked(false);
                    teacher_radio.setSelected(false);
                } else {
                    student_radio.setChecked(false);
                    student_radio.setSelected(false);
                }
            }
        });
        teacher_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student_radio.setError(null);
                teacher_radio.setError(null);
                if (!teacher_radio.isSelected()) {
                    student_radio.setChecked(false);
                    student_radio.setSelected(false);
                    teacher_radio.setChecked(true);
                    teacher_radio.setSelected(true);
                } else {
                    teacher_radio.setChecked(false);
                    teacher_radio.setSelected(false);
                }
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sign_Up.this, Sign_In.class);
                startActivity(intent);
            }
        });



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pass,confrm_password;
                pass = Pass.getText().toString();
                confrm_password = confrm_pass.getText().toString();

                // Take the value of two edit texts in Strings
                String email, password,confrm_passcode;
                email = Email.getText().toString();
                password = Pass.getText().toString();
                confrm_passcode = confrm_pass.getText().toString();


                // Validations for input email and password
                if (TextUtils.isEmpty(email)) {
                    Email.setError("Email can not be Empty");
                    Email.requestFocus();
                    return;
                }
                else if (password.length()<6)
                {
                    Pass.setError("Password length can't be less than 6");
                    Pass.requestFocus();
                }
                else if (TextUtils.isEmpty(password)) {
                    Pass.setError("Password can not be Empty");
                    Pass.requestFocus();
                    return;
                }else if (TextUtils.isEmpty(confrm_passcode)) {
                    confrm_pass.setError("Confirm Password can not be Empty");
                    confrm_pass.requestFocus();
                    return;
                }
                else if (!pass.equals(confrm_password))
                {
                    confrm_pass.setError("Password did not matched");
                    confrm_pass.requestFocus();

                }
                else if (!student_radio.isSelected() & !teacher_radio.isSelected() )
                {
                    student_radio.setError("Choose your role");
                    teacher_radio.setError("Choose your role");
                }
                else
                {
                    registerNewUser(email,password);
                }

            }
        });


    }


    private void registerNewUser(String email,String password) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),
                                    "Registration Successful!",
                                    Toast.LENGTH_LONG)
                            .show();
                    if (student_radio.isSelected())
                    {
                        user_role = "Student";
                        Intent i = new Intent(Sign_Up.this,Student_Signup.class);
                        i.putExtra("email",(email));
                        i.putExtra("user_role",user_role);
                        startActivity(i);
                        finish();
                    }
                    else if (teacher_radio.isSelected())
                    {
                        user_role = "Teacher";
                        Intent i = new Intent(Sign_Up.this,Student_Signup.class);
                        i.putExtra("email",(email));
                        i.putExtra("user_role",user_role);
                        startActivity(i);
                        finish();
                    }
                } else {

                    // Registration failed
                    Toast.makeText(
                                    getApplicationContext(),
                                    "Registration failed!!"
                                            + task.getException().getMessage(),
                                    Toast.LENGTH_LONG)
                            .show();


                }

            }
        });


    }

}