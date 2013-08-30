package model;

public interface Roomba {

	/**
	 * this is the only method that will get called by the
	 * simulation. At each time step, the simulation will ask your
	 * roomba for it's next move.
	 * 
	 * Note that in the process of deciding on what your next move is,
	 * you can call many methods in the Simulation method to gain information,
	 * such as how many balls you have collected, how many balls your last move
	 * collected, what your roomba's capacity is, the location of your roomba 
	 * on the range, whether you are up against a certain fence, how
	 * many time steps have elapsed, or whether the simulation is returning you
	 * back to the corner to empty your balls. 
	 * 
	 * @return the Direction where this roomba wants to try to move for the next
	 * time step.
	 * 		UP: attempts to move the robot further towards the back fence of the
	 * 			range, so that if the robot moves UP, during the next time step,
	 * 			the Simulation.
	 * 
	 * 
	 */
	public Direction getMove(Simulation s);

}
