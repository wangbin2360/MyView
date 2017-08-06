package com.example.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public class CustomTitleView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private String title_text;
    private int title_text_color;
    private int title_text_dimension;
    private Rect mBound;


    public CustomTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs , R.styleable.CustomTitleView);
        for(int i=0;i<array.getIndexCount();i++){
           int a = array.getIndex(i);
            switch (a){
                case R.styleable.CustomTitleView_title_text:
                    title_text = array.getString(a);
                    break;
                case R.styleable.CustomTitleView_title_text_color:
                    title_text_color = array.getColor(a, Color.RED);
                    break;
                case R.styleable.CustomTitleView_title_text_size:
                    title_text_dimension = array.getDimensionPixelSize(a, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
            }
        }
        array.recycle();
        paint.setTextSize(title_text_dimension);
        mBound = new Rect();
        paint.getTextBounds(title_text,0,title_text.length(),mBound);
        this.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                title_text = randomText();
                postInvalidate();
            }

        });
    }

    private String randomText()
    {
        Random random = new Random();
        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < 4)
        {
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb = new StringBuffer();
        for (Integer i : set)
        {
            sb.append("" + i);
        }

        return sb.toString();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.YELLOW);
        canvas.drawRect(0,0,getWidth(),getHeight(),paint);
        paint.setColor(title_text_color);

        //第二个参数表示左边的位置，第二个参数比表示text底部的位置
        canvas.drawText(title_text,getWidth()/2-mBound.width()/2,getHeight()/2+mBound.height()/2,paint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        int width=0;
        int height=0;
        if(widthSpecMode == MeasureSpec.EXACTLY){
            width = widthSpecSize;
        }else if(widthSpecMode == MeasureSpec.AT_MOST){
            width = getPaddingLeft()+getPaddingRight()+mBound.width();
        }

        if(heightSpecMode == MeasureSpec.EXACTLY){
            height = heightSpecSize;
        }else if(heightSpecMode == MeasureSpec.AT_MOST){
            height = getPaddingBottom()+getPaddingTop()+mBound.height();
        }

        setMeasuredDimension(width,height);
    }

    public CustomTitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomTitleView(Context context) {
       this(context,null);
    }
}
