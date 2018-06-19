package com.dr.betadapurrakyat.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.dr.betadapurrakyat.Model.ProductData;
import com.dr.betadapurrakyat.R;
import com.dr.betadapurrakyat.VideoViewActivity;

import java.util.List;


public class HolderRCBar extends RecyclerView.Adapter<HolderRCBar.myHolder> {

    private Context context;

    private List<ProductData> datas;


    public HolderRCBar(Context context, List<ProductData> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_bar, parent, false);

        return new myHolder(view);

    }

    @Override
    public void onBindViewHolder(final myHolder holder, final int position) {
        final ProductData productData = datas.get(position);
        holder.title.setText(productData.getTitle());

        Glide.with(context)
        .asBitmap()
        .load(productData.getUri())
        .into(holder.imageView);


        holder.playImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VideoViewActivity.class);
                intent.putExtra("title", datas.get(position).getTitle()); //you can name the keys whatever you like
                intent.putExtra("subtitle", datas.get(position).getSubtitle()); //note that all these values have to be primitive (i.e boolean, int, double, String, etc.)
                intent.putExtra("price", datas.get(position).getStock());
                intent.putExtra("uri", datas.get(position).getUri());
                intent.putExtra("stock", datas.get(position).getStock());
                intent.putExtra("videoUri", datas.get(position).getVideoUri()); //note that all these values have to be primitive (i.e boolean, int, double, String, etc.)
                intent.putExtra("textbahan", datas.get(position).getBahan());
                intent.putExtra("textbumbu", datas.get(position).getBumbu());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class myHolder extends RecyclerView.ViewHolder {
        ImageView imageView, playImage;
        TextView title;


        public myHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.rBar_image);
            title = itemView.findViewById(R.id.rbar_title);
            playImage = itemView.findViewById(R.id.rBar_play);
        }
    }
}




