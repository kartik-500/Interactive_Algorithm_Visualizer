package com.kd.myapplication.Activities.MainCourse.Searching;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;
import com.kd.myapplication.R;
import java.util.ArrayList;
import co.ankurg.expressview.ExpressView;
import co.ankurg.expressview.OnCheckListener;

public class SearchingActivity extends AppCompatActivity {

    String element;
    String[] example;
    String tag;
    RelativeLayout ll;
    ObjectAnimator animator;

    ArrayList<Integer> first_values = new ArrayList<>(), last_values = new ArrayList<>(), low_values = new ArrayList<>(), high_values = new ArrayList<>();

    TextView low_tv, mid_tv, high_tv;
    int l, r, x;
    int[] arr;

    TextView iteration, elementTV, elementString, definition, toolbar_title;
    HTextView animatedText;
    ImageView next, back, play, tv, backBtn;
    RelativeLayout actionMenu;
    ExpressView likeBtn;

    boolean isPlay = false, isAnimationPlaying = false, isReverse = false;
    int i = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ll = findViewById(R.id.ll);

        iteration = findViewById(R.id.iteration);
        iteration.setText(iteration.getText() + "0");
        elementTV = findViewById(R.id.elementTV);
        definition = findViewById(R.id.definition);

        elementString = findViewById(R.id.elementString);
        actionMenu = findViewById(R.id.actionMenu);

        animatedText = findViewById(R.id.animatedText);
        likeBtn = findViewById(R.id.likeButton);
        toolbar_title = findViewById(R.id.toolbar_title);

        next = findViewById(R.id.next);
        back = findViewById(R.id.back);
        play = findViewById(R.id.play);
        backBtn = findViewById(R.id.backBtn);

        example = getIntent().getStringExtra("input").split("[,]");
        tag = getIntent().getStringExtra("tag");

        if (!tag.toLowerCase().contains("linear")) {
            toolbar_title.setText("Binary Search");
        }
        createDynamicTextView();

        AlertDialog.Builder alert2 = new AlertDialog.Builder(SearchingActivity.this);
        final EditText edittext2 = new EditText(SearchingActivity.this);
        edittext2.setText("5");
        alert2.setMessage("Enter Element to be Searched");
        alert2.setTitle("Enter Your Input");

        alert2.setView(edittext2);

        alert2.setNegativeButton("Cancel", (dialog, which) -> Toast.makeText(SearchingActivity.this, "Error", Toast.LENGTH_SHORT).show()).setPositiveButton("Okay", (dialog, which) -> {


            if (tag.toLowerCase().contains("linear")) {
                element = edittext2.getText().toString();
                definition.setText(getString(R.string.linear_search_definition));
                ls_generateDynamicElementToSearch();
            } else {
                element = edittext2.getText().toString();
//                        startAnimation();
                int size = example.length;
                arr = new int[size];
                for (int i = 0; i < size; i++) {
                    arr[i] = Integer.parseInt(example[i]);
                }
                definition.setText(getString(R.string.binary_search_definition));
                l = 0;
                r = arr.length - 1;
                x = Integer.parseInt(element);
            }

            definition.setVisibility(View.VISIBLE);
            elementTV.setHeight(100);
            elementTV.setPadding(16, 10, 0, 16);
            elementTV.setBackgroundResource(R.color.purple_500);
            elementTV.setGravity(Gravity.CENTER);
            elementTV.setWidth((getResources().getDisplayMetrics().widthPixels - (10 * example.length)) / example.length);
            elementTV.setText(element);
            elementTV.setVisibility(View.VISIBLE);
            elementString.setVisibility(View.VISIBLE);

        }).create().show();


