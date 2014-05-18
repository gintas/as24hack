package com.ccuhack24.angrycars.testing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import android.util.Log;

import com.ccuhack24.angrycars.engine.VehicleDataReader.Entry;

public class Events {

	private static BlockingQueue<String> eventQueue = new ArrayBlockingQueue<String>(500);
	private static List<String> mostAggressivePlayers = new ArrayList<String>(5);
	
	public static Queue<String> getEventString(){
		return eventQueue;
	}

	public static void addEventString(String newEvent){
		eventQueue.add(newEvent);
	}

	private Player maxSpeed;
	private Player mostAggressive;
	
	public Set<Player> fireEvents(Entry entry, Set<Player> players) {
		if (maxSpeed(entry.speed, entry.player) !=null) {
			players.remove(maxSpeed(entry.speed, entry.player));
			players.add(maxSpeed(entry.speed, entry.player));
			eventQueue.add("New max speed: " + entry.speed + " by player " + entry.player.getName());
		}
		mostAgressive(entry);
		
		return players;
	}
	
	private Player maxSpeed(double speed, Player player)
	{
		if (player!= null && player.getMaxSpeed() < speed) {
			player.setMaxSpeed(speed);
			maxSpeed=player;
			return player;
		}
		return null;

	}	
	
	private void mostAgressive(Entry entry) {
		if (entry.player == null) {
			return;
		}
		
		if (entry.rpm>2700) {
			entry.player.setAgressivity(entry.player.getAgressivity()+ entry.rpm - 2700);
		}	


		if (mostAggressive.getAgressivity() < currentplayer.getAgressivity()) {
			mostAggressive=currentplayer;
		}
		
		allplayer.remove(currentplayer);
		allplayer.add(currentplayer);
		return allplayer;	
	}
	
	public static List<String> mostAggressiveRanking() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		return list;
	}
	
}
