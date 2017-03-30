package com.ider.kiwilauncherpackage.views;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ider.kiwilauncherpackage.util.EntryAnimation;


/**
 * Created by ider-eric on 2017/2/23.
 */

public class SwipeLayout extends RelativeLayout implements View.OnFocusChangeListener{

    private static final String TAG = "SwipeLayout";
    private Animator animator;
    private boolean shown;
    private SwipeListener swipeListener;

    public interface SwipeListener {
        void onSwipeOpen();
        void onSwipeClose();
    }

    public void setSwipeListener(SwipeListener swipeListener) {
        this.swipeListener = swipeListener;
    }

    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        for(int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setOnFocusChangeListener(this);
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {

        animator = EntryAnimation.createLoseFocusAnimator(view);
        if (animator!=null ){
            animator.start();
        }
        SettingLargeView viewItem = (SettingLargeView) findFocus();
        if (viewItem!=null){
            animator = EntryAnimation.createFocusAnimator(viewItem);
            animator.start();

        }

        if(hasFocus) {
            if(!shown) {
                show();

            }
        } else {
            if(shown && allLostFocus()) {
                close();
            }
        }
    }


    public void show() {
        this.shown = true;
        this.swipeListener.onSwipeOpen();
    }

    public boolean isSwipeShown() {
        return this.shown;
    }

    private void close() {
        this.shown = false;
        this.swipeListener.onSwipeClose();
    }

    private boolean allLostFocus() {
        for(int i = 0; i < getChildCount(); i++) {
            if(getChildAt(i).hasFocus()) {
                return false;
            }
        }
        return true;
    }

    public void enableFocusable() {
        for(int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setFocusable(true);
        }
    }


}
