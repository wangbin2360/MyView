package com.example.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public class CustomVolumnView extends View {
    private int volumn_first_color;
    private int volumn_second_color;
    private int volumn_split_size;
    private int volumn_dot_count;
    private int volumn_circle_width;
    private Bitmap bitmap;
    private int mCurrentCount = 3;
    private Rect rect;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CustomVolumnView(Context context) {
        this(context,null);
    }

    public CustomVolumnView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs , R.styleable.CustomVolumnView,defStyleAttr,0);
        for(int i= 0 ; i<array.getIndexCount();i++){
            int attr = array.getIndex(i);
            switch (attr){
                case R.styleable.CustomVolumnView_volumn_first_color:
                    volumn_first_color = array.getColor(attr, Color.YELLOW);
                    break;
                case R.styleable.CustomVolumnView_volumn_second_color:
                    volumn_second_color = array.getColor(attr , Color.RED);
                    break;
                case R.styleable.CustomVolumnView_volumn_split_size:
                     volumn_split_size = array.getInteger(attr , 20);
                    break;
                case R.styleable.CustomVolumnView_volumn_dot_count:
                    volumn_dot_count = array.getInteger(attr , 20);
                    break;
                case R.styleable.CustomVolumnView_volumn_circle_width:
                  volumn_circle_width = array.getDimensionPixelSize(attr , (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
                  ,20,getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomVolumnView_volumn_bg:
                    bitmap = BitmapFactory.decodeResource(getResources() , array.getResourceId(attr,0));
                    break;
            }
        }
        array.recycle();
        rect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    int x;
    int y;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                 x= (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                y = (int) event.getY();
                if(x > y){
                    up();
                }
                else {
                    down();
                }
                break;

        }
        return true;
    }

    private void down() {
        degree=degree+10;
        invalidate();
    }

    private void up() {
        degree=degree-10;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(volumn_circle_width);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        int centre = getWidth()/2;
        int r = centre - volumn_circle_width/2;
        DrawOvel(canvas , centre , r);

        rect.left = (int)(centre - Math.sqrt(2)*r*1.0f/2);
        rect.right = (int)(centre + Math.sqrt(2)*r*1.0f/2);
        rect.top = (int)(centre - Math.sqrt(2)*r*1.0f/2);
        rect.bottom = (int)(centre - Math.sqrt(2)*r*1.0f/2);

        if(bitmap.getWidth()<r*Math.sqrt(2)){
            rect.left = centre - bitmap.getWidth()/2;
            rect.right = centre + bitmap.getWidth()/2;
            rect.top = centre - bitmap.getHeight()/2;
            rect.bottom = centre + bitmap.getHeight()/2;
        }
       canvas.drawBitmap(bitmap,null,rect,paint);

    }

    private int degree = 16;
    private void DrawOvel(Canvas canvas , int centre , int r){
        float itemDegree = (360*1.0f - volumn_split_size*volumn_dot_count)/volumn_dot_count;
        RectF rectF = new RectF(centre-r ,centre-r , centre+r ,centre+r);
        paint.setColor(volumn_first_color);
        for(int i=0;i<volumn_dot_count;i++){
            canvas.drawArc(rectF , i*(itemDegree+volumn_split_size) ,degree ,false , paint);
        }

        paint.setColor(volumn_second_color);
        for(int i =0 ; i<mCurrentCount ;i++){
            canvas.drawArc(rectF , i*(itemDegree + volumn_split_size) , degree,false ,paint);
        }
    }

    public CustomVolumnView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }
}
