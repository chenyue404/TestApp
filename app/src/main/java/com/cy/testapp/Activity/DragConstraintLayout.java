package com.cy.testapp.Activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

public class DragConstraintLayout extends ConstraintLayout {
    private ViewDragHelper mViewDragHelper;

    private Point originPoint = new Point();

    private View targetView;

    private DragListener listener;

    private boolean callEvent = false;
    private boolean canDragDown = true;

    public void setDragListener(DragListener listener) {
        this.listener = listener;
    }

    public void setCanDragDown(boolean canDragDown) {
        this.canDragDown = canDragDown;
    }

    public interface DragListener {
        void onDragFinished();

        void onDragChange(float scale);
    }

    public DragConstraintLayout(Context context) {
        super(context);
    }

    public DragConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DragConstraintLayout bind(View view) {
        targetView = view;
        return this;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mViewDragHelper = ViewDragHelper.create(this, 1f, new ViewDragCallback());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        if (action == MotionEvent.ACTION_CANCEL
                || action == MotionEvent.ACTION_UP
                || action == MotionEvent.ACTION_POINTER_DOWN
                || action == MotionEvent.ACTION_POINTER_UP) {
            mViewDragHelper.cancel();
            return false;
        }
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        originPoint.x = targetView.getLeft();
        originPoint.y = targetView.getTop();
    }

    public void updatePoints() {
        originPoint.x = (int) targetView.getX();
        originPoint.y = (int) targetView.getY();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    private class ViewDragCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == targetView;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return canDragDown ? Math.max(top, originPoint.y) : child.getTop();
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
//            return super.clampViewPositionHorizontal(child, left, dx);
            return child.getLeft();
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return 0;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return 1;
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            if (releasedChild == targetView && canDragDown) {
                if (callEvent
                        || yvel > 8000
                ) {
                    if (listener != null) {
                        listener.onDragFinished();
                    }
                    callEvent = false;
                    mViewDragHelper.settleCapturedViewAt(originPoint.x, getHeight());
                } else {
                    mViewDragHelper.settleCapturedViewAt(originPoint.x, originPoint.y);
                }
                invalidate();
            }
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            if (top > originPoint.y) {
                float a = (float) (top - originPoint.y) / (float) (getMeasuredHeight() - originPoint.y);
//                setBackgroundColor(changeAlpha(0xff000000, 1 - a));
//                targetView.setScaleX(1 - a);
//                targetView.setScaleY(1 - a);
                Log.e("TAG", "onViewPositionChanged: " + a);

                if (listener != null) {
                    listener.onDragChange(a);
                }

                callEvent = (top - originPoint.y) > getMeasuredHeight() / 2;
            }
        }
    }

    public int changeAlpha(int color, float fraction) {
        fraction = fraction < 0 ? 0 : fraction;
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }
}
