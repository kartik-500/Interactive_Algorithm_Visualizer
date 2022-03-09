package com.kd.myapplication.Activities.MainCourse.Sorting.TreeHelper;
import android.view.View;
import android.widget.TextView;

import com.kd.myapplication.R;

public class Viewholder {

    public TextView nodeTitle;

    public Viewholder(View view) {
        nodeTitle = view.findViewById(R.id.nodeTitle);
    }
}

