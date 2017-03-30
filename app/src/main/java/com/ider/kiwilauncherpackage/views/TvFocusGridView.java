//package com.ider.kiwilauncherpackage.views;
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.graphics.Canvas;
//import android.graphics.Rect;
//import android.graphics.drawable.Drawable;
//import android.util.AttributeSet;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AbsListView;
//import android.widget.GridView;
//
//import com.ider.kiwilauncherpackage.R;
//
///**
// * Created by Eric on 2017/3/15.
// */
//
//public class TvFocusGridView extends GridView {
//    private Drawable mFocusBackDrawable;
//    private Rect mFocusBackRect;
//    private int mSelectedPosition;
//    private View mSelectedView;
//    private float mScaleX;
//    private float mScaleY;
//    private OnItemFocusSelectedListener mOnItemFocusSelectedListener;
//    public TvFocusGridView(Context context) { this(context, null); }
//    public TvFocusGridView(Context context, AttributeSet attrs) { this(context, attrs, 0); }
//    public TvFocusGridView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        setClipChildren(false);
//        setClipToPadding(false);
//        setChildrenDrawingOrderEnabled(true);//设置绘制顺序可重定义，需要重写getChildDrawingOrder来变化绘制顺序
//        mFocusBackRect = new Rect();
//        mFocusBackDrawable.getPadding(mFocusBackRect); //Drawable中实际填充图像的Rect
//        // System.out.println("mFocusBackRect-->" + mFocusBackRect);
//    }
//    public interface OnItemFocusSelectedListener {
//        void onFocused(View child, int position);
//        void unfocused(View child, int position);
//    }
//    public void setOnItemFocusSelectedListener(OnItemFocusSelectedListener onItemFocusSelectedListener) {
//        this.mOnItemFocusSelectedListener = onItemFocusSelectedListener;
//    }
//    @Override
//    protected void dispatchDraw(Canvas canvas) {
//        super.dispatchDraw(canvas);
//        if (mFocusBackDrawable == null) { return; }
//        drawSelector(canvas);
//    }
//    private void drawSelector(Canvas canvas) {
//        View view = getSelectedView();
//        if (view == null) return;
//        bringChildToFront(view);
//        if (isFocused()) {
//            scaleSelectedView(view);
//            Rect gvVisibleRect = new Rect();
//            this.getGlobalVisibleRect(gvVisibleRect); //GridView可见区 在屏幕中的绝对坐标 rect
//            Rect selectedViewRect = new Rect();
//            if (mSelectedView instanceof ViewGroup) {
//                mSelectedView.getGlobalVisibleRect(selectedViewRect); //选中View可见区 在屏幕中的绝对坐标 rect
//                selectedViewRect.offset(-gvVisibleRect.left, - gvVisibleRect.top); //偏移
//                selectedViewRect.left -= mFocusBackRect.left + 40;//+阴影偏移
//                selectedViewRect.top -= mFocusBackRect.top + 20;
//                selectedViewRect.right += mFocusBackRect.right + 40;
//                selectedViewRect.bottom += mFocusBackRect.bottom + 20;
//                mFocusBackDrawable.setBounds(selectedViewRect);
//                mFocusBackDrawable.draw(canvas);
//            }
//        }
//    }
//    /*
//     * 使子view位置在上层
//     * @param child
//     */
//    @Override
//    public void bringChildToFront(View child) {//重写，不调用父类方法；获取child的实际position
//        // super.bringChildToFront(child);
//        mSelectedPosition = indexOfChild(child);
//        invalidate();
//    } @Override
//    protected int getChildDrawingOrder(int childCount, int i) {//交换 选中项与最后一项 绘制的顺序
//        if (mSelectedPosition != AbsListView.INVALID_POSITION) {
//            if (i == mSelectedPosition) { return childCount - 1; }
//            if (i == childCount - 1) { return mSelectedPosition; }
//        } return super.getChildDrawingOrder(childCount, i); }
//    private void scaleSelectedView(View view) {//缩放
//        unScalePreView();
//        mSelectedView = view;
//        mSelectedView.setScaleX(mScaleX); //api11
//        mSelectedView.setScaleY(mScaleY);
//        if (mOnItemFocusSelectedListener != null) {
//            mOnItemFocusSelectedListener.onFocused(mSelectedView, indexOfChild(mSelectedView));
//        }
//    }
//    private void unScalePreView() {//还原
//        if (mSelectedView != null) {
//            mSelectedView.setScaleX(1);
//            mSelectedView.setScaleY(1);
//            if (mOnItemFocusSelectedListener != null) {
//                mOnItemFocusSelectedListener.unfocused(mSelectedView, indexOfChild(mSelectedView));
//            }
//            mSelectedView = null;
//        }
//    }
//    @Override
//    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {//整个GridView焦点状态
//        if (!gainFocus) {
//            unScalePreView();
//            requestLayout();
//        }
//        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
//    }
//    public void setScale(float scaleX, float scaleY) {
//        this.mScaleX = scaleX; this.mScaleY = scaleY;
//    }
//}
//
