package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.commons.math3.distribution.PoissonDistribution;

public class ProbibalisticGreedyRoomba implements Roomba{

	public static final Zatrix INCREMENTING_FUNCTION;
	protected static final Direction[] dirs = Direction.values();
	
	protected Zatrix expectedValue;
	private List<Direction> valid;
	private Random r;
	
	static {
		INCREMENTING_FUNCTION = new Zatrix(DrivingRange.LENGTH_OF_RANGE,
				DrivingRange.WIDTH_OF_RANGE);
		PoissonDistribution pois = new PoissonDistribution(DrivingRange.BALL_DIST_AVG);
		
		for (int i = 1; i <= INCREMENTING_FUNCTION.getRows(); i++){
			for (int j = 1; j <= INCREMENTING_FUNCTION.getColumns(); j++){
				INCREMENTING_FUNCTION.setElement(i,j, DrivingRange.BALLS_HIT_LAMBDA * 
						(1.0 / DrivingRange.WIDTH_OF_RANGE)* 
						pois.probability(i));
			}
		}
	}
	
	public ProbibalisticGreedyRoomba(){
		expectedValue = new Zatrix(DrivingRange.LENGTH_OF_RANGE,
				DrivingRange.WIDTH_OF_RANGE);
		valid = new ArrayList<Direction>();
		r = new Random();
	}
	
	public Direction getMove(Simulation sim){
		// increment EV
		expectedValue.addZatrix(INCREMENTING_FUNCTION);
		//reset current cell's EV
		expectedValue.setElement(sim.getRow(), sim.getColumn(),
				DrivingRange.PICK_UP_RATE * 
				expectedValue.getElement(sim.getRow(), sim.getColumn()));
		// find max from adjacent cells
		valid.clear();
		
		// find the max
		double max = 0;
		for (int i = 0; i < dirs.length; i++){
			double val = getAdjacentProb(sim, dirs[i]);
			if (val > max){
				max = val;
			}
		}
		
		// find all moves with that max value
		for (int i = 0; i < dirs.length; i++){
			if (getAdjacentProb(sim, dirs[i]) == max){
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
	private double getAdjacentProb(Simulation sim, Direction dir){
		if (!sim.isValidMove(dir)){
			return -1;
		}
		
		if (dir.equals(Direction.UP)){
			return expectedValue.getElement(sim.getRow() - 1, sim.getColumn());
		} else if (dir.equals(Direction.LEFT)){
			return expectedValue.getElement(sim.getRow(), sim.getColumn() - 1);
		} else if (dir.equals(Direction.DOWN)){
			return expectedValue.getElement(sim.getRow() + 1, sim.getColumn());
		} else {
			// dir = right
			return expectedValue.getElement(sim.getRow(), sim.getColumn() + 1);
		}
	}

	
	public static double poisPMF(int lambda, int x){
		if (x > 16) {
			// we can use our own algo
			
			long factorial = 1;
			for (int i = 1; i < x; i++){
				factorial *= i;
			}
			return Math.pow(lambda, x) *
			Math.exp(-1*lambda) / factorial;	
		} else {
			// thanks apache... 
			PoissonDistribution pois = new PoissonDistribution(lambda);
			return pois.probability(x);
		}
	}
	
	
}
