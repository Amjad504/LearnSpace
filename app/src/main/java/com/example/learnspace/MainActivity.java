package com.example.learnspace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    Home home = new Home();
    Private_Room private_room  = new Private_Room();
    Profile profile = new Profile();
    String Email;

    CircleImageView profilepic;

    TextView toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Email = getIntent().getStringExtra("email");
        bottomNavigationView = findViewById(R.id.bottom);
        toolbar_title = findViewById(R.id.toolbar_title);
        profilepic = findViewById(R.id.profilepic);

        String Email = getIntent().getStringExtra("email");


        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container,profile);
                transaction.commit();
                // Update the toolbar title
                toolbar_title.setText("Profile");

                // Simulate a click on the profile icon in the bottom bar
                Menu bottomMenu = bottomNavigationView.getMenu();
                MenuItem profileMenuItem = bottomMenu.findItem(R.id.profile);
                profileMenuItem.setChecked(true);
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.container,home).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId())
                {
                    case R.id.home:
//                        Toast.makeText(MainActivity.this, "Data Added Successfully", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,home).commit();
                        return true;
                    case R.id.private_room:
                        toolbar_title.setText("Private Room");
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,private_room).commit();
                        return true;
                   case R.id.profile:
                       toolbar_title.setText("Profile");
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,profile).commit();

                        return true;
                }




                return false;
            }
        });

    }
}