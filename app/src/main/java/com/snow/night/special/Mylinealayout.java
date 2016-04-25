package com.snow.night.special;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/4/25.
 */
public class Mylinealayout extends LinearLayout{
    public Mylinealayout(Context context) {
        super(context);
    }

    public Mylinealayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Mylinealayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private Slidmenu slidMenu;
    public void  setSlidMenu(Slidmenu slidMenu){
        this.slidMenu = slidMenu;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(slidMenu != null && slidMenu.getmSlidState() == Slidmenu.SlidState.Open)
        {return  true;}
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(slidMenu!= null && slidMenu.getmSlidState()== Slidmenu.SlidState.Open){
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                slidMenu.close();
            }
            return true;
        }
        return super.onTouchEvent(event);
    }
}
