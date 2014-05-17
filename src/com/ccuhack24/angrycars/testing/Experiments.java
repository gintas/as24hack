package com.ccuhack24.angrycars.testing;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import com.ccuhack24.angrycars.engine.Engine;
import com.ccuhack24.angrycars.engine.GarageDataReader;
import com.ccuhack24.angrycars.engine.GridPoint;
import com.ccuhack24.angrycars.engine.Rectangle;
import com.ccuhack24.angrycars.engine.VehicleDataReader;
import com.ccuhack24.angrycars.engine.VehicleDataReader.Entry;

public class Experiments {

	
	private static long currentMaxSpeed = 20000; 
	
	public static void main(String[] args) throws Exception {
		InputStream stream = new FileInputStream("/Users/Chaoran/git/as24hack/res/routes/car1.json");
				//Experiments.class.getResourceAsStream(
				//"routes/car1.json");
		String data = new Scanner(stream,"UTF-8").useDelimiter("\\A").next();
		VehicleDataReader r = new VehicleDataReader(data);
		List<Entry> entries1 = r.parse();
		Rectangle bounds = VehicleDataReader.findBounds(entries1);
		
		InputStream stream2 = new FileInputStream("/Users/Chaoran/git/as24hack/res/routes/car5.json");
			//Experiments.class.getResourceAsStream(
			//"routes/car5.json");
		String data2 = new Scanner(stream2,"UTF-8").useDelimiter("\\A").next();
		VehicleDataReader r2 = new VehicleDataReader(data2);
		List<Entry> entries2 = r2.parse();
		Rectangle bounds2 = VehicleDataReader.findBounds(entries2);

		System.out.println(bounds);
		System.out.println(bounds2);

		bounds.expand(bounds2);
		
		System.out.println(bounds);
		
		System.out.println(bounds.minX + " " + bounds.minY + " " + bounds.maxX + " " + bounds.maxY);
		List<GridPoint> e1 = VehicleDataReader.normalize(entries1, bounds, 50, 50);
		List<GridPoint> e2 = VehicleDataReader.normalize(entries2, bounds, 50, 50);

		Engine e = new Engine();
		for (int i = 0; i < 20; i++) {
			GridPoint team1 = e1.get(i);
			GridPoint team2 = e2.get(i);
			e.insertPoint(team1.x, team1.y, 1);
			e.insertPoint(team2.x, team2.y, 2);
			e.step(0);
//			System.out.println(e);
			
			// TODO: make me nice
			checkEvent(entries1.get(i));
			checkEvent(entries2.get(i));
			
			GarageDataReader.checkGarageEvent(entries1.get(1), 1);
			GarageDataReader.checkGarageEvent(entries2.get(i), 2);
		}
    }

	public static void checkEvent(Entry entry) {
		if (entry.speed > currentMaxSpeed) {
			currentMaxSpeed = entry.speed;
			System.out.println("New maxspeed " + currentMaxSpeed);
			// TOOO ANDROID event hurra hurra
		}
	}
	
	
	
	
	public static void engineTest() {
		Engine e = new Engine(50, 50);
		e.insertPoint(3, 5, 1);
		e.insertPoint(8, 3, 2);
		for (int i = 0; i < 5; i++) {
			e.step(0);
			System.out.println(e);
		}
	}	
}
