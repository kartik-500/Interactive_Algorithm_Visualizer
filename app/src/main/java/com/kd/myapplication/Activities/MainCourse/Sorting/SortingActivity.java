package com.kd.myapplication.Activities.MainCourse.Sorting;

import androidx.appcompat.app.AppCompatActivity;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;
import com.kd.myapplication.R;
import java.util.ArrayList;

public class SortingActivity extends AppCompatActivity {

    RelativeLayout ll, actionMenu;
    TextView iteration, elementTV, elementString, definition, toolbar_title, bottomElement, sortedElement, sortedText, minimumTV;
    HTextView animatedText;
    ImageView back, next, play, backBtn;

    ArrayList<Integer[]> previous_step;
    int[] ids;
    ArrayList<Boolean> previous_steps;
    String[] example;
    String tag;
    Integer[] arr;
    int i, j, key, swaps, min_idx;
    float width;
    boolean isSwapped = false, isPlay = false, isAnimationPlaying = false, isAlreadyCreated = false, isReset = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorting);

        example = getIntent().getStringExtra("input").split("[,]");
        tag = getIntent().getStringExtra("tag");

        bindViews();

        reset();
    }

    @SuppressLint("SetTextI18n")
    private void bindViews() {

        ll = findViewById(R.id.ll);
        actionMenu = findViewById(R.id.actionMenu);
        width = (getResources().getDisplayMetrics().widthPixels) - 80;
        iteration = findViewById(R.id.iteration);
        elementTV = findViewById(R.id.elementTV);
        toolbar_title = findViewById(R.id.toolbar_title);
        elementString = findViewById(R.id.elementString);
        animatedText = findViewById(R.id.animatedText);
        definition = findViewById(R.id.definition);
        play = findViewById(R.id.play);
        next = findViewById(R.id.next);
        back = findViewById(R.id.back);
        backBtn = findViewById(R.id.backBtn);

        if (tag.toLowerCase().contains("improved bubble")) {
            toolbar_title.setText("Improved Bubble Sort");
            definition.setText(getString(R.string.improved_bubble_sort_definition));
        } else if (tag.toLowerCase().contains("insertion sort")) {
            toolbar_title.setText("Insertion Sort");
            definition.setText(getString(R.string.insertion_sort_definition));
        } else if (tag.toLowerCase().contains("selection sort")) {
            toolbar_title.setText("Selection Sort");
            definition.setText(getString(R.string.selection_sort_definition));
        } else {
            definition.setText(getString(R.string.bubble_sort_definition));
        }

    }

    public void createDynamicTextView() {
        for (int i = 0; i < example.length; i++) {
            TextView tv = new TextView(this); // Prepare textview object programmatically
            tv.setText(example[i]);
            tv.setHeight(100);

            RelativeLayout.LayoutParams lprams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            lprams.setMargins(5, 100, 5, 0);
            tv.setPadding(0, 10, 0, 10);

            tv.setTextColor(Color.WHITE);

            tv.setBackgroundResource(R.color.purple_500);
            tv.setGravity(Gravity.CENTER);

            tv.setLayoutParams(lprams);
            tv.setWidth((int) ((width - (10 * example.length)) / example.length));
            tv.setId(i + 1);

            if (i != 0) {
                lprams.addRule(RelativeLayout.END_OF, i);
            }

            ll.addView(tv); // Add to your ViewGroup using this method
        }
    }

    public void createIndices() {
        for (int i = 0; i < example.length; i++) {
            TextView tv = new TextView(this); // Prepare textview object programmatically
            tv.setText(String.valueOf(i));
            tv.setHeight(100);

            RelativeLayout.LayoutParams lprams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            lprams.setMargins(5, 0, 5, 0);
            tv.setPadding(0, 10, 0, 10);

            tv.setTextColor(Color.BLACK);
            tv.setGravity(Gravity.CENTER);

            tv.setLayoutParams(lprams);
            tv.setWidth((getResources().getDisplayMetrics().widthPixels - 40 - (10 * this.example.length)) / this.example.length);

            if (i != 0) {
                lprams.addRule(RelativeLayout.END_OF, i);
            }
            lprams.addRule(RelativeLayout.BELOW, 1);
            ll.addView(tv); // Add to your ViewGroup using this method

        }
    }

    @SuppressLint("SetTextI18n")
    public void reset() {

        for (int i = 0; i < example.length; i++) {
            ll.removeView(ll.findViewById(i + 1));
        }

        previous_steps = new ArrayList<>();
        previous_step = new ArrayList<>();

        swaps = 0;
        iteration.setText("Swaps : " + (swaps));
        int size = example.length;
        arr = new Integer[size];
        for (int i = 0; i < size; i++) {
            arr[i] = Integer.parseInt(example[i]);
        }

        createDynamicTextView();

        isPlay = false;
        isSwapped = false;
        isAnimationPlaying = false;
        isAlreadyCreated = false;
        i = 0;
        j = 1;

        if (!isReset && tag.toLowerCase().contains("insertion")) {
            createIndices();
            isReset = true;
        }

        play.setOnClickListener(v -> {
            if (tag.toLowerCase().contains("bubble")) {
                if (!isAnimationPlaying) {
                    isPlay = true;
                    actionMenu.setVisibility(View.GONE);
                    bubbleSort();
                }
            } else if (tag.toLowerCase().contains("insertion")) {
                if (!isAnimationPlaying) {
                    if (i == 0 && j == 1) {
                        i = 1;
                        j = 0;
                        key = arr[i];
                    }
                    isPlay = true;
                    actionMenu.setVisibility(View.GONE);
                    insertionSort();
                    showSortedElements();
                }
            } else if (tag.toLowerCase().contains("selection")) {
                if (!isAnimationPlaying) {

                    if (i == 0 && j == 1) {
                        ids = new int[arr.length];
                        for (int i = 0; i < arr.length; i++) {
                            ids[i] = i + 1;
                        }
                    }

                    min_idx = 0;
                    isPlay = true;
                    selectionSort();
                }
            }
        });

        next.setOnClickListener(v -> {
            if (tag.toLowerCase().contains("bubble")) {
                if (!isAnimationPlaying) {
                    isPlay = false;
                    bubbleSort();
                }
            } else if (tag.toLowerCase().contains("insertion")) {
                if (!isAnimationPlaying) {
                    if (i == 0 && j == 1) {
                        i = 1;
                        j = 0;
                        key = arr[i];
                    }
                    isPlay = false;
                    addBackToStack();
                    insertionSort();
                    showSortedElements();
                }
            } else if (tag.toLowerCase().contains("selection")) {
                if (!isAnimationPlaying) {
                    if (i == 0 && j == 1) {
                        ids = new int[arr.length];
                        for (int i = 0; i < arr.length; i++) {
                            ids[i] = i + 1;
                        }
                        min_idx = 0;
                    }
                    isPlay = false;
                    selectionSort();
                }
            }
        });

        back.setOnClickListener(v -> {
            if (tag.toLowerCase().contains("bubble")) {
                if (!isAnimationPlaying) {
                    isPlay = false;
                    bs_goBackOneStep();
                }
            } else if (tag.toLowerCase().contains("insertion")) {
                if (!isAnimationPlaying) {
                    isPlay = false;
                    if (previous_step.size() != 0)
                        reverseInsertionSort();
                    else
                        Toast.makeText(SortingActivity.this, "You've reached at Start", Toast.LENGTH_SHORT).show();
                }
            } else if (tag.toLowerCase().contains("selection")) {
                if (!isAnimationPlaying && previous_step.size() != 0) {
                    ss_goBackOneStep();
                }
            }
        });

        backBtn.setOnClickListener(v -> onBackPressed());
        animatedText.setVisibility(View.INVISIBLE);
        actionMenu.setVisibility(View.VISIBLE);
        previous_step = new ArrayList<>();

        if (sortedElement != null) {
            ll.removeView(sortedElement);
            sortedElement = null;
        }

        if (sortedText != null) {
            ll.removeView(sortedText);
            sortedText = null;
        }

        if (minimumTV != null) {
            ll.removeView(minimumTV);
            minimumTV = null;
        }
    }


    // --------------------------------------------------------------------  Bubble Sort Section  -------------------------------------------------------------------------------------------------------


    @SuppressLint("SetTextI18n")
    public void bubbleSort() {
        int n = arr.length;
        for (; i < n; i++) {
            for (; j < (n - i); j++) {
                if (arr[j - 1] > arr[j]) {
                    isSwapped = true;
                    iteration.setText("Swaps : " + (++swaps));
                    swapelement();
                    return;
                } else {
                    noswap();
                    return;
                }
            }
            if ((!isSwapped) && tag.toLowerCase().contains("improved bubble"))
                break;
            isSwapped = false;
            j = 1;
        }
        animatedText.setAnimateType(HTextViewType.RAINBOW);
        animatedText.setBackgroundColor(Color.BLACK);
        animatedText.animateText("Sorted !");
        animatedText.setVisibility(View.VISIBLE);
        new Handler(getMainLooper()).postDelayed(this::reset, 2500);
    }

    private void noswap() {

        previous_steps.add(false);

        TextView tv1, tv2;
        tv1 = ll.findViewById(j);
        tv2 = ll.findViewById(j + 1);

        tv1.setBackgroundColor(Color.RED);
        tv2.setBackgroundColor(Color.RED);

        isAnimationPlaying = true;

        animatedText.setAnimateType(HTextViewType.SCALE);
        animatedText.setBackgroundColor(Color.BLACK);
        animatedText.animateText(arr[j - 1] + " < " + arr[j] + " (No Swapping)");
        animatedText.setVisibility(View.VISIBLE);

        new Handler(getMainLooper()).postDelayed(() -> {

            tv1.setBackgroundResource(R.color.purple_500);
            tv2.setBackgroundResource(R.color.purple_500);

            if (j == arr.length - 1) {
                i++;
                j = 1;

                if ((!isSwapped) && tag.toLowerCase().contains("improved bubble")) {
                    animatedText.setAnimateType(HTextViewType.RAINBOW);
                    animatedText.setBackgroundColor(Color.BLACK);
                    animatedText.animateText("Sorted !");
                    animatedText.setVisibility(View.VISIBLE);
                    new Handler(getMainLooper()).postDelayed(this::reset, 2500);
                } else {
                    if (isPlay) {
                        bubbleSort();
                    }
                }
            } else {
                j++;
                if (isPlay) {
                    bubbleSort();
                }
            }

            isAnimationPlaying = false;
            Log.d("TAG", "i: " + i + " j: " + j);

        }, 2500);

    }

    private void swapelement() {

        previous_steps.add(true);

        TextView tv1, tv2;
        tv1 = ll.findViewById(j);
        tv2 = ll.findViewById(j + 1);

        tv1.setBackgroundColor(Color.RED);
        tv2.setBackgroundColor(Color.RED);

        animatedText.setAnimateType(HTextViewType.EVAPORATE);
        animatedText.setBackgroundColor(Color.BLACK);
        animatedText.animateText(arr[j - 1] + " > " + arr[j] + " (Swapping)");
        animatedText.setVisibility(View.VISIBLE);

        Path path1 = new Path();
        isAnimationPlaying = true;

        path1.arcTo(5 + (width * (j - 1) / example.length), 0, (width * (j - 1) / example.length) + (((width) + (10 * ((j - 1)))) / example.length), 200, 0, -180, true);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(tv2, View.X, View.Y, path1);
        animator1.setDuration(5000);
        animator1.start();

        Path path2 = new Path();
        path2.arcTo(5 + (width * (j - 1) / example.length), 0, (width * (j - 1) / example.length) + (((width) + (10 * (j))) / example.length), 200, 180, -180, true);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(tv1, View.X, View.Y, path2);
        animator2.setDuration(5000);
        animator2.start();

        animator2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                tv1.setId(j + 1);
                tv2.setId(j);

                tv1.setBackgroundResource(R.color.purple_500);
                tv2.setBackgroundResource(R.color.purple_500);

                int temp = arr[j - 1];
                arr[j - 1] = arr[j];
                arr[j] = temp;
                if (j == arr.length - 1) {
                    i++;
                    j = 1;
                } else {
                    j++;
                }

                isAnimationPlaying = false;
                Log.d("TAG", "i: " + i + " j: " + j);

                if (isPlay) {
                    bubbleSort();
                }
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void bs_goBackOneStep() {

        if (previous_steps.size() == 0) {
            Toast.makeText(SortingActivity.this, "Reached End", Toast.LENGTH_SHORT).show();
            return;
        }

        if (previous_steps.get(previous_steps.size() - 1)) {

            if (j == 1) {
                i--;
                j = arr.length - 1;
            } else {
                j--;
            }

            iteration.setText("Swaps : " + (--swaps));

            TextView tv1, tv2;
            tv1 = ll.findViewById(j);
            tv2 = ll.findViewById(j + 1);

            tv1.setBackgroundColor(Color.RED);
            tv2.setBackgroundColor(Color.RED);

            animatedText.setAnimateType(HTextViewType.EVAPORATE);
            animatedText.setBackgroundColor(Color.BLACK);
            animatedText.animateText(arr[j - 1] + " < " + arr[j] + " (Swapping)");
            animatedText.setVisibility(View.VISIBLE);

            Path path1 = new Path();
            isAnimationPlaying = true;

            path1.arcTo(5 + (width * (j - 1) / example.length), 0, (width * (j - 1) / example.length) + (((width) + (10 * ((j - 1)))) / example.length), 200, 0, -180, true);
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(tv2, View.X, View.Y, path1);
            animator1.setDuration(5000);
            animator1.start();

            Path path2 = new Path();
            path2.arcTo(5 + (width * (j - 1) / example.length), 0, (width * (j - 1) / example.length) + (((width) + (10 * (j))) / example.length), 200, 180, -180, true);
            ObjectAnimator animator2 = ObjectAnimator.ofFloat(tv1, View.X, View.Y, path2);
            animator2.setDuration(5000);
            animator2.start();

            animator2.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);

                    tv1.setId(j + 1);
                    tv2.setId(j);

                    tv1.setBackgroundResource(R.color.purple_500);
                    tv2.setBackgroundResource(R.color.purple_500);

                    int temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;

                    isAnimationPlaying = false;
                    Log.d("TAG", "i: " + i + " j: " + j);

                    previous_steps.remove(previous_steps.size() - 1);
                }
            });


        } else {

            if (j == 1) {
                i--;
                j = arr.length - 1;
            } else {
                j--;
            }

            TextView tv1, tv2;
            tv1 = ll.findViewById(j);
            tv2 = ll.findViewById(j + 1);

            tv1.setBackgroundColor(Color.RED);
            tv2.setBackgroundColor(Color.RED);

            isAnimationPlaying = true;

            animatedText.setAnimateType(HTextViewType.SCALE);
            animatedText.setBackgroundColor(Color.BLACK);
            animatedText.animateText(arr[j - 1] + " < " + arr[j] + " (No Swapping)");
            animatedText.setVisibility(View.VISIBLE);

            new Handler(getMainLooper()).postDelayed(() -> {

                tv1.setBackgroundResource(R.color.purple_500);
                tv2.setBackgroundResource(R.color.purple_500);

                isAnimationPlaying = false;
                Log.d("TAG", "i: " + i + " j: " + j);
                previous_steps.remove(previous_steps.size() - 1);

            }, 2500);

        }
    }


    // --------------------------------------------------------------------  Insertion Sort Section  -------------------------------------------------------------------------------------------------------


    @SuppressLint("SetTextI18n")
    private void reverseInsertionSort() {
        boolean isChanges = false;
        isAnimationPlaying = true;
        Log.d("TAG", "reverseInsertionSort: " + previous_step.get(0)[0]);
        Integer[] buffer = previous_step.get(previous_step.size() - 1);
        previous_step.remove((previous_step.size() - 1));
        for (int i = 0; i < buffer.length; i++) {
            TextView tv = ll.findViewById(i + 1);
            if (!arr[i].equals(buffer[i])) {
                isChanges = true;
                tv.setText("" + buffer[i]);
                arr[i] = buffer[i];
                tv.setBackgroundColor(Color.RED);
            }
        }

        if (!isChanges) {
            Toast.makeText(SortingActivity.this, "No Swaps in the Last Step", Toast.LENGTH_SHORT).show();
        }

        new Handler(getMainLooper()).postDelayed(() -> {
            for (int i = 0; i < buffer.length; i++) {
                TextView tv = ll.findViewById(i + 1);
                tv.setBackgroundResource(R.color.purple_500);
            }
            isAnimationPlaying = false;
            iteration.setText(getString(R.string.swaps) + (--swaps));

            i--;
            j = i - 1;
        }, 2000);


    }

    @SuppressLint("SetTextI18n")
    public void showSortedElements() {

        if (sortedElement == null) {

            sortedElement = new TextView(this);
            sortedElement.setHeight(5);
            sortedElement.setPadding(0, 10, 0, 10);
            sortedElement.setBackgroundColor(Color.GRAY);
            sortedElement.setGravity(Gravity.CENTER);

        } else {
            ll.removeView(sortedElement);
        }

        if (sortedText == null) {
            sortedText = new TextView(this);
            sortedText.setHeight(100);
            sortedText.setText("Sorted");
            sortedText.setPadding(0, 10, 0, 10);
            sortedText.setTextColor(Color.GRAY);
            sortedText.setGravity(Gravity.CENTER);
            ll.addView(sortedText);
        }


        RelativeLayout.LayoutParams lprams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        sortedElement.setWidth((getResources().getDisplayMetrics().widthPixels - 40 - (10 * this.example.length)) * (i) / this.example.length);

        lprams.setMargins(5, 150, 5, 25);

        lprams.addRule(RelativeLayout.BELOW, 1);
        sortedElement.setLayoutParams(lprams);
        ll.addView(sortedElement); // Add to your ViewGroup using this method

        RelativeLayout.LayoutParams lprams2 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        sortedText.setWidth((getResources().getDisplayMetrics().widthPixels - 40 - (10 * this.example.length)) / this.example.length);
        lprams2.setMargins(sortedElement.getWidth() / 2, 150, 5, 0);
        lprams2.addRule(RelativeLayout.BELOW, 1);
        sortedText.setLayoutParams(lprams2);
    }

    @SuppressLint("SetTextI18n")
    public void insertionSort() {
        animatedText.setVisibility(View.VISIBLE);
        for (; i < arr.length; i++) {
            if (!isAlreadyCreated) {
                isAnimationPlaying = true;
                createBottomElement();
                isAlreadyCreated = true;
            }
            while (j >= 0 && arr[j] > key) {
                isAnimationPlaying = true;
                swaps++;
                iteration.setText(getString(R.string.swaps) + swaps);
                is_swapelement();
                return;
            }
            is_noswap();
            return;
        }
        animatedText.setBackgroundColor(Color.BLACK);
        animatedText.setVisibility(View.VISIBLE);
        animatedText.setAnimateType(HTextViewType.RAINBOW);
        animatedText.animateText("Sorted!");
        new Handler(getMainLooper()).postDelayed(this::reset, 2500);

    }

    private void is_noswap() {

        isAnimationPlaying = true;
        animatedText.setBackgroundColor(Color.BLACK);
        animatedText.setVisibility(View.VISIBLE);
        animatedText.animateText((j + 1) + " is correct position for element : " + key);

        new Handler(getMainLooper()).postDelayed(() -> {
            deselectItem(i);
            arr[j + 1] = key;
            j = i;
            if (i != arr.length - 1) {
                key = arr[i + 1];
            }
            if (i != arr.length - 1) {
                animatedText.setBackgroundColor(Color.BLACK);
                animatedText.setVisibility(View.VISIBLE);
                animatedText.animateText("Next Element is : " + arr[i + 1]);
            }
//            previous_step.add(arr);

        }, 2000);

        new Handler(getMainLooper()).postDelayed(() -> {
            i++;
            showSortedElements();
            if (isPlay) {
                insertionSort();
            }
            isAnimationPlaying = false;
        }, 4500);
    }

    private void addBackToStack() {
        Integer[] abc = new Integer[arr.length];
        System.arraycopy(arr, 0, abc, 0, arr.length);
        previous_step.add(abc);
    }

    private void deleteBottomElement() {
        TextView tv1;
        tv1 = ll.findViewById(j + 2);
        tv1.setText(bottomElement.getText().toString());
        bottomElement.setVisibility(View.GONE);
        isAlreadyCreated = false;
    }

    @SuppressLint("ResourceType")
    private void createBottomElement() {

        isAnimationPlaying = true;
        bottomElement = new TextView(this); // Prepare textview object programmatically
        bottomElement.setText(example[i]);
        bottomElement.setHeight(100);

        TextView tv1 = ll.findViewById(i + 1);
        tv1.setText("");
        tv1.setBackgroundColor(Color.RED);

        RelativeLayout.LayoutParams lprams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        lprams.setMargins(5, 100, 5, 0);
        bottomElement.setPadding(0, 10, 0, 10);

        bottomElement.setTextColor(Color.WHITE);

        bottomElement.setBackgroundResource(R.color.purple_500);
        bottomElement.setGravity(Gravity.CENTER);

        bottomElement.setLayoutParams(lprams);
        bottomElement.setWidth((int) ((width - (10 * example.length)) / example.length));
        bottomElement.setId(-100);

        if (i != 0) {
            lprams.addRule(RelativeLayout.END_OF, i);
        }
        lprams.addRule(RelativeLayout.BELOW, 1);

        ll.addView(bottomElement); // Add to your ViewGroup using this method

    }

    private void deselectItem(int i) {
        isAnimationPlaying = true;
        TextView tv1;
        tv1 = ll.findViewById(i + 1);
        tv1.setBackgroundResource(R.color.purple_500);
        deleteBottomElement();
    }

    public void is_swapelement() {

        TextView tv1, tv2;
        tv1 = ll.findViewById(j + 1);
        tv2 = ll.findViewById(j + 2);

        tv1.setBackgroundColor(Color.RED);
        tv2.setBackgroundColor(Color.RED);

        animatedText.setAnimateType(HTextViewType.EVAPORATE);
        animatedText.setBackgroundColor(Color.BLACK);
        animatedText.setVisibility(View.VISIBLE);

        isAnimationPlaying = true;

        Log.d("TAG", "insertionSort1: " + arr[0] + " " + arr[1] + " " + arr[2] + " " + arr[3] + " " + arr[4] + " ");
        animatedText.setBackgroundColor(Color.BLACK);
        animatedText.setVisibility(View.VISIBLE);
        animatedText.animateText(arr[j] + " > " + key + " (Swapping)");

        new Handler(getMainLooper()).postDelayed(() -> {
            tv2.setText(tv1.getText().toString());
            tv1.setText("");
            tv1.setBackgroundResource(R.color.purple_500);
            tv2.setBackgroundResource(R.color.purple_500);
            animatedText.setBackgroundColor(Color.BLACK);
            animatedText.setVisibility(View.VISIBLE);
            animatedText.animateText("Now check for previous element");
            arr[j + 1] = arr[j];
            j--;
        }, 2000);

        new Handler(getMainLooper()).postDelayed(this::insertionSort, 4500);

    }


    // --------------------------------------------------------------------  Selection Sort Section  -------------------------------------------------------------------------------------------------------


    public void selectionSort() {
        int n = arr.length;
        isAnimationPlaying = true;
        for (; i < n - 1; i++) {
            for (; j < n; j++) {
                if (arr[j] < arr[min_idx]) {
                    changeMinimum();
                    return;
                } else {
                    noChange();
                    return;
                }
            }
            swapMinimumElement();
            return;
        }

        animatedText.setAnimateType(HTextViewType.RAINBOW);
        animatedText.setBackgroundColor(Color.BLACK);
        animatedText.animateText("Sorted !");
        animatedText.setVisibility(View.VISIBLE);

        new Handler(getMainLooper()).postDelayed(this::reset, 2500);

    }

    private void noChange() {
        TextView tv = ll.findViewById(ids[j]);
        tv.setBackgroundColor(Color.RED);

        animatedText.setAnimateType(HTextViewType.EVAPORATE);
        animatedText.setBackgroundColor(Color.BLACK);
        animatedText.animateText(arr[min_idx] + " < " + arr[j] + " (No Change in Minimum)");
        animatedText.setVisibility(View.VISIBLE);

        new Handler(getMainLooper()).postDelayed(() -> {
            tv.setBackgroundResource(R.color.purple_500);
            j++;
            selectionSort();
        }, 2500);

    }

    private void swapMinimumElement() {

        if (min_idx == i) {
            previous_step.add(new Integer[]{i, i});

            ss_showSortedElements();

            i++;
            j = i + 1;
            min_idx = i;

            if (isPlay) {
                selectionSort();
            }
        } else {

            previous_step.add(new Integer[]{min_idx, i});

            TextView tv1, tv2;
            tv1 = ll.findViewById(ids[i]);
            tv2 = ll.findViewById(ids[min_idx]);

            tv1.setBackgroundColor(Color.RED);
            tv2.setBackgroundColor(Color.RED);

            animatedText.setAnimateType(HTextViewType.EVAPORATE);
            animatedText.setBackgroundColor(Color.BLACK);
            animatedText.animateText(arr[min_idx] + " is the Minimum Element (Swapping)");
            animatedText.setVisibility(View.VISIBLE);

            Path path1 = new Path();

            path1.arcTo(5 + (width * (i) / example.length), 0, (width * (min_idx - 1) / example.length) + (((width) + (10 * ((i)))) / example.length), 200, 0, -180, true);
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(tv2, View.X, View.Y, path1);
            animator1.setDuration(5000);
            animator1.start();

            Path path2 = new Path();
            path2.arcTo((width * (i) / example.length), 0, 5 + (width * (min_idx - 1) / example.length) + (((width) + (10 * (i))) / example.length), 200, 180, -180, true);
            ObjectAnimator animator2 = ObjectAnimator.ofFloat(tv1, View.X, View.Y, path2);
            animator2.setDuration(5000);
            animator2.start();

            animator2.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);

                    tv1.setBackgroundResource(R.color.purple_500);
                    tv2.setBackgroundResource(R.color.purple_500);

                    int temp2 = ids[min_idx];
                    ids[min_idx] = ids[i];
                    ids[i] = temp2;

                    int temp = arr[min_idx];
                    arr[min_idx] = arr[i];
                    arr[i] = temp;
                    Log.d("TAG", "minidx: " + ids[min_idx] + " i: " + i + 1);

                    ss_showSortedElements();

                    i++;
                    j = i + 1;
                    min_idx = i;
                    minimumTV.setVisibility(View.INVISIBLE);
                    isAnimationPlaying = false;
                    Log.d("TAG", "i: " + i + " j: " + j);

                    if (isPlay) {
                        selectionSort();
                    }
                }
            });
        }

    }

    private void ss_showSortedElements() {

        if (sortedElement == null) {
            sortedElement = new TextView(SortingActivity.this);
            sortedElement.setHeight(5);
            sortedElement.setPadding(0, 10, 0, 10);
            sortedElement.setBackgroundColor(Color.GRAY);
            sortedElement.setGravity(Gravity.CENTER);
        } else {
            ll.removeView(sortedElement);
        }

        if (sortedText == null) {
            sortedText = new TextView(SortingActivity.this);
            sortedText.setHeight(100);
            sortedText.setText("Sorted");
            sortedText.setPadding(0, 10, 0, 10);
            sortedText.setTextColor(Color.GRAY);
            sortedText.setGravity(Gravity.CENTER);
            ll.addView(sortedText);
        }


        RelativeLayout.LayoutParams lprams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        sortedElement.setWidth((getResources().getDisplayMetrics().widthPixels - 40 - (10 * example.length)) * (i+1) / example.length);

        lprams.setMargins(5, 150, 5, 25);

        lprams.addRule(RelativeLayout.BELOW, 1);
        sortedElement.setLayoutParams(lprams);
        ll.addView(sortedElement); // Add to your ViewGroup using this method

        RelativeLayout.LayoutParams lprams2 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        sortedText.setWidth((getResources().getDisplayMetrics().widthPixels - 40 - (10 * example.length)) / example.length);
        lprams2.setMargins(sortedElement.getWidth() / 2, 150, 5, 0);
        lprams2.addRule(RelativeLayout.BELOW, 1);
        sortedText.setLayoutParams(lprams2);
    }

    private void ss_goBackOneStep() {

        int x, y;
        x = previous_step.get(previous_step.size() - 1)[0];
        y = previous_step.get(previous_step.size() - 1)[1];

        TextView tv1, tv2;
        tv1 = ll.findViewById(ids[y]);
        tv2 = ll.findViewById(ids[x]);

        tv1.setBackgroundColor(Color.RED);
        tv2.setBackgroundColor(Color.RED);

        animatedText.setAnimateType(HTextViewType.EVAPORATE);
        animatedText.setBackgroundColor(Color.BLACK);
        animatedText.animateText(arr[min_idx] + " is the Minimum Element (Swapping)");
        animatedText.setVisibility(View.VISIBLE);

        Path path1 = new Path();
        isAnimationPlaying = true;

        path1.arcTo(5 + (width * (y) / example.length), 0, (width * (x - 1) / example.length) + (((width) + (10 * ((y)))) / example.length), 200, 0, -180, true);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(tv2, View.X, View.Y, path1);
        animator1.setDuration(5000);
        animator1.start();

        Path path2 = new Path();
        path2.arcTo((width * (y) / example.length), 0, 5 + (width * (x - 1) / example.length) + (((width) + (10 * (y))) / example.length), 200, 180, -180, true);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(tv1, View.X, View.Y, path2);
        animator2.setDuration(5000);
        animator2.start();

        animator2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                tv1.setBackgroundResource(R.color.purple_500);
                tv2.setBackgroundResource(R.color.purple_500);

                int temp2 = ids[x];
                ids[x] = ids[y];
                ids[y] = temp2;

                int temp = arr[y];
                arr[y] = arr[x];
                arr[x] = temp;

                i--;
                j = i + 1;
                min_idx = i;
                minimumTV.setVisibility(View.INVISIBLE);
                isAnimationPlaying = false;

                previous_step.remove(previous_step.size() - 1);

                Log.d("TAG", "i: " + i + " j: " + j);

                if (isPlay) {
                    selectionSort();
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void changeMinimum() {
        if (minimumTV == null) {
            minimumTV = new TextView(this); // Prepare textview object programmatically
            minimumTV.setHeight(100);

            minimumTV.setText("Minimum");
            minimumTV.setBackgroundResource(R.color.purple_500);

            RelativeLayout.LayoutParams lprams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            lprams.setMargins(5, 50, 5, 0);
            minimumTV.setPadding(0, 10, 0, 10);

            minimumTV.setTextColor(Color.WHITE);

            minimumTV.setBackgroundResource(R.color.purple_500);
            minimumTV.setGravity(Gravity.CENTER);

            minimumTV.setLayoutParams(lprams);
            minimumTV.setWidth((int) ((width - (10 * example.length)) / example.length));

            if (j != 0) {
                lprams.addRule(RelativeLayout.END_OF, j);
            }
            lprams.addRule(RelativeLayout.BELOW, 1);

            ll.addView(minimumTV); // Add to your ViewGroup using this method

        }

        RelativeLayout.LayoutParams lprams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        lprams.setMargins(5, 50, 5, 0);


        if (j != 0) {
            lprams.addRule(RelativeLayout.END_OF, j);
        }

        lprams.addRule(RelativeLayout.BELOW, 1);
        minimumTV.setLayoutParams(lprams);
        isAnimationPlaying = true;
        minimumTV.setVisibility(View.VISIBLE);
        TextView tv = ll.findViewById(ids[j]);
        tv.setBackgroundColor(Color.RED);

        animatedText.setAnimateType(HTextViewType.EVAPORATE);
        animatedText.setBackgroundColor(Color.BLACK);
        animatedText.animateText(arr[min_idx] + " > " + arr[j] + " (Change Minimum)");
        animatedText.setVisibility(View.VISIBLE);

        new Handler(getMainLooper()).postDelayed(() -> {
            min_idx = j;
            j++;
            tv.setBackgroundResource(R.color.purple_500);
            selectionSort();
        }, 2500);
    }
}