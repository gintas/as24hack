package com.ccuhack24.cargame.Screens.MapScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.ccuhack24.angrycars.engine.Engine.Cell;
import com.ccuhack24.angrycars.engine.GridPoint;
import com.ccuhack24.angrycars.engine.GridUpdater;
import com.ccuhack24.angrycars.testing.Events;
import com.ccuhack24.cargame.R;

public class PaintableGridView extends View {

    private static final int INTERVAL = 500;
	// transparency of all our colors
    private int teamPaintAlpha = 120;
    public ArrayList<Paint> teamPaints;
	private Paint markerPaint;

    // dimensions of the a single cell in grid
    private float cellWidth;
    private float cellHeight;

    // dimensions of screen
    private float screenWidth;
    private float screenHeight;

    // the actual playing field with all the team IDs in each cell
    private Cell[][] teamField;

    // the timer that updates the UI
    private Timer updateUITimer;

    // position of the map
    float x = 0;
    float y = 0;

    private GridUpdater updater;
    
    // map is stored as an image
    private Bitmap map;

    public PaintableGridView(final Context context, int screenWidth,
	    int screenHeight, Bitmap mapImg) {
	super(context);
	
	updater = new GridUpdater(getResources());
	updater.importTrack(R.raw.car1);
	updater.importTrack(R.raw.car2);
	updater.importTrack(R.raw.car4);
	teamField = updater.setUpGrid(50, 50);
	updater.step();
	
	float numberOfCellsX = 50;
	float numberOfCellsY = 50;

	this.screenWidth = screenWidth;
	this.screenHeight = screenHeight;

	map = getResizedBitmap(mapImg, mapImg.getHeight() * 4,
		mapImg.getWidth() * 4);

	// TODO: use square cells
	cellWidth = map.getWidth() / numberOfCellsX;
	cellHeight = map.getHeight() / numberOfCellsY;

	// first center the map
	x = screenWidth / 2;
	y = screenHeight / 2;

	// initialize the different team Paints, so we can use them in the drawing process
	initPaints();

	TimerTask updateUItask = new TimerTask() {
	    @Override
	    // change the alpha so we can see if the timer works
	    public void run() {
    		updater.step();
	    	postInvalidate();
	    }
	};

	updateUITimer = new Timer();
	updateUITimer.scheduleAtFixedRate(updateUItask, INTERVAL, INTERVAL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
	Queue<String> queue = Events.getEventString();
	while(!queue.isEmpty()){
		Toast toast = Toast.makeText(getContext(), queue.poll(), Toast.LENGTH_LONG);
		toast.show();	    		
	}
    	
	canvas.drawColor(Color.BLUE);//To make background 
	
	canvas.drawBitmap(map, x - (map.getWidth() / 2), y
		- (map.getHeight() / 2), null);

	for (int i = 0; i < teamField.length; i++)
	    for (int j = 0; j < teamField[0].length; j++) {
		// get the correct color for the current field based on the team ID
		int currentTeam = teamField[i][j].team;
		Paint currentPaint = teamPaints.get(currentTeam);

		// calculate the position of the current field
		float currentX = x - map.getWidth() / 2 + i * cellWidth;
		float currentY = y - map.getHeight() / 2 + j * cellHeight;

		// now draw the colored rectangle
		canvas.drawRect(currentX, currentY, currentX + cellWidth,
			currentY + cellHeight, currentPaint);

	    }
	
//	List<GridPoint> lastPos = updater.lastPos();
//	for (int i = 0; i < lastPos.size(); i++) {
//		GridPoint pos = lastPos.get(i);
//
//		float currentX = x - map.getWidth() / 2 + pos.x * cellWidth;
//		float currentY = y - map.getHeight() / 2 + pos.y * cellHeight;
//
//		canvas.drawRect(currentX, currentY, currentX + cellWidth,
//				currentY + cellHeight, markerPaint);
//	}

	super.onDraw(canvas);
    }

    public boolean onTouchEvent(MotionEvent event) {
	int tmpX = (int) event.getX();
	int tmpY = (int) event.getY();

	x -= (tmpX - screenWidth / 2) / 20;
	y -= (tmpY - screenHeight / 2) / 20;

	// check if out of bounds
	if (x > map.getWidth() / 2)
	    x = map.getWidth() / 2;

	if (y > map.getHeight() / 2)
	    y = map.getHeight() / 2;

	if (x < screenWidth - map.getWidth() / 2)
	    x = screenWidth - map.getWidth() / 2;

	if (y < screenHeight - map.getHeight() / 2)
	    y = screenHeight - map.getHeight() / 2;

	invalidate();
	
	return true;
    }

    private void initPaints() {
	// we have 4 different team colors, all use the same transparency
	// neutral
	// red
	// green
	// yellow
	// cyan
	teamPaints = new ArrayList<Paint>();
	Paint tmpPaint = new Paint();
	tmpPaint.setAlpha(0);
	teamPaints.add(tmpPaint);

	tmpPaint = new Paint();
	tmpPaint.setColor(Color.RED);
	tmpPaint.setAlpha(teamPaintAlpha);
	teamPaints.add(tmpPaint);

	tmpPaint = new Paint();
	tmpPaint.setColor(Color.GREEN);
	tmpPaint.setAlpha(teamPaintAlpha);
	teamPaints.add(tmpPaint);

	tmpPaint = new Paint();
	tmpPaint.setColor(Color.YELLOW);
	tmpPaint.setAlpha(teamPaintAlpha);
	teamPaints.add(tmpPaint);

	tmpPaint = new Paint();
	tmpPaint.setColor(Color.CYAN);
	tmpPaint.setAlpha(teamPaintAlpha);
	teamPaints.add(tmpPaint);
	
	markerPaint = new Paint();
	markerPaint.setColor(Color.BLACK);
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
	int width = bm.getWidth();
	int height = bm.getHeight();
	float scaleWidth = ((float) newWidth) / width;
	float scaleHeight = ((float) newHeight) / height;
	// CREATE A MATRIX FOR THE MANIPULATION
	Matrix matrix = new Matrix();
	// RESIZE THE BIT MAP
	matrix.postScale(scaleWidth, scaleHeight);

	// "RECREATE" THE NEW BITMAP
	Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
		matrix, false);
	return resizedBitmap;
    }
}
