package com.example.otpverification;import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ImagesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private List<ImageItem> imageItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        imageItemList = new ArrayList<>();
        // Add sample data (replace with your drawable resources)
        imageItemList.add(new ImageItem(R.drawable.girl, "Akhil"));
        imageItemList.add(new ImageItem(R.drawable.girl, "Madhan"));
        imageItemList.add(new ImageItem(R.drawable.girl, "Gandhi"));
        imageItemList.add(new ImageItem(R.drawable.girl, "Nagi"));
        imageItemList.add(new ImageItem(R.drawable.girl, "Vishnu"));
        imageItemList.add(new ImageItem(R.drawable.girl, "Naresh"));
        imageAdapter = new ImageAdapter(this, imageItemList);
        recyclerView.setAdapter(imageAdapter);
    }
}