        play.setOnClickListener(v -> {
            if (tag.toLowerCase().contains("linear")) {
                if (!isAnimationPlaying) {
                    isPlay = true;
                    actionMenu.setVisibility(View.GONE);
                    if (i == 0) {
                        ls_startLinearSearchFromFirst();
                    } else {
                        ls_startLinearSearch();
                    }
                }
            } else {
                if (!isAnimationPlaying) {
                    isPlay = true;
                    actionMenu.setVisibility(View.GONE);
                    startBinarySearch();
                }
            }
        });

        back.setOnClickListener(v -> {
            if (tag.toLowerCase().contains("linear")) {
                if (!isAnimationPlaying) {
                    ls_gobackOneStep();
                }
            } else {
                if (!isAnimationPlaying) {
                    if (first_values.size() != 0 && last_values.size() != 0) {
                        isPlay = false;
                        isReverse = true;

                        changeColorTextView(first_values.get(first_values.size() - 1), last_values.get(last_values.size() - 1));
                        low_values.remove(low_values.size() - 1);
                        high_values.remove(high_values.size() - 1);
//
//                        l = first_values.get(first_values.size() - 1);
//                        r = last_values.get(last_values.size() - 1);
//
//                        first_values.remove(first_values.size() - 1);
//                        last_values.remove(last_values.size() - 1);

                    } else {
                        Toast.makeText(SearchingActivity.this, "Reached End", Toast.LENGTH_SHORT).show();
                    }
                }
                // change
            }
        });

        next.setOnClickListener(v -> {
            if (tag.toLowerCase().contains("linear")) {
                if (!isAnimationPlaying) {
                    if (i == 0) {
                        ls_startLinearSearchFromFirst();
                    } else {
                        ls_startLinearSearch();
                    }
                }
            } else {
                if (!isAnimationPlaying) {
                    isPlay = false;
                    if (isReverse) {
                        if (low_values.size() <= 1) {
                            l = 0;
                            r = arr.length - 1;
                        } else {
                            low_values.remove(low_values.size() - 1);
                            high_values.remove(high_values.size() - 1);
                            l = low_values.get(low_values.size() - 1);
                            r = high_values.get(high_values.size() - 1);
                        }
                        isReverse = false;
                        startBinarySearch();
                    } else {
                        startBinarySearch();
                    }
                }
            }

        });

        likeBtn.setOnCheckListener(new OnCheckListener() {
            @Override
            public void onChecked(@Nullable ExpressView expressView) {

            }

            @Override
            public void onUnChecked(@Nullable ExpressView expressView) {

            }
        });

