package com.seven.mynah.custominterface;

import com.seven.mynah.R;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class CustomButton extends FrameLayout{

	Context context;
	public CustomButtonsFragment cbf;
	
	//int border_color = android.graphics.Color.TRANSPARENT;
	int border_color = android.graphics.Color.BLACK;
	

	public void setCBF(CustomButtonsFragment _cbf)
	{
		cbf = _cbf;
	}
	
	public CustomButton(Context context) {
		super(context);
		this.context = context;
		init(this.context);
	}
	
	//inflater로 생성
	public CustomButton(Context context, AttributeSet attrs) {
		super(context,attrs);
		this.context = context;
		init(this.context);
	}
	
	public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		init(this.context);
	}
	
	private void init(Context context) {
		
		//혹시나 이 부분에서 필요할 부분을 찾음...만약에 글 아래로 들어간다면?
	    //inflate(getContext(), R.layout.layout_custombutton, this);	
	    inflate(context, R.layout.layout_custombutton, this);	
	    
	    //background 설정
	    
    }
	

	@Override
	public void addView(View child) {
		// TODO Auto-generated method stub
		super.addView(child);
		//this.addView(child);
	}
	
	
	public void test()
	{
		//Intent intent = new Intent();
		//ActivityManager am = ActivityManager
	}
	
	
	@Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();

        paint.setColor(border_color);

        canvas.drawLine(0, 0, this.getWidth() - 1, 0, paint);
        canvas.drawLine(0, 0, 0, this.getHeight() - 1, paint);
        canvas.drawLine(this.getWidth() - 1, 0, this.getWidth() - 1,
                this.getHeight() - 1, paint);
        canvas.drawLine(0, this.getHeight() - 1, this.getWidth() - 1,
                this.getHeight() - 1, paint);
    }
	
	public void setBorderColor(int color)
	{
		border_color = color;
	}



}
