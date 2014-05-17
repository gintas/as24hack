package com.ccuhack24.angrycars.engine;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ccuhack24.angrycars.engine.VehicleDataReader.Entry;

public class GarageDataReader {

	// http://werkstatt.autoscout24.de/api/v2/garagesearch/list/directory?lat=48.0982&lon=11.5992
	/**
	 * Map<garage id, player>
	 */
	private static Map<String, Integer> capturedGarages = new HashMap<String, Integer>();
	/**
	 * Map<Player-ID, score>
	 */
	public static final Map<Integer, Integer> scores = new HashMap<Integer, Integer>();

	public static void checkGarageEvent(Entry entry, int player)
			throws IOException, JSONException {
		URL url = new URL(
				"http://werkstatt.autoscout24.de/api/v2/garagesearch/list/directory?lat="
						+ entry.latitude + "&lon=" + entry.longitude);
		URLConnection connection = url.openConnection();
		String jsonData = new Scanner(connection.getInputStream(), "UTF-8")
				.useDelimiter("\\A").next();
		GarageDataReader garageDataReader = new GarageDataReader(jsonData);

		List<Garage> garages = garageDataReader.getGaragesInCloseVicinity();
		boolean update = false;
		for (Garage garage : garages) {
			// TODO ...
			if (!capturedGarages.containsKey(garage.id)
					|| capturedGarages.get(garage.id) != player) {
				System.out.println("Player " + player + " captures "
						+ garage.name);
				capturedGarages.put(garage.id, player);
				if (scores.containsKey(player))
					scores.put(player, scores.get(player) + 1);
				else
					scores.put(player, 1);
				update = true;
			}
		}
		if (update) {
			// TODO ...
			System.out.println("Scores:");
			for (int person : scores.keySet()) {
				System.out.println(person + ": " + scores.get(person));
			}
		}
	}

	private final JSONObject json;

	public GarageDataReader(String jsonData) throws JSONException {
		this.json = new JSONObject(jsonData);
	}

	public List<Garage> getGaragesInCloseVicinity() throws JSONException {
		List<Garage> result = new ArrayList<GarageDataReader.Garage>();
		if (!json.has("result"))
			return result;

		JSONArray garages = json.getJSONArray("result");

		int len = garages.length();
		for (int i = 0; i < len; i++) {
			JSONObject obj = garages.getJSONObject(i);
			JSONObject gar = obj.getJSONObject("garage");
			if (obj.isNull("distanceToSearchLocation") || gar.isNull("id")
					|| gar.isNull("name")) {
				continue;
			}
			if (obj.getDouble("distanceToSearchLocation") > 2)
				continue;
			String id = gar.getString("id");
			String name = gar.getString("name");

			result.add(new Garage(id, name));
		}
		return result;
	}

	public static class Garage {
		public final String id;
		public final String name;

		public Garage(String id, String name) {
			this.id = id;
			this.name = name;
		}
	}

}
