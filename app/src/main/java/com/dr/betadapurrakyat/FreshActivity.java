package com.dr.betadapurrakyat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dr.betadapurrakyat.Adapter.Adapter_Promo_Activity;
import com.dr.betadapurrakyat.Adapter.HolderFreshBar;
import com.dr.betadapurrakyat.Model.Fresh;
import com.dr.betadapurrakyat.Model.FreshUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FreshActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<FreshUtils> listFresh;
    RecyclerView.Adapter adapter;
    private RelativeLayout freshIntent;
    private ImageView imageViewfresh;
    private TextView fresh_subtitle,fresh_price,fresh_title;
    private Button order_button_fresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresh);
        listFresh = new ArrayList<>();
        recyclerView = findViewById(R.id.nMenu_recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        adapter = new HolderFreshBar(getBaseContext(),listFresh);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        IntentCheck();
        loadproduct();

    }

    private void IntentCheck() {
        freshIntent = findViewById(R.id.fresh_intent_rl);
        imageViewfresh = findViewById(R.id.imageViewfresh);
        fresh_title = findViewById(R.id.fresh_title);
        fresh_subtitle = findViewById(R.id.fresh_subtitle);
        fresh_price = findViewById(R.id.fresh_price);
        order_button_fresh = findViewById(R.id.order_button_fresh);

        Bundle intent = getIntent().getExtras();
        String a = intent.getString("title");
        String b = intent.getString("uri");
        String c = intent.getString("subtitle");
        if (a==null){
            freshIntent.setVisibility(View.GONE);
        }else{
            freshIntent.setVisibility(View.VISIBLE);

            Glide.with(getBaseContext())
                    .asBitmap()
                    .load(b)
                    .into(imageViewfresh);
            fresh_title.setText(a);
            fresh_subtitle.setText(c);
        }
    }


    private void loadproduct() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("Fresh").child("Ikan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    FreshUtils data = postSnapshot.getValue(FreshUtils.class);
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
                    FreshUtils data = postSnapshot.getValue(FreshUtils.class);
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
                    FreshUtils data = postSnapshot.getValue(FreshUtils.class);
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
