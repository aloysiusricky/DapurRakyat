package com.dr.betadapurrakyat.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dr.betadapurrakyat.Adapter.Adapter_Promo_Activity;
import com.dr.betadapurrakyat.Model.Fresh;
import com.dr.betadapurrakyat.Model.NewMenu;
import com.dr.betadapurrakyat.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Fragment_Promo_Fresh extends Fragment {

    View v;
    private RecyclerView recyclerView;
    private List <Fresh> listFresh;
    RecyclerView.Adapter adapter;
    private RelativeLayout freshIntent;
    private ImageView imageViewfresh;
    private TextView fresh_subtitle,fresh_price,fresh_title;
    private Button order_button_fresh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.content_fragment_fresh,container,false);
        listFresh = new ArrayList<>();
        recyclerView = v.findViewById(R.id.nMenu_recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new Adapter_Promo_Activity(getContext(),listFresh);
        recyclerView.setLayoutManager(layoutManager);
        loadproduct();

        return v;
        }



    private void loadproduct() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("Fresh").child("Ikan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Fresh data = postSnapshot.getValue(Fresh.class);
                    listFresh.add(data);

                }

                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        myRef.child("Fresh").child("Sayur").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Fresh data = postSnapshot.getValue(Fresh.class);
                    listFresh.add(data);

                }

                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        myRef.child("Fresh").child("Meat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Fresh data = postSnapshot.getValue(Fresh.class);
                    listFresh.add(data);

                }

                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



}
