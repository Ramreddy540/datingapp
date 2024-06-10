package com.example.otpverification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context context;

    private List<User> users;

    public UserAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_data, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);

        holder.heightTextView.setText("Height: " + user.getHeight());
        holder.religionTextView.setText("Religion: " + user.getReligion());
        holder.communityTextView.setText("Community: " + user.getCommunity());
        holder.smokeTextView.setText("Smoke: " + user.getSmoke());
        holder.statusTextView.setText("Status: " + user.getStatus());
        holder.drinkingTextView.setText("Drinking: " + user.getDrinking());
        holder.languageTextView.setText("Language: " + user.getLanguage());

        // Ensure imageUrls is not null
        if (user.getImageUrls() != null && !user.getImageUrls().isEmpty()) {
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(context, user.getImageUrls());
            holder.viewPager.setAdapter(viewPagerAdapter);
        } else {
            // Handle case where imageUrls is null or empty
            holder.viewPager.setAdapter(null);
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        TextView heightTextView, religionTextView, communityTextView, smokeTextView, statusTextView, drinkingTextView, languageTextView;
        ViewPager viewPager;
        ImageView imageview;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            heightTextView = itemView.findViewById(R.id.heightTextView);
            religionTextView = itemView.findViewById(R.id.religionTextView);
            communityTextView = itemView.findViewById(R.id.communityTextView);
            smokeTextView = itemView.findViewById(R.id.smokeTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            drinkingTextView = itemView.findViewById(R.id.drinkingTextView);
            languageTextView = itemView.findViewById(R.id.languageTextView);
            viewPager = itemView.findViewById(R.id.viewPager);
        }
    }
}
