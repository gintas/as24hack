package com.ccuhack24.angrycars.testing;

public class TopPlayer {
	private Player player;
	private long value;
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
	public long getValue() {
		return value;
	}
	public void setValue(long speed) {
		this.value = speed;
	} 
}
