package com.ccuhack24.cargame.Screens.MapScreen;

import com.ccuhack24.angrycars.engine.Engine.Cell;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONException;

import com.ccuhack24.angrycars.engine.Engine;
import com.ccuhack24.angrycars.engine.GridPoint;
import com.ccuhack24.angrycars.engine.GridUpdater;
import com.ccuhack24.angrycars.engine.Rectangle;
import com.ccuhack24.angrycars.engine.VehicleDataReader;
import com.ccuhack24.angrycars.engine.VehicleDataReader.Entry;
import com.ccuhack24.cargame.R;
import com.ccuhack24.cargame.R.id;
import com.ccuhack24.cargame.R.layout;
import com.ccuhack24.cargame.R.menu;
import com.ccuhack24.cargame.Screens.RankingScreen.RankingScreen;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.ccuhack24.angrycars.testing.Events;

public class MapScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_map_screen);

	// we need to find out the screen dimensions for the calculation
	Display display = getWindowManager().getDefaultDisplay();
	Point size = new Point();
	display.getSize(size);
	int screenWidth = size.x;
	int screenHeight = size.y;

	/*
	int[][] theField = new int[17][17];
	// fake a field
	for (int i = 0; i < theField.length; i++)
	    for (int j = 0; j < theField.length; j++)
		theField[i][j] = (i + j) % 5;
	*/

	Bitmap theMap = BitmapFactory.decodeResource(getResources(),
		R.drawable.map3);

	// attach view to activity
	PaintableGridView gridView = new PaintableGridView(this, screenWidth,
		screenHeight, theMap);
	setContentView(gridView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.map_screen, menu);
	final Intent intent = new Intent(this, RankingScreen.class);
	menu.findItem(R.id.action_rank).setOnMenuItemClickListener(
		new OnMenuItemClickListener() {

		    @Override
		    public boolean onMenuItemClick(MenuItem item) {
			startActivity(intent);
			return false;
		    }
		});
	return true;
    }

}
