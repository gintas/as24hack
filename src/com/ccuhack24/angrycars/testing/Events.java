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

	private Date parseTimeStamp(String s)
	{	
		Date date = new Date(Integer.parseInt(s.substring(0,3)), 
				Integer.parseInt(s.substring(5,6)), Integer.parseInt(s.substring(8,9)), 
				Integer.parseInt(s.substring(11,12)), Integer.parseInt(s.substring(14,15)), 
				Integer.parseInt(s.substring(17,18)));
		return date;			
	}
	
}
