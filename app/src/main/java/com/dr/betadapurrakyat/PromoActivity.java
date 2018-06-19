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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dr.betadapurrakyat.Utils.BottomNavSetting;
import com.dr.betadapurrakyat.fragment.Fragment_Promo_Fresh;
import com.dr.betadapurrakyat.fragment.Fragment_Promo_Menu;
import com.dr.betadapurrakyat.fragment.Fragment_Promo_Tools;
import com.dr.betadapurrakyat.fragment.ViewPagerAdapterNewRelease;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PromoActivity extends AppCompatActivity {
    private Context context = PromoActivity.this;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapterNewRelease viewPagerAdapter;
    private ImageView toolbar_user_img,toolbar_user_search;
    Boolean clickIcon = false;
    RelativeLayout searchBarLayout;
    ImageView addPictureToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promo_activity);


        bottomNavListener();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Dapur Rakyat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(8);

        toolBar();

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewpager_newrelease_activity);

        viewPagerAdapter = new ViewPagerAdapterNewRelease(getSupportFragmentManager());


        viewPagerAdapter.addfragment(new Fragment_Promo_Fresh(),"Fresh");
        viewPagerAdapter.addfragment(new Fragment_Promo_Menu(),"Menu Baru");
        viewPagerAdapter.addfragment(new Fragment_Promo_Tools(),"Peralatan");

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
        ImageButton searchIcon = findViewById(R.id.toolbar_search_home);
        addPictureToolBar = findViewById(R.id.add_user_image);

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
        addPictureToolBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, UserProfileActivity.class));
            }
        });
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
                        Glide.with(getApplicationContext())
                                .load(uri)
                                .apply(RequestOptions.circleCropTransform())
                                .into(toolbar_user_img);
                        if (uri!= null){
                            addPictureToolBar.setVisibility(View.INVISIBLE);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
    private void bottomNavListener() {
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation2);
        navigation.setSelectedItemId(R.id.navigation_newRelease);
        BottomNavSetting.bNavListener(context, navigation);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_tabs, menu);
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
