package com.kd.myapplication.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.kd.myapplication.Activities.Starter.SelectedItemActivity;
import com.kd.myapplication.Models.HomeModel;
import com.kd.myapplication.R;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    Context context;
    ArrayList<HomeModel> list;
    ArrayList<HomeModel> filteredList = new ArrayList<>();

    public HomeAdapter(Context context, ArrayList<HomeModel> list) {
        this.context = context;
        this.list = list;
        this.filteredList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.home_item, parent, false);
        return new MyViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HomeModel homeData = filteredList.get(position);
        holder.title.setText(homeData.getTitle());
        holder.index.setText(String.valueOf(position+1));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pass intent
                Intent intent = new Intent(context, SelectedItemActivity.class);
                intent.putExtra("Title",homeData.getTitle());
                intent.putExtra("Description",homeData.getDesc());
                intent.putExtra("Type",homeData.getType());

                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) context, (View)holder.title, "text");
                context.startActivity(intent, options.toBundle());

            }
        });

    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, index;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            index = itemView.findViewById(R.id.index);
        }
    }
}

