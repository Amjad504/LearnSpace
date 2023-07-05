package com.example.learnspace;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Random;
import java.util.UUID;


public class Private_Room extends Fragment {

    TextInputEditText meeting_id,username;
    Button join_meet,create_meet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_private__room, container, false);


        meeting_id = view.findViewById(R.id.meeting_id_private);
        username = view.findViewById(R.id.private_name);

        join_meet = view.findViewById(R.id.join_meet_btn);
        create_meet = view.findViewById(R.id.create_meet_btn);


        join_meet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Meeting_id = meeting_id.getText().toString();
                String name = username.getText().toString();
                if (Meeting_id.length()!= 10)
                {
                    meeting_id.setError("Invalid Meeting ID");
                    meeting_id.requestFocus();
                    return;
                }

                else if (name.isEmpty())
                {
                    username.setError("Name is required");
                    username.requestFocus();
                    return;
                }
                else {
                    Join_Meeting(Meeting_id,name);

                }
            }
        });

        create_meet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString();
                String id = getRandomMeetingID();

                if (name.isEmpty())
                {
                    username.setError("Name is required");
                    username.requestFocus();
                    return;
                }
                else
                {
                    Join_Meeting(id,name);
                }
            }
        });

        return view;


    }
    void Join_Meeting(String meeting_id,String name)
    {

        Intent i = new Intent(getActivity(), Meeting_Screen.class);
        i.putExtra("ID",meeting_id);
        i.putExtra("name",name);
        startActivity(i);
        getActivity().finish();
    }

    String getRandomMeetingID() {
        StringBuilder id = new StringBuilder();
        while (id.length() != 10) {
            int random = new Random().nextInt(10);
            id.append(random);
        }
        return id.toString();
    }
}