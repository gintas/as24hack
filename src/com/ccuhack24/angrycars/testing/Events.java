package com.ccuhack24.angrycars.testing;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

import android.util.Log;

import com.ccuhack24.angrycars.engine.VehicleDataReader.Entry;

public class Events {

	private static BlockingQueue<String> eventQueue;
	
	public static Queue<String> getEventString(){
		return eventQueue;
	}

	public static void addEventString(String newEvent){
		eventQueue.add(newEvent);
		Log.i("Event", "Added " + newEvent);
	}

	private Player maxSpeed;
	private Player mostAgressive;
	
	public void fireEvents(Entry entry) {
		// TODO: add push notification to 
		addEventString("Aufpassen!!");
	}
	
	public Player maxSpeed(long speed,Player player)
	{
		if(player!= null && player.getMaxSpeed()<speed){
			player.setMaxSpeed(speed);
			maxSpeed=player;}
		return player;
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
