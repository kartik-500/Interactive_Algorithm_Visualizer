package com.kd.myapplication.Activities.Starter.Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kd.myapplication.Adapters.HomeAdapter;
import com.kd.myapplication.Models.HomeModel;
import com.kd.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class HomeFragment extends Fragment {

    RecyclerView homeRecyclerView;
    ArrayList<HomeModel> list;
    HomeAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Home");

        homeRecyclerView = view.findViewById(R.id.HomeRecyclerView);

        homeRecyclerView.setHasFixedSize(true);
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();

        list.add(new HomeModel("Searching","* Searching is the process of finding a given value position in a list of values.\n" +
                "* It decides whether a search key is present in the data or not.\n" +
                "* It is the algorithmic process of finding a particular item in a collection of items.\n" +
                "* It can be done on internal data structure or on external data structure.", new ArrayList<>(Arrays.asList("Linear Search", "Binary Search"))));
        list.add(new HomeModel("Sorting","*) Sorting refers to ordering data in an increasing or decreasing fashion according to some linear relationship among the data items.\n"+
                "* Sorting can be done on names, numbers and records. Sorting reduces the For example, it is relatively easy to look up the phone number of a friend from a telephone dictionary because the names in the phone book have been sorted into alphabetical order.", new ArrayList<>(Arrays.asList("Bubble Sort", "Improved Bubble Sort", "Insertion Sort", "Selection Sort"))));

        adapter = new HomeAdapter(getContext(), list);
        homeRecyclerView.setAdapter(adapter);

        return view;
    }
}