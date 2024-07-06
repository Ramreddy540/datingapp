package com.example.otpverification;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {

    private List<Member> memberList;
    private List<Member> memberListFull; // Full list to store the original data
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Member member);
    }

    public MemberAdapter(List<Member> memberList, OnItemClickListener onItemClickListener) {
        this.memberList = memberList;
        this.memberListFull = new ArrayList<>(memberList); // Copy the original data
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member, parent, false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        Member member = memberList.get(position);
        holder.bind(member, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    public void filter(String query) {
        memberList.clear();
        if (query.isEmpty()) {
            memberList.addAll(memberListFull);
        } else {
            String filterPattern = query.toLowerCase().trim();
            for (Member member : memberListFull) {
                if (member.getName().toLowerCase().contains(filterPattern)) {
                    memberList.add(member);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class MemberViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView, time, lastmsg;
        private ImageView profileImageView;

        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            time = itemView.findViewById(R.id.timeTextView);
            lastmsg = itemView.findViewById(R.id.lastMessageTextView);
            profileImageView = itemView.findViewById(R.id.photoImageView);
        }

        public void bind(final Member member, final OnItemClickListener onItemClickListener) {
            nameTextView.setText(member.getName());
            time.setText(member.getTime());
            lastmsg.setText(member.getLastMessage());
            profileImageView.setImageResource(member.getPhoto());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(member);
                }
            });
        }
    }
}
