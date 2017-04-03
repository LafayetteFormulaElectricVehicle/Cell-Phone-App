//package com.lfev2017.ktdilsiz.cellapptest.Listeners;
//
//import android.content.Context;
//import android.graphics.Matrix;
//import android.graphics.PointF;
//import android.graphics.RectF;
//import android.support.v4.view.ScaleGestureDetectorCompat;
//import android.view.MotionEvent;
//import android.view.ScaleGestureDetector;
//import android.view.View;
//
///**
// * Created by ktdilsiz on 3/23/17.
// */
//
//public class MyScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
//
//    private ScaleGestureDetector mScaleDetector;
//    private float mScaleFactor = 1.f;
//
//    public MyScaleListener(Context context){
//        mScaleDetector = new ScaleGestureDetector(context, this);
//    }
//
//
//
//
//    @Override
//    public boolean onScale(ScaleGestureDetector detector) {
//        mScaleFactor *= detector.getScaleFactor();
//
//        // Don't let the object get too small or too large.
//        mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));
//
//        invalidate();
//        return true;
//    }
//
//}
