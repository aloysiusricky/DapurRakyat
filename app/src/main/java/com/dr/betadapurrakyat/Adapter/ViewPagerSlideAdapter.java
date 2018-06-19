package com.dr.betadapurrakyat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dr.betadapurrakyat.Model.ProductData;
import com.dr.betadapurrakyat.Model.ProductDataV;
import com.dr.betadapurrakyat.R;
import com.dr.betadapurrakyat.VideoViewActivity;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerSlideAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List <ProductDataV> list;
    private ImageView imageView;
    private TextView textView;
    private ProductDataV productData;

    public ViewPagerSlideAdapter(Context context, List<ProductDataV> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.viewpager_slide, null);
        imageView = v.findViewById(R.id.imageView_VP);

        productData = list.get(position);
        Glide.with(context)
                .asBitmap()
                .load(productData.getUri())
                .into(imageView);
        ViewPager vp = (ViewPager) container;
        vp.addView(v);
//        v.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (position == 0) {
//                    ProductData mProductData = list.get(0);
//                    Intent intent = new Intent(context, VideoViewActivity.class);
//                    intent.putExtra("title", mProductData.getTitle()); //you can name the keys whatever you like
//                    intent.putExtra("subtitle", mProductData.getSubtitle()); //note that all these values have to be primitive (i.e boolean, int, double, String, etc.)
//                    intent.putExtra("price", mProductData.getStock());
//                    intent.putExtra("uri", mProductData.getUri());
//                    intent.putExtra("stock", mProductData.getStock());
//                    intent.putExtra("videoUri", mProductData.getVideoUri()); //note that all these values have to be primitive (i.e boolean, int, double, String, etc.)
//                    intent.putExtra("textbahan", mProductData.getBahan());
//                    intent.putExtra("textbumbu", mProductData.getBumbu());
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent);
//                }else if (position == 1){
//                    ProductData mProductData = list.get(1);
//                    Intent intent = new Intent(context, VideoViewActivity.class);
//                    intent.putExtra("title", mProductData.getTitle()); //you can name the keys whatever you like
//                    intent.putExtra("subtitle", mProductData.getSubtitle()); //note that all these values have to be primitive (i.e boolean, int, double, String, etc.)
//                    intent.putExtra("price", mProductData.getStock());
//                    intent.putExtra("uri", mProductData.getUri());
//                    intent.putExtra("stock", mProductData.getStock());
//                    intent.putExtra("videoUri", mProductData.getVideoUri()); //note that all these values have to be primitive (i.e boolean, int, double, String, etc.)
//                    intent.putExtra("textbahan", mProductData.getBahan());
//                    intent.putExtra("textbumbu", mProductData.getBumbu());
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent);
//                }else if (position == 2) {
//                    ProductData mProductData = list.get(2);
//                    Intent intent = new Intent(context, VideoViewActivity.class);
//                    intent.putExtra("title", mProductData.getTitle()); //you can name the keys whatever you like
//                    intent.putExtra("subtitle", mProductData.getSubtitle()); //note that all these values have to be primitive (i.e boolean, int, double, String, etc.)
//                    intent.putExtra("price", mProductData.getStock());
//                    intent.putExtra("uri", mProductData.getUri());
//                    intent.putExtra("stock", mProductData.getStock());
//                    intent.putExtra("videoUri", mProductData.getVideoUri()); //note that all these values have to be primitive (i.e boolean, int, double, String, etc.)
//                    intent.putExtra("textbahan", mProductData.getBahan());
//                    intent.putExtra("textbumbu", mProductData.getBumbu());
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent);
//                }
//            }
//        });
        return v;


    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}