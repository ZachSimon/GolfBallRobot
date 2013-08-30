package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlmostRandomRoomba implements Roomba{

	private Random r;
	private Direction previous;
	
	public AlmostRandomRoomba(){
		r = new Random();
		previous = Direction.UP;
	}
	
	public Direction getMove(Simulation sim){
		double prob = r.nextDouble();
		List<Direction> possibilities = getPossibleMoves();
		if (prob < (1.0 / 3)){
			previous = possibilities.get(0);
			return possibilities.get(0);
		} else if (prob < (2.0 / 3)){
			previous = possibilities.get(1);
			return possibilities.get(1);
		} else {
			previous = possibilities.get(2);
			return possibilities.get(2);
		}

	}
	
	/**
	 * returns an array containing all directions except this.previous
	 * 
	 * @return an array containing possible moves for this roomba.
	 */
	public List<Direction> getPossibleMoves(){
		Direction[] allValues = Direction.values();
		List<Direction> values = new ArrayList<Direction>();
		for (int i = 0; i < allValues.length; i++){
			if (previous != allValues[i]){
				values.add(allValues[i]);
			}
		}
		return values;
	}
	
	
}
