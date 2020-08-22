package com.agadimi.loading;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agadimi.loadingdotbar.LoadingDotBarView;

public class MainActivity extends AppCompatActivity
{
    private boolean loading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout addToCartBtn = findViewById(R.id.add_to_cart_btn);
//
        addToCartBtn.setOnClickListener(v -> {
            if (loading)
            {
                return;
            }

            loading = true;
            LoadingDotBarView loadingView = (LoadingDotBarView) addToCartBtn.getChildAt(0);
            TextView btnText = (TextView) addToCartBtn.getChildAt(1);
            float translationYpx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());

            btnText.setTranslationY(0);
            btnText.setAlpha(1);
            btnText.animate()
                    .translationY(translationYpx)
                    .alpha(0)
                    .setDuration(150)
                    .setInterpolator(new DecelerateInterpolator())
                    .start();

            Log.d("SSSSSSSS", "started at: " + System.currentTimeMillis());
            loadingView.setTranslationY(-translationYpx);
            loadingView.setAlpha(0.5f);
            loadingView.animate()
                    .translationY(0)
                    .alpha(1)
                    .setDuration(200)
                    .setInterpolator(new OvershootInterpolator())
                    .setListener(new AnimatorListenerAdapter()
                    {
                        @Override
                        public void onAnimationEnd(Animator animation)
                        {
                            Log.d("SSSSSSSS", "ended at: " + System.currentTimeMillis());
                            loadingView.start();
                        }
                    })
                    .start();


            v.postDelayed(() -> {
                loading = false;
                btnText.setTranslationY(translationYpx);
                btnText.setAlpha(0);
                btnText.animate()
                        .translationY(0)
                        .alpha(1)
                        .setDuration(200)
                        .setInterpolator(new OvershootInterpolator())
                        .start();

                loadingView.setTranslationY(0);
                loadingView.setAlpha(1);
                loadingView.animate()
                        .translationY(-translationYpx)
                        .alpha(0.5f)
                        .setDuration(150)
                        .setInterpolator(new DecelerateInterpolator())
                        .setListener(new AnimatorListenerAdapter()
                        {
                            @Override
                            public void onAnimationEnd(Animator animation)
                            {
                                loadingView.end(false);
                            }
                        })
                        .start();
            }, 4000);
        });
    }
}