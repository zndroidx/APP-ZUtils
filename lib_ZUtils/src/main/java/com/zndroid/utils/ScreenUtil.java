package com.zndroid.utils;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by lazy on 2019-11-04
 */

public class ScreenUtil {
    private int mStatusHeight = -1;

    public int dp2px(Context ctx, float dpValue) {
        final float density = ctx.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    public int sp2px(Context ctx, float spValue) {
        final float scaledDensity = ctx.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scaledDensity + 0.5f);
    }

    /**
     * 获取屏幕的宽度
     * @param context
     * @return
     */
    public int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        assert manager != null;

        Display display = manager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * 获取屏幕的高度
     * @param context
     * @return
     */
    public int getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        assert manager != null;

        Display display = manager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.heightPixels;
    }

    public float getDensity(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        assert manager != null;

        Display display = manager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.density;
    }

    /**
     * 获得状态栏的高度
     * @param context
     * @return mStatusHeight
     */
    public int getStatusHeight(Context context) {
        if (mStatusHeight != -1) {
            return mStatusHeight;
        }
        try {
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                mStatusHeight = context.getResources().getDimensionPixelSize(resourceId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mStatusHeight;
    }


    /**
     * 获取当前屏幕截图，不包含状态栏
     * @param activity
     * @return bp
     */
    public Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        if (bmp == null) {
            return null;
        }
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        Bitmap bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, bmp.getWidth(), bmp.getHeight() - statusBarHeight);
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(false);

        return bp;
    }

    /**
     * 获取actionbar的像素高度，默认使用android官方兼容包做actionbar兼容
     *
     * @return
     */
    public int getActionBarHeight(Context context) {
        int actionBarHeight=0;
        if(context instanceof AppCompatActivity &&((AppCompatActivity) context).getSupportActionBar()!=null) {
            Log.d("isAppCompatActivity", "==AppCompatActivity");
            actionBarHeight = ((AppCompatActivity) context).getSupportActionBar().getHeight();
        }else if(context instanceof Activity && ((Activity) context).getActionBar()!=null) {
            Log.d("isActivity","==Activity");
            actionBarHeight = ((Activity) context).getActionBar().getHeight();
        }else if(context instanceof ActivityGroup){
            Log.d("ActivityGroup","==ActivityGroup");
            if (((ActivityGroup) context).getCurrentActivity() instanceof AppCompatActivity && ((AppCompatActivity) ((ActivityGroup) context).getCurrentActivity()).getSupportActionBar()!=null){
                actionBarHeight = ((AppCompatActivity) ((ActivityGroup) context).getCurrentActivity()).getSupportActionBar().getHeight();
            }else if (((ActivityGroup) context).getCurrentActivity() instanceof Activity && ((Activity) ((ActivityGroup) context).getCurrentActivity()).getActionBar()!=null){
                actionBarHeight = ((Activity) ((ActivityGroup) context).getCurrentActivity()).getActionBar().getHeight();
            }
        }
        if (actionBarHeight != 0)
            return actionBarHeight;
        final TypedValue tv = new TypedValue();
        if(context.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)){
            if (context.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true))
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }else {
            if (context.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true))
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        Log.d("actionBarHeight","===="+actionBarHeight);
        return actionBarHeight;
    }


    /**
     * 设置view margin
     * @param v
     * @param l
     * @param t
     * @param r
     * @param b
     */
    public void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

}
