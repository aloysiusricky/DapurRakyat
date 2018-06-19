package com.dr.betadapurrakyat.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dr.betadapurrakyat.Model.NewTools;
import com.dr.betadapurrakyat.R;

import java.util.List;


public class ToolsAdapter extends RecyclerView.Adapter <ToolsAdapter.myHolder> {

    private Context context;

    private List <NewTools> datas;

    public ToolsAdapter(Context context, List<NewTools> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_nw_new_tool, parent, false);

        return new myHolder(view);

    }

    @Override
    public void onBindViewHolder(final myHolder holder, int position) {
        NewTools productData = datas.get(position);
        holder.title.setText(productData.getTitle());
        holder.subtitle.setText(productData.getSubtitle());
        holder.price.setText(productData.getHarga());
        holder.text_deskripsi.setText(productData.getDeskripsi());
        holder.dropdown_details.setVisibility(View.GONE);

        Glide.with(context)
                .asBitmap()
                .load(productData.getUri())
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Test, OnClick",Toast.LENGTH_SHORT).show();
            }
        });

//        holder.text_bumbu.setText((CharSequence) productData.getmBumbu());
//        holder.text_bahan.setText((CharSequence) productData.getmBahan());
        holder.orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.dropdown_details.setVisibility(View.VISIBLE);
                holder.dropDown.setVisibility(View.INVISIBLE);
                holder.dropUp.setVisibility(View.VISIBLE);

            }
        });
        holder.dropUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.dropUp.setVisibility(View.GONE);
                holder.dropDown.setVisibility(View.VISIBLE);
                holder.dropdown_details.setVisibility(View.GONE);
            }
        });
        holder.dropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.dropdown_details.setVisibility(View.VISIBLE);
                holder.dropDown.setVisibility(View.INVISIBLE);
                holder.dropUp.setVisibility(View.VISIBLE);

            }
        });
    }

    @Override
    public int getItemCount() {

        return datas.size();
    }

    class myHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView title, subtitle, price, stock;
        public Button orderButton,konfrimasiButton;
        //drop down
        public LinearLayout dropdown_details;
        public ImageView dropDown,dropUp;

        public TextView text_deskripsi;



        public myHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewProduct_tool);
            title = itemView.findViewById(R.id.textViewTitle_tool);
            subtitle = itemView.findViewById(R.id.textViewSubTitle_tool);
            price = itemView.findViewById(R.id.textViewPrice_tool);

            dropDown = itemView.findViewById(R.id.dropDown_tool);
            dropUp = itemView.findViewById(R.id.dropUp_tool);
            orderButton = itemView.findViewById(R.id.order_button_tool);

            dropdown_details = itemView.findViewById(R.id.dropdown_details_tool);

            text_deskripsi = itemView.findViewById(R.id.text_deskripsi_recycler);

        }
    }
}




