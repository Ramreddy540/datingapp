package com.example.otpverification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class YourLikeAdapter extends RecyclerView.Adapter<YourLikeAdapter.YourLikeViewHolder> {

    private List<YourLikeItem> itemList;
    private Context context;

    public YourLikeAdapter(Context context, List<YourLikeItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public YourLikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.your_like_item, parent, false);
        return new YourLikeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YourLikeViewHolder holder, int position) {
        YourLikeItem item = itemList.get(position);
        holder.textView.setText(item.getText());
        holder.imageView.setImageResource(item.getImageResourceId());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class YourLikeViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public YourLikeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.like_item_image);
            textView = itemView.findViewById(R.id.like_item_text);
        }
    }
}
