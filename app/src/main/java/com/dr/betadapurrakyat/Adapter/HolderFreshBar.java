package com.dr.betadapurrakyat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dr.betadapurrakyat.FreshActivity;
import com.dr.betadapurrakyat.Model.FreshUtils;
import com.dr.betadapurrakyat.Model.ProductData;
import com.dr.betadapurrakyat.PromoActivity;
import com.dr.betadapurrakyat.R;
import com.dr.betadapurrakyat.VideoViewActivity;
import com.dr.betadapurrakyat.fragment.Fragment_Promo_Fresh;

import java.util.List;


public class HolderFreshBar extends RecyclerView.Adapter<HolderFreshBar.myHolder> {

    private Context context;

    private List<FreshUtils> datas;


    public HolderFreshBar(Context context, List<FreshUtils> datas) {
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
        final FreshUtils productData = datas.get(position);
        holder.title.setText(productData.getTitle());

        Glide.with(context)
        .asBitmap()
        .load(productData.getUri())
        .into(holder.imageView);
        holder.playImage.setVisibility(View.INVISIBLE);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (context,FreshActivity.class);
                Bundle bundle = new Bundle();
                // set Fragmentclass Arguments

                String a=  datas.get(position).getTitle();
                String b = datas.get(position).getUri();
                String c = datas.get(position).getSubtitle();
                //you can name the keys whatever you like
                bundle.putString("title", a);
                bundle.putString("uri",b);
                bundle.putString("subtitle",c);
//                Fragment_Promo_Fresh fragobj = new Fragment_Promo_Fresh();
//                fragobj.setArguments(bundle);

                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
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




