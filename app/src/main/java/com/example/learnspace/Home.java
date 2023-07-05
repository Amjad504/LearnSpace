package com.example.learnspace;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.zegocloud.uikit.prebuilt.liveaudioroom.ZegoUIKitPrebuiltLiveAudioRoomConfig;
import com.zegocloud.uikit.prebuilt.liveaudioroom.ZegoUIKitPrebuiltLiveAudioRoomFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;


public class Home extends Fragment {


    SearchView searchView;
    RecyclerView rooms;

    Room_Adapter room_adapter;
    List<Room_info> room_infoList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);


        searchView = view.findViewById(R.id.searchView);

        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterlist(newText);
                return true;
            }
        });
        
        rooms = view.findViewById(R.id.rooms);

        room_infoList = new ArrayList<>();

        room_adapter = new Room_Adapter(room_infoList, getActivity());
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        rooms.setLayoutManager(lm);
        rooms.setAdapter(room_adapter);

        room_infoList.add(new Room_info("Chemistry Room",getResources().getDrawable(R.drawable.chemistry),"1122334455"));
        room_infoList.add(new Room_info("Math Room",getResources().getDrawable(R.drawable.math),"1111222233"));
        room_infoList.add(new Room_info("Physics Room",getResources().getDrawable(R.drawable.physics),"1212343456"));
        room_infoList.add(new Room_info("Computer Science Room",getResources().getDrawable(R.drawable.computer_science),"1234123456"));



        room_adapter.notifyDataSetChanged();
        return view;


    }

    private void filterlist(String newText) {
        List<Room_info> filteredlist = new ArrayList<Room_info>();
        for (Room_info item : room_infoList) {
            if (item.getRoomName().toLowerCase().contains(newText.toLowerCase())) {
                filteredlist.add(item);
            }
            if (filteredlist.isEmpty()) {
                Toast.makeText(getActivity().getApplicationContext(),
                                "No Data Found",
                                Toast.LENGTH_LONG)
                        .show();
                room_adapter.setfilterList(filteredlist);
            } else {
                // at last we are passing that filtered
                // list to our adapter class.
                room_adapter.setfilterList(filteredlist);
            }
        }
    }

}