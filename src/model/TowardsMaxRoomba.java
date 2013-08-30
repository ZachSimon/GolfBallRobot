package model;

public class TowardsMaxRoomba extends ProbibalisticGreedyRoomba{
	// doens't actually extend, i just wanna reuse code
	
	public Direction getMove(Simulation sim){
		//we are incrementing super's data here
		super.getMove(sim);
		
		int maxI = 1;
		int maxJ = 1;
		for (int row = 1; row <= expectedValue.getRows(); row++){
			for (int col = 1; col <= expectedValue.getColumns(); col++){
				if (expectedValue.getElement(row, col) > expectedValue.getElement(maxI, maxJ)){
					maxI = row;
					maxJ = col;
				}
			}
		}
		
		// we can also change the order in which these decisions are made or tiebreak randomly
		if (maxI > sim.getRow()){
			return Direction.DOWN;
		} else if (maxI < sim.getRow()){
			return Direction.UP;
		} else if (maxJ > sim.getColumn()){
			return Direction.RIGHT;
		} else {
			return Direction.LEFT;
		}
	
	}
	
	
}
