package com.vs.elearnquran.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.vs.elearnquran.R;
import com.vs.elearnquran.model.HomeMenu;
import java.util.List;

public class HomeMenuAdapter extends RecyclerView.Adapter<HomeMenuAdapter.MyViewHolder>{

    private final Context context;
    private final List<HomeMenu> list;
    private final OnMyOwnClickListener onMyOwnClickListener;


    public HomeMenuAdapter(Context context, List<HomeMenu> list,
                           OnMyOwnClickListener onMyOwnClickListener) {
        this.context = context;
        this.list = list;
        this.onMyOwnClickListener = onMyOwnClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams")
        final View view = inflater.inflate(R.layout.design_home, null, false);
        return new MyViewHolder(view,onMyOwnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final HomeMenu obj = list.get(position);
        holder.tv_title.setText(obj.getTitle());
        Picasso.get().load(obj.getImg()).into(holder.img_icon);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView card_view;
        TextView tv_title;
        ImageView img_icon;

        OnMyOwnClickListener onMyOwnClickListener;

        public MyViewHolder(@NonNull View itemView, OnMyOwnClickListener onMyOwnClickListener) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            img_icon = itemView.findViewById(R.id.img_icon);
            card_view = itemView.findViewById(R.id.card_view);

            this.onMyOwnClickListener = onMyOwnClickListener;

            card_view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onMyOwnClickListener.onMyOwnClick(getAdapterPosition(),view);
        }
    }

    public interface OnMyOwnClickListener{
        void onMyOwnClick(int position,View view);
    }



}
