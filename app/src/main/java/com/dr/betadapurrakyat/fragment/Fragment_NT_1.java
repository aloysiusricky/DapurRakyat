package com.dr.betadapurrakyat.fragment;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dr.betadapurrakyat.Model.NetworkUri;
import com.dr.betadapurrakyat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Fragment_NT_1 extends Fragment implements View.OnClickListener {


    View v;
    private RecyclerView recyclerView4,recyclerView5,recyclerView6,recyclerView7;
    private List<NetworkUri> dataList = new ArrayList<>(16);;
    private ImageView imageView;
    private RecyclerView.Adapter adapter;
    ImageView user_image_board;
    ImageView id1,id2,id3,id4,id5,id6,id7,id8,id9,id10,id11,id12,id13,id14;
    FirebaseDatabase database ;
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String userPhone;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_main_network, container, false);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userPhone = user.getPhoneNumber();

        recyclerView4();
        recyclerView5();
        recyclerView6();
        recyclerView7();



        setImageUser();
        assigndownline();
        downlineTree();
        return v;


    }



    private void setImageUser() {
        loadproduct();
        user_image_board = v.findViewById(R.id.user_image_board);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userPhone = user.getPhoneNumber();
        myRef.child("User").child(userPhone).child("uri")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String uri = dataSnapshot.getValue(String.class);
                        if (uri != null){
                            Glide.with(getContext())
                                    .load(uri)
                                    .apply(RequestOptions.circleCropTransform())
                                    .into(user_image_board);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }


    private void recyclerView7() {
        recyclerView7 = (RecyclerView) v.findViewById(R.id.recyclerview_baris7);
        recyclerView7.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView7.setLayoutManager(layoutManager);
        recyclerView7.setAdapter(new MyAdapter7());
    }


    private void recyclerView6() {
        recyclerView6 = (RecyclerView) v.findViewById(R.id.recyclerview_baris6);
        recyclerView6.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView6.setLayoutManager(layoutManager);
        recyclerView6.setAdapter(new MyAdapter6());
    }

    private void recyclerView5() {
        recyclerView5 = (RecyclerView) v.findViewById(R.id.recyclerview_baris5);
        recyclerView5.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView5.setLayoutManager(layoutManager);
        recyclerView5.setAdapter(new MyAdapter5());
    }

    private void recyclerView4() {
        recyclerView4 = (RecyclerView) v.findViewById(R.id.recyclerview_baris4);
        recyclerView4.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView4.setLayoutManager(layoutManager);
    }

    private void loadproduct() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("User").child("Downline").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    NetworkUri data = postSnapshot.getValue(NetworkUri.class);
                    dataList.add(data);

                }
                recyclerView4.setAdapter(new MyAdapter());

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        public class EmptyViewHolder extends RecyclerView.ViewHolder {
            public EmptyViewHolder(View itemView) {
                super(itemView);
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView user;

            public ViewHolder(View v) {
                super(v);
                user = (ImageView) v.findViewById(R.id.user_image_card);
            }
        }

        @Override
        public int getItemCount() {
            return dataList.size() > 0 ? dataList.size() : 16;
        }

        @Override
        public int getItemViewType(int position) {
            if (dataList.size() == 0) {
                return EMPTY_VIEW;
            }
            return super.getItemViewType(position);
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder vho, final int pos) {
            if (vho instanceof ViewHolder) {
                ViewHolder vh = (ViewHolder) vho;
                NetworkUri pi = dataList.get(pos);
                Glide.with(getContext())
                        .asBitmap()
                        .load(pi.getUri())
                        .into(vh.user);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v;

            if (viewType == EMPTY_VIEW) {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_network_user_empty, null);
                EmptyViewHolder evh = new EmptyViewHolder(v);
                return evh;
            }

            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_network_user, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        private static final int EMPTY_VIEW = 16;
    }

    private class MyAdapter5 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        public class EmptyViewHolder extends RecyclerView.ViewHolder {
            public EmptyViewHolder(View itemView) {
                super(itemView);
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView user;

            public ViewHolder(View v) {
                super(v);
                user = (ImageView) v.findViewById(R.id.user_image_card);
            }
        }

        @Override
        public int getItemCount() {
            return dataList.size() > 0 ? dataList.size() : 32;
        }

        @Override
        public int getItemViewType(int position) {
            if (dataList.size() == 0) {
                return EMPTY_VIEW;
            }
            return super.getItemViewType(position);
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder vho, final int pos) {
            if (vho instanceof ViewHolder) {
                ViewHolder vh = (ViewHolder) vho;
                NetworkUri pi = dataList.get(pos);
                Glide.with(getContext())
                        .asBitmap()
                        .load(pi.getUri())
                        .into(vh.user);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v;

            if (viewType == EMPTY_VIEW) {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_network_user_empty, null);
                EmptyViewHolder evh = new EmptyViewHolder(v);
                return evh;
            }

            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_network_user, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        private static final int EMPTY_VIEW = 16;
    }

    private class MyAdapter6 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        public class EmptyViewHolder extends RecyclerView.ViewHolder {
            public EmptyViewHolder(View itemView) {
                super(itemView);
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView user;

            public ViewHolder(View v) {
                super(v);
                user = (ImageView) v.findViewById(R.id.user_image_card);
            }
        }

        @Override
        public int getItemCount() {
            return dataList.size() > 0 ? dataList.size() : 64;
        }

        @Override
        public int getItemViewType(int position) {
            if (dataList.size() == 0) {
                return EMPTY_VIEW;
            }
            return super.getItemViewType(position);
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder vho, final int pos) {
            if (vho instanceof ViewHolder) {
                ViewHolder vh = (ViewHolder) vho;
                NetworkUri pi = dataList.get(pos);
                Glide.with(getContext())
                        .asBitmap()
                        .load(pi.getUri())
                        .into(vh.user);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v;

            if (viewType == EMPTY_VIEW) {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_network_user_empty, null);
                EmptyViewHolder evh = new EmptyViewHolder(v);
                return evh;
            }

            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_network_user, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        private static final int EMPTY_VIEW = 16;
    }

    private class MyAdapter7 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        public class EmptyViewHolder extends RecyclerView.ViewHolder {
            public EmptyViewHolder(View itemView) {
                super(itemView);
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView user;

            public ViewHolder(View v) {
                super(v);
                user = (ImageView) v.findViewById(R.id.user_image_card);
            }
        }

        @Override
        public int getItemCount() {
            return dataList.size() > 0 ? dataList.size() : 128;
        }

        @Override
        public int getItemViewType(int position) {
            if (dataList.size() == 0) {
                return EMPTY_VIEW;
            }
            return super.getItemViewType(position);
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder vho, final int pos) {
            if (vho instanceof ViewHolder) {
                ViewHolder vh = (ViewHolder) vho;
                NetworkUri pi = dataList.get(pos);
                Glide.with(getContext())
                        .asBitmap()
                        .load(pi.getUri())
                        .into(vh.user);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v;

            if (viewType == EMPTY_VIEW) {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_network_user_empty, null);
                EmptyViewHolder evh = new EmptyViewHolder(v);
                return evh;
            }

            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_network_user, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        private static final int EMPTY_VIEW = 16;
    }
    private void assigndownline() {
        id1 = v.findViewById(R.id.d1);
        id1.setOnClickListener(this);
        id2 = v.findViewById(R.id.d2);
        id2.setOnClickListener(this);
        id3 = v.findViewById(R.id.d3);
        id3.setOnClickListener(this);
        id4 = v.findViewById(R.id.d4);
        id4.setOnClickListener(this);
        id5 = v.findViewById(R.id.d5);
        id5.setOnClickListener(this);
        id6 = v.findViewById(R.id.d6);
        id6.setOnClickListener(this);
        id7 = v.findViewById(R.id.d7);
        id7.setOnClickListener(this);
        id8 = v.findViewById(R.id.d8);
        id8.setOnClickListener(this);
        id9 = v.findViewById(R.id.d9);
        id9.setOnClickListener(this);
        id10 = v.findViewById(R.id.d10);
        id10.setOnClickListener(this);
        id11 = v.findViewById(R.id.d11);
        id11.setOnClickListener(this);
        id12 = v.findViewById(R.id.d12);
        id12.setOnClickListener(this);
        id13 = v.findViewById(R.id.d13);
        id13.setOnClickListener(this);
        id14 = v.findViewById(R.id.d14);
        id14.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
            if(v == id1){
                // your stuff
            }
            else if(v == id2){
                // your stuff
            }
            else if(v == id3){
                // your stuff
            }
            else if(v == id4){
                // your stuff
            }
    }

    private void imageClick() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("User").child("Downline").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    NetworkUri data = postSnapshot.getValue(NetworkUri.class);
                    dataList.add(data);
                }
                recyclerView4.setAdapter(new MyAdapter());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void downlineTree() {
        myRef.child("user").child(userPhone).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String d1 = dataSnapshot.child("User").child(userPhone).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d2 = dataSnapshot.child("User").child(userPhone).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d3 = dataSnapshot.child("User").child(d1).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d4 = dataSnapshot.child("User").child(d1).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d5 = dataSnapshot.child("User").child(d2).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d6 = dataSnapshot.child("User").child(d2).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);


                    String d7 = dataSnapshot.child("User").child(d3).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d8 = dataSnapshot.child("User").child(d3).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d9 = dataSnapshot.child("User").child(d4).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d10 = dataSnapshot.child("User").child(d4).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d11 = dataSnapshot.child("User").child(d5).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d12 = dataSnapshot.child("User").child(d5).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d13 = dataSnapshot.child("User").child(d6).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d14 = dataSnapshot.child("User").child(d6).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d15 = dataSnapshot.child("User").child(d7).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d16 = dataSnapshot.child("User").child(d7).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d17 = dataSnapshot.child("User").child(d8).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d18 = dataSnapshot.child("User").child(d8).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d19 = dataSnapshot.child("User").child(d9).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d20 = dataSnapshot.child("User").child(d9).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d21 = dataSnapshot.child("User").child(d10).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d22 = dataSnapshot.child("User").child(d10).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d23 = dataSnapshot.child("User").child(d11).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d24 = dataSnapshot.child("User").child(d11).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d25 = dataSnapshot.child("User").child(d12).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d26 = dataSnapshot.child("User").child(d12).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d27 = dataSnapshot.child("User").child(d13).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d28 = dataSnapshot.child("User").child(d13).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d29 = dataSnapshot.child("User").child(d14).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d30 = dataSnapshot.child("User").child(d14).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d31 = dataSnapshot.child("User").child(d15).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d32 = dataSnapshot.child("User").child(d15).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d33 = dataSnapshot.child("User").child(d16).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d34 = dataSnapshot.child("User").child(d16).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d35 = dataSnapshot.child("User").child(d17).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d36 = dataSnapshot.child("User").child(d17).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d37 = dataSnapshot.child("User").child(d18).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d38 = dataSnapshot.child("User").child(d18).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d39 = dataSnapshot.child("User").child(d19).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d40 = dataSnapshot.child("User").child(d19).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d41 = dataSnapshot.child("User").child(d20).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d42 = dataSnapshot.child("User").child(d20).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d43 = dataSnapshot.child("User").child(d21).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d44 = dataSnapshot.child("User").child(d21).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d45 = dataSnapshot.child("User").child(d22).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d46 = dataSnapshot.child("User").child(d22).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d47 = dataSnapshot.child("User").child(d23).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d48 = dataSnapshot.child("User").child(d23).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d49 = dataSnapshot.child("User").child(d24).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d50 = dataSnapshot.child("User").child(d24).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d51 = dataSnapshot.child("User").child(d25).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d52 = dataSnapshot.child("User").child(d25).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d53 = dataSnapshot.child("User").child(d26).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d54 = dataSnapshot.child("User").child(d26).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d55 = dataSnapshot.child("User").child(d27).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d56 = dataSnapshot.child("User").child(d27).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d57 = dataSnapshot.child("User").child(d28).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d58 = dataSnapshot.child("User").child(d28).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d59 = dataSnapshot.child("User").child(d29).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d60 = dataSnapshot.child("User").child(d29).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d61 = dataSnapshot.child("User").child(d30).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d62 = dataSnapshot.child("User").child(d30).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d63 = dataSnapshot.child("User").child(d31).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d64 = dataSnapshot.child("User").child(d31).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d65 = dataSnapshot.child("User").child(d32).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d66 = dataSnapshot.child("User").child(d32).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d67 = dataSnapshot.child("User").child(d33).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d68 = dataSnapshot.child("User").child(d33).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d69 = dataSnapshot.child("User").child(d34).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d70 = dataSnapshot.child("User").child(d34).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d71 = dataSnapshot.child("User").child(d35).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d72 = dataSnapshot.child("User").child(d35).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d73 = dataSnapshot.child("User").child(d36).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d74 = dataSnapshot.child("User").child(d36).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d75 = dataSnapshot.child("User").child(d37).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d76 = dataSnapshot.child("User").child(d37).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d77 = dataSnapshot.child("User").child(d38).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d78 = dataSnapshot.child("User").child(d38).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d79 = dataSnapshot.child("User").child(d39).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d80 = dataSnapshot.child("User").child(d39).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d81 = dataSnapshot.child("User").child(d40).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d82 = dataSnapshot.child("User").child(d40).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d83 = dataSnapshot.child("User").child(d41).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d84 = dataSnapshot.child("User").child(d41).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d85 = dataSnapshot.child("User").child(d42).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d86 = dataSnapshot.child("User").child(d42).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d87 = dataSnapshot.child("User").child(d43).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d88 = dataSnapshot.child("User").child(d43).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d89 = dataSnapshot.child("User").child(d44).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d90 = dataSnapshot.child("User").child(d44).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d91 = dataSnapshot.child("User").child(d45).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d92 = dataSnapshot.child("User").child(d45).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d93 = dataSnapshot.child("User").child(d46).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d94 = dataSnapshot.child("User").child(d46).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d95 = dataSnapshot.child("User").child(d47).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d96 = dataSnapshot.child("User").child(d47).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d97 = dataSnapshot.child("User").child(d48).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d98 = dataSnapshot.child("User").child(d48).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d99 = dataSnapshot.child("User").child(d49).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d100 = dataSnapshot.child("User").child(d49).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d101 = dataSnapshot.child("User").child(d50).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d102 = dataSnapshot.child("User").child(d50).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d103 = dataSnapshot.child("User").child(d51).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d104 = dataSnapshot.child("User").child(d51).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d105 = dataSnapshot.child("User").child(d52).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d106 = dataSnapshot.child("User").child(d52).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d107 = dataSnapshot.child("User").child(d53).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d108 = dataSnapshot.child("User").child(d53).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d109 = dataSnapshot.child("User").child(d54).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d110 = dataSnapshot.child("User").child(d54).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d111 = dataSnapshot.child("User").child(d55).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d112 = dataSnapshot.child("User").child(d55).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d113 = dataSnapshot.child("User").child(d56).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d114 = dataSnapshot.child("User").child(d56).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d115 = dataSnapshot.child("User").child(d57).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d116 = dataSnapshot.child("User").child(d57).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d117 = dataSnapshot.child("User").child(d58).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d118 = dataSnapshot.child("User").child(d58).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d119 = dataSnapshot.child("User").child(d59).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d120 = dataSnapshot.child("User").child(d59).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d121 = dataSnapshot.child("User").child(d60).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d122 = dataSnapshot.child("User").child(d60).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d123 = dataSnapshot.child("User").child(d61).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d124 = dataSnapshot.child("User").child(d61).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d125 = dataSnapshot.child("User").child(d62).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d126 = dataSnapshot.child("User").child(d62).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d127 = dataSnapshot.child("User").child(d63).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d128 = dataSnapshot.child("User").child(d63).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d129 = dataSnapshot.child("User").child(d64).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d130 = dataSnapshot.child("User").child(d64).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d131 = dataSnapshot.child("User").child(d65).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d132 = dataSnapshot.child("User").child(d65).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d133 = dataSnapshot.child("User").child(d66).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d134 = dataSnapshot.child("User").child(d66).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d135 = dataSnapshot.child("User").child(d67).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d136 = dataSnapshot.child("User").child(d67).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d137 = dataSnapshot.child("User").child(d68).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d138 = dataSnapshot.child("User").child(d68).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d139 = dataSnapshot.child("User").child(d69).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d140 = dataSnapshot.child("User").child(d69).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d141 = dataSnapshot.child("User").child(d70).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d142 = dataSnapshot.child("User").child(d70).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d143 = dataSnapshot.child("User").child(d71).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d144 = dataSnapshot.child("User").child(d71).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d145 = dataSnapshot.child("User").child(d72).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d146 = dataSnapshot.child("User").child(d72).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d147 = dataSnapshot.child("User").child(d73).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d148 = dataSnapshot.child("User").child(d73).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d149 = dataSnapshot.child("User").child(d74).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d150 = dataSnapshot.child("User").child(d74).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d151 = dataSnapshot.child("User").child(d75).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d152 = dataSnapshot.child("User").child(d75).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d153 = dataSnapshot.child("User").child(d76).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d154 = dataSnapshot.child("User").child(d76).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d155 = dataSnapshot.child("User").child(d77).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d156 = dataSnapshot.child("User").child(d77).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d157 = dataSnapshot.child("User").child(d78).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d158 = dataSnapshot.child("User").child(d78).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d159 = dataSnapshot.child("User").child(d79).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d160 = dataSnapshot.child("User").child(d79).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d161 = dataSnapshot.child("User").child(d80).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d162 = dataSnapshot.child("User").child(d80).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d163 = dataSnapshot.child("User").child(d81).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d164 = dataSnapshot.child("User").child(d81).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d165 = dataSnapshot.child("User").child(d82).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d166 = dataSnapshot.child("User").child(d82).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d167 = dataSnapshot.child("User").child(d83).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d168 = dataSnapshot.child("User").child(d83).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d169 = dataSnapshot.child("User").child(d84).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d170 = dataSnapshot.child("User").child(d84).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d171 = dataSnapshot.child("User").child(d85).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d172 = dataSnapshot.child("User").child(d85).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d173 = dataSnapshot.child("User").child(d86).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d174 = dataSnapshot.child("User").child(d86).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d175 = dataSnapshot.child("User").child(d87).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d176 = dataSnapshot.child("User").child(d87).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d177 = dataSnapshot.child("User").child(d88).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d178 = dataSnapshot.child("User").child(d88).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d179 = dataSnapshot.child("User").child(d89).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d180 = dataSnapshot.child("User").child(d89).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d181 = dataSnapshot.child("User").child(d90).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d182 = dataSnapshot.child("User").child(d90).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d183 = dataSnapshot.child("User").child(d91).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d184 = dataSnapshot.child("User").child(d91).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d185 = dataSnapshot.child("User").child(d92).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d186 = dataSnapshot.child("User").child(d92).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d187 = dataSnapshot.child("User").child(d93).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d188 = dataSnapshot.child("User").child(d93).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d189 = dataSnapshot.child("User").child(d94).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d190 = dataSnapshot.child("User").child(d94).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d191 = dataSnapshot.child("User").child(d95).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d192 = dataSnapshot.child("User").child(d95).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d193 = dataSnapshot.child("User").child(d96).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d194 = dataSnapshot.child("User").child(d96).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d195 = dataSnapshot.child("User").child(d97).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d196 = dataSnapshot.child("User").child(d97).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d197 = dataSnapshot.child("User").child(d98).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d198 = dataSnapshot.child("User").child(d98).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d199 = dataSnapshot.child("User").child(d99).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d200 = dataSnapshot.child("User").child(d99).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d201 = dataSnapshot.child("User").child(d100).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d202 = dataSnapshot.child("User").child(d100).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d203 = dataSnapshot.child("User").child(d101).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d204 = dataSnapshot.child("User").child(d101).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d205 = dataSnapshot.child("User").child(d102).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d206 = dataSnapshot.child("User").child(d102).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d207 = dataSnapshot.child("User").child(d103).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d208 = dataSnapshot.child("User").child(d103).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d209 = dataSnapshot.child("User").child(d104).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d210 = dataSnapshot.child("User").child(d104).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d211 = dataSnapshot.child("User").child(d105).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d212 = dataSnapshot.child("User").child(d105).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d213 = dataSnapshot.child("User").child(d106).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d214 = dataSnapshot.child("User").child(d106).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d215 = dataSnapshot.child("User").child(d107).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d216 = dataSnapshot.child("User").child(d107).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d217 = dataSnapshot.child("User").child(d108).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d218 = dataSnapshot.child("User").child(d108).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d219 = dataSnapshot.child("User").child(d109).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d220 = dataSnapshot.child("User").child(d109).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d221 = dataSnapshot.child("User").child(d110).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d222 = dataSnapshot.child("User").child(d110).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d223 = dataSnapshot.child("User").child(d111).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d224 = dataSnapshot.child("User").child(d111).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d225 = dataSnapshot.child("User").child(d112).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d226 = dataSnapshot.child("User").child(d112).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d227 = dataSnapshot.child("User").child(d113).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d228 = dataSnapshot.child("User").child(d113).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d229 = dataSnapshot.child("User").child(d114).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d230 = dataSnapshot.child("User").child(d114).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d231 = dataSnapshot.child("User").child(d115).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d232 = dataSnapshot.child("User").child(d115).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d233 = dataSnapshot.child("User").child(d116).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d234 = dataSnapshot.child("User").child(d116).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d235 = dataSnapshot.child("User").child(d117).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d236 = dataSnapshot.child("User").child(d117).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d237 = dataSnapshot.child("User").child(d118).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d238 = dataSnapshot.child("User").child(d118).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d239 = dataSnapshot.child("User").child(d119).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d240 = dataSnapshot.child("User").child(d119).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d241 = dataSnapshot.child("User").child(d120).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d242 = dataSnapshot.child("User").child(d120).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d243 = dataSnapshot.child("User").child(d121).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d244 = dataSnapshot.child("User").child(d121).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d245 = dataSnapshot.child("User").child(d122).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d246 = dataSnapshot.child("User").child(d122).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d247 = dataSnapshot.child("User").child(d123).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d248 = dataSnapshot.child("User").child(d123).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d249 = dataSnapshot.child("User").child(d124).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d250 = dataSnapshot.child("User").child(d124).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d251 = dataSnapshot.child("User").child(d125).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d252 = dataSnapshot.child("User").child(d125).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                    String d253 = dataSnapshot.child("User").child(d126).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                    String d254 = dataSnapshot.child("User").child(d126).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);






                    if (d3 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring2").child("downline3").setValue(d3);

                    }else  if (d4 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring2").child("downline4").setValue(d4);
                    }else  if (d5 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring2").child("downline5").setValue(d5);
                    }else  if (d6 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring2").child("downline6").setValue(d6);
                    }else  if (d7 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring3").child("downline7").setValue(d7);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring2").child("downline3").setValue(d7);
                    }else  if (d8 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring3").child("downline8").setValue(d8);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring2").child("downline4").setValue(d8);
                    }else  if (d9 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring3").child("downline9").setValue(d9);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring2").child("downline5").setValue(d9);
                    }else  if (d10 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring3").child("downline10").setValue(d10);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring2").child("downline6").setValue(d10);
                    }else  if (d11 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring3").child("downline11").setValue(d11);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring2").child("downline3").setValue(d11);
                    }else  if (d12 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring3").child("downline12").setValue(d12);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring2").child("downline4").setValue(d12);
                    }else  if (d13 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring3").child("downline13").setValue(d13);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring2").child("downline5").setValue(d13);
                    }else  if (d14 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring3").child("downline14").setValue(d14);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring2").child("downline6").setValue(d14);
                    }else  if (d15 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring4").child("downline15").setValue(d15);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring3").child("downline7").setValue(d15);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring2").child("downline3").setValue(d15);
                    }else  if (d16 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring4").child("downline16").setValue(d16);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring3").child("downline8").setValue(d16);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring2").child("downline4").setValue(d16);
                    }else  if (d17 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring4").child("downline17").setValue(d17);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring3").child("downline9").setValue(d17);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring2").child("downline5").setValue(d17);
                    }else  if (d18 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring4").child("downline18").setValue(d18);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring3").child("downline10").setValue(d18);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring2").child("downline6").setValue(d18);
                    }else  if (d19 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring4").child("downline19").setValue(d19);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring3").child("downline11").setValue(d19);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring2").child("downline3").setValue(d19);
                    }else  if (d20 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring4").child("downline20").setValue(d20);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring3").child("downline12").setValue(d20);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring2").child("downline4").setValue(d20);
                    }else  if (d21 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring4").child("downline21").setValue(d21);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring3").child("downline13").setValue(d21);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring2").child("downline5").setValue(d21);
                    }else  if (d22 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring4").child("downline22").setValue(d22);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring3").child("downline14").setValue(d22);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring2").child("downline6").setValue(d22);
                    }else  if (d23 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring4").child("downline23").setValue(d23);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring3").child("downline7").setValue(d23);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring2").child("downline3").setValue(d23);
                    }else  if (d24 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring4").child("downline24").setValue(d24);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring3").child("downline8").setValue(d24);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring2").child("downline4").setValue(d24);
                    }else  if (d25 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring4").child("downline25").setValue(d25);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring3").child("downline9").setValue(d25);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring2").child("downline5").setValue(d25);
                    }else  if (d26 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring4").child("downline26").setValue(d26);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring3").child("downline10").setValue(d26);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring2").child("downline6").setValue(d26);
                    }else  if (d27 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring4").child("downline27").setValue(d27);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring3").child("downline11").setValue(d27);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring2").child("downline3").setValue(d27);
                    }else  if (d28 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring4").child("downline28").setValue(d28);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring3").child("downline12").setValue(d28);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring2").child("downline4").setValue(d28);
                    }else  if (d29 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring4").child("downline29").setValue(d29);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring3").child("downline13").setValue(d29);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring2").child("downline5").setValue(d29);
                    }else  if (d30 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring4").child("downline30").setValue(d30);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring3").child("downline14").setValue(d30);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring2").child("downline6").setValue(d30);
                    }else  if (d31 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring5").child("downline31").setValue(d31);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring4").child("downline15").setValue(d31);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring3").child("downline7").setValue(d31);
                        myRef.child("User").child(d7).child("downlineTree").child("Ring2").child("downline3").setValue(d31);
                    }else  if (d32 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring5").child("downline32").setValue(d32);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring4").child("downline16").setValue(d32);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring3").child("downline8").setValue(d32);
                        myRef.child("User").child(d7).child("downlineTree").child("Ring2").child("downline4").setValue(d32);
                    }else  if (d33 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring5").child("downline33").setValue(d33);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring4").child("downline17").setValue(d33);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring3").child("downline9").setValue(d33);
                        myRef.child("User").child(d7).child("downlineTree").child("Ring2").child("downline5").setValue(d33);
                    }else  if (d34 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring5").child("downline34").setValue(d34);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring4").child("downline18").setValue(d34);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring3").child("downline10").setValue(d34);
                        myRef.child("User").child(d7).child("downlineTree").child("Ring2").child("downline6").setValue(d34);
                    }else  if (d35 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring5").child("downline35").setValue(d35);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring4").child("downline19").setValue(d35);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring3").child("downline11").setValue(d35);
                        myRef.child("User").child(d8).child("downlineTree").child("Ring2").child("downline3").setValue(d35);
                    }else  if (d36 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring5").child("downline36").setValue(d36);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring4").child("downline20").setValue(d36);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring3").child("downline12").setValue(d36);
                        myRef.child("User").child(d8).child("downlineTree").child("Ring2").child("downline4").setValue(d36);
                    }else  if (d37 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring5").child("downline37").setValue(d37);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring4").child("downline21").setValue(d37);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring3").child("downline13").setValue(d37);
                        myRef.child("User").child(d8).child("downlineTree").child("Ring2").child("downline5").setValue(d37);
                    }else  if (d38 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring5").child("downline38").setValue(d38);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring4").child("downline22").setValue(d38);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring3").child("downline14").setValue(d38);
                        myRef.child("User").child(d8).child("downlineTree").child("Ring2").child("downline6").setValue(d38);
                    }else  if (d39 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring5").child("downline39").setValue(d39);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring4").child("downline23").setValue(d39);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring3").child("downline7").setValue(d39);
                        myRef.child("User").child(d9).child("downlineTree").child("Ring2").child("downline3").setValue(d39);
                    }else  if (d40 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring5").child("downline40").setValue(d40);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring4").child("downline24").setValue(d40);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring3").child("downline8").setValue(d40);
                        myRef.child("User").child(d9).child("downlineTree").child("Ring2").child("downline4").setValue(d40);
                    }else  if (d41 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring5").child("downline41").setValue(d41);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring4").child("downline25").setValue(d41);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring3").child("downline9").setValue(d41);
                        myRef.child("User").child(d9).child("downlineTree").child("Ring2").child("downline5").setValue(d41);
                    }else  if (d42 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring5").child("downline42").setValue(d42);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring4").child("downline26").setValue(d42);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring3").child("downline10").setValue(d42);
                        myRef.child("User").child(d9).child("downlineTree").child("Ring2").child("downline6").setValue(d42);
                    }else  if (d43 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring5").child("downline43").setValue(d43);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring4").child("downline27").setValue(d43);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring3").child("downline11").setValue(d43);
                        myRef.child("User").child(d10).child("downlineTree").child("Ring2").child("downline3").setValue(d43);
                    }else  if (d44 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring5").child("downline44").setValue(d44);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring4").child("downline28").setValue(d44);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring3").child("downline12").setValue(d44);
                        myRef.child("User").child(d10).child("downlineTree").child("Ring2").child("downline4").setValue(d44);
                    }else  if (d45 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring5").child("downline45").setValue(d45);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring4").child("downline29").setValue(d45);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring3").child("downline13").setValue(d45);
                        myRef.child("User").child(d10).child("downlineTree").child("Ring2").child("downline5").setValue(d45);
                    }else  if (d46 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring5").child("downline46").setValue(d46);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring4").child("downline30").setValue(d46);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring3").child("downline14").setValue(d46);
                        myRef.child("User").child(d10).child("downlineTree").child("Ring2").child("downline6").setValue(d46);
                    }else  if (d47 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring5").child("downline47").setValue(d47);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring4").child("downline15").setValue(d47);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring3").child("downline7").setValue(d47);
                        myRef.child("User").child(d11).child("downlineTree").child("Ring2").child("downline3").setValue(d47);
                    }else  if (d48 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring5").child("downline48").setValue(d48);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring4").child("downline16").setValue(d48);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring3").child("downline8").setValue(d48);
                        myRef.child("User").child(d11).child("downlineTree").child("Ring2").child("downline4").setValue(d48);
                    }else  if (d49 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring5").child("downline49").setValue(d49);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring4").child("downline17").setValue(d49);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring3").child("downline9").setValue(d49);
                        myRef.child("User").child(d11).child("downlineTree").child("Ring2").child("downline5").setValue(d49);
                    }else  if (d50 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring5").child("downline50").setValue(d50);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring4").child("downline18").setValue(d50);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring3").child("downline10").setValue(d50);
                        myRef.child("User").child(d11).child("downlineTree").child("Ring2").child("downline6").setValue(d50);
                    }else  if (d51 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring5").child("downline51").setValue(d51);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring4").child("downline19").setValue(d51);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring3").child("downline11").setValue(d51);
                        myRef.child("User").child(d12).child("downlineTree").child("Ring2").child("downline3").setValue(d51);
                    }else  if (d52 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring5").child("downline52").setValue(d52);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring4").child("downline20").setValue(d52);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring3").child("downline12").setValue(d52);
                        myRef.child("User").child(d12).child("downlineTree").child("Ring2").child("downline4").setValue(d52);
                    }else  if (d53 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring5").child("downline53").setValue(d53);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring4").child("downline21").setValue(d53);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring3").child("downline13").setValue(d53);
                        myRef.child("User").child(d12).child("downlineTree").child("Ring2").child("downline5").setValue(d53);
                    }else  if (d54 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring5").child("downline54").setValue(d54);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring4").child("downline22").setValue(d54);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring3").child("downline14").setValue(d54);
                        myRef.child("User").child(d12).child("downlineTree").child("Ring2").child("downline6").setValue(d54);
                    }else  if (d55 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring5").child("downline55").setValue(d55);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring4").child("downline23").setValue(d55);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring3").child("downline7").setValue(d55);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring2").child("downline3").setValue(d55);
                    }else  if (d56 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring5").child("downline56").setValue(d56);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring4").child("downline24").setValue(d56);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring3").child("downline8").setValue(d56);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring2").child("downline4").setValue(d56);
                    }else  if (d57 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring5").child("downline57").setValue(d57);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring4").child("downline25").setValue(d57);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring3").child("downline9").setValue(d57);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring2").child("downline5").setValue(d57);
                    }else  if (d58 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring5").child("downline58").setValue(d58);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring4").child("downline26").setValue(d58);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring3").child("downline10").setValue(d58);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring2").child("downline6").setValue(d58);
                    }else  if (d59 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring5").child("downline59").setValue(d59);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring4").child("downline27").setValue(d59);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring3").child("downline11").setValue(d59);
                        myRef.child("User").child(d14).child("downlineTree").child("Ring2").child("downline3").setValue(d59);
                    }else  if (d60 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring5").child("downline60").setValue(d60);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring4").child("downline28").setValue(d60);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring3").child("downline12").setValue(d60);
                        myRef.child("User").child(d14).child("downlineTree").child("Ring2").child("downline4").setValue(d60);
                    }else  if (d61 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring5").child("downline61").setValue(d61);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring4").child("downline29").setValue(d61);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring3").child("downline13").setValue(d61);
                        myRef.child("User").child(d14).child("downlineTree").child("Ring2").child("downline5").setValue(d61);
                    }else  if (d62 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring5").child("downline62").setValue(d62);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring4").child("downline30").setValue(d62);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring3").child("downline14").setValue(d62);
                        myRef.child("User").child(d14).child("downlineTree").child("Ring2").child("downline6").setValue(d62);
                    }else  if (d63 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline63").setValue(d63);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring5").child("downline31").setValue(d63);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring4").child("downline15").setValue(d63);
                        myRef.child("User").child(d7).child("downlineTree").child("Ring3").child("downline7").setValue(d63);
                        myRef.child("User").child(d15).child("downlineTree").child("Ring2").child("downline3").setValue(d63);
                    }else  if (d64 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline64").setValue(d64);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring5").child("downline32").setValue(d64);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring4").child("downline16").setValue(d64);
                        myRef.child("User").child(d7).child("downlineTree").child("Ring3").child("downline8").setValue(d64);
                        myRef.child("User").child(d15).child("downlineTree").child("Ring2").child("downline4").setValue(d64);
                    }else  if (d65 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline65").setValue(d65);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring5").child("downline33").setValue(d65);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring4").child("downline17").setValue(d65);
                        myRef.child("User").child(d7).child("downlineTree").child("Ring3").child("downline9").setValue(d65);
                        myRef.child("User").child(d15).child("downlineTree").child("Ring2").child("downline5").setValue(d65);
                    }else  if (d66 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline66").setValue(d66);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring5").child("downline34").setValue(d66);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring4").child("downline18").setValue(d66);
                        myRef.child("User").child(d7).child("downlineTree").child("Ring3").child("downline10").setValue(d66);
                        myRef.child("User").child(d15).child("downlineTree").child("Ring2").child("downline6").setValue(d66);
                    }else  if (d67 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline67").setValue(d67);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring5").child("downline35").setValue(d67);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring4").child("downline19").setValue(d67);
                        myRef.child("User").child(d7).child("downlineTree").child("Ring3").child("downline11").setValue(d67);
                        myRef.child("User").child(d16).child("downlineTree").child("Ring2").child("downline3").setValue(d67);
                    }else  if (d68 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline68").setValue(d68);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring5").child("downline36").setValue(d68);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring4").child("downline20").setValue(d68);
                        myRef.child("User").child(d7).child("downlineTree").child("Ring3").child("downline12").setValue(d68);
                        myRef.child("User").child(d16).child("downlineTree").child("Ring2").child("downline4").setValue(d68);
                    }else  if (d69 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline69").setValue(d69);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring5").child("downline37").setValue(d69);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring4").child("downline21").setValue(d69);
                        myRef.child("User").child(d7).child("downlineTree").child("Ring3").child("downline13").setValue(d69);
                        myRef.child("User").child(d16).child("downlineTree").child("Ring2").child("downline5").setValue(d69);
                    }else  if (d70 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline70").setValue(d70);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring5").child("downline38").setValue(d70);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring4").child("downline22").setValue(d70);
                        myRef.child("User").child(d7).child("downlineTree").child("Ring3").child("downline14").setValue(d70);
                        myRef.child("User").child(d16).child("downlineTree").child("Ring2").child("downline6").setValue(d70);
                    }else  if (d71 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline71").setValue(d71);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring5").child("downline39").setValue(d71);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring4").child("downline23").setValue(d71);
                        myRef.child("User").child(d8).child("downlineTree").child("Ring3").child("downline7").setValue(d71);
                        myRef.child("User").child(d17).child("downlineTree").child("Ring2").child("downline3").setValue(d71);
                    }else  if (d72 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline72").setValue(d72);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring5").child("downline40").setValue(d72);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring4").child("downline24").setValue(d72);
                        myRef.child("User").child(d8).child("downlineTree").child("Ring3").child("downline8").setValue(d72);
                        myRef.child("User").child(d17).child("downlineTree").child("Ring2").child("downline4").setValue(d72);
                    }else  if (d73 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline73").setValue(d73);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring5").child("downline41").setValue(d73);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring4").child("downline25").setValue(d73);
                        myRef.child("User").child(d8).child("downlineTree").child("Ring3").child("downline9").setValue(d73);
                        myRef.child("User").child(d17).child("downlineTree").child("Ring2").child("downline5").setValue(d73);
                    }else  if (d74 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline74").setValue(d74);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring5").child("downline42").setValue(d74);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring4").child("downline26").setValue(d74);
                        myRef.child("User").child(d8).child("downlineTree").child("Ring3").child("downline10").setValue(d74);
                        myRef.child("User").child(d17).child("downlineTree").child("Ring2").child("downline6").setValue(d74);
                    }else  if (d75 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline75").setValue(d75);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring5").child("downline43").setValue(d75);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring4").child("downline27").setValue(d75);
                        myRef.child("User").child(d8).child("downlineTree").child("Ring3").child("downline11").setValue(d75);
                        myRef.child("User").child(d18).child("downlineTree").child("Ring2").child("downline3").setValue(d75);
                    }else  if (d76 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline76").setValue(d76);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring5").child("downline44").setValue(d76);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring4").child("downline28").setValue(d76);
                        myRef.child("User").child(d8).child("downlineTree").child("Ring3").child("downline12").setValue(d76);
                        myRef.child("User").child(d18).child("downlineTree").child("Ring2").child("downline4").setValue(d76);
                    }else  if (d77 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline77").setValue(d77);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring5").child("downline45").setValue(d77);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring4").child("downline29").setValue(d77);
                        myRef.child("User").child(d8).child("downlineTree").child("Ring3").child("downline13").setValue(d77);
                        myRef.child("User").child(d18).child("downlineTree").child("Ring2").child("downline5").setValue(d77);
                    }else  if (d78 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline78").setValue(d78);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring5").child("downline46").setValue(d78);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring4").child("downline30").setValue(d78);
                        myRef.child("User").child(d8).child("downlineTree").child("Ring3").child("downline14").setValue(d78);
                        myRef.child("User").child(d18).child("downlineTree").child("Ring2").child("downline6").setValue(d78);
                    }else  if (d79 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline79").setValue(d79);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring5").child("downline47").setValue(d79);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring4").child("downline15").setValue(d79);
                        myRef.child("User").child(d9).child("downlineTree").child("Ring3").child("downline7").setValue(d79);
                        myRef.child("User").child(d19).child("downlineTree").child("Ring2").child("downline3").setValue(d79);
                    }else  if (d80 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline80").setValue(d80);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring5").child("downline48").setValue(d80);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring4").child("downline16").setValue(d80);
                        myRef.child("User").child(d9).child("downlineTree").child("Ring3").child("downline8").setValue(d80);
                        myRef.child("User").child(d19).child("downlineTree").child("Ring2").child("downline4").setValue(d80);
                    }else  if (d81 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline81").setValue(d81);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring5").child("downline49").setValue(d81);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring4").child("downline17").setValue(d81);
                        myRef.child("User").child(d9).child("downlineTree").child("Ring3").child("downline9").setValue(d81);
                        myRef.child("User").child(d19).child("downlineTree").child("Ring2").child("downline5").setValue(d81);
                    }else  if (d82 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline82").setValue(d82);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring5").child("downline50").setValue(d82);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring4").child("downline18").setValue(d82);
                        myRef.child("User").child(d9).child("downlineTree").child("Ring3").child("downline10").setValue(d82);
                        myRef.child("User").child(d19).child("downlineTree").child("Ring2").child("downline6").setValue(d82);
                    }else  if (d83 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline83").setValue(d83);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring5").child("downline51").setValue(d83);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring4").child("downline19").setValue(d83);
                        myRef.child("User").child(d9).child("downlineTree").child("Ring3").child("downline11").setValue(d83);
                        myRef.child("User").child(d20).child("downlineTree").child("Ring2").child("downline3").setValue(d83);
                    }else  if (d84 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline84").setValue(d84);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring5").child("downline52").setValue(d84);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring4").child("downline20").setValue(d84);
                        myRef.child("User").child(d9).child("downlineTree").child("Ring3").child("downline12").setValue(d84);
                        myRef.child("User").child(d20).child("downlineTree").child("Ring2").child("downline4").setValue(d84);
                    }else  if (d85 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline85").setValue(d85);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring5").child("downline53").setValue(d85);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring4").child("downline21").setValue(d85);
                        myRef.child("User").child(d9).child("downlineTree").child("Ring3").child("downline13").setValue(d85);
                        myRef.child("User").child(d20).child("downlineTree").child("Ring2").child("downline5").setValue(d85);
                    }else  if (d86 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline86").setValue(d86);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring5").child("downline54").setValue(d86);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring4").child("downline22").setValue(d86);
                        myRef.child("User").child(d9).child("downlineTree").child("Ring3").child("downline14").setValue(d86);
                        myRef.child("User").child(d20).child("downlineTree").child("Ring2").child("downline6").setValue(d86);
                    }else  if (d87 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline87").setValue(d87);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring5").child("downline55").setValue(d87);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring4").child("downline23").setValue(d87);
                        myRef.child("User").child(d10).child("downlineTree").child("Ring3").child("downline7").setValue(d87);
                        myRef.child("User").child(d21).child("downlineTree").child("Ring2").child("downline3").setValue(d87);
                    }else  if (d88 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline88").setValue(d88);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring5").child("downline56").setValue(d88);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring4").child("downline24").setValue(d88);
                        myRef.child("User").child(d10).child("downlineTree").child("Ring3").child("downline8").setValue(d88);
                        myRef.child("User").child(d21).child("downlineTree").child("Ring2").child("downline4").setValue(d88);
                    }else  if (d89 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline89").setValue(d89);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring5").child("downline57").setValue(d89);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring4").child("downline25").setValue(d89);
                        myRef.child("User").child(d10).child("downlineTree").child("Ring3").child("downline9").setValue(d89);
                        myRef.child("User").child(d21).child("downlineTree").child("Ring2").child("downline5").setValue(d89);
                    }else  if (d90 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline90").setValue(d90);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring5").child("downline58").setValue(d90);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring4").child("downline26").setValue(d90);
                        myRef.child("User").child(d10).child("downlineTree").child("Ring3").child("downline10").setValue(d90);
                        myRef.child("User").child(d21).child("downlineTree").child("Ring2").child("downline6").setValue(d90);
                    }else  if (d91 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline91").setValue(d91);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring5").child("downline59").setValue(d91);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring4").child("downline27").setValue(d91);
                        myRef.child("User").child(d10).child("downlineTree").child("Ring3").child("downline11").setValue(d91);
                        myRef.child("User").child(d22).child("downlineTree").child("Ring2").child("downline3").setValue(d91);
                    }else  if (d92 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline92").setValue(d92);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring5").child("downline60").setValue(d92);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring4").child("downline28").setValue(d92);
                        myRef.child("User").child(d10).child("downlineTree").child("Ring3").child("downline12").setValue(d92);
                        myRef.child("User").child(d22).child("downlineTree").child("Ring2").child("downline4").setValue(d92);
                    }else  if (d93 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline93").setValue(d93);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring5").child("downline61").setValue(d93);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring4").child("downline29").setValue(d93);
                        myRef.child("User").child(d10).child("downlineTree").child("Ring3").child("downline13").setValue(d93);
                        myRef.child("User").child(d22).child("downlineTree").child("Ring2").child("downline5").setValue(d93);
                    }else  if (d94 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline94").setValue(d94);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring5").child("downline62").setValue(d94);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring4").child("downline30").setValue(d94);
                        myRef.child("User").child(d10).child("downlineTree").child("Ring3").child("downline14").setValue(d94);
                        myRef.child("User").child(d22).child("downlineTree").child("Ring2").child("downline6").setValue(d94);
                    }else  if (d95 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline95").setValue(d95);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring5").child("downline31").setValue(d95);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring4").child("downline15").setValue(d95);
                        myRef.child("User").child(d11).child("downlineTree").child("Ring3").child("downline7").setValue(d95);
                        myRef.child("User").child(d23).child("downlineTree").child("Ring2").child("downline3").setValue(d95);
                    }else  if (d96 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline96").setValue(d96);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring5").child("downline32").setValue(d96);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring4").child("downline16").setValue(d96);
                        myRef.child("User").child(d11).child("downlineTree").child("Ring3").child("downline8").setValue(d96);
                        myRef.child("User").child(d23).child("downlineTree").child("Ring2").child("downline4").setValue(d96);
                    }else  if (d97 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline97").setValue(d97);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring5").child("downline33").setValue(d97);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring4").child("downline17").setValue(d97);
                        myRef.child("User").child(d11).child("downlineTree").child("Ring3").child("downline9").setValue(d97);
                        myRef.child("User").child(d23).child("downlineTree").child("Ring2").child("downline5").setValue(d97);
                    }else if (d98 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline98").setValue(d98);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring5").child("downline34").setValue(d98);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring4").child("downline18").setValue(d98);
                        myRef.child("User").child(d11).child("downlineTree").child("Ring3").child("downline10").setValue(d98);
                        myRef.child("User").child(d23).child("downlineTree").child("Ring2").child("downline6").setValue(d98);
                    }else if (d99 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline99").setValue(d99);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring5").child("downline35").setValue(d99);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring4").child("downline19").setValue(d99);
                        myRef.child("User").child(d11).child("downlineTree").child("Ring3").child("downline11").setValue(d99);
                        myRef.child("User").child(d24).child("downlineTree").child("Ring2").child("downline3").setValue(d99);
                    }else if (d100 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline100").setValue(d100);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring5").child("downline36").setValue(d100);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring4").child("downline20").setValue(d100);
                        myRef.child("User").child(d11).child("downlineTree").child("Ring3").child("downline12").setValue(d100);
                        myRef.child("User").child(d24).child("downlineTree").child("Ring2").child("downline4").setValue(d100);
                    }else if (d101 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline101").setValue(d101);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring5").child("downline37").setValue(d101);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring4").child("downline21").setValue(d101);
                        myRef.child("User").child(d11).child("downlineTree").child("Ring3").child("downline13").setValue(d101);
                        myRef.child("User").child(d24).child("downlineTree").child("Ring2").child("downline5").setValue(d101);
                    }else if (d102 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline102").setValue(d102);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring5").child("downline38").setValue(d102);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring4").child("downline22").setValue(d102);
                        myRef.child("User").child(d11).child("downlineTree").child("Ring3").child("downline14").setValue(d102);
                        myRef.child("User").child(d24).child("downlineTree").child("Ring2").child("downline6").setValue(d102);
                    }else if (d103 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline103").setValue(d103);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring5").child("downline39").setValue(d103);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring4").child("downline23").setValue(d103);
                        myRef.child("User").child(d12).child("downlineTree").child("Ring3").child("downline7").setValue(d103);
                        myRef.child("User").child(d25).child("downlineTree").child("Ring2").child("downline3").setValue(d103);
                    }else if (d104 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline104").setValue(d104);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring5").child("downline40").setValue(d104);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring4").child("downline24").setValue(d104);
                        myRef.child("User").child(d12).child("downlineTree").child("Ring3").child("downline8").setValue(d104);
                        myRef.child("User").child(d25).child("downlineTree").child("Ring2").child("downline4").setValue(d104);
                    }else if (d105 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline105").setValue(d105);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring5").child("downline41").setValue(d105);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring4").child("downline25").setValue(d105);
                        myRef.child("User").child(d12).child("downlineTree").child("Ring3").child("downline9").setValue(d105);
                        myRef.child("User").child(d25).child("downlineTree").child("Ring2").child("downline5").setValue(d105);
                    }else if (d106 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline106").setValue(d106);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring5").child("downline42").setValue(d106);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring4").child("downline26").setValue(d106);
                        myRef.child("User").child(d12).child("downlineTree").child("Ring3").child("downline10").setValue(d106);
                        myRef.child("User").child(d25).child("downlineTree").child("Ring2").child("downline6").setValue(d106);
                    }else if (d107 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline107").setValue(d107);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring5").child("downline43").setValue(d107);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring4").child("downline27").setValue(d107);
                        myRef.child("User").child(d12).child("downlineTree").child("Ring3").child("downline11").setValue(d107);
                        myRef.child("User").child(d26).child("downlineTree").child("Ring2").child("downline3").setValue(d107);
                    }else if (d108 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline108").setValue(d108);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring5").child("downline44").setValue(d108);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring4").child("downline28").setValue(d108);
                        myRef.child("User").child(d12).child("downlineTree").child("Ring3").child("downline12").setValue(d108);
                        myRef.child("User").child(d26).child("downlineTree").child("Ring2").child("downline4").setValue(d108);
                    }else if (d109 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline109").setValue(d109);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring5").child("downline45").setValue(d109);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring4").child("downline29").setValue(d109);
                        myRef.child("User").child(d12).child("downlineTree").child("Ring3").child("downline13").setValue(d109);
                        myRef.child("User").child(d26).child("downlineTree").child("Ring2").child("downline5").setValue(d109);
                    }else if (d110 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline110").setValue(d110);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring5").child("downline46").setValue(d110);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring4").child("downline30").setValue(d110);
                        myRef.child("User").child(d12).child("downlineTree").child("Ring3").child("downline14").setValue(d110);
                        myRef.child("User").child(d26).child("downlineTree").child("Ring2").child("downline6").setValue(d110);
                    }else if (d111 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline111").setValue(d111);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring5").child("downline47").setValue(d111);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring4").child("downline15").setValue(d111);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring3").child("downline7").setValue(d111);
                        myRef.child("User").child(d27).child("downlineTree").child("Ring2").child("downline3").setValue(d111);
                    }else if (d112 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline112").setValue(d112);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring5").child("downline48").setValue(d112);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring4").child("downline16").setValue(d112);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring3").child("downline8").setValue(d112);
                        myRef.child("User").child(d27).child("downlineTree").child("Ring2").child("downline4").setValue(d112);
                    }else if (d113 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline113").setValue(d113);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring5").child("downline49").setValue(d113);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring4").child("downline17").setValue(d113);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring3").child("downline9").setValue(d113);
                        myRef.child("User").child(d27).child("downlineTree").child("Ring2").child("downline5").setValue(d113);
                    }else if (d114 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline114").setValue(d114);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring5").child("downline50").setValue(d114);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring4").child("downline18").setValue(d114);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring3").child("downline10").setValue(d114);
                        myRef.child("User").child(d27).child("downlineTree").child("Ring2").child("downline6").setValue(d114);
                    }else if (d115 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline115").setValue(d115);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring5").child("downline51").setValue(d115);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring4").child("downline19").setValue(d115);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring3").child("downline11").setValue(d115);
                        myRef.child("User").child(d28).child("downlineTree").child("Ring2").child("downline3").setValue(d115);
                    }else if (d116 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline116").setValue(d116);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring5").child("downline52").setValue(d116);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring4").child("downline20").setValue(d116);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring3").child("downline12").setValue(d116);
                        myRef.child("User").child(d28).child("downlineTree").child("Ring2").child("downline4").setValue(d116);
                    }else if (d117 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline117").setValue(d117);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring5").child("downline53").setValue(d117);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring4").child("downline21").setValue(d117);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring3").child("downline13").setValue(d117);
                        myRef.child("User").child(d28).child("downlineTree").child("Ring2").child("downline5").setValue(d117);
                    }else if (d118 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline118").setValue(d118);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring5").child("downline54").setValue(d118);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring4").child("downline22").setValue(d118);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring3").child("downline14").setValue(d118);
                        myRef.child("User").child(d28).child("downlineTree").child("Ring2").child("downline6").setValue(d118);
                    }else if (d119 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline119").setValue(d119);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring5").child("downline55").setValue(d119);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring4").child("downline23").setValue(d119);
                        myRef.child("User").child(d14).child("downlineTree").child("Ring3").child("downline7").setValue(d119);
                        myRef.child("User").child(d29).child("downlineTree").child("Ring2").child("downline3").setValue(d119);
                    }else if (d120 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline120").setValue(d120);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring5").child("downline56").setValue(d120);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring4").child("downline24").setValue(d120);
                        myRef.child("User").child(d14).child("downlineTree").child("Ring3").child("downline8").setValue(d120);
                        myRef.child("User").child(d29).child("downlineTree").child("Ring2").child("downline4").setValue(d120);
                    }else if (d121 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline121").setValue(d121);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring5").child("downline57").setValue(d121);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring4").child("downline25").setValue(d121);
                        myRef.child("User").child(d14).child("downlineTree").child("Ring3").child("downline9").setValue(d121);
                        myRef.child("User").child(d29).child("downlineTree").child("Ring2").child("downline5").setValue(d121);
                    }else if (d122 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline122").setValue(d122);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring5").child("downline58").setValue(d122);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring4").child("downline26").setValue(d122);
                        myRef.child("User").child(d14).child("downlineTree").child("Ring3").child("downline10").setValue(d122);
                        myRef.child("User").child(d29).child("downlineTree").child("Ring2").child("downline6").setValue(d122);
                    }else if (d123 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline123").setValue(d123);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring5").child("downline59").setValue(d123);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring4").child("downline27").setValue(d123);
                        myRef.child("User").child(d14).child("downlineTree").child("Ring3").child("downline11").setValue(d123);
                        myRef.child("User").child(d30).child("downlineTree").child("Ring2").child("downline3").setValue(d123);
                    }else if (d124 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline124").setValue(d124);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring5").child("downline60").setValue(d124);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring4").child("downline28").setValue(d124);
                        myRef.child("User").child(d14).child("downlineTree").child("Ring3").child("downline12").setValue(d124);
                        myRef.child("User").child(d30).child("downlineTree").child("Ring2").child("downline4").setValue(d124);
                    }else if (d125 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline125").setValue(d125);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring5").child("downline61").setValue(d125);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring4").child("downline29").setValue(d125);
                        myRef.child("User").child(d14).child("downlineTree").child("Ring3").child("downline13").setValue(d125);
                        myRef.child("User").child(d30).child("downlineTree").child("Ring2").child("downline5").setValue(d125);
                    }else if (d126 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring6").child("downline126").setValue(d126);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring5").child("downline62").setValue(d126);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring4").child("downline30").setValue(d126);
                        myRef.child("User").child(d14).child("downlineTree").child("Ring3").child("downline14").setValue(d126);
                        myRef.child("User").child(d30).child("downlineTree").child("Ring2").child("downline6").setValue(d126);
                    }else if (d127 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline127").setValue(d127);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline63").setValue(d127);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring5").child("downline31").setValue(d127);
                        myRef.child("User").child(d7).child("downlineTree").child("Ring4").child("downline15").setValue(d127);
                        myRef.child("User").child(d15).child("downlineTree").child("Ring3").child("downline7").setValue(d127);
                        myRef.child("User").child(d31).child("downlineTree").child("Ring2").child("downline3").setValue(d127);
                    }else if (d128 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline128").setValue(d128);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline64").setValue(d128);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring5").child("downline32").setValue(d128);
                        myRef.child("User").child(d7).child("downlineTree").child("Ring4").child("downline16").setValue(d128);
                        myRef.child("User").child(d15).child("downlineTree").child("Ring3").child("downline8").setValue(d128);
                        myRef.child("User").child(d31).child("downlineTree").child("Ring2").child("downline4").setValue(d128);
                    }else if (d129 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline129").setValue(d129);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline65").setValue(d129);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring5").child("downline33").setValue(d129);
                        myRef.child("User").child(d7).child("downlineTree").child("Ring4").child("downline17").setValue(d129);
                        myRef.child("User").child(d15).child("downlineTree").child("Ring3").child("downline9").setValue(d129);
                        myRef.child("User").child(d31).child("downlineTree").child("Ring2").child("downline5").setValue(d129);
                    }else if (d130 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline130").setValue(d130);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline66").setValue(d130);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring5").child("downline34").setValue(d130);
                        myRef.child("User").child(d7).child("downlineTree").child("Ring4").child("downline18").setValue(d130);
                        myRef.child("User").child(d15).child("downlineTree").child("Ring3").child("downline10").setValue(d130);
                        myRef.child("User").child(d31).child("downlineTree").child("Ring2").child("downline6").setValue(d130);
                    }else if (d131 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline131").setValue(d131);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline67").setValue(d131);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring5").child("downline35").setValue(d131);
                        myRef.child("User").child(d7).child("downlineTree").child("Ring4").child("downline19").setValue(d131);
                        myRef.child("User").child(d15).child("downlineTree").child("Ring3").child("downline11").setValue(d131);
                        myRef.child("User").child(d32).child("downlineTree").child("Ring2").child("downline3").setValue(d131);
                    }else if (d132 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline132").setValue(d132);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline68").setValue(d132);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring5").child("downline36").setValue(d132);
                        myRef.child("User").child(d7).child("downlineTree").child("Ring4").child("downline20").setValue(d132);
                        myRef.child("User").child(d15).child("downlineTree").child("Ring3").child("downline12").setValue(d132);
                        myRef.child("User").child(d32).child("downlineTree").child("Ring2").child("downline4").setValue(d132);
                    }else if (d133 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline133").setValue(d133);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline69").setValue(d133);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring5").child("downline37").setValue(d133);
                        myRef.child("User").child(d7).child("downlineTree").child("Ring4").child("downline21").setValue(d133);
                        myRef.child("User").child(d15).child("downlineTree").child("Ring3").child("downline13").setValue(d133);
                        myRef.child("User").child(d32).child("downlineTree").child("Ring2").child("downline5").setValue(d133);
                    }else if (d134 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline134").setValue(d134);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline70").setValue(d134);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring5").child("downline38").setValue(d134);
                        myRef.child("User").child(d7).child("downlineTree").child("Ring4").child("downline22").setValue(d134);
                        myRef.child("User").child(d15).child("downlineTree").child("Ring3").child("downline14").setValue(d134);
                        myRef.child("User").child(d32).child("downlineTree").child("Ring2").child("downline6").setValue(d134);
                    }else if (d135 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline135").setValue(d135);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline71").setValue(d135);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring5").child("downline39").setValue(d135);
                        myRef.child("User").child(d7).child("downlineTree").child("Ring4").child("downline23").setValue(d135);
                        myRef.child("User").child(d16).child("downlineTree").child("Ring3").child("downline7").setValue(d135);
                        myRef.child("User").child(d33).child("downlineTree").child("Ring2").child("downline3").setValue(d135);
                    }else if (d136 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline136").setValue(d136);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline72").setValue(d136);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring5").child("downline40").setValue(d136);
                        myRef.child("User").child(d7).child("downlineTree").child("Ring4").child("downline24").setValue(d136);
                        myRef.child("User").child(d16).child("downlineTree").child("Ring3").child("downline8").setValue(d136);
                        myRef.child("User").child(d33).child("downlineTree").child("Ring2").child("downline4").setValue(d136);
                    }else if (d137 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline137").setValue(d137);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline73").setValue(d137);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring5").child("downline41").setValue(d137);
                        myRef.child("User").child(d7).child("downlineTree").child("Ring4").child("downline25").setValue(d137);
                        myRef.child("User").child(d16).child("downlineTree").child("Ring3").child("downline9").setValue(d137);
                        myRef.child("User").child(d33).child("downlineTree").child("Ring2").child("downline5").setValue(d137);
                    }else if (d138 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline138").setValue(d138);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline74").setValue(d138);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring5").child("downline42").setValue(d138);
                        myRef.child("User").child(d7).child("downlineTree").child("Ring4").child("downline26").setValue(d138);
                        myRef.child("User").child(d16).child("downlineTree").child("Ring3").child("downline10").setValue(d138);
                        myRef.child("User").child(d33).child("downlineTree").child("Ring2").child("downline6").setValue(d138);
                    }else if (d139 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline139").setValue(d139);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline75").setValue(d139);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring5").child("downline43").setValue(d139);
                        myRef.child("User").child(d7).child("downlineTree").child("Ring4").child("downline27").setValue(d139);
                        myRef.child("User").child(d16).child("downlineTree").child("Ring3").child("downline11").setValue(d139);
                        myRef.child("User").child(d34).child("downlineTree").child("Ring2").child("downline3").setValue(d139);
                    }else if (d140 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline140").setValue(d140);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline76").setValue(d140);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring5").child("downline44").setValue(d140);
                        myRef.child("User").child(d7).child("downlineTree").child("Ring4").child("downline28").setValue(d140);
                        myRef.child("User").child(d16).child("downlineTree").child("Ring3").child("downline12").setValue(d140);
                        myRef.child("User").child(d34).child("downlineTree").child("Ring2").child("downline4").setValue(d140);
                    }else if (d141 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline141").setValue(d141);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline77").setValue(d141);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring5").child("downline45").setValue(d141);
                        myRef.child("User").child(d7).child("downlineTree").child("Ring4").child("downline29").setValue(d141);
                        myRef.child("User").child(d16).child("downlineTree").child("Ring3").child("downline13").setValue(d141);
                        myRef.child("User").child(d34).child("downlineTree").child("Ring2").child("downline5").setValue(d141);
                    }else if (d142 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline142").setValue(d142);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline78").setValue(d142);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring5").child("downline46").setValue(d142);
                        myRef.child("User").child(d7).child("downlineTree").child("Ring4").child("downline30").setValue(d142);
                        myRef.child("User").child(d16).child("downlineTree").child("Ring3").child("downline14").setValue(d142);
                        myRef.child("User").child(d34).child("downlineTree").child("Ring2").child("downline6").setValue(d142);
                    }else if (d143 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline143").setValue(d143);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline79").setValue(d143);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring5").child("downline47").setValue(d143);
                        myRef.child("User").child(d8).child("downlineTree").child("Ring4").child("downline15").setValue(d143);
                        myRef.child("User").child(d17).child("downlineTree").child("Ring3").child("downline7").setValue(d143);
                        myRef.child("User").child(d35).child("downlineTree").child("Ring2").child("downline3").setValue(d143);
                    }else if (d144 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline144").setValue(d144);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline80").setValue(d144);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring5").child("downline48").setValue(d144);
                        myRef.child("User").child(d8).child("downlineTree").child("Ring4").child("downline16").setValue(d144);
                        myRef.child("User").child(d17).child("downlineTree").child("Ring3").child("downline8").setValue(d144);
                        myRef.child("User").child(d35).child("downlineTree").child("Ring2").child("downline4").setValue(d144);
                    }else if (d145 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline145").setValue(d145);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline81").setValue(d145);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring5").child("downline49").setValue(d145);
                        myRef.child("User").child(d8).child("downlineTree").child("Ring4").child("downline17").setValue(d145);
                        myRef.child("User").child(d17).child("downlineTree").child("Ring3").child("downline9").setValue(d145);
                        myRef.child("User").child(d35).child("downlineTree").child("Ring2").child("downline5").setValue(d145);
                    }else if (d146 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline146").setValue(d146);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline82").setValue(d146);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring5").child("downline50").setValue(d146);
                        myRef.child("User").child(d8).child("downlineTree").child("Ring4").child("downline18").setValue(d146);
                        myRef.child("User").child(d17).child("downlineTree").child("Ring3").child("downline10").setValue(d146);
                        myRef.child("User").child(d35).child("downlineTree").child("Ring2").child("downline6").setValue(d146);
                    }else if (d147 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline147").setValue(d147);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline83").setValue(d147);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring5").child("downline51").setValue(d147);
                        myRef.child("User").child(d8).child("downlineTree").child("Ring4").child("downline19").setValue(d147);
                        myRef.child("User").child(d17).child("downlineTree").child("Ring3").child("downline11").setValue(d147);
                        myRef.child("User").child(d36).child("downlineTree").child("Ring2").child("downline3").setValue(d147);
                    }else if (d148 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline148").setValue(d148);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline84").setValue(d148);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring5").child("downline52").setValue(d148);
                        myRef.child("User").child(d8).child("downlineTree").child("Ring4").child("downline20").setValue(d148);
                        myRef.child("User").child(d17).child("downlineTree").child("Ring3").child("downline12").setValue(d148);
                        myRef.child("User").child(d36).child("downlineTree").child("Ring2").child("downline4").setValue(d148);
                    }else if (d149 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline149").setValue(d149);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline85").setValue(d149);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring5").child("downline53").setValue(d149);
                        myRef.child("User").child(d8).child("downlineTree").child("Ring4").child("downline21").setValue(d149);
                        myRef.child("User").child(d17).child("downlineTree").child("Ring3").child("downline13").setValue(d149);
                        myRef.child("User").child(d36).child("downlineTree").child("Ring2").child("downline5").setValue(d149);
                    }else if (d150 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline150").setValue(d150);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline86").setValue(d150);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring5").child("downline54").setValue(d150);
                        myRef.child("User").child(d8).child("downlineTree").child("Ring4").child("downline22").setValue(d150);
                        myRef.child("User").child(d17).child("downlineTree").child("Ring3").child("downline14").setValue(d150);
                        myRef.child("User").child(d36).child("downlineTree").child("Ring2").child("downline6").setValue(d150);
                    }else if (d151 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline151").setValue(d151);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline87").setValue(d151);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring5").child("downline55").setValue(d151);
                        myRef.child("User").child(d8).child("downlineTree").child("Ring4").child("downline23").setValue(d151);
                        myRef.child("User").child(d18).child("downlineTree").child("Ring3").child("downline7").setValue(d151);
                        myRef.child("User").child(d37).child("downlineTree").child("Ring2").child("downline3").setValue(d151);
                    }else if (d152 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline152").setValue(d152);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline88").setValue(d152);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring5").child("downline56").setValue(d152);
                        myRef.child("User").child(d8).child("downlineTree").child("Ring4").child("downline24").setValue(d152);
                        myRef.child("User").child(d18).child("downlineTree").child("Ring3").child("downline8").setValue(d152);
                        myRef.child("User").child(d37).child("downlineTree").child("Ring2").child("downline4").setValue(d152);
                    }else if (d153 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline153").setValue(d153);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline89").setValue(d153);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring5").child("downline57").setValue(d153);
                        myRef.child("User").child(d8).child("downlineTree").child("Ring4").child("downline25").setValue(d153);
                        myRef.child("User").child(d18).child("downlineTree").child("Ring3").child("downline9").setValue(d153);
                        myRef.child("User").child(d37).child("downlineTree").child("Ring2").child("downline5").setValue(d153);
                    }else if (d154 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline154").setValue(d154);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline90").setValue(d154);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring5").child("downline58").setValue(d154);
                        myRef.child("User").child(d8).child("downlineTree").child("Ring4").child("downline26").setValue(d154);
                        myRef.child("User").child(d18).child("downlineTree").child("Ring3").child("downline10").setValue(d154);
                        myRef.child("User").child(d37).child("downlineTree").child("Ring2").child("downline6").setValue(d154);
                    }else if (d155 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline155").setValue(d155);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline91").setValue(d155);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring5").child("downline59").setValue(d155);
                        myRef.child("User").child(d8).child("downlineTree").child("Ring4").child("downline27").setValue(d155);
                        myRef.child("User").child(d18).child("downlineTree").child("Ring3").child("downline11").setValue(d155);
                        myRef.child("User").child(d38).child("downlineTree").child("Ring2").child("downline3").setValue(d155);
                    }else if (d156 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline156").setValue(d156);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline92").setValue(d156);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring5").child("downline60").setValue(d156);
                        myRef.child("User").child(d8).child("downlineTree").child("Ring4").child("downline28").setValue(d156);
                        myRef.child("User").child(d18).child("downlineTree").child("Ring3").child("downline12").setValue(d156);
                        myRef.child("User").child(d38).child("downlineTree").child("Ring2").child("downline4").setValue(d156);
                    }else if (d157 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline157").setValue(d157);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline93").setValue(d157);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring5").child("downline61").setValue(d157);
                        myRef.child("User").child(d8).child("downlineTree").child("Ring4").child("downline29").setValue(d157);
                        myRef.child("User").child(d18).child("downlineTree").child("Ring3").child("downline13").setValue(d157);
                        myRef.child("User").child(d38).child("downlineTree").child("Ring2").child("downline5").setValue(d157);
                    }else if (d158 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline158").setValue(d158);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline94").setValue(d158);
                        myRef.child("User").child(d3).child("downlineTree").child("Ring5").child("downline62").setValue(d158);
                        myRef.child("User").child(d8).child("downlineTree").child("Ring4").child("downline30").setValue(d158);
                        myRef.child("User").child(d18).child("downlineTree").child("Ring3").child("downline14").setValue(d158);
                        myRef.child("User").child(d38).child("downlineTree").child("Ring2").child("downline6").setValue(d158);
                    }else if (d159 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline159").setValue(d159);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline95").setValue(d159);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring5").child("downline31").setValue(d159);
                        myRef.child("User").child(d9).child("downlineTree").child("Ring4").child("downline15").setValue(d159);
                        myRef.child("User").child(d19).child("downlineTree").child("Ring3").child("downline7").setValue(d159);
                        myRef.child("User").child(d39).child("downlineTree").child("Ring2").child("downline3").setValue(d159);
                    }else if (d160 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline160").setValue(d160);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline96").setValue(d160);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring5").child("downline32").setValue(d160);
                        myRef.child("User").child(d9).child("downlineTree").child("Ring4").child("downline16").setValue(d160);
                        myRef.child("User").child(d19).child("downlineTree").child("Ring3").child("downline8").setValue(d160);
                        myRef.child("User").child(d39).child("downlineTree").child("Ring2").child("downline4").setValue(d160);
                    }else if (d161 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline161").setValue(d161);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline97").setValue(d161);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring5").child("downline33").setValue(d161);
                        myRef.child("User").child(d9).child("downlineTree").child("Ring4").child("downline17").setValue(d161);
                        myRef.child("User").child(d19).child("downlineTree").child("Ring3").child("downline9").setValue(d161);
                        myRef.child("User").child(d39).child("downlineTree").child("Ring2").child("downline5").setValue(d161);
                    }else if (d162 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline162").setValue(d162);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline98").setValue(d162);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring5").child("downline34").setValue(d162);
                        myRef.child("User").child(d9).child("downlineTree").child("Ring4").child("downline18").setValue(d162);
                        myRef.child("User").child(d19).child("downlineTree").child("Ring3").child("downline10").setValue(d162);
                        myRef.child("User").child(d39).child("downlineTree").child("Ring2").child("downline6").setValue(d162);
                    }else if (d163 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline163").setValue(d163);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline99").setValue(d163);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring5").child("downline35").setValue(d163);
                        myRef.child("User").child(d9).child("downlineTree").child("Ring4").child("downline19").setValue(d163);
                        myRef.child("User").child(d19).child("downlineTree").child("Ring3").child("downline11").setValue(d163);
                        myRef.child("User").child(d40).child("downlineTree").child("Ring2").child("downline3").setValue(d163);
                    }else if (d164 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline164").setValue(d164);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline100").setValue(d164);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring5").child("downline36").setValue(d164);
                        myRef.child("User").child(d9).child("downlineTree").child("Ring4").child("downline20").setValue(d164);
                        myRef.child("User").child(d19).child("downlineTree").child("Ring3").child("downline12").setValue(d164);
                        myRef.child("User").child(d40).child("downlineTree").child("Ring2").child("downline4").setValue(d164);
                    }else if (d165 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline165").setValue(d165);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline101").setValue(d165);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring5").child("downline37").setValue(d165);
                        myRef.child("User").child(d9).child("downlineTree").child("Ring4").child("downline21").setValue(d165);
                        myRef.child("User").child(d19).child("downlineTree").child("Ring3").child("downline13").setValue(d165);
                        myRef.child("User").child(d40).child("downlineTree").child("Ring2").child("downline5").setValue(d165);
                    }else if (d166 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline166").setValue(d166);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline102").setValue(d166);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring5").child("downline38").setValue(d166);
                        myRef.child("User").child(d9).child("downlineTree").child("Ring4").child("downline22").setValue(d166);
                        myRef.child("User").child(d19).child("downlineTree").child("Ring3").child("downline14").setValue(d166);
                        myRef.child("User").child(d40).child("downlineTree").child("Ring2").child("downline6").setValue(d166);
                    }else if (d167 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline167").setValue(d167);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline103").setValue(d167);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring5").child("downline39").setValue(d167);
                        myRef.child("User").child(d9).child("downlineTree").child("Ring4").child("downline23").setValue(d167);
                        myRef.child("User").child(d20).child("downlineTree").child("Ring3").child("downline7").setValue(d167);
                        myRef.child("User").child(d41).child("downlineTree").child("Ring2").child("downline3").setValue(d167);
                    }else if (d168 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline168").setValue(d168);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline104").setValue(d168);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring5").child("downline40").setValue(d168);
                        myRef.child("User").child(d9).child("downlineTree").child("Ring4").child("downline24").setValue(d168);
                        myRef.child("User").child(d20).child("downlineTree").child("Ring3").child("downline8").setValue(d168);
                        myRef.child("User").child(d41).child("downlineTree").child("Ring2").child("downline4").setValue(d168);
                    }else if (d169 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline169").setValue(d169);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline105").setValue(d169);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring5").child("downline41").setValue(d169);
                        myRef.child("User").child(d9).child("downlineTree").child("Ring4").child("downline25").setValue(d169);
                        myRef.child("User").child(d20).child("downlineTree").child("Ring3").child("downline9").setValue(d169);
                        myRef.child("User").child(d41).child("downlineTree").child("Ring2").child("downline5").setValue(d169);
                    }else if (d170 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline170").setValue(d170);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline106").setValue(d170);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring5").child("downline42").setValue(d170);
                        myRef.child("User").child(d9).child("downlineTree").child("Ring4").child("downline26").setValue(d170);
                        myRef.child("User").child(d20).child("downlineTree").child("Ring3").child("downline10").setValue(d170);
                        myRef.child("User").child(d41).child("downlineTree").child("Ring2").child("downline6").setValue(d170);
                    }else if (d171 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline171").setValue(d171);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline107").setValue(d171);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring5").child("downline43").setValue(d171);
                        myRef.child("User").child(d9).child("downlineTree").child("Ring4").child("downline27").setValue(d171);
                        myRef.child("User").child(d20).child("downlineTree").child("Ring3").child("downline11").setValue(d171);
                        myRef.child("User").child(d42).child("downlineTree").child("Ring2").child("downline3").setValue(d171);
                    }else if (d172 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline172").setValue(d172);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline108").setValue(d172);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring5").child("downline44").setValue(d172);
                        myRef.child("User").child(d9).child("downlineTree").child("Ring4").child("downline28").setValue(d172);
                        myRef.child("User").child(d20).child("downlineTree").child("Ring3").child("downline12").setValue(d172);
                        myRef.child("User").child(d42).child("downlineTree").child("Ring2").child("downline4").setValue(d172);
                    }else if (d173 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline173").setValue(d173);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline109").setValue(d173);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring5").child("downline45").setValue(d173);
                        myRef.child("User").child(d9).child("downlineTree").child("Ring4").child("downline29").setValue(d173);
                        myRef.child("User").child(d20).child("downlineTree").child("Ring3").child("downline13").setValue(d173);
                        myRef.child("User").child(d42).child("downlineTree").child("Ring2").child("downline5").setValue(d173);
                    }else if (d174 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline174").setValue(d174);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline110").setValue(d174);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring5").child("downline46").setValue(d174);
                        myRef.child("User").child(d9).child("downlineTree").child("Ring4").child("downline30").setValue(d174);
                        myRef.child("User").child(d20).child("downlineTree").child("Ring3").child("downline14").setValue(d174);
                        myRef.child("User").child(d42).child("downlineTree").child("Ring2").child("downline6").setValue(d174);
                    }else if (d175 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline175").setValue(d175);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline111").setValue(d175);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring5").child("downline47").setValue(d175);
                        myRef.child("User").child(d10).child("downlineTree").child("Ring4").child("downline15").setValue(d175);
                        myRef.child("User").child(d21).child("downlineTree").child("Ring3").child("downline7").setValue(d175);
                        myRef.child("User").child(d43).child("downlineTree").child("Ring2").child("downline3").setValue(d175);
                    }else if (d176 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline176").setValue(d176);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline112").setValue(d176);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring5").child("downline48").setValue(d176);
                        myRef.child("User").child(d10).child("downlineTree").child("Ring4").child("downline16").setValue(d176);
                        myRef.child("User").child(d21).child("downlineTree").child("Ring3").child("downline8").setValue(d176);
                        myRef.child("User").child(d43).child("downlineTree").child("Ring2").child("downline4").setValue(d176);
                    }else if (d177 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline177").setValue(d177);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline113").setValue(d177);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring5").child("downline49").setValue(d177);
                        myRef.child("User").child(d10).child("downlineTree").child("Ring4").child("downline17").setValue(d177);
                        myRef.child("User").child(d21).child("downlineTree").child("Ring3").child("downline9").setValue(d177);
                        myRef.child("User").child(d43).child("downlineTree").child("Ring2").child("downline5").setValue(d177);
                    }else if (d178 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline178").setValue(d178);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline114").setValue(d178);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring5").child("downline50").setValue(d178);
                        myRef.child("User").child(d10).child("downlineTree").child("Ring4").child("downline18").setValue(d178);
                        myRef.child("User").child(d21).child("downlineTree").child("Ring3").child("downline10").setValue(d178);
                        myRef.child("User").child(d43).child("downlineTree").child("Ring2").child("downline6").setValue(d178);
                    }else if (d179 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline179").setValue(d179);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline115").setValue(d179);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring5").child("downline51").setValue(d179);
                        myRef.child("User").child(d10).child("downlineTree").child("Ring4").child("downline19").setValue(d179);
                        myRef.child("User").child(d21).child("downlineTree").child("Ring3").child("downline11").setValue(d179);
                        myRef.child("User").child(d44).child("downlineTree").child("Ring2").child("downline3").setValue(d179);
                    }else if (d180 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline180").setValue(d180);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline116").setValue(d180);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring5").child("downline52").setValue(d180);
                        myRef.child("User").child(d10).child("downlineTree").child("Ring4").child("downline20").setValue(d180);
                        myRef.child("User").child(d21).child("downlineTree").child("Ring3").child("downline12").setValue(d180);
                        myRef.child("User").child(d44).child("downlineTree").child("Ring2").child("downline4").setValue(d180);
                    }else if (d181 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline181").setValue(d181);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline117").setValue(d181);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring5").child("downline53").setValue(d181);
                        myRef.child("User").child(d10).child("downlineTree").child("Ring4").child("downline21").setValue(d181);
                        myRef.child("User").child(d21).child("downlineTree").child("Ring3").child("downline13").setValue(d181);
                        myRef.child("User").child(d44).child("downlineTree").child("Ring2").child("downline5").setValue(d181);
                    }else if (d182 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline182").setValue(d182);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline118").setValue(d182);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring5").child("downline54").setValue(d182);
                        myRef.child("User").child(d10).child("downlineTree").child("Ring4").child("downline22").setValue(d182);
                        myRef.child("User").child(d21).child("downlineTree").child("Ring3").child("downline14").setValue(d182);
                        myRef.child("User").child(d44).child("downlineTree").child("Ring2").child("downline6").setValue(d182);
                    }else if (d183 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline183").setValue(d183);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline119").setValue(d183);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring5").child("downline55").setValue(d183);
                        myRef.child("User").child(d10).child("downlineTree").child("Ring4").child("downline23").setValue(d183);
                        myRef.child("User").child(d22).child("downlineTree").child("Ring3").child("downline7").setValue(d183);
                        myRef.child("User").child(d45).child("downlineTree").child("Ring2").child("downline3").setValue(d183);
                    }else if (d184 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline184").setValue(d184);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline120").setValue(d184);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring5").child("downline56").setValue(d184);
                        myRef.child("User").child(d10).child("downlineTree").child("Ring4").child("downline24").setValue(d184);
                        myRef.child("User").child(d22).child("downlineTree").child("Ring3").child("downline8").setValue(d184);
                        myRef.child("User").child(d45).child("downlineTree").child("Ring2").child("downline4").setValue(d184);
                    }else if (d185 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline185").setValue(d185);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline121").setValue(d185);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring5").child("downline57").setValue(d185);
                        myRef.child("User").child(d10).child("downlineTree").child("Ring4").child("downline25").setValue(d185);
                        myRef.child("User").child(d22).child("downlineTree").child("Ring3").child("downline9").setValue(d185);
                        myRef.child("User").child(d45).child("downlineTree").child("Ring2").child("downline5").setValue(d185);
                    }else if (d186 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline186").setValue(d186);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline122").setValue(d186);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring5").child("downline58").setValue(d186);
                        myRef.child("User").child(d10).child("downlineTree").child("Ring4").child("downline26").setValue(d186);
                        myRef.child("User").child(d22).child("downlineTree").child("Ring3").child("downline10").setValue(d186);
                        myRef.child("User").child(d45).child("downlineTree").child("Ring2").child("downline6").setValue(d186);
                    }else if (d187 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline187").setValue(d187);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline123").setValue(d187);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring5").child("downline59").setValue(d187);
                        myRef.child("User").child(d10).child("downlineTree").child("Ring4").child("downline27").setValue(d187);
                        myRef.child("User").child(d22).child("downlineTree").child("Ring3").child("downline11").setValue(d187);
                        myRef.child("User").child(d46).child("downlineTree").child("Ring2").child("downline3").setValue(d187);
                    }else if (d188 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline188").setValue(d188);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline124").setValue(d188);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring5").child("downline60").setValue(d188);
                        myRef.child("User").child(d10).child("downlineTree").child("Ring4").child("downline28").setValue(d188);
                        myRef.child("User").child(d22).child("downlineTree").child("Ring3").child("downline12").setValue(d188);
                        myRef.child("User").child(d46).child("downlineTree").child("Ring2").child("downline4").setValue(d188);
                    }else if (d189 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline189").setValue(d189);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline125").setValue(d189);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring5").child("downline61").setValue(d189);
                        myRef.child("User").child(d10).child("downlineTree").child("Ring4").child("downline29").setValue(d189);
                        myRef.child("User").child(d22).child("downlineTree").child("Ring3").child("downline13").setValue(d189);
                        myRef.child("User").child(d46).child("downlineTree").child("Ring2").child("downline5").setValue(d189);
                    }else if (d190 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline190").setValue(d190);
                        myRef.child("User").child(d1).child("downlineTree").child("Ring6").child("downline126").setValue(d190);
                        myRef.child("User").child(d4).child("downlineTree").child("Ring5").child("downline62").setValue(d190);
                        myRef.child("User").child(d10).child("downlineTree").child("Ring4").child("downline30").setValue(d190);
                        myRef.child("User").child(d22).child("downlineTree").child("Ring3").child("downline14").setValue(d190);
                        myRef.child("User").child(d46).child("downlineTree").child("Ring2").child("downline6").setValue(d190);
                    }else if (d191 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline191").setValue(d191);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline63").setValue(d191);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring5").child("downline31").setValue(d191);
                        myRef.child("User").child(d11).child("downlineTree").child("Ring4").child("downline15").setValue(d191);
                        myRef.child("User").child(d23).child("downlineTree").child("Ring3").child("downline7").setValue(d191);
                        myRef.child("User").child(d47).child("downlineTree").child("Ring2").child("downline3").setValue(d191);
                    }else if (d192 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline192").setValue(d192);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline64").setValue(d192);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring5").child("downline32").setValue(d192);
                        myRef.child("User").child(d11).child("downlineTree").child("Ring4").child("downline16").setValue(d192);
                        myRef.child("User").child(d23).child("downlineTree").child("Ring3").child("downline8").setValue(d192);
                        myRef.child("User").child(d47).child("downlineTree").child("Ring2").child("downline4").setValue(d192);
                    }else if (d193 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline193").setValue(d193);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline65").setValue(d193);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring5").child("downline33").setValue(d193);
                        myRef.child("User").child(d11).child("downlineTree").child("Ring4").child("downline17").setValue(d193);
                        myRef.child("User").child(d23).child("downlineTree").child("Ring3").child("downline9").setValue(d193);
                        myRef.child("User").child(d47).child("downlineTree").child("Ring2").child("downline5").setValue(d193);
                    }else if (d194 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline194").setValue(d194);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline66").setValue(d194);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring5").child("downline34").setValue(d194);
                        myRef.child("User").child(d11).child("downlineTree").child("Ring4").child("downline18").setValue(d194);
                        myRef.child("User").child(d23).child("downlineTree").child("Ring3").child("downline10").setValue(d194);
                        myRef.child("User").child(d47).child("downlineTree").child("Ring2").child("downline6").setValue(d194);
                    }else if (d195 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline195").setValue(d195);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline67").setValue(d195);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring5").child("downline35").setValue(d195);
                        myRef.child("User").child(d11).child("downlineTree").child("Ring4").child("downline19").setValue(d195);
                        myRef.child("User").child(d23).child("downlineTree").child("Ring3").child("downline11").setValue(d195);
                        myRef.child("User").child(d48).child("downlineTree").child("Ring2").child("downline3").setValue(d195);
                    }else if (d196 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline196").setValue(d196);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline68").setValue(d196);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring5").child("downline36").setValue(d196);
                        myRef.child("User").child(d11).child("downlineTree").child("Ring4").child("downline20").setValue(d196);
                        myRef.child("User").child(d23).child("downlineTree").child("Ring3").child("downline12").setValue(d196);
                        myRef.child("User").child(d48).child("downlineTree").child("Ring2").child("downline4").setValue(d196);
                    }else if (d197 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline197").setValue(d197);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline69").setValue(d197);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring5").child("downline37").setValue(d197);
                        myRef.child("User").child(d11).child("downlineTree").child("Ring4").child("downline21").setValue(d197);
                        myRef.child("User").child(d23).child("downlineTree").child("Ring3").child("downline13").setValue(d197);
                        myRef.child("User").child(d48).child("downlineTree").child("Ring2").child("downline5").setValue(d197);
                    }else if (d198 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline198").setValue(d198);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline70").setValue(d198);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring5").child("downline38").setValue(d198);
                        myRef.child("User").child(d11).child("downlineTree").child("Ring4").child("downline22").setValue(d198);
                        myRef.child("User").child(d23).child("downlineTree").child("Ring3").child("downline14").setValue(d198);
                        myRef.child("User").child(d48).child("downlineTree").child("Ring2").child("downline6").setValue(d198);
                    }else if (d199 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline199").setValue(d199);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline71").setValue(d199);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring5").child("downline39").setValue(d199);
                        myRef.child("User").child(d11).child("downlineTree").child("Ring4").child("downline23").setValue(d199);
                        myRef.child("User").child(d24).child("downlineTree").child("Ring3").child("downline7").setValue(d199);
                        myRef.child("User").child(d49).child("downlineTree").child("Ring2").child("downline3").setValue(d199);
                    }else if (d200 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline200").setValue(d200);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline72").setValue(d200);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring5").child("downline40").setValue(d200);
                        myRef.child("User").child(d11).child("downlineTree").child("Ring4").child("downline24").setValue(d200);
                        myRef.child("User").child(d24).child("downlineTree").child("Ring3").child("downline8").setValue(d200);
                        myRef.child("User").child(d49).child("downlineTree").child("Ring2").child("downline4").setValue(d200);
                    }else if (d201 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline201").setValue(d201);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline73").setValue(d201);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring5").child("downline41").setValue(d201);
                        myRef.child("User").child(d11).child("downlineTree").child("Ring4").child("downline25").setValue(d201);
                        myRef.child("User").child(d24).child("downlineTree").child("Ring3").child("downline9").setValue(d201);
                        myRef.child("User").child(d49).child("downlineTree").child("Ring2").child("downline5").setValue(d201);
                    }else if (d202 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline202").setValue(d202);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline74").setValue(d202);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring5").child("downline42").setValue(d202);
                        myRef.child("User").child(d11).child("downlineTree").child("Ring4").child("downline26").setValue(d202);
                        myRef.child("User").child(d24).child("downlineTree").child("Ring3").child("downline10").setValue(d202);
                        myRef.child("User").child(d49).child("downlineTree").child("Ring2").child("downline6").setValue(d202);
                    }else if (d203 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline203").setValue(d203);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline75").setValue(d203);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring5").child("downline43").setValue(d203);
                        myRef.child("User").child(d11).child("downlineTree").child("Ring4").child("downline27").setValue(d203);
                        myRef.child("User").child(d24).child("downlineTree").child("Ring3").child("downline11").setValue(d203);
                        myRef.child("User").child(d50).child("downlineTree").child("Ring2").child("downline3").setValue(d203);
                    }else if (d204 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline204").setValue(d204);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline76").setValue(d204);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring5").child("downline44").setValue(d204);
                        myRef.child("User").child(d11).child("downlineTree").child("Ring4").child("downline28").setValue(d204);
                        myRef.child("User").child(d24).child("downlineTree").child("Ring3").child("downline12").setValue(d204);
                        myRef.child("User").child(d50).child("downlineTree").child("Ring2").child("downline4").setValue(d204);
                    }else if (d205 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline205").setValue(d205);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline77").setValue(d205);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring5").child("downline45").setValue(d205);
                        myRef.child("User").child(d11).child("downlineTree").child("Ring4").child("downline29").setValue(d205);
                        myRef.child("User").child(d24).child("downlineTree").child("Ring3").child("downline13").setValue(d205);
                        myRef.child("User").child(d50).child("downlineTree").child("Ring2").child("downline5").setValue(d205);
                    }else if (d206 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline206").setValue(d206);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline78").setValue(d206);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring5").child("downline46").setValue(d206);
                        myRef.child("User").child(d11).child("downlineTree").child("Ring4").child("downline30").setValue(d206);
                        myRef.child("User").child(d24).child("downlineTree").child("Ring3").child("downline14").setValue(d206);
                        myRef.child("User").child(d50).child("downlineTree").child("Ring2").child("downline6").setValue(d206);
                    }else if (d207 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline207").setValue(d207);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline79").setValue(d207);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring5").child("downline47").setValue(d207);
                        myRef.child("User").child(d12).child("downlineTree").child("Ring4").child("downline15").setValue(d207);
                        myRef.child("User").child(d25).child("downlineTree").child("Ring3").child("downline7").setValue(d207);
                        myRef.child("User").child(d51).child("downlineTree").child("Ring2").child("downline3").setValue(d207);
                    }else if (d208 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline208").setValue(d208);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline80").setValue(d208);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring5").child("downline48").setValue(d208);
                        myRef.child("User").child(d12).child("downlineTree").child("Ring4").child("downline16").setValue(d208);
                        myRef.child("User").child(d25).child("downlineTree").child("Ring3").child("downline8").setValue(d208);
                        myRef.child("User").child(d51).child("downlineTree").child("Ring2").child("downline4").setValue(d208);
                    }else if (d209 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline209").setValue(d209);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline81").setValue(d209);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring5").child("downline49").setValue(d209);
                        myRef.child("User").child(d12).child("downlineTree").child("Ring4").child("downline17").setValue(d209);
                        myRef.child("User").child(d25).child("downlineTree").child("Ring3").child("downline9").setValue(d209);
                        myRef.child("User").child(d51).child("downlineTree").child("Ring2").child("downline5").setValue(d209);
                    }else if (d210 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline210").setValue(d210);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline82").setValue(d210);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring5").child("downline50").setValue(d210);
                        myRef.child("User").child(d12).child("downlineTree").child("Ring4").child("downline18").setValue(d210);
                        myRef.child("User").child(d25).child("downlineTree").child("Ring3").child("downline10").setValue(d210);
                        myRef.child("User").child(d51).child("downlineTree").child("Ring2").child("downline6").setValue(d210);
                    }else if (d211 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline211").setValue(d211);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline83").setValue(d211);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring5").child("downline51").setValue(d211);
                        myRef.child("User").child(d12).child("downlineTree").child("Ring4").child("downline19").setValue(d211);
                        myRef.child("User").child(d25).child("downlineTree").child("Ring3").child("downline11").setValue(d211);
                        myRef.child("User").child(d52).child("downlineTree").child("Ring2").child("downline3").setValue(d211);
                    }else if (d212 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline212").setValue(d212);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline84").setValue(d212);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring5").child("downline52").setValue(d212);
                        myRef.child("User").child(d12).child("downlineTree").child("Ring4").child("downline20").setValue(d212);
                        myRef.child("User").child(d25).child("downlineTree").child("Ring3").child("downline12").setValue(d212);
                        myRef.child("User").child(d52).child("downlineTree").child("Ring2").child("downline4").setValue(d212);
                    }else if (d213 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline213").setValue(d213);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline85").setValue(d213);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring5").child("downline53").setValue(d213);
                        myRef.child("User").child(d12).child("downlineTree").child("Ring4").child("downline21").setValue(d213);
                        myRef.child("User").child(d25).child("downlineTree").child("Ring3").child("downline13").setValue(d213);
                        myRef.child("User").child(d52).child("downlineTree").child("Ring2").child("downline5").setValue(d213);
                    }else if (d214 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline214").setValue(d214);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline86").setValue(d214);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring5").child("downline54").setValue(d214);
                        myRef.child("User").child(d12).child("downlineTree").child("Ring4").child("downline22").setValue(d214);
                        myRef.child("User").child(d25).child("downlineTree").child("Ring3").child("downline14").setValue(d214);
                        myRef.child("User").child(d52).child("downlineTree").child("Ring2").child("downline6").setValue(d214);
                    }else if (d215 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline215").setValue(d215);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline87").setValue(d215);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring5").child("downline55").setValue(d215);
                        myRef.child("User").child(d12).child("downlineTree").child("Ring4").child("downline23").setValue(d215);
                        myRef.child("User").child(d26).child("downlineTree").child("Ring3").child("downline7").setValue(d215);
                        myRef.child("User").child(d53).child("downlineTree").child("Ring2").child("downline3").setValue(d215);
                    }else if (d216 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline216").setValue(d216);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline88").setValue(d216);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring5").child("downline56").setValue(d216);
                        myRef.child("User").child(d12).child("downlineTree").child("Ring4").child("downline24").setValue(d216);
                        myRef.child("User").child(d25).child("downlineTree").child("Ring3").child("downline8").setValue(d216);
                        myRef.child("User").child(d52).child("downlineTree").child("Ring2").child("downline4").setValue(d216);
                    }else if (d217 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline217").setValue(d217);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline89").setValue(d217);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring5").child("downline57").setValue(d217);
                        myRef.child("User").child(d12).child("downlineTree").child("Ring4").child("downline25").setValue(d217);
                        myRef.child("User").child(d25).child("downlineTree").child("Ring3").child("downline9").setValue(d217);
                        myRef.child("User").child(d52).child("downlineTree").child("Ring2").child("downline5").setValue(d217);
                    }else if (d218 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline218").setValue(d218);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline90").setValue(d218);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring5").child("downline58").setValue(d218);
                        myRef.child("User").child(d12).child("downlineTree").child("Ring4").child("downline26").setValue(d218);
                        myRef.child("User").child(d25).child("downlineTree").child("Ring3").child("downline10").setValue(d218);
                        myRef.child("User").child(d52).child("downlineTree").child("Ring2").child("downline6").setValue(d218);
                    }else if (d219 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline219").setValue(d219);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline91").setValue(d219);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring5").child("downline59").setValue(d219);
                        myRef.child("User").child(d12).child("downlineTree").child("Ring4").child("downline27").setValue(d219);
                        myRef.child("User").child(d25).child("downlineTree").child("Ring3").child("downline11").setValue(d219);
                        myRef.child("User").child(d53).child("downlineTree").child("Ring2").child("downline3").setValue(d219);
                    }else if (d220 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline220").setValue(d220);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline92").setValue(d220);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring5").child("downline60").setValue(d220);
                        myRef.child("User").child(d12).child("downlineTree").child("Ring4").child("downline28").setValue(d220);
                        myRef.child("User").child(d25).child("downlineTree").child("Ring3").child("downline12").setValue(d220);
                        myRef.child("User").child(d53).child("downlineTree").child("Ring2").child("downline4").setValue(d220);
                    }else if (d221 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline221").setValue(d221);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline93").setValue(d221);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring5").child("downline61").setValue(d221);
                        myRef.child("User").child(d12).child("downlineTree").child("Ring4").child("downline29").setValue(d221);
                        myRef.child("User").child(d25).child("downlineTree").child("Ring3").child("downline13").setValue(d221);
                        myRef.child("User").child(d53).child("downlineTree").child("Ring2").child("downline5").setValue(d221);
                    }else if (d222 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline222").setValue(d222);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline94").setValue(d222);
                        myRef.child("User").child(d5).child("downlineTree").child("Ring5").child("downline62").setValue(d222);
                        myRef.child("User").child(d12).child("downlineTree").child("Ring4").child("downline30").setValue(d222);
                        myRef.child("User").child(d25).child("downlineTree").child("Ring3").child("downline14").setValue(d222);
                        myRef.child("User").child(d53).child("downlineTree").child("Ring2").child("downline6").setValue(d222);
                    }else if (d223 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline223").setValue(d223);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline95").setValue(d223);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring5").child("downline31").setValue(d223);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring4").child("downline15").setValue(d223);
                        myRef.child("User").child(d26).child("downlineTree").child("Ring3").child("downline7").setValue(d223);
                        myRef.child("User").child(d54).child("downlineTree").child("Ring2").child("downline3").setValue(d223);
                    }else if (d224 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline224").setValue(d224);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline96").setValue(d224);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring5").child("downline32").setValue(d224);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring4").child("downline16").setValue(d224);
                        myRef.child("User").child(d26).child("downlineTree").child("Ring3").child("downline8").setValue(d224);
                        myRef.child("User").child(d54).child("downlineTree").child("Ring2").child("downline4").setValue(d224);
                    }else if (d225 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline225").setValue(d225);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline97").setValue(d225);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring5").child("downline33").setValue(d225);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring4").child("downline17").setValue(d225);
                        myRef.child("User").child(d26).child("downlineTree").child("Ring3").child("downline9").setValue(d225);
                        myRef.child("User").child(d54).child("downlineTree").child("Ring2").child("downline5").setValue(d225);
                    }else if (d226 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline226").setValue(d226);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline98").setValue(d226);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring5").child("downline34").setValue(d226);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring4").child("downline18").setValue(d226);
                        myRef.child("User").child(d26).child("downlineTree").child("Ring3").child("downline10").setValue(d226);
                        myRef.child("User").child(d54).child("downlineTree").child("Ring2").child("downline6").setValue(d226);
                    }else if (d227 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline227").setValue(d227);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline99").setValue(d227);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring5").child("downline35").setValue(d227);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring4").child("downline19").setValue(d227);
                        myRef.child("User").child(d26).child("downlineTree").child("Ring3").child("downline11").setValue(d227);
                        myRef.child("User").child(d55).child("downlineTree").child("Ring2").child("downline3").setValue(d227);
                    }else if (d228 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline228").setValue(d228);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline100").setValue(d228);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring5").child("downline36").setValue(d228);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring4").child("downline20").setValue(d228);
                        myRef.child("User").child(d26).child("downlineTree").child("Ring3").child("downline12").setValue(d228);
                        myRef.child("User").child(d55).child("downlineTree").child("Ring2").child("downline4").setValue(d228);
                    }else if (d229 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline229").setValue(d229);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline101").setValue(d229);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring5").child("downline37").setValue(d229);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring4").child("downline21").setValue(d229);
                        myRef.child("User").child(d26).child("downlineTree").child("Ring3").child("downline13").setValue(d229);
                        myRef.child("User").child(d55).child("downlineTree").child("Ring2").child("downline5").setValue(d229);
                    }else if (d230 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline230").setValue(d230);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline102").setValue(d230);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring5").child("downline38").setValue(d230);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring4").child("downline22").setValue(d230);
                        myRef.child("User").child(d26).child("downlineTree").child("Ring3").child("downline14").setValue(d230);
                        myRef.child("User").child(d55).child("downlineTree").child("Ring2").child("downline6").setValue(d230);
                    }else if (d231 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline231").setValue(d231);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline103").setValue(d231);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring5").child("downline39").setValue(d231);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring4").child("downline23").setValue(d231);
                        myRef.child("User").child(d27).child("downlineTree").child("Ring3").child("downline7").setValue(d231);
                        myRef.child("User").child(d56).child("downlineTree").child("Ring2").child("downline3").setValue(d231);
                    }else if (d232 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline232").setValue(d232);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline104").setValue(d232);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring5").child("downline40").setValue(d232);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring4").child("downline24").setValue(d232);
                        myRef.child("User").child(d27).child("downlineTree").child("Ring3").child("downline8").setValue(d232);
                        myRef.child("User").child(d56).child("downlineTree").child("Ring2").child("downline4").setValue(d232);
                    }else if (d233 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline233").setValue(d233);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline105").setValue(d233);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring5").child("downline41").setValue(d233);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring4").child("downline25").setValue(d233);
                        myRef.child("User").child(d27).child("downlineTree").child("Ring3").child("downline9").setValue(d233);
                        myRef.child("User").child(d56).child("downlineTree").child("Ring2").child("downline5").setValue(d233);
                    }else if (d234 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline234").setValue(d234);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline106").setValue(d234);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring5").child("downline42").setValue(d234);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring4").child("downline26").setValue(d234);
                        myRef.child("User").child(d27).child("downlineTree").child("Ring3").child("downline10").setValue(d234);
                        myRef.child("User").child(d56).child("downlineTree").child("Ring2").child("downline6").setValue(d234);
                    }else if (d235 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline235").setValue(d235);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline107").setValue(d235);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring5").child("downline43").setValue(d235);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring4").child("downline27").setValue(d235);
                        myRef.child("User").child(d27).child("downlineTree").child("Ring3").child("downline11").setValue(d235);
                        myRef.child("User").child(d57).child("downlineTree").child("Ring2").child("downline3").setValue(d235);
                    }else if (d236 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline236").setValue(d236);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline108").setValue(d236);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring5").child("downline44").setValue(d236);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring4").child("downline28").setValue(d236);
                        myRef.child("User").child(d27).child("downlineTree").child("Ring3").child("downline12").setValue(d236);
                        myRef.child("User").child(d57).child("downlineTree").child("Ring2").child("downline4").setValue(d236);
                    }else if (d237 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline237").setValue(d237);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline109").setValue(d237);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring5").child("downline45").setValue(d237);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring4").child("downline29").setValue(d237);
                        myRef.child("User").child(d27).child("downlineTree").child("Ring3").child("downline13").setValue(d237);
                        myRef.child("User").child(d57).child("downlineTree").child("Ring2").child("downline5").setValue(d237);
                    }else if (d238 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline238").setValue(d238);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline110").setValue(d238);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring5").child("downline46").setValue(d238);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring4").child("downline30").setValue(d238);
                        myRef.child("User").child(d27).child("downlineTree").child("Ring3").child("downline14").setValue(d238);
                        myRef.child("User").child(d57).child("downlineTree").child("Ring2").child("downline6").setValue(d238);
                    }else if (d239 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline239").setValue(d239);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline111").setValue(d239);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring5").child("downline47").setValue(d239);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring4").child("downline15").setValue(d239);
                        myRef.child("User").child(d27).child("downlineTree").child("Ring3").child("downline7").setValue(d239);
                        myRef.child("User").child(d58).child("downlineTree").child("Ring2").child("downline3").setValue(d239);
                    }else if (d240 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline240").setValue(d240);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline112").setValue(d240);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring5").child("downline48").setValue(d240);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring4").child("downline16").setValue(d240);
                        myRef.child("User").child(d27).child("downlineTree").child("Ring3").child("downline8").setValue(d240);
                        myRef.child("User").child(d58).child("downlineTree").child("Ring2").child("downline4").setValue(d240);
                    }else if (d241 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline241").setValue(d241);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline113").setValue(d241);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring5").child("downline49").setValue(d241);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring4").child("downline17").setValue(d241);
                        myRef.child("User").child(d27).child("downlineTree").child("Ring3").child("downline9").setValue(d241);
                        myRef.child("User").child(d58).child("downlineTree").child("Ring2").child("downline5").setValue(d241);
                    }else if (d242 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline242").setValue(d242);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline114").setValue(d242);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring5").child("downline50").setValue(d242);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring4").child("downline18").setValue(d242);
                        myRef.child("User").child(d27).child("downlineTree").child("Ring3").child("downline10").setValue(d242);
                        myRef.child("User").child(d58).child("downlineTree").child("Ring2").child("downline6").setValue(d242);
                    }else if (d243 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline243").setValue(d243);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline115").setValue(d243);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring5").child("downline51").setValue(d243);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring4").child("downline19").setValue(d243);
                        myRef.child("User").child(d27).child("downlineTree").child("Ring3").child("downline11").setValue(d243);
                        myRef.child("User").child(d59).child("downlineTree").child("Ring2").child("downline3").setValue(d243);
                    }else if (d244 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline244").setValue(d244);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline116").setValue(d244);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring5").child("downline52").setValue(d244);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring4").child("downline20").setValue(d244);
                        myRef.child("User").child(d27).child("downlineTree").child("Ring3").child("downline12").setValue(d244);
                        myRef.child("User").child(d59).child("downlineTree").child("Ring2").child("downline4").setValue(d244);
                    }else if (d245 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline245").setValue(d245);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline117").setValue(d245);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring5").child("downline53").setValue(d245);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring4").child("downline21").setValue(d245);
                        myRef.child("User").child(d27).child("downlineTree").child("Ring3").child("downline13").setValue(d245);
                        myRef.child("User").child(d59).child("downlineTree").child("Ring2").child("downline5").setValue(d245);
                    }else if (d246 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline246").setValue(d246);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline118").setValue(d246);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring5").child("downline54").setValue(d246);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring4").child("downline22").setValue(d246);
                        myRef.child("User").child(d27).child("downlineTree").child("Ring3").child("downline14").setValue(d246);
                        myRef.child("User").child(d59).child("downlineTree").child("Ring2").child("downline6").setValue(d246);
                    }else if (d247 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline247").setValue(d247);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline119").setValue(d247);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring5").child("downline55").setValue(d247);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring4").child("downline23").setValue(d247);
                        myRef.child("User").child(d28).child("downlineTree").child("Ring3").child("downline7").setValue(d247);
                        myRef.child("User").child(d60).child("downlineTree").child("Ring2").child("downline3").setValue(d247);
                    }else if (d248 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline248").setValue(d248);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline120").setValue(d248);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring5").child("downline56").setValue(d248);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring4").child("downline24").setValue(d248);
                        myRef.child("User").child(d28).child("downlineTree").child("Ring3").child("downline8").setValue(d248);
                        myRef.child("User").child(d60).child("downlineTree").child("Ring2").child("downline4").setValue(d248);
                    }else if (d249 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline249").setValue(d249);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline121").setValue(d249);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring5").child("downline57").setValue(d249);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring4").child("downline25").setValue(d249);
                        myRef.child("User").child(d28).child("downlineTree").child("Ring3").child("downline9").setValue(d249);
                        myRef.child("User").child(d60).child("downlineTree").child("Ring2").child("downline5").setValue(d249);
                    }else if (d250 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline250").setValue(d250);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline122").setValue(d250);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring5").child("downline58").setValue(d250);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring4").child("downline26").setValue(d250);
                        myRef.child("User").child(d28).child("downlineTree").child("Ring3").child("downline10").setValue(d250);
                        myRef.child("User").child(d60).child("downlineTree").child("Ring2").child("downline6").setValue(d250);
                    }else if (d251 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline251").setValue(d251);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline123").setValue(d251);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring5").child("downline59").setValue(d251);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring4").child("downline27").setValue(d251);
                        myRef.child("User").child(d28).child("downlineTree").child("Ring3").child("downline11").setValue(d251);
                        myRef.child("User").child(d61).child("downlineTree").child("Ring2").child("downline3").setValue(d251);
                    }else if (d252 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline252").setValue(d252);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline124").setValue(d252);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring5").child("downline60").setValue(d252);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring4").child("downline28").setValue(d252);
                        myRef.child("User").child(d28).child("downlineTree").child("Ring3").child("downline12").setValue(d252);
                        myRef.child("User").child(d61).child("downlineTree").child("Ring2").child("downline4").setValue(d252);
                    }else if (d253 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline253").setValue(d253);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline125").setValue(d253);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring5").child("downline61").setValue(d253);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring4").child("downline29").setValue(d253);
                        myRef.child("User").child(d28).child("downlineTree").child("Ring3").child("downline13").setValue(d253);
                        myRef.child("User").child(d61).child("downlineTree").child("Ring2").child("downline5").setValue(d253);
                    }else if (d254 == null) {
                        myRef.child("User").child(userPhone).child("downlineTree").child("Ring7").child("downline254").setValue(d254);
                        myRef.child("User").child(d2).child("downlineTree").child("Ring6").child("downline126").setValue(d254);
                        myRef.child("User").child(d6).child("downlineTree").child("Ring5").child("downline62").setValue(d254);
                        myRef.child("User").child(d13).child("downlineTree").child("Ring4").child("downline30").setValue(d254);
                        myRef.child("User").child(d28).child("downlineTree").child("Ring3").child("downline14").setValue(d254);
                        myRef.child("User").child(d61).child("downlineTree").child("Ring2").child("downline6").setValue(d254);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}
