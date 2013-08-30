package model;

import java.util.Random;

public class RandomRoomba implements Roomba{

	private Random r;
	
	public RandomRoomba(){
		r = new Random();
	}
	
	public Direction getMove(Simulation sim){
		double prob = r.nextDouble();
		if (prob < .25){
			return Direction.DOWN;
		} else if (prob < .5){
			return Direction.UP;
		} else if (prob < .75){
			return Direction.LEFT;
		} else {
			return Direction.RIGHT;
		}
	}
}
