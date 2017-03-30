package com.ider.kiwilauncherpackage.views;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ider.kiwilauncherpackage.R;
import com.ider.kiwilauncherpackage.util.EntryAnimation;

/**
 * Created by Eric on 2017/3/13.
 */

public class HomeLargeView extends RelativeLayout {
    private ObjectAnimator animator = null;
    private ImageView imageView1;
    private ImageView imageView2;
    private TextView title;

    public HomeLargeView(Context context) {
        this(context, null);
    }
    public HomeLargeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.home_large_view, this);
        imageView1 = (ImageView) findViewById(R.id.home_large_image1);
        imageView2 = (ImageView) findViewById(R.id.home_large_image2) ;
        title = (TextView) findViewById(R.id.home_small_text);

    }



    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
    }
    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {

        if(gainFocus) {
            animator = EntryAnimation.createFocusAnimator(this);
        } else {

            if(animator != null && animator.isRunning()) {
                animator.cancel();
            }
            animator = EntryAnimation.createLoseFocusAnimator(this);

        }
        animator.start();
    }
}