        backBtn.setOnClickListener(v -> onBackPressed());

    }

    public void createDynamicTextView() {

        for (int i = 0; i < example.length; i++) {
            TextView tv = new TextView(this); // Prepare textview object programmatically
            tv.setText(example[i]);
            tv.setHeight(100);

            RelativeLayout.LayoutParams lprams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            lprams.setMargins(5, 50, 5, 0);
            tv.setPadding(0, 10, 0, 10);

            tv.setBackgroundResource(R.color.purple_500);
            tv.setTextColor(Color.WHITE);
            tv.setGravity(Gravity.CENTER);

            tv.setLayoutParams(lprams);
            tv.setWidth((getResources().getDisplayMetrics().widthPixels - 40 - (10 * this.example.length)) / this.example.length);
            tv.setId(i + 1);

            if (i != 0) {
                lprams.addRule(RelativeLayout.END_OF, i);
            }

            ll.addView(tv); // Add to your ViewGroup using this method

        }

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

    @SuppressLint({"ResourceType", "SetTextI18n"})
    private void ls_generateDynamicElementToSearch() {
        tv = new ImageView(this); // Prepare textview object programmatically
        tv.setMaxHeight(100);
        tv.setImageResource(R.drawable.ic_up);

        RelativeLayout.LayoutParams lprams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        lprams.setMargins(5, 80, 5, 0);

        tv.setColorFilter(R.color.purple_500);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tv.setForegroundGravity(Gravity.CENTER);
        }
        lprams.addRule(RelativeLayout.BELOW, 1);
        tv.setLayoutParams(lprams);
        tv.setMinimumWidth((getResources().getDisplayMetrics().widthPixels - 50 - (10 * example.length)) / example.length);
        tv.setId(example.length + 1);
        ll.addView(tv); // Add to your ViewGroup using this method

    }

    @SuppressLint("SetTextI18n")
    private void ls_gobackOneStep() {

        if (i == 0) {
            Toast.makeText(SearchingActivity.this, "Oops you reached end!", Toast.LENGTH_SHORT).show();
        } else if (i == 1) {
            --i;
            iteration.setText(getString(R.string.steps) + (i));
            TextView tv = (TextView) ll.getChildAt(i);
            tv.setBackgroundResource(R.color.purple_500);
            animatedText.setVisibility(View.INVISIBLE);
        } else {
            --i;
            iteration.setText(getString(R.string.steps) + (i));
            TextView tv2 = (TextView) ll.getChildAt(i);
            tv2.setBackgroundResource(R.color.purple_500);


            animatedText.setAnimateType(HTextViewType.TYPER);
            animatedText.setBackgroundColor(Color.BLACK);
            animatedText.animateText(example[i - 1] + " ≠ " + element);
            animatedText.setVisibility(View.VISIBLE);

            animator = new ObjectAnimator();
            animator.setFloatValues(-((getResources().getDisplayMetrics().widthPixels - 50) / (float) example.length));
            animator.setTarget(tv);
            animator.setDuration(2000);
            animator.setAutoCancel(false);
            animator.setProperty(View.TRANSLATION_X);
            animator.setInterpolator(new AccelerateInterpolator());
            animator.start();
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);

                    ll.removeView(tv);

                    tv = new ImageView(SearchingActivity.this); // Prepare textview object programmatically
                    tv.setMaxHeight(100);
                    tv.setImageResource(R.drawable.ic_up);

                    RelativeLayout.LayoutParams lprams = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);

                    lprams.setMargins(5, 80, 5, 0);

                    tv.setColorFilter(R.color.purple_500);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setForegroundGravity(Gravity.CENTER);
                    }
                    lprams.addRule(RelativeLayout.BELOW, 1);
                    tv.setLayoutParams(lprams);
                    tv.setMinimumWidth((getResources().getDisplayMetrics().widthPixels - 50 - (10 * example.length)) / example.length);
                    tv.setMaxHeight(100);
                    tv.setId(example.length + 1);
                    ll.addView(tv); // Add to your ViewGroup using this method
                    lprams.addRule(RelativeLayout.BELOW, 1);
                    lprams.setMargins((int) (5 + ((getResources().getDisplayMetrics().widthPixels - 50) * (i - 1)) / (float) example.length), 80, 5, 0);
                    tv.setLayoutParams(lprams);

                }
            });

        }

    }

    @SuppressLint("SetTextI18n")
    private void ls_startLinearSearchFromFirst() {
        if (!example[0].equals(element)) {
            TextView tv2 = (TextView) ll.getChildAt(0);
            tv2.setBackgroundColor(Color.RED);
            if (example.length > 1) {
                i += 1;
//                takeScreenshot();
                if (iteration.getVisibility() == View.GONE) {
                    iteration.setVisibility(View.VISIBLE);
                }
                iteration.setText(getResources().getString(R.string.steps) + " " + (1));
                animatedText.setAnimateType(HTextViewType.TYPER);
                animatedText.setBackgroundColor(Color.BLACK);
                animatedText.animateText(example[0] + " ≠ " + element);
                animatedText.setVisibility(View.VISIBLE);
                if (isPlay) {
                    isAnimationPlaying = true;
                    new Handler(getMainLooper()).postDelayed(() -> {
                        ls_startLinearSearch();
                        isAnimationPlaying = false;
                    }, 3000);
                }
            } else {
                new Handler(getMainLooper()).postDelayed(
                        () -> {
                            animatedText.setAnimateType(HTextViewType.LINE);
                            animatedText.setBackgroundColor(Color.RED);
                            animatedText.animateText("Element Not Found !");
                            animatedText.setVisibility(View.VISIBLE);

                            if (isPlay) {
                                actionMenu.setVisibility(View.VISIBLE);
                                isPlay = false;
                            }
                            new Handler(getMainLooper()).postDelayed(this::reset, 3000);

                        }, 1000);

            }
        } else {
            isAnimationPlaying = true;
            iteration.setText(getResources().getString(R.string.steps) + " " + (1));
            TextView tv2 = (TextView) ll.getChildAt(0);
            tv2.setBackgroundColor(Color.GREEN);
            tv.setColorFilter(Color.GREEN);
            new Handler(getMainLooper()).postDelayed(
                    () -> {
                        animatedText.setAnimateType(HTextViewType.LINE);
                        animatedText.setBackgroundColor(Color.BLACK);
                        animatedText.animateText("Element Found !");
                        animatedText.setVisibility(View.VISIBLE);

                        if (isPlay) {
                            actionMenu.setVisibility(View.VISIBLE);
                            isPlay = false;
                        }
                        new Handler(getMainLooper()).postDelayed(() -> {
                            reset();
                            isAnimationPlaying = false;
                        }, 3000);

                    }, 1000);
        }
    }

    @SuppressLint("SetTextI18n")
    public void ls_startLinearSearch() {
        iteration.setVisibility(View.VISIBLE);
        iteration.setText(getResources().getString(R.string.steps) + " " + (i));

        animator = new ObjectAnimator();
        animator.setFloatValues((getResources().getDisplayMetrics().widthPixels - 50) / (float) example.length);
        animator.setTarget(tv);
        animator.setDuration(2000);
        animator.setAutoCancel(false);
        animator.setProperty(View.TRANSLATION_X);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
        isAnimationPlaying = true;


        animatedText.setAnimateType(HTextViewType.TYPER);
        animatedText.setBackgroundColor(Color.BLACK);
        if (example[i].equals(element)) {
            animatedText.animateText(example[i] + " = " + element);
        } else {
            animatedText.animateText(example[i] + " ≠ " + element);
        }
        animatedText.setVisibility(View.VISIBLE);

        animator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!example[i].equals(element)) {
                    animation.removeAllListeners();
                    TextView t = (TextView) ll.getChildAt(i);
                    TextView tv2 = (TextView) ll.getChildAt(0);
                    t.setBackgroundColor(Color.RED);
                    tv2.setBackgroundColor(Color.RED);
                    ll.removeView(tv);

                    tv = new ImageView(SearchingActivity.this); // Prepare textview object programmatically
                    tv.setMaxHeight(100);
                    tv.setImageResource(R.drawable.ic_up);

                    RelativeLayout.LayoutParams lprams = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);

                    lprams.setMargins(5, 80, 5, 0);

                    tv.setColorFilter(R.color.purple_500);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setForegroundGravity(Gravity.CENTER);
                    }
                    lprams.addRule(RelativeLayout.BELOW, 1);
                    tv.setLayoutParams(lprams);
                    tv.setMinimumWidth((getResources().getDisplayMetrics().widthPixels - 50 - (10 * example.length)) / example.length);
                    tv.setMaxHeight(100);
                    tv.setId(example.length + 1);
                    ll.addView(tv); // Add to your ViewGroup using this method
                    lprams.addRule(RelativeLayout.BELOW, 1);
                    lprams.setMargins((int) (5 + ((getResources().getDisplayMetrics().widthPixels - 50) * (i)) / (float) example.length), 80, 5, 0);
                    tv.setLayoutParams(lprams);
