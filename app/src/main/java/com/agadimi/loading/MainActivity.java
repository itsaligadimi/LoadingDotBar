package com.agadimi.loading;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.agadimi.loadingdotbar.LoadingDotBarView;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LoadingDotBarView loading = findViewById(R.id.loading);
        Button start = findViewById(R.id.process_btn);

        start.setOnClickListener(v -> {
            loading.setVisibility(View.VISIBLE);
            loading.start();
        });
    }
}