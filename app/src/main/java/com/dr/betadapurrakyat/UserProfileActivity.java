package com.dr.betadapurrakyat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dr.betadapurrakyat.Utils.BottomNavSetting;
import com.dr.betadapurrakyat.fragment.Fragment_NT_1;
import com.dr.betadapurrakyat.fragment.ViewPagerAdapterNewRelease;
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

public class UserProfileActivity extends AppCompatActivity {
    private Uri filePath;
    private int REQUEST_CODE = 21;

    private Context context = UserProfileActivity.this;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView toolbar_user_img, toolbar_user_search;
    private ViewPagerAdapterNewRelease viewPagerAdapter;
    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference upRef;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String userPhone;
    String uri;
    String referal;
    String fReferral;
    String referencefirebase;
    boolean bSetup = false;


    Boolean clickIcon = false;
    RelativeLayout searchBarLayout;
    ImageView main_user_image;
    EditText user_name, nama_referensi;
    TextView b_add_refferensi, user_hp, textRefferal,textAddImage;
    ImageView ic_edit_name,addimageView,addPictureToolBar;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        hideKeyboard();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        upRef = database.getReference();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userPhone = user.getPhoneNumber();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.dismissPopupMenus();
        //getSupportActionBar().setTitle("Dapur Rakyat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);



        assignElement();

        refferal();
        checkReferral();

    }



    private void checkReferral() {
        myRef.child("User").child(userPhone).child("Referral").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    fReferral =dataSnapshot.getValue(String.class);
                    textRefferal.setText("Reff ID: " + fReferral);
                    textRefferal.setVisibility(View.VISIBLE);
                    b_add_refferensi.setVisibility(View.INVISIBLE);
                    nama_referensi.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
private void toolBar(){
    LayoutInflater mInflater = LayoutInflater.from(this);
    View mCustomView = mInflater.inflate(R.layout.custom_toolbar, null);
    getSupportActionBar().setCustomView(mCustomView);


    EditText textSearch = findViewById(R.id.search_editText_home);
    searchBarLayout = findViewById(R.id.search_bar_home);
    Button buttonSearch = findViewById(R.id.button_search_home);
    toolbar_user_img = findViewById(R.id.toolbar_user_image);
    ImageButton searchIcon = findViewById(R.id.toolbar_search_home);
    addPictureToolBar = findViewById(R.id.add_user_image);

        myRef.child("User").child(userPhone).child("uri")
                .addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            String uri = dataSnapshot.getValue(String.class);

            if (uri!= null){
                Glide.with(getApplicationContext())
                        .load(uri)
                        .apply(RequestOptions.circleCropTransform())
                        .into(toolbar_user_img);
                Glide.with(getApplicationContext())
                        .load(uri)
                        .apply(RequestOptions.circleCropTransform())
                        .into(main_user_image);
                addPictureToolBar.setVisibility(View.INVISIBLE);
                addimageView.setVisibility(View.GONE);
                textAddImage.setVisibility(View.GONE);
            }

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
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

    private void assignElement() {

        user_name = findViewById(R.id.user_name);
        user_hp = findViewById(R.id.user_hp);
        ic_edit_name = findViewById(R.id.ic_edit_name);
        nama_referensi = findViewById(R.id.nama_referensi);
        user_hp.setText(userPhone);
        textRefferal = findViewById(R.id.tnama_referensi);
        main_user_image = findViewById(R.id.main_user_image);
        addimageView = findViewById(R.id.add_image);
        textAddImage = findViewById(R.id.text_add_image);
        toolBar();

        main_user_image.setOnClickListener(new View.OnClickListener() {
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

    }

    private void refferal() {
        b_add_refferensi = findViewById(R.id.b_add_referensi);
        b_add_refferensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(referal)) {
                    nama_referensi.setError("Masukan No HandPhone Refferal Dengan Format (+62)");


                    String ref = nama_referensi.getText().toString();
                    if (ref.charAt(0) == '0') { // Remove all the # chars in front of the real string
                        String ref1 = ref.substring(1);
                        String codePhoneId = "+62";
                        referal = codePhoneId + ref1;
                        nama_referensi.setVisibility(View.GONE);
                        textRefferal.setText("Reff ID: " + referal);
                        textRefferal.setVisibility(View.VISIBLE);
                        b_add_refferensi.setVisibility(View.INVISIBLE);
                        firebaseReferral();
                        //referal = ref
                    } else if (ref.charAt(0) == '+') {
                        referal = ref;
                        nama_referensi.setVisibility(View.GONE);
                        textRefferal.setText("Reff ID: " + referal);
                        b_add_refferensi.setVisibility(View.INVISIBLE);
                    }

                }

            }
        });

    }

    private void firebaseReferral() {
        myRef.child("User").child(userPhone).child("Referral").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    myRef.child("User").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                           if (!bSetup) {
                               for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                   referencefirebase = ds.getKey();
                                   assert referencefirebase != null;
                                   if (referencefirebase.equals(referal)) {
                                       myRef.child("User").child(referencefirebase).child("downlineTree").child("Ring1").addValueEventListener(new ValueEventListener() {
                                           @Override
                                           public void onDataChange(DataSnapshot dataSnapshot) {
                                               String d1 = dataSnapshot.child("User").child(referencefirebase).child("downlineTree").child("Ring1").child("downline1").getValue(String.class);
                                               String d2 = dataSnapshot.child("User").child(referencefirebase).child("downlineTree").child("Ring1").child("downline2").getValue(String.class);
                                               if (d1 == null){
                                                   myRef.child("User").child(userPhone).child("Referral").setValue(referal, new DatabaseReference.CompletionListener() {
                                                       @Override
                                                       public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                       }
                                                   });
                                                   Toast.makeText(getBaseContext(), "Referral Berhasil Diupload", Toast.LENGTH_SHORT).show();
                                                   finish();
                                               }else if (d2 == null) {
                                                   myRef.child("User").child(userPhone).child("Referral").setValue(referal, new DatabaseReference.CompletionListener() {
                                                       @Override
                                                       public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                       }
                                                   });
                                                   Toast.makeText(getBaseContext(), "Referral Berhasil Diupload", Toast.LENGTH_SHORT).show();
                                                   finish();
                                               } else {
                                                   Toast.makeText(getBaseContext(),"Referral Tidak Dapat Diupload, Harap Menghubungi Referral", Toast.LENGTH_SHORT).show();
                                               }



                                           }

                                           @Override
                                           public void onCancelled(DatabaseError databaseError) {

                                           }
                                       });





                                   } else {
                                       bSetup = true;
                                       return;
                                   }
                               }
                           }
                            if (bSetup){
                                Toast.makeText(getBaseContext(), "Referral Belum Terdaftar,Mohon Ulangi Kembali", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    @SuppressLint("ClickableViewAccessibility")
    public void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
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
                            Toast.makeText(UserProfileActivity.this, "Success Upload ", Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(UserProfileActivity.this, "Failed Upload ", Toast.LENGTH_SHORT).show();
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


}
