package com.dr.betadapurrakyat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.dr.betadapurrakyat.Model.ProductDataHome;
import com.dr.betadapurrakyat.R;
import com.dr.betadapurrakyat.VideoViewActivity;

import java.util.List;


public class CardHolderHome extends RecyclerView.Adapter<CardHolderHome.myHolder> {

    private Context context;

    private List<ProductDataHome> datas;


    public CardHolderHome(Context context, List<ProductDataHome> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view_image, parent, false);

        return new myHolder(view);

    }

    @Override
    public void onBindViewHolder(final myHolder holder, final int position) {
        final ProductDataHome productData = datas.get(position);
        holder.rootCard.setVisibility(View.GONE);
        if (datas != null) {
            holder.rootCard.setVisibility(View.VISIBLE);
            holder.title.setText(productData.getTitle());
            holder.subtitle.setText(productData.getSubtitle());
            holder.price.setText(productData.getPrice());
            holder.stock.setText(productData.getStock());

            holder.dropdown_details.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.VISIBLE);
            holder.playButton.setVisibility(View.INVISIBLE);
            holder.playIcon.setVisibility(View.GONE);

            Glide.with(context)
                    .asBitmap()
                    .load(productData.getUri())
                    .into(holder.imageView);

//        holder.text_bumbu.setText((CharSequence) productData.getmBumbu());
//        holder.text_bahan.setText((CharSequence) productData.getmBahan());
            holder.orderButton.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick(View view) {

                    holder.dropdown_details.setVisibility(View.VISIBLE);
                    holder.dropDown.setVisibility(View.INVISIBLE);
                    holder.dropUp.setVisibility(View.VISIBLE);

                }
            });
            holder.text_bahan.setText(productData.getBahan());
            holder.text_bumbu.setText(productData.getBumbu());
            holder.dropUp.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick(View view) {
                    holder.dropUp.setVisibility(View.GONE);
                    holder.dropDown.setVisibility(View.VISIBLE);
                    holder.dropdown_details.setVisibility(View.GONE);
                }
            });
            holder.dropDown.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick(View view) {
                    holder.dropdown_details.setVisibility(View.VISIBLE);
                    holder.dropDown.setVisibility(View.INVISIBLE);
                    holder.dropUp.setVisibility(View.VISIBLE);
                }
            });
            holder.playButton.setVisibility(View.VISIBLE);
            holder.videoView.setVisibility(View.GONE);
            holder.videoViewControl.setVisibility(View.GONE);
            holder.bufferProgress.setVisibility(View.INVISIBLE);


            holder.playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, VideoViewActivity.class);
                    intent.putExtra("title", datas.get(position).getTitle()); //you can name the keys whatever you like
                    intent.putExtra("subtitle", datas.get(position).getSubtitle()); //note that all these values have to be primitive (i.e boolean, int, double, String, etc.)
                    intent.putExtra("price", datas.get(position).getPrice());
                    intent.putExtra("uri", datas.get(position).getUri());
                    intent.putExtra("stock", datas.get(position).getStock());
                    intent.putExtra("videoUri", datas.get(position).getVideoUri()); //note that all these values have to be primitive (i.e boolean, int, double, String, etc.)
                    intent.putExtra("textbahan", datas.get(position).getBahan());
                    intent.putExtra("textbumbu", datas.get(position).getBumbu());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

