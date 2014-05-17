package com.ccuhack24.cargame.Screens.MapScreen;

import com.ccuhack24.cargame.R;
import com.ccuhack24.cargame.R.id;
import com.ccuhack24.cargame.R.layout;
import com.ccuhack24.cargame.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Canvas;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;

public class MapScreen extends Activity {

    public Canvas theCanvas;
    public RelativeLayout theGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_map_screen);

	// get grid layout
	theGrid = (RelativeLayout) this.findViewById(R.id.mapScreen_grid);
	theGrid.addView(new PaintableGridView(this));
	// attach view to activity
	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.map_screen, menu);
	return true;
    }

}
