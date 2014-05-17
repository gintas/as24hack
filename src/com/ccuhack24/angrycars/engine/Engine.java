package com.ccuhack24.angrycars.engine;

import java.util.Random;

public class Engine {
	
	private static final int TIME_TO_LIVE = 2;
	private static final double TIME_TO_SPREAD = 2.0;
	private static final double SPREAD_PROBABILITY = 0.1;
	private static final double DECAY_PROBABILITY = 0.01;
	
	private final int maxY;
	private final int maxX;
	
	Cell[][] grid;
	
	public Engine(int maxY, int maxX) {
		this.maxY = maxY;
		this.maxX = maxX;
		grid = new Cell[maxY][maxX];
		for (int y = 0; y < maxY; y++) {
			for (int x = 0; x < maxX; x++) {
				grid[y][x] = new Cell();
			}
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < maxY; y++) {
			for (int x = 0; x < maxX; x++) {
				String c = grid[y][x].toString();
				sb.append(c);
			}
			sb.append('\n');
		}
		return sb.toString();
	}
	
	public void insertPoint(int x, int y, int team) {
		grid[y][x].team = team;
		grid[y][x].timeToLive = TIME_TO_LIVE;
	}
	
	public void step(double dt) {
		for (int y = 0; y < maxY; y++) {
			for (int x = 0; x < maxX; x++) {
				decay(y, x);
			}
		}

		for (int y = 0; y < maxY; y++) {
			for (int x = 0; x < maxX; x++) {
				if (grid[y][x].team != 0) {
					spreadInfluence(y, x);
				}
			}
		}

		for (int y = 0; y < maxY; y++) {
			for (int x = 0; x < maxX; x++) {
				if (grid[y][x].candidateTeam != 0) {
					convertCell(y, x);
				}
			}
		}
	}
	
	private void decay(int y, int x) {
		Cell cell = grid[y][x];
		if (cell.team != 0) {
			if (cell.timeToLive == 0 || rand.nextDouble() < DECAY_PROBABILITY) {
				cell.team = 0;
				cell.timeToLive = 0;
			} else {
				cell.timeToLive--;
			}
		}
	}

	private Random rand = new Random(123);
	
	private void spreadInfluence(int y, int x) {
      int t = grid[y][x].team;
      for (int dy = -1; dy <= 1; dy++) {
          for (int dx = -1; dx <= 1; dx++) {
        	  int nx = x + dx;
        	  int ny = y + dy;
        	  if (nx < 0 || nx >= maxX || ny < 0 || ny >= maxY) {
        		  continue;
        	  }
        	  if (rand.nextDouble() > SPREAD_PROBABILITY) {
        		  continue;
        	  }
        	  if (grid[ny][nx].team == 0) {
        		grid[ny][nx].candidateTeam = t;
        	  }
          }
      }
	}
	
	private void convertCell(int y, int x) {
		Cell cell = grid[y][x];
		if (cell.candidateTeam != 0) {
			cell.team = cell.candidateTeam;
			cell.timeToLive = TIME_TO_LIVE;
			cell.candidateTeam = 0;
		}
	}

	public Cell[][] getGrid() {
		return grid;
	}
	
	public static class Cell {
		public int team = 0;
		int candidateTeam = 0;
		double timeToSpread = 0;
		double timeToLive = 0;
		
		public String toString() {
			if (team == 0) {
				return ".";
			} else {
				return Integer.toString(team);
			}
		}
	}
}
