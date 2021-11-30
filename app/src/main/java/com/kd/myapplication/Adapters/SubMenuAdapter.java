package com.kd.myapplication.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kd.myapplication.Activities.MainCourse.Searching.SearchingActivity;
import com.kd.myapplication.Activities.MainCourse.Sorting.SortingActivity;
import com.kd.myapplication.Activities.Starter.SelectedItemActivity;
import com.kd.myapplication.Models.SubMenuModel;
import com.kd.myapplication.R;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

public class SubMenuAdapter extends RecyclerView.Adapter<SubMenuAdapter.MyViewHolder> {
    Context context;
    ArrayList<SubMenuModel> list;
    ArrayList<SubMenuModel> filteredList = new ArrayList<>();
    boolean isOpen = false;
    FoldingCell foldingCell;

    public SubMenuAdapter(Context context, ArrayList<SubMenuModel> list) {
        this.context = context;
        this.list = list;
        this.filteredList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.submenu_item, parent, false);
        return new MyViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SubMenuModel homeData = filteredList.get(position);

        Log.d("TAG", "onBindViewHolder: "+homeData.getTitle());

        holder.title.setText(homeData.getTitle());
        holder.index.setText(String.valueOf(position + 1));

        // attach click listener to folding cell
        holder.fc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    foldingCell.fold(false);
                    holder.fc.unfold(false);
                    isOpen = true;
                    foldingCell = holder.fc;
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive())
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                } else {
                    holder.fc.unfold(false);
                    foldingCell = holder.fc;
                    isOpen = true;
                }

            }
        });

        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.fc.fold(false);
                isOpen = false;
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive())
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        holder.generateRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pass intent
            }
        });

        holder.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SelectedItemActivity activity = (SelectedItemActivity) context;
                String title = activity.getIntent().getStringExtra("Title");

                if (title.toLowerCase().contains("search")) {
                    Intent intent = new Intent(context, SearchingActivity.class);
                    intent.putExtra("input", holder.input.getText().toString());
                    intent.putExtra("tag", holder.title.getText().toString());
                    context.startActivity(intent);
                    holder.fc.fold(false);
                    isOpen = false;
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive())
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                } else if (title.toLowerCase().contains("sort")) {
                    Intent intent = new Intent(context, SortingActivity.class);
                    intent.putExtra("input", holder.input.getText().toString());
                    intent.putExtra("tag", holder.title.getText().toString());
                    context.startActivity(intent);
                    holder.fc.fold(false);
                    isOpen = false;
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive())
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }

            }
        });

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // pass intent
//                Intent intent = new Intent(context, SelectedItemActivity.class);
//                intent.putExtra("Title",homeData.getTitle());
//                context.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, index, done, generateRandom;
        FoldingCell fc;
        ImageView close;
        EditText input;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            index = itemView.findViewById(R.id.index);
            fc = itemView.findViewById(R.id.folding_cell);
            close = itemView.findViewById(R.id.close);
            input = itemView.findViewById(R.id.input);
            done = itemView.findViewById(R.id.done);
            generateRandom = itemView.findViewById(R.id.generateRandom);
        }
    }
}

