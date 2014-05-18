package com.ccuhack24.angrycars.engine;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class VehicleDataReader {
    private final JSONArray json;

	public VehicleDataReader(String jsonData) throws JSONException {
    	this.json = new JSONArray(jsonData);
    }
	
	public List<Entry> parse() throws JSONException {
		int len = json.length();
		List<Entry> result = new ArrayList<Entry>();
		for (int i = 0; i < len; i++) {
			JSONObject obj = json.getJSONObject(i);
			if (obj.isNull("recorded_at") || obj.isNull("latitude") || obj.isNull("longitude")) {
				continue;
			}
			// TODO: parse timestamp properly
			String timestamp = obj.getString("recorded_at");
			double lat = obj.getDouble("latitude");
			double longitude = obj.getDouble("longitude");
			
			Entry entry = new Entry(timestamp, lat, longitude);
			if (obj.has("GPS_SPEED")) {
				JSONObject speedO = obj.getJSONObject("GPS_SPEED");
				long speed = speedO.getLong("1");
				entry.speed = speed;
			}
			if (obj.has("MDI_OBD_MILEAGE")) {
				JSONObject mileage0 = obj.getJSONObject("MDI_OBD_MILEAGE");
				long mileage = mileage0.getLong("1");
				entry.mileage = mileage;
			}
			if (obj.has("DIO_IGNITION")) {
				boolean ignition = obj.getBoolean("DIO_IGNITION");
				entry.ignition = ignition;
			}
			result.add(entry);
		}
		Collections.reverse(result);
		return result;
	}
	
	public static Rectangle findBounds(Iterable<Entry> entries) { 
		Rectangle r = new Rectangle();
		r.minX = Double.MAX_VALUE;
		r.minY = Double.MAX_VALUE;
		r.maxX = Double.MIN_VALUE;
		r.maxY = Double.MIN_VALUE;
		
		for (Entry entry : entries) {
			double y = entry.latitude;
			double x = entry.longitude;
			if (y > r.maxY) r.maxY = y;
			if (x > r.maxX) r.maxX = x;
			if (y < r.minY) r.minY = y;
			if (x < r.minX) r.minX = x;
		}
		return r;
	}
	
	public static List<GridPoint> normalize(Iterable<Entry> entries, Rectangle bounds,
			int ny, int nx) {
		double yRange = bounds.maxY - bounds.minY;
		double xRange = bounds.maxX - bounds.minX;
		
		double yCellSize = yRange / ny;
		double xCellSize = xRange / nx;
		
		if (yCellSize > xCellSize) {
			xCellSize = yCellSize;
		} else {
			yCellSize = xCellSize;
		}
	
		List<GridPoint> result = new ArrayList<GridPoint>();
		for (Entry entry : entries) {
			GridPoint p = new GridPoint();
			double y = (entry.latitude - bounds.minY) / yCellSize;
			p.y =(int) Math.floor(y);
			double x = (entry.longitude - bounds.minX) / xCellSize;
			p.x = (int) Math.floor(x);
			result.add(p);
		}
		return result;
	}

	public static class Entry {
		public Calendar timestamp;
		public long mileage = 0;
		public final double latitude;
		public final double longitude;
		public double speed = 0;
		public boolean ignition = false;
		
		public void setTimestamp(String s) {
			timestamp = Calendar.getInstance();
			timestamp.set(
					Integer.parseInt(s.substring(0,4)), // year 
					Integer.parseInt(s.substring(5,7)), // month 
					Integer.parseInt(s.substring(8,10)),  // day
					Integer.parseInt(s.substring(11,13)), // hour 
					Integer.parseInt(s.substring(14,16)),  // minute
					Integer.parseInt(s.substring(17,19))); //second
		}

		public Entry(String timestamp, double lat, double longitude) {
			setTimestamp(timestamp);
			this.latitude = lat;
			this.longitude = longitude;
		}		
	}
}
