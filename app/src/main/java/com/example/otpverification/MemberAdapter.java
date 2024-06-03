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
    private List<Member> memberListFull;

    public MemberAdapter(List<Member> memberList) {
        this.memberList = memberList;
        this.memberListFull = new ArrayList<>(memberList);
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member, parent, false);
        return new MemberViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        Member currentMember = memberList.get(position);
        holder.photoImageView.setImageResource(currentMember.getPhoto());
        holder.nameTextView.setText(currentMember.getName());
        holder.lastMessageTextView.setText(currentMember.getLastMessage());
        holder.timeTextView.setText(currentMember.getTime());
        holder.messageCountTextView.setText(String.valueOf(currentMember.getMessageCount()));
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    public void filter(String text) {
        memberList.clear();
        if (text.isEmpty()) {
            memberList.addAll(memberListFull);
        } else {
            text = text.toLowerCase();
            for (Member member : memberListFull) {
                if (member.getName().toLowerCase().contains(text) || member.getLastMessage().toLowerCase().contains(text)) {
                    memberList.add(member);
                }
            }
        }
        notifyDataSetChanged();
    }

    static class MemberViewHolder extends RecyclerView.ViewHolder {
        ImageView photoImageView;
        TextView nameTextView;
        TextView lastMessageTextView;
        TextView timeTextView;
        TextView messageCountTextView;

        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            photoImageView = itemView.findViewById(R.id.photoImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            lastMessageTextView = itemView.findViewById(R.id.lastMessageTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            messageCountTextView = itemView.findViewById(R.id.messageCountTextView);
        }
    }
}
