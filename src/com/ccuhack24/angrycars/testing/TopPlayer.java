package com.ccuhack24.angrycars.testing;

public class TopPlayer {
	private Player player;
	private double value;
	public TopPlayer(Player player, int value) {
		this.player = player;
		this.value = value;
	}
	public Player getplayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double speed) {
		this.value = speed;
	} 
}
