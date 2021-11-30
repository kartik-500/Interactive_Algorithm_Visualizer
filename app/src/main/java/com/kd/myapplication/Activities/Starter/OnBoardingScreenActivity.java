package com.kd.myapplication.Activities.Starter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.kd.myapplication.R;
import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnRightOutListener;

import java.util.ArrayList;

public class OnBoardingScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_screen);

        PaperOnboardingPage scr1 = new PaperOnboardingPage("having difficulty in understanding an Algorithm?",
                "We will describe Algorithm's\n with help of Animations",
                Color.parseColor("#49999C"), R.drawable.ic_onboard1, R.drawable.ic_onboardbottom);

        PaperOnboardingPage scr2 = new PaperOnboardingPage("Having an unsolved doubt in Mind?",
                "Our app does take your example and show step-by-step solution to your problem",
                Color.parseColor("#B9D115"), R.drawable.ic_onboard2, R.drawable.ic_onboardbottom);

        PaperOnboardingPage scr3 = new PaperOnboardingPage("Easy to Follow",
                "Get clear step-by-step solutions",
                Color.parseColor("#F9A973"), R.drawable.ic_onboard3, R.drawable.ic_onboardbottom);

        PaperOnboardingPage scr4 = new PaperOnboardingPage("Congratulations!",
                "You're ready to go\n\nSwipe right to get started\n\n>>>>>>>>>>>>",
                Color.parseColor("#995EEE"), R.drawable.ic_onboard4, R.drawable.ic_onboardbottom);

        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        elements.add(scr1);
        elements.add(scr2);
        elements.add(scr3);
        elements.add(scr4);

        FragmentManager fragmentManager = getSupportFragmentManager();

        PaperOnboardingFragment onBoardingFragment = PaperOnboardingFragment.newInstance(elements);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, onBoardingFragment);
        fragmentTransaction.commit();

        onBoardingFragment.setOnRightOutListener(new PaperOnboardingOnRightOutListener() {
            @Override
            public void onRightOut() {
                startActivity(new Intent(OnBoardingScreenActivity.this, SidebarActivity.class));
                finish();
            }
        });



    }
}