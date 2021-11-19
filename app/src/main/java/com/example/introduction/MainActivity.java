package com.example.introduction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Bundle;

import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnBoardingIndicators;
    private MaterialButton buttonOnboardingAction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        layoutOnBoardingIndicators = findViewById(R.id.layotOnboardingIndicators);
        buttonOnboardingAction = findViewById(R.id.buttonOnboardingAction);
        setupOnboardingItems();

        ViewPager2 onboardingViewPager = findViewById(R.id.onboardingViewPage);
        onboardingViewPager.setAdapter(onboardingAdapter);

        setupOnBoardingIndicators();
        setCurrentOnboardingIndicator(0);


        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);
            }
        });


        buttonOnboardingAction.setOnClickListener(view -> {
            if(onboardingViewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()){
                onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem()+1);
            }else {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                finish();
            }
        });
       // HideBar();
    }

    private void setupOnboardingItems(){

        List<OnboardingItem> onboardingItems = new ArrayList<>();
        OnboardingItem itemPayOnline = new OnboardingItem();
        itemPayOnline.setTitle("Pay Your Bill Online");
        itemPayOnline.setDescription("Electric bill payment is a feature of online, mobile and telephone.");
        itemPayOnline.setImage(R.drawable.pay_online);

        OnboardingItem itemOnTheWay = new OnboardingItem();
        itemOnTheWay.setTitle("Your Food Is On The Way");
        itemOnTheWay.setDescription("Our delivery rider is on the way to delivery your order.");
        itemOnTheWay.setImage(R.drawable.on_my_way);

        OnboardingItem itemEatTogether = new OnboardingItem();
        itemEatTogether.setTitle("Eat Together");
        itemEatTogether.setDescription("Enjoy your meal and have a great day. Don't forget to rate us.");
        itemEatTogether.setImage(R.drawable.eat_together);

        onboardingItems.add(itemPayOnline);
        onboardingItems.add(itemOnTheWay);
        onboardingItems.add(itemEatTogether);

        onboardingAdapter = new OnboardingAdapter(onboardingItems);

    }

    private void setupOnBoardingIndicators(){

        ImageView[] indicator = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for(int i = 0; i< indicator.length ; i++){
            indicator[i] = new ImageView(getApplicationContext());
            indicator[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboarding_indicator_inactive
            ));
            indicator[i].setLayoutParams(layoutParams);
            layoutOnBoardingIndicators.addView(indicator[i]);


        }
    }

    @SuppressLint("SetTextI18n")
    private  void setCurrentOnboardingIndicator(int index){
        int childCount = layoutOnBoardingIndicators.getChildCount();
        for(int i=0;i<childCount;i++){
            ImageView imageView= (ImageView) layoutOnBoardingIndicators.getChildAt(i);
            if(i==index){
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_active)

                );
            }else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_inactive)
                );
            }
        }
        if(index == onboardingAdapter.getItemCount() - 1){
            buttonOnboardingAction.setText("Start");
        }else {
            buttonOnboardingAction.setText("Next");
        }

    }
    private void HideBar(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
    }
}