package com.example.learnspace;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Random;

public class Student_Signup extends AppCompatActivity {

    RadioButton male_radio,female_radio;

    EditText student_name,student_details,student_speciality;

    Button add_data;

    String Email = "",Role;

    String name,details,speciality;

    String gender = "";

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Student_Profile profiledata;
    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_signup);
        getSupportActionBar().hide();


        Email = getIntent().getStringExtra("email");
        Role = getIntent().getStringExtra("user_role");


        male_radio = findViewById(R.id.Male);
        female_radio = findViewById(R.id.Female);
        student_name = findViewById(R.id.reg_student_username);
        student_details = findViewById(R.id.reg_student_details);
        student_speciality = findViewById(R.id.reg_student_speciality);
        add_data = findViewById(R.id.reg_student_signup);


        male_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!male_radio.isSelected()) {
                    male_radio.setChecked(true);
                    male_radio.setSelected(true);
                    female_radio.setChecked(false);
                    female_radio.setSelected(false);
                } else {
                    male_radio.setChecked(false);
                    male_radio.setSelected(false);
                }
            }
        });
        female_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!female_radio.isSelected()) {
                    male_radio.setChecked(false);
                    male_radio.setSelected(false);
                    female_radio.setChecked(true);
                    female_radio.setSelected(true);
                } else {
                    female_radio.setChecked(false);
                    female_radio.setSelected(false);
                }
            }
        });


        mAuth = FirebaseAuth.getInstance();


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User_info");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        profiledata = new Student_Profile();


        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                name = student_name.getText().toString();
                details = student_details.getText().toString();
                speciality = student_speciality.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    student_name.setError("Name can not be Empty");
                    student_name.requestFocus();
                    return;
                }
                else if (TextUtils.isEmpty(details)) {
                    student_details.setError("Details can not be Empty");
                    student_details.requestFocus();
                    return;
                }
                else if (TextUtils.isEmpty(speciality)) {
                    student_speciality.setError("Speciality can not be Empty");
                    student_speciality.requestFocus();
                    return;
                }
                else if (!male_radio.isSelected() && !female_radio.isSelected())
                {
                    male_radio.setError("Choose your Gender");
                    female_radio.setError("Choose your Gender");
                }
                else
                {
                    addDatatoFirebase();
                }
            }
        });
    }
    private void addDatatoFirebase() {
        // getting text from our edittext fields.
        String name = student_name.getText().toString();
        String about = student_details.getText().toString();
        String email = Email;

        if (male_radio.isSelected())
        {
            gender = "male";
        }
        else
        {
            gender = "female";
        }

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String Id = currentUser.getUid();

        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference root=db.getReference("User_info");
        Student_Profile obj=new Student_Profile(name,about,gender,email,speciality,Role);
        root.child(Id).setValue(obj);

        Toast.makeText(Student_Signup.this, "Profile Setup Successfull", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(Student_Signup.this, MainActivity.class);
        i.putExtra("email",(email));
        startActivity(i);

    }
}