package com.ider.kiwilauncherpackage.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ider.kiwilauncherpackage.R;

/**
 * Created by Eric on 2017/3/14.
 */

public class SetImageView {
    public static Bitmap setImageView(int id,String title){
        int height=330;
        int width =400;
        View view = View.inflate(MyApplication.getContext(), R.layout.setting_large_view, null);
        view.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));
        view.layout(0, 0, width, height);
        RelativeLayout relativeLayout = (RelativeLayout)view;
        ((ImageView) relativeLayout.getChildAt(0)).setImageBitmap(BitmapFactory.decodeResource(MyApplication.getContext().getResources(), id));
        ((TextView)relativeLayout.getChildAt(1)).setText(title);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;

    }
}
