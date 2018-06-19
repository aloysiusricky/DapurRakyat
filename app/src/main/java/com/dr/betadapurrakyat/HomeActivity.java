package com.dr.betadapurrakyat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dr.betadapurrakyat.Adapter.CardHolderHome;
import com.dr.betadapurrakyat.Adapter.HolderFreshBar;
import com.dr.betadapurrakyat.Adapter.HolderRCBar;
import com.dr.betadapurrakyat.Adapter.HolderSearchList;
import com.dr.betadapurrakyat.Adapter.ViewPagerSlideAdapter;
import com.dr.betadapurrakyat.Model.FreshUtils;
import com.dr.betadapurrakyat.Model.ProductData;
import com.dr.betadapurrakyat.Model.ProductDataHome;
import com.dr.betadapurrakyat.Model.ProductDataV;
import com.dr.betadapurrakyat.Utils.BottomNavSetting;
import com.dr.betadapurrakyat.Utils.BottomScroolHelper;
import com.dr.betadapurrakyat.fragment.Fragment_Promo_Menu;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Context context = HomeActivity.this;
    private RecyclerView recyclerView;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference myRef;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager layoutManager;
    private List<ProductData> datas = new ArrayList<>();
    private List<ProductDataV> datav = new ArrayList<>();
    private List<ProductDataHome> dataHome = new ArrayList<>();
    private List<FreshUtils> fresh = new ArrayList<>();
    private RecyclerView.Adapter adapter;
    private Toolbar toolbar;

    ///Header
    private TextView NameNav, UserNav;
    private ImageView UserPicture, toolbar_user_img;
    private String dUsername, userPhone;
    private Uri filePath;
    private int REQUEST_CODE = 21;

    private ViewPager viewPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    //View Pager
    ViewPagerSlideAdapter viewPagerSlideAdapter;
    private ViewPagerSlideAdapter mAdapter;
    private LinearLayout sliderdots;
    private int dotcount;
    private ImageView[] dotview;

    //RecyclerviewBar
    RecyclerView recyclerViewBarFresh, recyclerViewBarMenu, recyclerViewMenuPesta;
    HolderRCBar holderRCBarAdapter;
    HolderFreshBar holderFreshBar;

    // SearchBar
    ImageButton searchIcon;
    RelativeLayout searchBarLayout;
    RecyclerView listSearch;
    AutoCompleteTextView textSearch;
    Button buttonSearch;
    String searching;
    Boolean clickIcon = false;
    HolderSearchList search_adapter;

    ProgressBar pd1,pd2,pd3;

    ImageView addPictureToolBar, addPictureDrawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        //Firebase
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        // nav Section
        navSection();



        setupRecyclerView();
        recyclerviewfresh();
        recyclerviewmenu();
        recyclerViewMenuPesta();
        bottomNavListener();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.dismissPopupMenus();
        //getSupportActionBar().setTitle("Dapur Rakyat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        toolBar();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
//        RecyclerSearch();

        viewPager(); //


        pd1 = findViewById(R.id.progresViewPager);
        pd2 = findViewById(R.id.progress_bar_fresh);
        pd3 = findViewById(R.id.progress_bar_favorite);


        if (datav == null){
            pd1.setVisibility(View.VISIBLE);
        }else if (datav!=null){
            pd1.setVisibility(View.INVISIBLE);
        }
        if (fresh == null){
            pd2.setVisibility(View.VISIBLE);
        }else if (fresh!=null){
            pd2.setVisibility(View.INVISIBLE);
        }
        if (dataHome == null){
            pd3.setVisibility(View.VISIBLE);
        }else if (dataHome!=null){
            pd3.setVisibility(View.INVISIBLE);
        }

    }

    private void toolBar() {
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_toolbar, null);
        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        textSearch = findViewById(R.id.search_editText_home);
        searchBarLayout = findViewById(R.id.search_bar_home);
        buttonSearch = findViewById(R.id.button_search_home);
        toolbar_user_img = findViewById(R.id.toolbar_user_image);
        addPictureToolBar = findViewById(R.id.add_user_image);
        searchIcon = mCustomView.findViewById(R.id.toolbar_search_home);
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

    }
