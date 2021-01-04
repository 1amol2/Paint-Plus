package com.amolchandra.giveawayapp.PaintApp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends Activity 
{
    LinearLayout body;
    RadioGroup rGroup;
        static Button clear;
        SeekBar skBar;
        int StrokeWidth;
    int penColor = Color.BLACK;
    int bgColor = Color.WHITE;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        body = findViewById(R.id.myCanvas);
        clear = findViewById(R.id.Clear);
        skBar=findViewById(R.id.mainSeekBar);
        skBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
        public void onProgressChanged(SeekBar p1, int p2, boolean p3)
        {
            
                paintView.changePenSize(p2);
                }

                @Override
                public void onStartTrackingTouch(SeekBar p1)
                    {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar p1)
                {

                }

            
        });
          paintMain();
          
             clear.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View p1)
                {
                    
                   paintView.ClearPath();
                    Toast.makeText(MainActivity.this, "Cleared", Toast.LENGTH_SHORT).show();
                }


            });
        rGroup = findViewById(R.id.mainRadioGroup);
        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup p1, int p2)
                {
                    switch (p2)
                    {
                        case R.id.red:
                            paintView.changePenColor(Color.RED);


                            break;
                        case R.id.yellow:
                            
                            paintView.changePenColor(Color.YELLOW);
                            

                            break;
                        case R.id.green:
                            paintView.changePenColor(Color.GREEN);
                            

                            break;
                        case R.id.blue:
                            paintView.changePenColor(Color.BLUE);
                            

                            break;
                    }




                }


            });
    }
    public void paintMain()
    {
        paintView = new DrawingView(this);
        body.addView(paintView);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(penColor); 
        mPaint.setStyle(Paint.Style.STROKE); 
        mPaint.setStrokeJoin(Paint.Join.ROUND); 
        
        mPaint.setStrokeWidth(StrokeWidth);
	}
    DrawingView paintView;
     Canvas canvas;
    Paint mPaint;
    
    class DrawingView extends View {

        ArrayList<DrawingPath> paths;
        private DrawingPath drawingPath;
        private Bitmap mBitmap;
        private Canvas mCanvas;


        public DrawingView(Context context) {
            super(context);
            paths = new ArrayList<>();
            mBitmap = Bitmap.createBitmap(820, 480, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
            this.setBackgroundColor(bgColor);
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setDither(true);
            mPaint.setColor(penColor);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeJoin(Paint.Join.ROUND);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setStrokeWidth(StrokeWidth);


        }

        public void changePenSize(int size) {
            StrokeWidth = size;
            mPaint.setStrokeWidth(StrokeWidth);
        }


        public void init() {
            mPaint = new Paint();
            mPaint.setDither(true);
            mPaint.setColor(Color.BLACK);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeJoin(Paint.Join.ROUND);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setStrokeWidth(StrokeWidth);

        }

        public void setBGColor(int color) {
            bgColor = color;
            this.setBackgroundColor(bgColor);
            invalidate();


        }

        public void ClearPath() {
            paths.clear();
            bgColor = Color.WHITE;
            
            init();
            invalidate();
        }

        public void changePenColor(int c) {
            penColor=c;
            mPaint.setColor(c);
            invalidate();
        }


        @Override
        public boolean onTouchEvent(MotionEvent event) {

            float touchX = event.getX();
            float touchY = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    drawingPath = new DrawingPath(mPaint.getStrokeWidth());
                    paths.add(drawingPath);
                    drawingPath.path.moveTo(touchX, touchY);
                    break;
                case MotionEvent.ACTION_MOVE:
                    drawingPath.path.lineTo(touchX, touchY);
                    break;
                case MotionEvent.ACTION_UP:
                    mCanvas.drawPath(drawingPath.path, mPaint);
                    break;
                default:
                    return false;
            }

            invalidate();
            return true;
        }


        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            for (DrawingPath drawingPath : paths) {
                mPaint.setStrokeWidth(drawingPath.strokeWidth);
                canvas.drawPath(drawingPath.path, mPaint);
            }


        }


    }

    class DrawingPath {
        Path path;
        float strokeWidth;

        DrawingPath(float strokeWidth) {
            path = new Path();
            this.strokeWidth = strokeWidth;
        }
    }
}
