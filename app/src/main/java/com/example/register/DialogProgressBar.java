package com.example.register;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.github.ybq.android.spinkit.style.Circle;

public class DialogProgressBar extends Dialog  {
    private Context mContext;
    public DialogProgressBar(@NonNull Context context) {
        super(context);
        mContext=context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflateView = inflater.inflate(R.layout.circle_progress,null);
        ProgressBar spin =(ProgressBar) inflateView.findViewById(R.id.spin_kit);
        Circle circle=new Circle();
        spin.setIndeterminateDrawable(circle);
        setContentView(inflateView);
    }
}
