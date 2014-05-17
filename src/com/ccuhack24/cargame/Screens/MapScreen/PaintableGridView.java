package com.ccuhack24.cargame.Screens.MapScreen;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class PaintableGridView extends View {
    
    public int height;
    public int width;

    public PaintableGridView(Context context) {
	super(context);
	// TODO Auto-generated constructor stub
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
	Paint paint = new Paint();
	paint.setColor(Color.BLUE);
	canvas.drawRect(100, 200, 300, 400, paint);
        super.onDraw(canvas);
    }

    
}
