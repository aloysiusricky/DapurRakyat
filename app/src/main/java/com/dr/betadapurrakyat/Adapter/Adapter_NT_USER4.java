package com.dr.betadapurrakyat.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dr.betadapurrakyat.Model.NetworkUri;
import com.dr.betadapurrakyat.Model.NewMenu;
import com.dr.betadapurrakyat.R;

import java.util.List;


public class Adapter_NT_USER4 extends RecyclerView.Adapter <Adapter_NT_USER4.myHolder> {

    private Context context;
    private List <NetworkUri> listUri;

    public Adapter_NT_USER4(Context context, List<NetworkUri> listUri) {
        this.context = context;
        this.listUri = listUri;
    }

    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_network_user, parent, false);

        return new myHolder(view);

    }

    @Override
    public void onBindViewHolder(myHolder holder, int position) {
        NetworkUri data = listUri.get(position);

            Glide.with(context)
                    .asBitmap()
                    .load(data.getUri())
                    .into(holder.imageView);



    }

    @Override
    public int getItemCount() {
        return listUri.size();
    }

    class myHolder extends ViewHolder {
        public ImageView imageView;



        public myHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.user_image_card);

        }
    }


}


//    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
//
//        Context context;
//        List<StudentDetails> MainImageUploadInfoList;
//
//        public RecyclerViewAdapter(Context context, List<StudentDetails> TempList) {
//
//            this.MainImageUploadInfoList = TempList;
//
//            this.context = context;
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);
//
//            ViewHolder viewHolder = new ViewHolder(view);
//
//            return viewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(ViewHolder holder, int position) {
//
//            StudentDetails studentDetails = MainImageUploadInfoList.get(position);
//
//            holder.StudentNameTextView.setText(studentDetails.getStudentName());
//
//            holder.StudentNumberTextView.setText(studentDetails.getStudentPhoneNumber());
//
//        }
//
//        @Override
//        public int getItemCount() {
//
//            return MainImageUploadInfoList.size();
//        }
//
//        class ViewHolder extends RecyclerView.ViewHolder {
//
//            public TextView StudentNameTextView;
//            public TextView StudentNumberTextView;
//
//            public ViewHolder(View itemView) {
//
//                super(itemView);
//
//                StudentNameTextView = (TextView) itemView.findViewById(R.id.ShowStudentNameTextView);
//
//                StudentNumberTextView = (TextView) itemView.findViewById(R.id.ShowStudentNumberTextView);
//            }
//        }
//    }



//    private static final String TAG = "CardHolderHome";
//
//    private Context context;
//    private List<ProductData> listdata = new ArrayList<>();
//
//    public CardHolderHome(Context context, List<ProductData> listdata) {
//        this.context = context;
//        this.listdata = listdata;
//    }
//
//    @Override
//    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View itemView = inflater.inflate(R.layout.card_view_image,parent,false);
//        return new myHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(myHolder holder, int position) {
//        Log.d(TAG, "onBindViewHolder: running");
//        ProductData productData = listdata.get(position);
//        holder.title.setText(productData.getTitle());
//        holder.subtitle.setText(productData.getSubtitle());
//        holder.price.setText(productData.getPrice());
//        holder.stock.setText(productData.getStock());
//        Glide.with(context)
//                .asBitmap()
//                .load(productData.getUri())
//                .into(holder.mimageView);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return listdata.size();
//    }
//
//    public class myHolder extends RecyclerView.ViewHolder {
//
//
//
//        public ImageView mimageView;
//        public TextView title, subtitle, price, stock;
//
//        myHolder(View itemView) {
//            super(itemView);
//            mimageView = itemView.findViewById(R.id.imageViewProduct);
//            title = itemView.findViewById(R.id.textViewTitle);
//            subtitle = itemView.findViewById(R.id.textViewSubTitle);
//            price = itemView.findViewById(R.id.textViewPrice);
//            stock = itemView.findViewById(R.id.textViewStock);
//
//            mimageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(context,"Test Click",Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    }
//
//}