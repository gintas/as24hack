package com.ccuhack24.angrycars.testing;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.ccuhack24.angrycars.engine.Engine;
import com.ccuhack24.angrycars.engine.GridPoint;
import com.ccuhack24.angrycars.engine.Rectangle;
import com.ccuhack24.angrycars.engine.VehicleDataReader;
import com.ccuhack24.angrycars.engine.VehicleDataReader.Entry;

public class Experiments {

	private static final String PATH = "C:/Users/gintas/Documents/AS24hackathon/inputs/car1.json";
	private static final String PATH2 = "C:/Users/gintas/Documents/AS24hackathon/inputs/car4.json";
	
	private static List<List<GridPoint>> readRuns() {
		List<List<GridPoint>> result = new ArrayList<List<GridPoint>>());
		Rectangle globalBounds = null;
		for (int i = 1; i <= 5; i++) {
			String data = new String(Files.readAllBytes(Paths.get(PATH)));
			VehicleDataReader r = new VehicleDataReader(data);
			List<Entry> entries = r.parse();
			Rectangle bounds = VehicleDataReader.findBounds(entries);
			if (globalBounds == null) {
				globalBounds = bounds;
			} else {
				globalBounds.expand(bounds);
			}
			temp.add(entries);
		}
		
		System.out.println(e);
	}
	
	public static void main(String[] args) throws Exception {
		String data = new String(Files.readAllBytes(Paths.get(PATH)));
		VehicleDataReader r = new VehicleDataReader(data);
		List<Entry> entries = r.parse();
		Rectangle bounds = VehicleDataReader.findBounds(entries);
		
		String data2 = new String(Files.readAllBytes(Paths.get(PATH2)));
		VehicleDataReader r2 = new VehicleDataReader(data2);
		List<Entry> entries2 = r2.parse();
		Rectangle bounds2 = VehicleDataReader.findBounds(entries2);

		System.err.println(bounds);
		System.err.println(bounds2);

		bounds.expand(bounds2);
		
		System.err.println(bounds);
		
		System.err.println(bounds.minX + " " + bounds.minY + " " + bounds.maxX + " " + bounds.maxY);
		List<GridPoint> e1 = VehicleDataReader.normalize(entries, bounds, 50, 50);
		List<GridPoint> e2 = VehicleDataReader.normalize(entries2, bounds, 50, 50);

		Engine e = new Engine();
		for (int i = 0; i < 20; i++) {
			GridPoint team1 = e1.get(i);
			GridPoint team2 = e2.get(i);
			e.insertPoint(team1.x, team1.y, 1);
			e.insertPoint(team2.x, team2.y, 2);
			e.step(0);
			System.out.println(e);
		}
    }
}