//                    tv.setEnabled(false);
                    i += 1;
                    iteration.setText(getResources().getString(R.string.steps) + " " + (i));
                    if (example.length != (i)) {
                        if (isPlay) {
                            ls_startLinearSearch();
                        }
                    } else {
                        new Handler(getMainLooper()).postDelayed(
                                () -> {
                                    animatedText.setAnimateType(HTextViewType.LINE);
                                    animatedText.setBackgroundColor(Color.RED);
                                    animatedText.animateText("Element Not Found !");
                                    animatedText.setVisibility(View.VISIBLE);

                                    if (isPlay) {
                                        new Handler(getMainLooper()).postDelayed(
                                                () -> {
                                                    actionMenu.setVisibility(View.VISIBLE);
                                                    isPlay = false;
                                                    reset();
                                                }, 3000);
                                    } else {
                                        new Handler(getMainLooper()).postDelayed(
                                                () -> reset(), 3000);
                                    }

                                }, 1000);
                    }
                } else {
                    iteration.setText(getResources().getString(R.string.steps) + " " + (i + 1));
                    TextView t = (TextView) ll.getChildAt(i);
                    t.setBackgroundColor(Color.GREEN);
                    tv.setColorFilter(Color.GREEN);
                    isAnimationPlaying = true;
                    new Handler(getMainLooper()).postDelayed(
                            () -> {
                                animatedText.setAnimateType(HTextViewType.LINE);
                                animatedText.setBackgroundColor(Color.GREEN);
                                animatedText.animateText("Element Found !");
                                animatedText.setVisibility(View.VISIBLE);

                                if (isPlay) {
                                    new Handler(getMainLooper()).postDelayed(
                                            () -> {
                                                actionMenu.setVisibility(View.VISIBLE);
                                                isPlay = false;
                                                reset();
                                                isAnimationPlaying = false;
                                            }, 3000);
                                } else {
                                    new Handler(getMainLooper()).postDelayed(
                                            () -> {
                                                reset();
                                                isAnimationPlaying = false;
                                            }, 3000);
                                }

                            }, 1000);

                }

                isAnimationPlaying = false;

            }
        });


    }

    @SuppressLint({"ResourceType", "SetTextI18n"})
    private void startBinarySearch() {

        if (!isReverse) {
            low_values.add(l);
            high_values.add(r);
        }

        isAnimationPlaying = true;

        ll.removeView(low_tv);
        ll.removeView(mid_tv);
        ll.removeView(high_tv);

        if (r >= l) {
            int mid = l + (r - l) / 2;
            setLowMidHigh(l, mid, r);
            i++;
            iteration.setText(getResources().getString(R.string.steps)+i);

            // If the element is present at the
            // middle itself
            if (arr[mid] == x) {
                isAnimationPlaying = true;
                new Handler(getMainLooper()).postDelayed(() -> {
                    TextView tv = (TextView) ll.getChildAt(mid);
                    tv.setBackgroundColor(Color.GREEN);
                    Toast.makeText(SearchingActivity.this, "Element found at Index " + mid, Toast.LENGTH_SHORT).show();
                    animatedText.setAnimateType(HTextViewType.LINE);
                    animatedText.setBackgroundColor(Color.GREEN);
                    animatedText.animateText("Element Found !");
                    animatedText.setVisibility(View.VISIBLE);

                    if (isPlay) {
                        actionMenu.setVisibility(View.VISIBLE);
                        isPlay = false;
                        isAnimationPlaying = false;
                    }

                    new Handler(getMainLooper()).postDelayed(this::bs_reset,2500);

                }, 5000);

//

            }
            // If element is smaller than mid, then
            // it can only be present in left sub array
            if (arr[mid] > x) {
//                return 0;
                new Handler(getMainLooper()).postDelayed(() -> {
                    changeColorTextView(mid, r);
                    r = mid - 1;
                    if (isPlay) {
                        startBinarySearch();
                    }
                    isAnimationPlaying = false;
                }, 5000);
            }

            // Else the element can only be present
            // in right sub array
            if (arr[mid] < x) {
//            return 0;
                new Handler(getMainLooper()).postDelayed(() -> {
                    changeColorTextView(l, mid);
                    l = mid + 1;
                    if (isPlay) {
                        startBinarySearch();
                    }
                    isAnimationPlaying = false;
                }, 5000);
            }
        } else {
            isAnimationPlaying = true;
            // We reach here when element is not present
            // in array
            Toast.makeText(SearchingActivity.this, "Element not present", Toast.LENGTH_SHORT).show();
            new Handler(getMainLooper()).postDelayed(
                    () -> {
                        animatedText.setAnimateType(HTextViewType.LINE);
                        animatedText.setBackgroundColor(Color.RED);
                        animatedText.animateText("Element Not Found !");
                        animatedText.setVisibility(View.VISIBLE);

                        if (isPlay) {
                            actionMenu.setVisibility(View.VISIBLE);
                            isPlay = false;
                            isAnimationPlaying = false;
                        }

                        new Handler(getMainLooper()).postDelayed(this::bs_reset,2500);

                    }, 1000);
        }

    }

    @SuppressLint("SetTextI18n")
    private void setLowMidHigh(int low, int mid, int high) {

        RelativeLayout.LayoutParams lprams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        lprams.setMargins(5, 100, 5, 0);

        TextView demo = (TextView) ll.getChildAt(low);
        low_tv = new TextView(this); // Prepare textview object programmatically
        low_tv.setText("low");
        low_tv.setMaxHeight(100);
        low_tv.setBackgroundResource(R.color.teal_200);
        low_tv.setX(demo.getX());
        lprams.addRule(RelativeLayout.BELOW, 1);
        low_tv.setLayoutParams(lprams);
        low_tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        low_tv.setMinimumWidth((getResources().getDisplayMetrics().widthPixels - 40 - (10 * example.length)) / example.length);
//        low_tv.setId(example.length);
        ll.addView(low_tv); // Add to your ViewGroup using this method


        TextView demo1 = (TextView) ll.getChildAt(mid);
        mid_tv = new TextView(this); // Prepare textview object programmatically
        mid_tv.setText("mid");
        mid_tv.setMaxHeight(100);
        mid_tv.setBackgroundResource(R.color.teal_200);
        mid_tv.setX(demo1.getX());
        lprams.addRule(RelativeLayout.BELOW, 1);
        mid_tv.setLayoutParams(lprams);
        mid_tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        mid_tv.setMinimumWidth((getResources().getDisplayMetrics().widthPixels - 40 - (10 * example.length)) / example.length);
//        mid_tv.setId(example.length);
        ll.addView(mid_tv); // Add to your ViewGroup using this method

        TextView demo2 = (TextView) ll.getChildAt(high);
        high_tv = new TextView(this); // Prepare textview object programmatically
        high_tv.setText("high");
        high_tv.setMaxHeight(100);
        high_tv.setBackgroundResource(R.color.teal_200);
        high_tv.setX(demo2.getX());
        high_tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        lprams.addRule(RelativeLayout.BELOW, 1);
        high_tv.setLayoutParams(lprams);
        high_tv.setMinimumWidth((getResources().getDisplayMetrics().widthPixels - (10 * example.length)) / example.length);
//        high_tv.setId(example.length);
        ll.addView(high_tv); // Add to your ViewGroup using this method

        animatedText.setAnimateType(HTextViewType.TYPER);
        animatedText.setBackgroundColor(Color.BLACK);
        animatedText.animateText("(" + low + " + " + high + ") / 2" + " = " + mid + " (mid)");
        animatedText.setVisibility(View.VISIBLE);

        new Handler(getMainLooper()).postDelayed(() -> {
            if (example[mid].equals(element)) {
                animatedText.animateText(example[mid] + " = " + element);
            } else {
                animatedText.animateText(example[mid] + " ≠ " + element);
            }
        }, 3000);

        if (low == mid) {
            if (low == high) {
                RelativeLayout.LayoutParams lpram = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                lpram.setMargins(5, 160, 5, 0);
                lpram.addRule(RelativeLayout.BELOW, 1);

                mid_tv.setLayoutParams(lpram);

                RelativeLayout.LayoutParams lpram3 = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                lpram3.setMargins(5, 220, 5, 0);
                lpram3.addRule(RelativeLayout.BELOW, 1);

                high_tv.setLayoutParams(lpram3);

            } else {
                RelativeLayout.LayoutParams lpram = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                lpram.setMargins(5, 160, 5, 0);
                lpram.addRule(RelativeLayout.BELOW, 1);

                mid_tv.setLayoutParams(lpram);
            }
        } else {
            if (low == high) {
                RelativeLayout.LayoutParams lpram = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                lpram.setMargins(5, 160, 5, 0);
                lpram.addRule(RelativeLayout.BELOW, 1);

                high_tv.setLayoutParams(lpram);

            }
            if (mid == high) {
                RelativeLayout.LayoutParams lpram = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                lpram.setMargins(5, 160, 5, 0);
                lpram.addRule(RelativeLayout.BELOW, 1);

                high_tv.setLayoutParams(lpram);
            }
        }

    }

    private void changeColorTextView(int first, int last) {


        if (!isReverse) {
            for (int i = first; i <= last; i++) {
                TextView tv = (TextView) ll.getChildAt(i);
                tv.setBackgroundColor(Color.RED);
            }
            first_values.add(first);
            last_values.add(last);
        } else {
            for (int i = first; i <= last; i++) {
                TextView tv = (TextView) ll.getChildAt(i);
                tv.setBackgroundResource(R.color.purple_500);
            }
            first_values.remove(first_values.size() - 1);
            last_values.remove(last_values.size() - 1);
        }
    }

