package com.snow.night.special;

import android.animation.FloatEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2016/4/23.
 */
public class Slidmenu extends FrameLayout {

    private ViewDragHelper viewDragHelper;
    private View menuView;
    private View mainView;
    private int menuWidth;
    private int menuHeight;
    private int mainWidth;
    private int mainHeight;
    private int dragrange;
    private FloatEvaluator floatevaluator;

    public Slidmenu(Context context) {
        super(context);
        init();
    }

    public Slidmenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public Slidmenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){
        viewDragHelper = ViewDragHelper.create(this,callback);
        floatevaluator = new FloatEvaluator();
    };
    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        /**
         * 判断对哪个view进行捕捉
         * @param child
         * @param pointerId
         * @return
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mainView|| child == menuView;
        }

        /**
         * 水平移动该方法必须实现 返回值大于0 即可
         * @param child
         * @return
         */
        @Override
        public int getViewHorizontalDragRange(View child) {
            return dragrange;
        }

        /**
         *控制view在水平方向的移动
         * @param child
         * @param left
         * @param dx
         * @return
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if(child == mainView){
                if(left > dragrange){
                    left =dragrange;
                }else if(left<0){
                    left = 0;
                }
            }
            return left;
        }

        /**
         * 实现伴随移动的效果
         * @param changedView
         * @param left
         * @param top
         * @param dx
         * @param dy
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if(changedView == menuView)
            {
                menuView.layout(0,getTop(),menuWidth,getBottom());
                int newleft=mainView.getLeft()+dx;
                if(newleft > dragrange){
                    newleft =dragrange;
                }else if(newleft<0){
                    newleft = 0;
                }
                mainView.layout(newleft,getTop(),newleft + mainWidth,getBottom());

            }

            //根据mainview移动的距离 实现伴随动画
            float fraction = mainView.getLeft()*1f/dragrange;
            //根据滑动的百分比的值 执行伴随动画
              executeAnimation(fraction);

            //执行状态更改的逻辑，和监听器方法的回调
            if(mainView.getLeft()==dragrange&& mSlidState != SlidState.Open){
              mSlidState = SlidState.Open;
                if(listener!=null){
                    listener.onOpen();
                }
            }
            if(mainView.getLeft()==0&& mSlidState != SlidState.Close){
                mSlidState = SlidState.Close;
                if(listener!=null){
                    listener.onClose();
                }
            }
            if(listener!=null){
                listener.onDraging();
            }
        }
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if(mainView.getLeft()>dragrange/2){
                //缓慢打开
                open();
            }else{
                //缓慢关闭
                close();
            }
            if(xvel>150){
                open();
            }
        }

    };

    private void close() {
        viewDragHelper.smoothSlideViewTo(mainView,0,mainView.getTop());
        ViewCompat.postInvalidateOnAnimation(this);
    }

    /**
     * 伴随动画的实现方法
     */
    private void executeAnimation(float fraction) {
//        当mainview打开的时候 fraction 是 0-1的
//        当mainview关闭的时候 fraction 是1-0的
//        动画比例值的计算方法 scale =  start +(end - start)*fraction
        animat1(fraction);
        ViewCompat.setScaleX(mainView, floatevaluator.evaluate(fraction,1.0f,0.7f));
        ViewCompat.setScaleY(mainView, floatevaluator.evaluate(fraction,1.0f,0.7f));
//
        ViewCompat.setScaleX(menuView, floatevaluator.evaluate(fraction,0.3f,1f));
        ViewCompat.setScaleY(menuView, floatevaluator.evaluate(fraction,0.3f,1f));
        float roat = 0 +(360)*fraction;
        ViewCompat.setRotationX(mainView,roat);
        ViewCompat.setTranslationX(menuView,floatevaluator.evaluate(fraction,-menuWidth/2,0));
        ViewCompat.setAlpha(menuView,floatevaluator.evaluate(fraction,0.3f,1f));
        if(getBackground()!= null){
            int color = (int) ColorUtil.evaluateColor(fraction, Color.BLACK,Color.TRANSPARENT);
            getBackground().setColorFilter(color, PorterDuff.Mode.SRC_OVER);
        }
    }

    private void animat1(float fraction) {
        ViewCompat.setScaleX(mainView, floatevaluator.evaluate(fraction,1.0f,0.7f));
        ViewCompat.setScaleY(mainView, floatevaluator.evaluate(fraction,1.0f,0.7f));
//
        ViewCompat.setScaleX(menuView, floatevaluator.evaluate(fraction,0.3f,1f));
        ViewCompat.setScaleY(menuView, floatevaluator.evaluate(fraction,0.3f,1f));
        float roat = 0 +(90)*fraction;
        float roat2 = -360 +(360)*fraction;
        ViewCompat.setRotationX(mainView,roat);
        ViewCompat.setRotationY(menuView,roat2);
        ViewCompat.setTranslationX(menuView,floatevaluator.evaluate(fraction,-menuWidth/2,0));
        ViewCompat.setAlpha(menuView,floatevaluator.evaluate(fraction,0.3f,1f));
        if(getBackground()!= null){
            int color = (int) ColorUtil.evaluateColor(fraction, Color.BLACK,Color.TRANSPARENT);
            getBackground().setColorFilter(color, PorterDuff.Mode.SRC_OVER);
        }
    }
     public enum SlidState {
         Open,Close
     }
    private SlidState mSlidState =SlidState.Close; //默认是关闭状态
    private void open() {
        viewDragHelper.smoothSlideViewTo(mainView,dragrange,mainView.getTop());
        ViewCompat.postInvalidateOnAnimation(this);
    }

    /**
     * 要实现缓慢滑动 要实现这个方法
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        //continueSettling(true)中的值可以随便传
        if(viewDragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /**
     * 填充完view进行调用 对view进行初始化
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        menuView = getChildAt(0);
        mainView = getChildAt(1);
    }

    /**
     * 该方法在onmeasure之后执行 对子view的宽高进行获取
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        menuWidth = menuView.getMeasuredWidth();
        menuHeight = menuView.getMeasuredHeight();
        mainWidth = mainView.getMeasuredWidth();
        mainHeight = mainView.getMeasuredHeight();
        dragrange = (int) (mainWidth * 0.6);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed,left,top,right,bottom);
//        menuView.layout(-menuWidth,0,0,menuHeight);
//        mainView.layout(0,0,mainWidth,mainHeight);
    }

    /**
     * viewdraghelper 帮助判断是否拦截事件
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean result = viewDragHelper.shouldInterceptTouchEvent(ev);
        return  result;
    }

    /**
     * 将事件传递给viewdraghelper处理
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return  true;
    }
    private OnStateChangeListener listener;
    public void setOnStateChangeListener(OnStateChangeListener listener){
        this.listener = listener;
    }
    public interface OnStateChangeListener{
        //对外暴露的打开和关闭的方法
        void onOpen();

        void  onClose();

        void  onDraging();
    }
}
