package com.ccuhack24.cargame.Screens.MapScreen;

import com.ccuhack24.angrycars.engine.Engine.Cell;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONException;

import com.ccuhack24.angrycars.engine.Engine;
import com.ccuhack24.angrycars.engine.GridPoint;
import com.ccuhack24.angrycars.engine.Rectangle;
import com.ccuhack24.angrycars.engine.VehicleDataReader;
import com.ccuhack24.angrycars.engine.VehicleDataReader.Entry;
import com.ccuhack24.cargame.R;
import com.ccuhack24.cargame.R.id;
import com.ccuhack24.cargame.R.layout;
import com.ccuhack24.cargame.R.menu;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MapScreen extends Activity {

    public Canvas theCanvas;
    public RelativeLayout theGrid;
    public com.ccuhack24.angrycars.engine.Engine engine;

    private List<Entry> readTrackEntries(int resourceId) {
	InputStream car1 = getResources().openRawResource(resourceId);
	Scanner s = new Scanner(car1);
	s.useDelimiter("\\A");
	String data = s.next();
	s.close();

	try {
	    VehicleDataReader r = new VehicleDataReader(data);
	    return r.parse();
	} catch (JSONException e) {
	    throw new IllegalStateException(e);
	}
    }

    private Engine readTrack() {
	List<Entry> car1 = readTrackEntries(R.raw.car1);
	List<Entry> car2 = readTrackEntries(R.raw.car2);

	Rectangle bounds = VehicleDataReader.findBounds(car1);
	Rectangle bounds2 = VehicleDataReader.findBounds(car2);
	bounds.expand(bounds2);

	List<GridPoint> points1 = VehicleDataReader.normalize(car1, bounds, 50,
		50);
	List<GridPoint> points2 = VehicleDataReader.normalize(car2, bounds, 50,
		50);

	Engine e = new Engine(51, 51);
	for (int i = 0; i < 100; i++) {
	    GridPoint team1 = points1.get(i);
	    GridPoint team2 = points2.get(i);
	    e.insertPoint(team1.x, team1.y, 1);
	    e.insertPoint(team2.x, team2.y, 2);
	    e.step(1);
	    // TODO: update display and wait here
	}
	return e;
    }

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
	engine = readTrack();
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
