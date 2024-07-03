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
import com.example.otpverification.OnImageClickListener;
import com.example.otpverification.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

 public class ImagePagerAdapter extends RecyclerView.Adapter<ImagePagerAdapter.ImageViewHolder> {

    private Context context;
    private List<Users> viewPagerItems;
    private OnImageClickListener onImageClickListener;

    public ImagePagerAdapter(Context context, List<Users> viewPagerItems, OnImageClickListener onImageClickListener) {
        this.context = context;
        this.viewPagerItems = viewPagerItems;
        this.onImageClickListener = onImageClickListener;
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

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        TextView firstName, dob, height, religion, status, language, smoke, community, drinking;
        ImageView imageView, like, dislike;

        public ImageViewHolder(@NonNull View itemView) {

            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewPager);
            like = itemView.findViewById(R.id.heart_like);
            dislike = itemView.findViewById(R.id.i1);
            firstName = itemView.findViewById(R.id.name1);
            dob = itemView.findViewById(R.id.name2);
            height = itemView.findViewById(R.id.heightTextView);
            religion = itemView.findViewById(R.id.religionTextView);
            status = itemView.findViewById(R.id.statusTextView);
            language = itemView.findViewById(R.id.languageTextView);
            smoke = itemView.findViewById(R.id.smokeTextView);
            community = itemView.findViewById(R.id.communityTextView);
            drinking = itemView.findViewById(R.id.drinkingTextView);

            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onImageClickListener != null) {

                        Users likedItem = viewPagerItems.get(position);

                        String likedUserName = viewPagerItems.get(position).getFirstName();
                        String likedUserImage = likedItem.getImageUrl();
                        String likedUserPhone = viewPagerItems.get(position).getPhone_Num();

                        onImageClickListener.onImageClick(likedUserName,likedUserImage,likedUserPhone);
                    }
                }
            });

        }

    }

}