//    private void RecyclerSearch () {
//
//
//        textSearch = findViewById(R.id.search_editText_home);
//        searching = textSearch.getText().toString();
//        listSearch = findViewById(R.id.list_search);
//        listSearch.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        listSearch.setLayoutManager(layoutManager);
//        listSearch.setNestedScrollingEnabled(false);
//        search_adapter = new HolderSearchList(context, datas);
//        if (searching == null){
//            listSearch.setVisibility(View.GONE);
//        }else {
//            listSearch.setVisibility(View.GONE);
//            Query searchQuary = myRef.child("Product").child("General").orderByChild("title")
//                    .startAt(searching).endAt(searching + "\uf8ff");
//
//            searchQuary.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    ProductData productData = dataSnapshot.getValue(ProductData.class);
//                    datas.add(productData);
//                    if (searching != null) {
//                        listSearch.setAdapter(search_adapter);
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//        }
//    }

    private void viewPager() {


        viewPager = (ViewPager) findViewById(R.id.viewPager_slide_view);
        sliderdots = findViewById(R.id.sliderIndicator);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("Product").child("Display").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    ProductDataV productData = ds.getValue(ProductDataV.class);
                    datav.add(productData);


                }
                mAdapter = new ViewPagerSlideAdapter(context, datav);
                viewPager.setAdapter(mAdapter);
                dotcount = mAdapter.getCount();

                //or dotcount = viewPagerSlideAdapter.getCount();
                dotview = new ImageView[dotcount];
                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        for (int i = 0; i < dotcount; i++) {
                            dotview[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                                    R.drawable.nonactive_dot_indicator_vp));

                        }
                        dotview[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                                R.drawable.active_dot_indicator_vp));
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                for (int i = 0; i < dotcount; i++) {
                    dotview[i] = new ImageView(context);
                    dotview[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                            R.drawable.nonactive_dot_indicator_vp));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                            (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10, 0, 10, 0);
                    sliderdots.addView(dotview[i], params);
                }
                dotview[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.active_dot_indicator_vp));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new timerSetup(), 2000, 5000);
    }

    public class timerSetup extends TimerTask {

        @Override
        public void run() {
            HomeActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(1);
                    } else if (viewPager.getCurrentItem() == 1) {
                        viewPager.setCurrentItem(0);
                    }else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }


    private void navSection() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setNavigationItemSelectedListener(this);

        NameNav = (TextView) navigationView.getHeaderView(0).findViewById(R.id.text_view_name_nav);
        UserNav = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textView_phone_nav);
        UserPicture = navigationView.getHeaderView(0).findViewById(R.id.imageView_Nav);
        addPictureDrawer = navigationView.getHeaderView(0).findViewById(R.id.add_image_drawer);

        FirebaseUser user = mAuth.getCurrentUser();
        userPhone = user != null ? user.getPhoneNumber() : null;
        UserNav.setText(userPhone);

        UserPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

                startActivityForResult(chooserIntent, REQUEST_CODE);
            }
        });


        myRef.child("User").child(userPhone).child("fullname")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String user = dataSnapshot.getValue(String.class);
                        NameNav.setText(user);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        myRef.child("User").child(userPhone).child("uri")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String uri = dataSnapshot.getValue(String.class);

                        if (uri != null) {

                            Glide.with(getApplicationContext())
                                    .load(uri)
                                    .apply(RequestOptions.circleCropTransform())
                                    .into(UserPicture);
                            Glide.with(getApplicationContext())
                                    .load(uri)
                                    .apply(RequestOptions.circleCropTransform())
                                    .into(toolbar_user_img);

                               addPictureDrawer.setVisibility(View.GONE);
                               addPictureToolBar.setVisibility(View.GONE);


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                // Get the url from data
                filePath = data.getData();
                if (null != filePath) {

                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();
                    // Get the path from the Uri
                    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                    StorageReference myRef = storageRef.child("UserImage").child(userPhone)
                            .child("dp").child("dislayimage.jpg");
                    myRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(HomeActivity.this, "Success Upload ", Toast.LENGTH_SHORT).show();
                            Uri picUri = taskSnapshot.getDownloadUrl();
                            String uri = picUri.toString();

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference newRef = database.getReference("User").child(userPhone).child("uri");
                            newRef.setValue(uri);
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(HomeActivity.this, "Failed Upload ", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                            .getTotalByteCount());
                                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                                    if (progress == 100) {
                                        progressDialog.dismiss();
                                    }
                                }
                            });
                }
            }
        }
    }

//    private String getPathFromURI(Uri selectedImageUri) {
//
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageReference = storage.getReference();
//    }

    private void bottomNavListener() {
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        BottomNavSetting.bNavListener(context, navigation);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
            layoutParams.setBehavior(new BottomScroolHelper());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            finish();
        }

        super.onBackPressed();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_search, menu);
