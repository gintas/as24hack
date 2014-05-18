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
import com.ccuhack24.angrycars.testing.Events;
import com.ccuhack24.angrycars.testing.Player;

public class GarageDataReader {

	// http://werkstatt.autoscout24.de/api/v2/garagesearch/list/directory?lat=48.0982&lon=11.5992
	/**
	 * Map<garage id, player>
	 */
	private static Map<String, Player> capturedGarages = new HashMap<String, Player>();
	/**
	 * Map<Player-ID, player>
	 */
	public static final Map<Player, Integer> scores = new HashMap<Player, Integer>();

	public static void checkGarageEvent(Entry entry, Player player)
			throws IOException, JSONException {
		try {
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
						|| capturedGarages.get(garage.id).getId()
								.equals(player.getId())) {
					Events.addEventString("Player " + player + " captures "
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
				String s = "Scores:";
				for (Player person : scores.keySet()) {
					s += "\n" + person + ": " + scores.get(person);
				}
				Events.addEventString(s);
			}
		} catch (Exception e) {
			// whatever will happen ...
			e.printStackTrace();
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

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Garage other = (Garage) obj;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}
	}

}
