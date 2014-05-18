package com.ccuhack24.angrycars.engine;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.json.JSONException;

import android.content.res.Resources;

import com.ccuhack24.angrycars.engine.Engine.Cell;
import com.ccuhack24.angrycars.engine.VehicleDataReader.Entry;
import com.ccuhack24.angrycars.testing.Events;
import com.ccuhack24.angrycars.testing.Player;

public class GridUpdater {

	private final Resources resources;
	private Rectangle bounds;
	List<List<Entry>> rawEntries = new ArrayList<List<Entry>>();
	private double yCellSize;
	private double xCellSize;
	int t = 0;
	private Engine engine;
	private Events events = new Events();
	private Set<Player> players = new HashSet<Player>();

	public GridUpdater(Resources resources) {
		this.resources = resources;
	}

	public void importTrack(int resourceId) {
		InputStream car1 = resources.openRawResource(resourceId);
		Scanner s = new Scanner(car1);
		s.useDelimiter("\\A");
		String data = s.next();
		s.close();

		List<Entry> entries;
		Player player = new Player ("" + resourceId, "" + resourceId, 0, 0);
		players.add(player);
		
		try {
		    VehicleDataReader r = new VehicleDataReader(data);
		    entries = r.parse(player);
		} catch (JSONException e) {
		    throw new IllegalStateException(e);
		}
		
		Rectangle localBounds = VehicleDataReader.findBounds(entries);
		if (bounds == null) {
			bounds = localBounds;
		} else {
			bounds.expand(localBounds);
		}
		rawEntries.add(entries);
	}
	
	public Cell[][] setUpGrid(int ny, int nx) {
		double yRange = bounds.maxY - bounds.minY;
		double xRange = bounds.maxX - bounds.minX;
		
		yCellSize = yRange / ny;
		xCellSize = xRange / nx;
		
		// Square cells.
		if (yCellSize > xCellSize) {
			xCellSize = yCellSize;
		} else {
			yCellSize = xCellSize;
		}
		engine = new Engine(ny+1, nx+1);
		return engine.getGrid();
	}
	
	public GridPoint entryPosition(Entry entry) {
		GridPoint p = new GridPoint();
		double y = (entry.latitude - bounds.minY) / yCellSize;
		p.y =(int) Math.floor(y);
		double x = (entry.longitude - bounds.minX) / xCellSize;
		p.x = (int) Math.floor(x);
		return p;
	}
	
	public void step() {
		for (int i = 0; i < rawEntries.size(); i++) {
			List<Entry> entryList = rawEntries.get(i);
			if (t < entryList.size()) {
				Entry entry = entryList.get(t);
				GridPoint p = entryPosition(entry);
				engine.insertPoint(p.y, p.x, i+1);
				players = events.fireEvents(entry, players);
			}
		}
		t++;
		engine.step(1);
	}
}
