package com.ccuhack24.angrycars.testing;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

public class Events {

	private Player maxSpeed;
	private Player mostAgressive;
	
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
