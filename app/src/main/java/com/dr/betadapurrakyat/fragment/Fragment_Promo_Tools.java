package com.dr.betadapurrakyat.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dr.betadapurrakyat.Adapter.MiscAdapter;
import com.dr.betadapurrakyat.Adapter.ToolsAdapter;
import com.dr.betadapurrakyat.Model.Miscellaneous;
import com.dr.betadapurrakyat.Model.NewTools;
import com.dr.betadapurrakyat.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2/1/2018.
 */

public class Fragment_Promo_Tools extends Fragment {

    View v;
    private Context context;
    private RecyclerView recyclerView;
    private List<Miscellaneous> listMisc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.content_nw_fragment3,container,false);
        listMisc = new ArrayList<>();
        recyclerView = v.findViewById(R.id.perlengkapan_recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        loadproduct();

        return v;
    }

    private void loadproduct() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("Peralatan").child("PeralatanMasak").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Miscellaneous data = postSnapshot.getValue(Miscellaneous.class);
                    listMisc.add(data);

                }
                RecyclerView.Adapter adapter = new MiscAdapter(getContext(),listMisc);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //.child("PeralatanTanganDapur")

        myRef.child("Peralatan").child("PeralatanTanganDapur").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Miscellaneous data = postSnapshot.getValue(Miscellaneous.class);
                    listMisc.add(data);

                }
                RecyclerView.Adapter adapter = new MiscAdapter(getContext(),listMisc);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
