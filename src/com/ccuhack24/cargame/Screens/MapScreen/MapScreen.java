package com.ccuhack24.cargame.Screens.MapScreen;

import com.ccuhack24.angrycars.engine.Engine.Cell;
import com.ccuhack24.cargame.R;
import com.ccuhack24.cargame.R.id;
import com.ccuhack24.cargame.R.layout;
import com.ccuhack24.cargame.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.service.wallpaper.WallpaperService.Engine;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MapScreen extends Activity {

    public Canvas theCanvas;
    public RelativeLayout theGrid;
    public com.ccuhack24.angrycars.engine.Engine engine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_map_screen);
	// get grid layout
	theGrid = (RelativeLayout) this.findViewById(R.id.mapScreen_grid);

	// we need to find out the screen dimensions for the calculation
	Display display = getWindowManager().getDefaultDisplay();
	Point size = new Point();
	display.getSize(size);
	int screenWidth = size.x;
	int screenHeight = size.y;

	// we also give the playing field matrix we get from the engine
	// the playing field is a 2-dimensional array which has the value of the team in each cell
	engine = new com.ccuhack24.angrycars.engine.Engine();
	Cell[][] grid = engine.getGrid();

	/*
	int[][] theField = new int[17][17];
	// fake a field
	for (int i = 0; i < theField.length; i++)
	    for (int j = 0; j < theField.length; j++)
		theField[i][j] = (i + j) % 5;
	*/

	// get the fake map
	Bitmap theMap = BitmapFactory.decodeResource(getResources(),
		R.drawable.map);

	// attach view to activity
	PaintableGridView gridView = new PaintableGridView(this, screenWidth,
		screenHeight, grid, theMap);
	setContentView(gridView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.map_screen, menu);
	return true;
    }

}
