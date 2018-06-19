package com.dr.betadapurrakyat;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.dr.betadapurrakyat.Adapter.HolderVideoView;
import com.dr.betadapurrakyat.Model.ProductData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VideoViewActivity extends AppCompatActivity {
    String title, subtitle, price, videoUri, textBahan, textBumbu, uri, stock;
    ImageView imageView;
    TextView mtitle, msubtitle, mprice, mstock;
    Button orderButton, konfrimasiButton;
    //drop down
    RelativeLayout dropdown_details, video_player_main;
    ImageView dropDown, dropUp, playButton, mimgplay;
    TextView text_bahan, text_bumbu, details_bumbu, details_bahan;
    VideoView videoView;
    /// Video player
    ImageView playbutton_bar;
    ProgressBar bufferProgress;
    TextView timer_indicator1, timer_indicator2;
    boolean isPlaying, videoIsPlaying;
    LinearLayout videoViewControl;
    RelativeLayout videoView_main;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    List <ProductData> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_view_layout);
        listStringExtra();
        videosetup();
        recyclerViewSetup();

    }

    private void recyclerViewSetup() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        recyclerView = findViewById(R.id.recycler_view_videoview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HolderVideoView(getBaseContext(), datas);

           //todo set ref based on product view
            myRef.child("Product").child("General").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        ProductData data = postSnapshot.getValue(ProductData.class);
                        datas.add(data);
                    }
                    recyclerView.setAdapter(adapter);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
    }

    private void videosetup() {
        videoViewControl.setVisibility(View.INVISIBLE);
        if (videoUri != null){
            videoView.setVideoURI(Uri.parse(videoUri));
        }
        videoView.requestFocus();
        Glide.with(getBaseContext())
                .asBitmap()
                .load(uri)
                .into(imageView);
    }

    private void assignVideoView() {
        mtitle.setText(title);
        msubtitle.setText(subtitle);
        mprice.setText(price);
        mstock.setText(stock);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
                    if (i == mediaPlayer.MEDIA_INFO_BUFFERING_START) {
                        bufferProgress.setVisibility(View.VISIBLE);
                    } else if (i == mediaPlayer.MEDIA_INFO_BUFFERING_END) {
                        bufferProgress.setVisibility(View.INVISIBLE);
                    }
                    return false;
                }
            });
            videoView_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isPlaying) {
                        mimgplay.setVisibility(View.INVISIBLE);
                        videoView.setVisibility(View.VISIBLE);
                        imageView.setVisibility(View.INVISIBLE);
                        isPlaying = true;
                        videoView.start();
                        videoIsPlaying = true;
                        videoViewControl.postDelayed(new Runnable() {
                            public void run() {
                                videoViewControl.setVisibility(View.INVISIBLE);
                            }
                        }, 10500);

                    } else if (videoIsPlaying) {
                        videoView.pause();
                        playbutton_bar.setImageResource(R.drawable.ic_playbar);
                        mimgplay.setVisibility(View.VISIBLE);
                        videoIsPlaying=false;
                        isPlaying=false;
                        videoViewControl.setVisibility(View.VISIBLE);

                    } else if (!videoIsPlaying){
                        playbutton_bar.setImageResource(R.drawable.ic_pause);
                        videoView.resume();
                        videoIsPlaying=true;
                        mimgplay.setVisibility(View.INVISIBLE);
                        videoViewControl.postDelayed(new Runnable() {
                            public void run() {
                                videoViewControl.setVisibility(View.INVISIBLE);
                            }
                        }, 10500);
                    }
                }
            });
        }
    }

    private void declareView() {
        imageView = findViewById(R.id.imageView_intro);
        mtitle = findViewById(R.id.textViewTitle);
        msubtitle = findViewById(R.id.textViewSubTitle);
        mprice = findViewById(R.id.textViewPrice);
        mstock = findViewById(R.id.textViewStock);
        dropDown = findViewById(R.id.dropDown);
        dropUp = findViewById(R.id.dropUp);
        orderButton = findViewById(R.id.order_button);
        konfrimasiButton = findViewById(R.id.button_order_konfirmasi);
        dropdown_details = findViewById(R.id.dropdown_details);
        text_bahan = findViewById(R.id.text_ingredient_recycler);
        text_bumbu = findViewById(R.id.text_bumbu_recycler);
        videoView = findViewById(R.id.videoview_card);
        playbutton_bar = findViewById(R.id.play_button_bar);
        timer_indicator1 = findViewById(R.id.time_indicator_bar);
        timer_indicator2 = findViewById(R.id.time_indicatorbar_2);
        isPlaying = false;
        videoIsPlaying = false;
        bufferProgress = findViewById(R.id.bufferProgress);
        videoViewControl = findViewById(R.id.videoview_control);
        mimgplay = findViewById(R.id.img_play);
        videoView_main = findViewById(R.id.videoView_main);
        assignVideoView();
    }

    private void listStringExtra() {
        title = getIntent().getExtras().getString("title");
        subtitle = getIntent().getExtras().getString("subtitle");
        price = getIntent().getExtras().getString("price");
        uri = getIntent().getExtras().getString("uri");
        stock = getIntent().getExtras().getString("stock");
        videoUri = getIntent().getExtras().getString("videoUri");
        textBahan = getIntent().getExtras().getString("textBahan");
        textBumbu = getIntent().getExtras().getString("textBumbu");
        declareView();
    }
}
