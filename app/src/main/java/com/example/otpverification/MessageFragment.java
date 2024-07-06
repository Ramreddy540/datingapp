package com.example.otpverification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.otpverification.Fragments.User_Chat_Fragment;
import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment implements MemberAdapter.OnItemClickListener {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private MemberAdapter memberAdapter;
    private List<Member> memberList;
    private ImageButton emojiButton;
    private ImageButton sendButton;
    private EditText editText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchView = view.findViewById(R.id.searchView);
        recyclerView = view.findViewById(R.id.recycler_view1);
        emojiButton = view.findViewById(R.id.emoji_button);
        sendButton = view.findViewById(R.id.send_button);
        editText = view.findViewById(R.id.edit_text);

        // Initialize member list with drawable resource IDs and example data
        memberList = new ArrayList<>();
        memberList.add(new Member("John Doe", R.drawable.jack, "Hey, how are you?", "12:45 PM", 2));
        memberList.add(new Member("Jane Smith", R.drawable.kp, "Let's catch up later.", "1:15 PM", 1));
        memberList.add(new Member("John Doe", R.drawable.jack, "Hey, how are you?", "12:45 PM", 2));
        memberList.add(new Member("Jane Smith", R.drawable.kp, "Let's catch up later.", "1:15 PM", 1));
        memberList.add(new Member("John Doe", R.drawable.jack, "Hey, how are you?", "12:45 PM", 2));
        memberList.add(new Member("Jane Smith", R.drawable.kp, "Let's catch up later.", "1:15 PM", 1));
        memberList.add(new Member("Virat", R.drawable.jack, "Hey, how are you?", "12:45 PM", 2));
        memberList.add(new Member("Jane Smith", R.drawable.kp, "Let's catch up later.", "1:15 PM", 1));
        memberList.add(new Member("John Doe", R.drawable.jack, "Hey, how are you?", "12:45 PM", 2));
        memberList.add(new Member("Jane Smith", R.drawable.kp, "Let's catch up later.", "1:15 PM", 1));
        memberList.add(new Member("John Doe", R.drawable.jack, "Hey, how are you?", "12:45 PM", 2));
        memberList.add(new Member("Jane Smith", R.drawable.kp, "Let's catch up later.", "1:15 PM", 1));
        memberList.add(new Member("John Cena", R.drawable.jack, "Hey, how are you?", "12:45 PM", 2));
        memberList.add(new Member("Jane Smith", R.drawable.kp, "Let's catch up later.", "1:15 PM", 1));
        memberList.add(new Member("John Doe", R.drawable.jack, "Hey, how are you?", "12:45 PM", 2));

        // Set up RecyclerView
        memberAdapter = new MemberAdapter(memberList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(memberAdapter);

        // Set up search listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Perform search when query is submitted
                memberAdapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Perform search when text is changed
                memberAdapter.filter(newText);
                return false;
            }
        });
    }

    @Override
    public void onItemClick(Member member) {
        // Handle the item click and open the new fragment
        Fragment newFragment = new User_Chat_Fragment(); // Replace with your target fragment
        Bundle args = new Bundle();
        args.putString("member_name", member.getName()); // Pass any required data
        newFragment.setArguments(args);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.framelayout, newFragment) // Make sure to use the correct container ID
                .addToBackStack(null)
                .commit();
    }
}
