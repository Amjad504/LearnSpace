package com.example.learnspace;

import static com.google.android.material.internal.ViewUtils.showKeyboard;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class Profile extends Fragment {


    CircleImageView Profile_pic;
    EditText username,about,speciality;
    String UserID;
    ImageView editspeciality,editname,editabout;
    ArrayList<String> profiledata = new ArrayList<>();
    //    String profiledata;
    Boolean check = false;

    androidx.appcompat.widget.AppCompatButton update_Button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        Profile_pic = view.findViewById(R.id.profilepicture);
        username = view.findViewById(R.id.User_Name);
        about = view.findViewById(R.id.About);
        speciality = view.findViewById(R.id.Speciality);
        editname = view.findViewById(R.id.editName);
        editabout = view.findViewById(R.id.editAbout);
        editspeciality = view.findViewById(R.id.editSpeciality);
        update_Button = view.findViewById(R.id.updateProfile);

        UserID = FirebaseAuth.getInstance().getCurrentUser().getUid();



        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String Id = currentUser.getUid();
        final FirebaseDatabase Update_Name = FirebaseDatabase.getInstance();
        DatabaseReference Ref = Update_Name.getReference("User_info").child(Id);
        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username.setText(snapshot.child("name").getValue().toString());
                about.setText(snapshot.child("details").getValue().toString());
                speciality.setText(snapshot.child("speciality").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        editname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username.requestFocus();
                username.setEnabled(true);
                username.setFocusable(true);
                showKeyboard(username);
                check = true;

            }
        });

        editabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                about.requestFocus();
                about.setEnabled(true);
                about.setFocusable(true);
                showKeyboard(about);
                check = true;
            }
        });


        editspeciality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speciality.requestFocus();
                speciality.setEnabled(true);
                speciality.setFocusable(true);
                showKeyboard(speciality);
                check = true;
            }
        });

        update_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check == false)
                {
                    Toast.makeText(getActivity().getApplicationContext(), "Nothing Updated!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    check = false;
                    String Name = username.getText().toString();
                    String About = about.getText().toString();
                    String Speciality = speciality.getText().toString();
                    username.setEnabled(false);
                    about.setEnabled(false);
                    speciality.setEnabled(false);
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    String Id = currentUser.getUid();
                    final FirebaseDatabase Update_Name = FirebaseDatabase.getInstance();
                    DatabaseReference Ref = Update_Name.getReference("User_info").child(Id);
                    Ref.child("name").setValue(Name);
                    Ref.child("details").setValue(About);
                    Ref.child("speciality").setValue(Speciality);

                    Toast.makeText(getActivity().getApplicationContext(), "Update Successfully!", Toast.LENGTH_LONG).show();

                }


            }
        });


        return view;
    }

    public void showKeyboard(final EditText ettext){
        ettext.requestFocus();
        ettext.postDelayed(new Runnable(){
                               @Override public void run(){
                                   InputMethodManager keyboard= null;
                                   if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                       keyboard = (InputMethodManager)getActivity().getSystemService(getActivity().getApplicationContext().INPUT_METHOD_SERVICE);
                                   }
                                   keyboard.showSoftInput(ettext,0);
                               }
                           }
                ,200);
    }
}