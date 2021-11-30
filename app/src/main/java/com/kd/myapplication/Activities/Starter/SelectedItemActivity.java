package com.kd.myapplication.Activities.Starter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.kd.myapplication.Adapters.SubMenuAdapter;
import com.kd.myapplication.Models.SubMenuModel;
import com.kd.myapplication.R;

import java.util.ArrayList;

public class SelectedItemActivity extends AppCompatActivity {

    RecyclerView subItemRecyclerView;
    TextView toolbar_title, description, title;
    ArrayList<String> list;
    ArrayList<SubMenuModel> l;
    SubMenuAdapter adapter;
    ImageView backBtn;

    @SuppressLint("SetTextI18n")
    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_item);

        Intent intent = getIntent();
        list = new ArrayList<>();

        toolbar_title = findViewById(R.id.toolbar_title);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        backBtn = findViewById(R.id.backBtn);

        toolbar_title.setText(intent.getStringExtra("Title"));
        title.setText(intent.getStringExtra("Title")+" :-");
        description.setText(intent.getStringExtra("Description"));
        list = (ArrayList<String>) intent.getSerializableExtra("Type");

        subItemRecyclerView = findViewById(R.id.subItemRecyclerView);

        subItemRecyclerView.setHasFixedSize(true);
        subItemRecyclerView.setLayoutManager(new LinearLayoutManager(SelectedItemActivity.this));
        l = new ArrayList<>();

        for(int i=0;i<list.size();i++) {
            l.add(new SubMenuModel(list.get(i)));
        }

        adapter = new SubMenuAdapter(SelectedItemActivity.this, l);
        subItemRecyclerView.setAdapter(adapter);

        backBtn.setOnClickListener(v -> onBackPressed());

    }
}