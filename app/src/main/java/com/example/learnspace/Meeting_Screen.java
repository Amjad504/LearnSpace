package com.example.learnspace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.zegocloud.uikit.prebuilt.liveaudioroom.ZegoUIKitPrebuiltLiveAudioRoomConfig;
import com.zegocloud.uikit.prebuilt.liveaudioroom.ZegoUIKitPrebuiltLiveAudioRoomFragment;
import com.zegocloud.uikit.prebuilt.videoconference.ZegoUIKitPrebuiltVideoConferenceConfig;
import com.zegocloud.uikit.prebuilt.videoconference.ZegoUIKitPrebuiltVideoConferenceFragment;

import java.util.UUID;

public class Meeting_Screen extends AppCompatActivity {

    String Con_ID;
    String UserID,Email;
    String Name,Role;
    TextView name,meeting_id;

    ImageView share_btn;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_screen);
        getSupportActionBar().hide();

        name = findViewById(R.id.username);
        meeting_id = findViewById(R.id.meeting_conf_id);
        share_btn = findViewById(R.id.share_btn);
        UserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Con_ID = getIntent().getStringExtra("ID");

        String private_name = getIntent().getStringExtra("name");


        if (private_name == null)
        {

            final FirebaseDatabase Update_Name = FirebaseDatabase.getInstance();
            DatabaseReference Ref = Update_Name.getReference("User_info").child(UserID);
            Ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    name.setText(snapshot.child("name").getValue().toString());
                    Role = (snapshot.child("role").getValue().toString());
                    Name = name.getText().toString();
                    meeting_id.setText("Meeting Id : "+Con_ID);
                    addFragment();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else {

            meeting_id.setText("Meeting Id : "+Con_ID);
            Name = private_name;
            addFragment();

        }


        share_btn.setOnClickListener(v -> {
            Intent intent= new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra (Intent.EXTRA_TEXT,"Join meeting in Learn Space app \n Meeting ID: "+Con_ID);
            startActivity (Intent.createChooser (intent, "Share via"));
        });


    }

    public void addFragment() {
        long appID = App_Constants.app_id;
        String appSign = App_Constants.app_sign;

        String conferenceID = Con_ID;
        String userID = UUID.randomUUID().toString();
        String userName = Name+" ( "+Role+" )";

        ZegoUIKitPrebuiltVideoConferenceConfig config = new ZegoUIKitPrebuiltVideoConferenceConfig();
        config.turnOnCameraWhenJoining = false;
        config.turnOnMicrophoneWhenJoining = false;
        ZegoUIKitPrebuiltVideoConferenceFragment fragment = ZegoUIKitPrebuiltVideoConferenceFragment.newInstance(appID, appSign, userID, userName,conferenceID,config);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commitNow();
    }


    @Override
    public void onBackPressed()
    {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_baseline_exit_to_app_24)
                .setCancelable(false)
                .setTitle("Alert")
                .setMessage("You have to leave meeting before going back")
                .setPositiveButton(Html.fromHtml("<font color='#01b1ec'>OK</font>"), null)
                .show();
    }

}