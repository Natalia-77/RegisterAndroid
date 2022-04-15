package com.example.register;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

public class DialogProgressBar extends Dialog {
    private Context mContext;
    int i=0;
    public DialogProgressBar(@NonNull Context context) {
        super(context);
        mContext=context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflateView = inflater.inflate(R.layout.circle_progress,findViewById(R.id.relativeLayoutProgress));
        ProgressBar bar =(ProgressBar)inflateView.findViewById(R.id.progressBar);
        setContentView(inflateView);
        //поставила трохи затримки,щоб можна було відслідкувати трохи візуально....
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (i <= 100) {
                    bar.setProgress(i);
                    i+=10;
                    handler.postDelayed(this, 200);
                } else {
                    handler.removeCallbacks(this);
                    bar.setProgress(0);
                }
            }
        }, 200);
    }
}
