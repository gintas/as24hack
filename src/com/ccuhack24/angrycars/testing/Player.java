package com.ccuhack24.angrycars.testing;

public class Player {

	private final String name;
	private final String id;
	private double maxSpeed;
	private double agressivity;
	private double gruneWelleMileage;
	
	public Player(String name, String id, double maxSpeed, double agressivity) {
		super();
		this.name = name;
		this.id = id;
		this.maxSpeed = maxSpeed;
		this.agressivity = agressivity;
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public double getAgressivity() {
		return agressivity;
	}

	public void setAgressivity(double agressivity) {
		this.agressivity = agressivity;
	}

	public Player(String name, String ID) {
		this.name = name;
		id = ID;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o!= null && o instanceof Player)
			return (((Player)o).getId()).equals(id);
		return false;
	}
	
}
