package com.ccuhack24.angrycars.testing;

public class Events {

	private TopPlayer maxSpeed;
	private TopPlayer mostAgressive;
	
	public void maxSpeed(long speed,Player player)
	{
		if(maxSpeed.getValue()<speed)
			maxSpeed.setValue(speed);
			maxSpeed.setPlayer(player);
	}
}