//                if (holder.videoIsPlaying) {
//                    holder.isPlaying = true;
//                    holder.imageView.setVisibility(View.GONE);
//                    holder.playButton.setVisibility(View.INVISIBLE);
//                    holder.videoView.setVisibility(View.VISIBLE);
//                    holder.videoViewControl.setVisibility(View.VISIBLE);
//                    holder.videoView.setVideoURI(Uri.parse(productData.getVideoUri()));
//                    holder.playPause.setVisibility(View.INVISIBLE);
//                    holder.videoView.requestFocus();
//                    holder.videoView.start();
//                    holder.videoIsPlaying = false;
//
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                        holder.videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
//                            @Override
//                            public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
//                                if (i == mediaPlayer.MEDIA_INFO_BUFFERING_START) {
//                                    holder.bufferProgress.setVisibility(View.VISIBLE);
//                                } else if (i == mediaPlayer.MEDIA_INFO_BUFFERING_END) {
//                                    holder.bufferProgress.setVisibility(View.INVISIBLE);
//                                }
//                                return false;
//                            }
//                        });
//
//                    }
//                    holder.playPause.setVisibility(View.INVISIBLE);
//
//                    holder.videoView_main.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            if (holder.isPlaying) {
//                                holder.videoView.pause();
//                                holder.isPlaying = false;
//                                holder.playbutton_bar.setImageResource(R.drawable.ic_playbar);
//                                holder.playPause.setVisibility(View.VISIBLE);
//                                holder.videoViewControl.setVisibility(View.VISIBLE);
//
//                            } else {
//                                holder.playbutton_bar.setImageResource(R.drawable.ic_pause);
//                                holder.videoView.start();
//                                holder.isPlaying = true;
//
//                                holder.playPause.setVisibility(View.INVISIBLE);
//                                holder.videoViewControl.postDelayed(new Runnable() {
//                                    public void run() {
//                                        holder.videoViewControl.setVisibility(View.INVISIBLE);
//                                    }
//                                }, 10500);
//                            }
//                        }
//
//                    });
//
//                    holder.playbutton_bar.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            if (holder.isPlaying) {
//                                holder.videoView.pause();
//                                holder.isPlaying = false;
//                                holder.playbutton_bar.setImageResource(R.drawable.ic_playbar);
//                                holder.playButton.setVisibility(View.VISIBLE);
//                                holder.videoViewControl.setVisibility(View.VISIBLE);
//
//                            } else {
//                                holder.playButton.setVisibility(View.INVISIBLE);
//                                holder.playbutton_bar.setImageResource(R.drawable.ic_pause);
//                                holder.videoView.start();
//                                holder.isPlaying = true;
//
//                                holder.videoViewControl.postDelayed(new Runnable() {
//                                    public void run() {
//                                        holder.videoViewControl.setVisibility(View.INVISIBLE);
//                                    }
//                                }, 10000);
//                            }
//                        }
//                    });
//                }else if (!holder.videoIsPlaying)
//                {
//                    holder.videoView.stopPlayback();
//                    holder.videoView_main.setVisibility(View.GONE);
//                    holder.videoViewControl.setVisibility(View.GONE);
//                    holder.imageView.setVisibility(View.VISIBLE);
//                    holder.playButton.setVisibility(View.VISIBLE);
//
//                }
//            }
//
//
//        });
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class myHolder extends RecyclerView.ViewHolder {
        CardView rootCard;
        ImageView imageView;
        TextView title, subtitle, price, stock;
        Button orderButton, konfrimasiButton;
        //drop down
        RelativeLayout dropdown_details, video_player_main;
        ImageView dropDown, dropUp, playButton, playPause;
        TextView text_bahan, text_bumbu, details_bumbu, details_bahan;
        VideoView videoView;
        /// Video player
        ImageView playbutton_bar, playIcon;
        ProgressBar bufferProgress;
        TextView timer_indicator1, timer_indicator2;
        boolean isPlaying, videoIsPlaying;
        LinearLayout videoViewControl;
        RelativeLayout videoView_main;


        public myHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewProduct);
            title = itemView.findViewById(R.id.textViewTitle);
            subtitle = itemView.findViewById(R.id.textViewSubTitle);
            price = itemView.findViewById(R.id.textViewPrice);
            stock = itemView.findViewById(R.id.textViewStock);
            dropDown = itemView.findViewById(R.id.dropDown);
            dropUp = itemView.findViewById(R.id.dropUp);
            orderButton = itemView.findViewById(R.id.order_button);
            konfrimasiButton = itemView.findViewById(R.id.button_order_konfirmasi);
            dropdown_details = itemView.findViewById(R.id.dropdown_details);
            text_bahan = itemView.findViewById(R.id.text_ingredient_recycler);
            text_bumbu = itemView.findViewById(R.id.text_bumbu_recycler);
            playButton = itemView.findViewById(R.id.playicon);
            videoView = itemView.findViewById(R.id.videoview_card);
            playbutton_bar = itemView.findViewById(R.id.play_button_bar);
            timer_indicator1 = itemView.findViewById(R.id.time_indicator_bar);
            timer_indicator2 = itemView.findViewById(R.id.time_indicatorbar_2);
            isPlaying = false;
            videoIsPlaying = false;
            bufferProgress = itemView.findViewById(R.id.bufferProgress);
            videoViewControl = itemView.findViewById(R.id.videoview_control);
            playPause = itemView.findViewById(R.id.pauseicon);
            videoView_main = itemView.findViewById(R.id.videoView_main);
            playIcon = itemView.findViewById(R.id.playicon);
            rootCard = itemView.findViewById(R.id.cardview_Grid);
        }
    }
}




