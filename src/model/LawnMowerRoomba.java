package model;


/**
 * This Roomba algorithm traces up and down each column in the range,
 * returning to (1,1) once it has collected in every square in the range.
 */
public class LawnMowerRoomba implements Roomba{
	
	private boolean returning;
	
	public LawnMowerRoomba(){
		returning = false;
	}
	
	public Direction getMove(Simulation sim){
		// first, check to see if it's moving back to (1,1) 
		if (returning){
			//check to see if it has returned
			if (sim.getRow() == 1 && sim.getColumn() == 1){
				returning = false;
				return Direction.DOWN;
			} else if (sim.getRow() != 1){
				return Direction.UP;
			} else {
				return Direction.LEFT;
			}
		}
		// check to see if it's in last square in rotation
		if (!sim.isValidMove(Direction.RIGHT) &&
				((DrivingRange.WIDTH_OF_RANGE % 2 == 0 && sim.getRow() == 1) ||
				DrivingRange.WIDTH_OF_RANGE % 2 == 1 && sim.getRow() == DrivingRange.LENGTH_OF_RANGE)){
			returning = true;
			return this.getMove(sim);
		}
		
		if (sim.getColumn() % 2 == 1){
			if (sim.getRow() == DrivingRange.LENGTH_OF_RANGE){
				return Direction.RIGHT;
			}
			return Direction.DOWN;
		}
		if (sim.getColumn() % 2 == 0){
			if (sim.getRow() == 1){
				return Direction.RIGHT;
			}
			return Direction.UP;
		}
		
		return Direction.UP;
		
	}
}
