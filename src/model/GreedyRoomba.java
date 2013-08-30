package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Greedy Roomba chooses it's moves by finding the adjacent square
 * with the maximum number of balls and moving there. If there is a
 * tie between more than one square, a move is chosen at random from
 * all the ball-maximizing moves.
 * 
 * 
 * @author Zach
 *
 */
public class GreedyRoomba implements Roomba {
	private static final Direction[] dirs = Direction.values();
	private List<Direction> valid;
	private Random r;
	
	public GreedyRoomba(){
		valid = new ArrayList<Direction>();
		r = new Random();
	}
	
	public Direction getMove(Simulation sim){
		valid.clear();
		
		if (sim.getTime() < DrivingRange.BALL_DIST_AVG){
			return Direction.DOWN;
		}
		
		// find the max
		int max = 0;
		for (int i = 0; i < dirs.length; i++){
			int val = getAdjacentBalls(sim, dirs[i]);
			if (val > max){
				max = val;
			}
		}
		
		// find all moves with that max value
		for (int i = 0; i < dirs.length; i++){
			if (getAdjacentBalls(sim, dirs[i]) == max){
				valid.add(dirs[i]);
			}
		}
		
		// choose a random move from those available
		double randy = r.nextDouble();
		for (int i = 0; i < valid.size(); i++){
			if (randy < (i + 1.0) / valid.size()){
				return valid.get(i);
			}
		}
		return valid.get(valid.size() - 1);
		
	}
	
	/**
	 * returns the number of balls in the square in direction dir from
	 * this roomba's current position. If dir is not a valid move, then
	 * this method returns -1.
	 * 
	 * @param sim the simulation that this roomba is running in
	 * @return the number of balls in the square dir from this roomba's
	 * 		location, or -1 if dir is not a valid move.
	 */
	private int getAdjacentBalls(Simulation sim, Direction dir){
		if (!sim.isValidMove(dir)){
			return -1;
		}
		
		if (dir.equals(Direction.UP)){
			return sim.getBalls(sim.getRow() - 1, sim.getColumn());
		} else if (dir.equals(Direction.LEFT)){
			return sim.getBalls(sim.getRow(), sim.getColumn() - 1);
		} else if (dir.equals(Direction.DOWN)){
			return sim.getBalls(sim.getRow() + 1, sim.getColumn());
		} else {
			// dir = right
			return sim.getBalls(sim.getRow(), sim.getColumn() + 1);
		}
	}
	
}
