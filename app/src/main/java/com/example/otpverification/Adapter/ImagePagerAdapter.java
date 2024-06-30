package com.example.otpverification.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.otpverification.Models.Users;
import com.example.otpverification.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

 public class ImagePagerAdapter extends RecyclerView.Adapter<ImagePagerAdapter.ImageViewHolder> {

    private Context context;
    private List<Users> viewPagerItems;

    public ImagePagerAdapter(Context context, List<Users> viewPagerItems) {
        this.context = context;
        this.viewPagerItems = viewPagerItems;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_user_data,
                parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        Users item = viewPagerItems.get(position);

        holder.firstName.setText(item.getFirstName());
        holder.dob.setText(item.getDob());
        holder.height.setText(item.getHeight());
        holder.religion.setText(item.getReligion());
        holder.status.setText(item.getStatus());
        holder.language.setText(item.getLanguage());
        holder.smoke.setText(item.getSmoke());
        holder.community.setText(item.getCommunity());
        holder.drinking.setText(item.getDrinking());


        Glide.with(context)
                .load(item.getImageUrl())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {

        return viewPagerItems.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {

        TextView firstName, dob, height, religion, status, language, smoke, community, drinking;
        ImageView imageView;

        ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewPager);
            firstName = itemView.findViewById(R.id.name1);
            dob = itemView.findViewById(R.id.name2);
            height = itemView.findViewById(R.id.heightTextView);
            religion = itemView.findViewById(R.id.religionTextView);
            status = itemView.findViewById(R.id.statusTextView);
            language = itemView.findViewById(R.id.languageTextView);
            smoke = itemView.findViewById(R.id.smokeTextView);
            community = itemView.findViewById(R.id.communityTextView);
            drinking = itemView.findViewById(R.id.drinkingTextView);

        }
    }

}