/*

    public void takeScreenshot() {

        // Make Folder with Example Name with Linear Search Name
        // Linear_Search_1234567

        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = getExternalFilesDir(null).toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

//            openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            Toast.makeText(SearchingActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            Log.d("TAG", "takeScreenshot: " + e.toString());
        }
    }
*/


    @SuppressLint("SetTextI18n")
    public void bs_reset() {
        isReverse = true;
        changeColorTextView(0, arr.length-1);

        ll.removeView(low_tv);
        ll.removeView(mid_tv);
        ll.removeView(high_tv);

        animatedText.setVisibility(View.INVISIBLE);
        iteration.setText(getResources().getString(R.string.steps)+ "0");

        i = 0;
        l = 0;
        r = arr.length-1;

        low_values = new ArrayList<>();
        high_values = new ArrayList<>();

        first_values = new ArrayList<>();
        last_values = new ArrayList<>();

        isReverse = false;
    }

    @SuppressLint("SetTextI18n")
    public void reset() {
        i = 0;
        iteration.setText(getResources().getString(R.string.steps) + " " + (0));
        ll.removeView(tv);
        for (int i = 0; i < example.length; i++) {
            TextView t = (TextView) ll.getChildAt(i);
            t.setBackgroundResource(R.color.purple_500);
        }
        if (animatedText.getVisibility() == View.VISIBLE) {
            animatedText.setVisibility(View.INVISIBLE);
        }
//        createDynamicTextView();
        elementTV.setHeight(100);
        elementTV.setPadding(16, 10, 0, 16);
        elementTV.setBackgroundResource(R.color.purple_500);
        elementTV.setGravity(Gravity.CENTER);
        elementTV.setWidth((getResources().getDisplayMetrics().widthPixels - (10 * example.length)) / example.length);
        elementTV.setText(element);
        elementTV.setVisibility(View.VISIBLE);
        elementString.setVisibility(View.VISIBLE);

        definition.setText(getString(R.string.linear_search_definition));
        definition.setVisibility(View.VISIBLE);

        ls_generateDynamicElementToSearch();

    }

}