//        return true;
//    }

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_nusantara) {
            boolean nusantara = false;
            Intent i = new Intent(this,PromoActivity.class);
            i.putExtra("nusantara",false);
            startActivity(i);
        } else if (id == R.id.nav_oriental) {
            boolean oriental = false;
            Intent i = new Intent(this,PromoActivity.class);
            i.putExtra("oriental",false);
            startActivity(i);
        } else if (id == R.id.nav_continental) {
            boolean continental = false;
            Intent i = new Intent(this,PromoActivity.class);
            i.putExtra("continental",false);
            startActivity(i);

        } else if (id == R.id.nav_pagi) {
            boolean pagi = false;
            Intent i = new Intent(this,PromoActivity.class);
            i.putExtra("pagi",false);
            startActivity(i);
        } else if (id == R.id.nav_pesta) {
            boolean pesta = false;
            Intent i = new Intent(this,PromoActivity.class);
            i.putExtra("pesta",false);
            startActivity(i);

        } else if (id == R.id.nav_anak) {
            boolean anak = false;
            Intent i = new Intent(this,PromoActivity.class);
            i.putExtra("anak",false);
            startActivity(i);


        } else if (id == R.id.nav_template) {
            boolean template = false;
            Intent i = new Intent(this,PromoActivity.class);
            i.putExtra("template",false);
            startActivity(i);
        } else if (id == R.id.nav_profile) {
            boolean profile = false;
            Intent i = new Intent(this,PromoActivity.class);
            i.putExtra("anak",false);
            startActivity(i);

        } else if (id == R.id.nav_fresh) {
            Intent i = new Intent(this,PromoActivity.class);
            i.putExtra("fresh",false);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if (firebaseUser == null) {
            startActivity(new Intent(this, MainActivity.class));
        }

    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.home_recycler_view);
        recyclerView.setHasFixedSize(true);
        int numCollum = 2;
        gridLayoutManager = new GridLayoutManager(this, numCollum);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        loadproduct();
        loadproduct2();
    }

    private void loadproduct() {
        myRef.child("Product").child("General").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ProductDataHome data = postSnapshot.getValue(ProductDataHome.class);
                    dataHome.add(data);
                }
                adapter = new CardHolderHome(context, dataHome);
                recyclerView.setAdapter(adapter);
                recyclerViewBarMenu.setAdapter(holderRCBarAdapter);
                recyclerViewMenuPesta.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    private void loadproduct2() {
        myRef.child("Product").child("General").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ProductData data = postSnapshot.getValue(ProductData.class);
                    datas.add(data);
                }

                recyclerViewBarMenu.setAdapter(holderRCBarAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void recyclerviewfresh() {
        recyclerViewBarFresh = findViewById(R.id.home_recycler_view_freshBar);
        recyclerViewBarFresh.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewBarFresh.setLayoutManager(layoutManager);
        recyclerViewBarFresh.setNestedScrollingEnabled(false);

        myRef.child("Fresh").child("Ikan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    FreshUtils productData = postSnapshot.getValue(FreshUtils.class);
                    fresh.add(productData);

                }
                holderFreshBar = new HolderFreshBar(context, fresh);
                recyclerViewBarFresh.setAdapter(holderFreshBar);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        myRef.child("Fresh").child("Sayur").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    FreshUtils productData = postSnapshot.getValue(FreshUtils.class);
                    fresh.add(productData);

                }
                holderFreshBar = new HolderFreshBar(context, fresh);
                recyclerViewBarFresh.setAdapter(holderFreshBar);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    private void recyclerviewmenu() {
        recyclerViewBarMenu = findViewById(R.id.home_recycler_view_NewMenu);
        recyclerViewBarMenu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewBarMenu.setLayoutManager(layoutManager);
        recyclerViewBarMenu.setNestedScrollingEnabled(false);
        holderRCBarAdapter = new HolderRCBar(context, datas);

    }

    private void recyclerViewMenuPesta() {
        recyclerViewMenuPesta = findViewById(R.id.home_recycler_menuPesta);
        recyclerViewMenuPesta.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewMenuPesta.setLayoutManager(layoutManager);
        recyclerViewMenuPesta.setNestedScrollingEnabled(false);
        adapter = new CardHolderHome(context, dataHome);
    }

}