package com.dr.betadapurrakyat;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dr.betadapurrakyat.Utils.BottomNavSetting;
import com.dr.betadapurrakyat.fragment.Fragment_NT_1;
import com.dr.betadapurrakyat.fragment.ViewPagerAdapterNewRelease;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NetworkActivity extends AppCompatActivity {

    private Context context = NetworkActivity.this;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView toolbar_user_img,toolbar_user_search,addImageToolbar;
    private ViewPagerAdapterNewRelease viewPagerAdapter;
    Boolean clickIcon = false;
    RelativeLayout searchBarLayout;
    FirebaseDatabase database ;
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String userPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

        bottomNavListener();
        Toolbar toolbar = findViewById(R.id.toolbar_net);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Dapur Rakyat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(8);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userPhone = user.getPhoneNumber();


        tabLayout = (TabLayout) findViewById(R.id.tabs_net);
        viewPager = findViewById(R.id.viewpager_network_activity);

        viewPagerAdapter = new ViewPagerAdapterNewRelease(getSupportFragmentManager());

        viewPagerAdapter.addfragment(new Fragment_NT_1(),"Jaringan");

        toolBar();
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void toolBar() {
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_toolbar, null);
        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        EditText textSearch = findViewById(R.id.search_editText_home);
        searchBarLayout = findViewById(R.id.search_bar_home);
        Button buttonSearch = findViewById(R.id.button_search_home);
        toolbar_user_img = findViewById(R.id.toolbar_user_image);
        addImageToolbar = findViewById(R.id.add_user_image);

        ImageButton searchIcon = findViewById(R.id.toolbar_search_home);

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!clickIcon){
                    searchBarLayout.setVisibility(View.VISIBLE);
                    clickIcon = true;
                } else {
                    searchBarLayout.setVisibility(View.GONE);
                    clickIcon = false;
                }
            }
        });
        toolbar_user_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, UserProfileActivity.class));
            }
        });
        addImageToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, UserProfileActivity.class));
            }
        });


        myRef.child("User").child(userPhone).child("uri")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String uri = dataSnapshot.getValue(String.class);
                        if(uri!=null){
                            Glide.with(context)
                                    .load(uri)
                                    .apply(RequestOptions.circleCropTransform())
                                    .into(toolbar_user_img);
                            addImageToolbar.setVisibility(View.GONE);
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }
    private void bottomNavListener() {

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation2_net);
        navigation.setSelectedItemId(R.id.navigation_network);
        BottomNavSetting.bNavListener(context, navigation);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }


}






