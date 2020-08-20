package com.agadimi.loadingdotbar;

import android.content.Context;
import android.widget.Toast;

public class LoadingDotBarView {
    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}