package com.ccuhack24.angrycars.testing;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import android.util.Log;

import com.ccuhack24.angrycars.engine.VehicleDataReader.Entry;

public class Events {

	private static BlockingQueue<String> eventQueue = new ArrayBlockingQueue<String>(500);
	
	public static Queue<String> getEventString(){
		return eventQueue;
	}

	public static void addEventString(String newEvent){
		eventQueue.add(newEvent);
	}

	private Player maxSpeed;
	private Player mostAgressive;
	
	public Set<Player> fireEvents(Entry entry, Set<Player> players) {
		if (maxSpeed(entry.speed, entry.player) !=null) {
			players.remove(maxSpeed(entry.speed, entry.player));
			players.add(maxSpeed(entry.speed, entry.player));
			eventQueue.add("New max speed: " + entry.speed + " by player " + entry.player.getName());
		}
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
	
	public Set<Player> mostAgressive(double rpm, double breaks,Player curentplayer,Set<Player> allplayer)
	{
		if(rpm>2700)
			curentplayer.setAgressivity(curentplayer.getAgressivity()+ rpm-2700);
		curentplayer.setAgressivity(curentplayer.getAgressivity()+ breaks*1000);	
		if (mostAgressive.getAgressivity()<curentplayer.getAgressivity())
			mostAgressive=curentplayer;
		allplayer.remove(curentplayer);
		allplayer.add(curentplayer);
		return allplayer;	
	}
	
}
