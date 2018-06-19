package com.dr.betadapurrakyat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dr.betadapurrakyat.Model.ProductData;
import com.dr.betadapurrakyat.R;
import com.dr.betadapurrakyat.VideoViewActivity;

import java.util.ArrayList;
import java.util.List;


public class
HolderSearchList extends RecyclerView.Adapter<HolderSearchList.myHolder> {

    private Context context;
    private List<ProductData> datas;;


    public HolderSearchList(Context context, List<ProductData> datas) {
        this.context = context;
        this.datas = datas;

    }

    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list, parent, false);
        return new myHolder(view);
    }
    @Override
    public void onBindViewHolder(final myHolder holder, final int position) {
        final ProductData productData = datas.get(position);
        holder.title.setText(productData.getTitle());

        holder.root.setOnClickListener(new View.OnClickListener() {
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
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }



    class myHolder extends RecyclerView.ViewHolder {
        TextView title;
        RelativeLayout root;


        public myHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_list_search);
            root = itemView.findViewById(R.id.rootview_search);
        }
    }
//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                List<ProductData> filteredResults = null;
//                if (charSequence.length() == 0) {
//                    filteredResults = datas;
//                } else {
//                    filteredResults = getFilteredResults(charSequence.toString().toLowerCase());
//                }
//
//                FilterResults results = new FilterResults();
//                results.values = filteredResults;
//
//                return results;
//            }
//
//            private List<ProductData> getFilteredResults(String s) {
//                List<ProductData> results = new ArrayList<>();
//
//                for (ProductData item : datas) {
//                    if (item.getTitle().toLowerCase().contains(s)) {
//                        results.add(item);
//                    }
//                }
//                return results;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                searchText = (List<String>) filterResults.values;
//                HolderSearchList.this.notifyDataSetChanged();
//            }
//        };
//    }
}




