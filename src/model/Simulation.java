package model;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * <b>Simulation</b> tests various algorithms for golf-ball collecting robots
 * to see which is the most effective.
 * <p>
 * To try your own strategy, implement the Roomba interface and add your class
 * to the list of roombas to be simulated.
 * 
 * @see doc/about.txt
 *
 */

public class Simulation {
	
	// If you write a new Roomba class, add it to this list for it to be
	// included in your simulation
	public static final Class[] roombas = {	TowardsMaxRoomba.class,
											ProbibalisticGreedyRoomba.class,
											RandomRoomba.class,
											LawnMowerRoomba.class};
	
	
	// number of time steps in each simulation. 5760 is recommended for analysis
	public static final int TIME_STEPS = 1000;
	// The number of simulations to run this roomba in.
	// 500 simulations recommended for analysis
	public static final int SIMS_TO_RUN = 1;

	// true causes simulations to run with a gui, while false runs
	// the simulation without a gui. If you're running the sim w/ a gui,
	// set the number of simulations low. 
	public static final boolean GUI_SWITCH = true;
	
	private int ballsCollected;
	private int ballsHit;
	private Roomba model;
	private DrivingRange range;
	private int row;
	private int column;
	private int time;
	
	
	public static void main(String[] args){
		Map<Class<Roomba>, Set<Simulation>> simulations =
				new HashMap<Class<Roomba>, Set<Simulation>>();
		for (Class<Roomba> c: roombas){
			simulations.put(c, new HashSet<Simulation>());
			for (int i = 0; i < SIMS_TO_RUN; i++){
				try {
					simulations.get(c).add(new Simulation(c.newInstance()));
				} catch (InstantiationException e) {
					System.err.println("Cannot instantiate roomba:" + c.toString());
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					System.err.println("Cannot instantiate roomba:" + c.toString());
					e.printStackTrace();
				}	
			}
		}
		
		RangeGUI2 gui = null;
		if (GUI_SWITCH){
			// instantiate graphics
			JFrame window = new JFrame("Golfball Roomba Simulation");
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setMinimumSize(new Dimension(1200, 700));
			window.setPreferredSize(new Dimension(1200, 700));
			gui = new RangeGUI2(new Simulation(new RandomRoomba()));
			gui.setSize(new Dimension(1200,700));
			gui.setSize(700, 700);
			window.setContentPane(gui);
			gui.setVisible(true);
			window.setVisible(true);
		}
		
		for (Class<Roomba> roomba: simulations.keySet()){
			for (Simulation sim: simulations.get(roomba)){
				if (gui != null){
					gui.changeSimulation(sim);
				}
				sim.run(gui);
			}
		}
		
		Simulation.analyze(simulations);
	}
	
	
	/**
	 * Constructs a new Simulation with the given Roomba as it's ball collector
	 * 
	 * @param model the Roomba that will collect balls in this simulation
	 */
	public Simulation(Roomba model){
		this.model = model;
		this.range = new DrivingRange();
		ballsCollected = 0;
		ballsHit = 0;
		row = 1;
		column = 1;
		time = 0;
	}
	
	/**
	 * returns the Roomba that this simulation is currently being run on
	 * 
	 * 
	 * @return the Roomba that this simulation is modeling
	 */
	public Roomba getCurrentRoomba(){
		return this.model; 
	}
	
	/**
	 * Runs this simulation for TIME_STEPS timesteps, and if there is
	 * a gui for this simulation, updates the gui at each timestep.
	 * 
	 * @param gui: If this simulation is being run with a GUI, then this
	 * 		is the JComponent that is repainted at each timestep. If there
	 * 		is no GUI being used, then gui can be null.
	 */
	private void run(JComponent gui){
//		File results = new File("moves3.txt");
//		PrintStream output = null;
//		try {
//			output = new PrintStream(results);
//		} catch (FileNotFoundException e) {
//			System.err.println("output file cannot be created");
//		}
		for (int i = 0; i < TIME_STEPS; i++){
			ballsHit += range.hitBalls();
			time++;
			Direction move = model.getMove(this);
			while (!isValidMove(move)){
				move = model.getMove(this);
			}
			moveRoomba(move);
			//output.println(move.toString());
			ballsCollected += range.collectBalls(row, column);
			if (gui != null){
				
				gui.repaint();
				try {
					Thread.sleep((long) 50);
				} catch (InterruptedException e) {
					System.err.println("Whoops!");
					e.printStackTrace();
				}
			}
		}
	}

	
	/**
	 * If Direction dir is a valid move, this method moves the roomba
	 * in that direction. 
	 * 
	 * @param dir the direction that this roomba will be moved if it is a
	 * 		valid  move.
	 */
	private void moveRoomba(Direction dir){
		if (isValidMove(dir)){
			if (dir.equals(Direction.UP)){
				row--;
			} else if (dir.equals(Direction.DOWN)){
				row++;
			} else if (dir.equals(Direction.LEFT)){
				column--;
			} else if (dir.equals(Direction.RIGHT)){
				column++;
			}
		}
	}
	
	/**
	 * This method returns true if there is another square in the direction
	 * passed in, i.e. the roomba is not up against a fence of the driving range
	 * in that direction.
	 * 
	 * Note that if your roomba returns a direction that is not "valid" according
	 * to this method, no error will occur, as you will simply stay in the square
	 * you were in previously and pick up the balls in that square.
	 * 
	 * Example: At the initial timestep, your roomba will be at (1,1), thus:
	 * 
	 *  	isValidMove(UP) and isValidMove(LEFT) will return false.
	 *  	
	 *  	isValidMove(DOWN) and isValidMove(RIGHT) will return true.
	 * 
	 * @param dir the Direction to be considered
	 * @return true if the roomba is able to move in direction dir for the next
	 * 		timestep, and false if the roomba is up against a fence in direction dir.
	 */
	public boolean isValidMove(Direction dir){
		if (dir.equals(Direction.UP)){
			return row > 1;
		} else if (dir.equals(Direction.DOWN)){
			return row < DrivingRange.LENGTH_OF_RANGE;
		} else if (dir.equals(Direction.LEFT)){
			return column > 1;
		} else {
			return column < DrivingRange.WIDTH_OF_RANGE;
		}
	}

	/**
	 * this method takes in simulations that have already been run and analyzes
	 * them. The results are saved in a data file containing the raw data for
	 * each simulation that was passed in.
	 * 
	 * 
	 * @param sims the simulations that are to be analyzed
	 */
	private static void analyze(Map<Class<Roomba>, Set<Simulation>> sims){
		// we can call the same methods on Simulation as
		// we do in the getMove() method, because it's a
		// reference to the exact same object,
		// so here is where we can get access to the raw
		// data, and analyze each simulation, or all of the
		// simulations at the same time.
		
		File results = new File("out.txt");
		PrintStream output = null;
		try {
			output = new PrintStream(results);
		} catch (FileNotFoundException e) {
			System.err.println("output file cannot be created");
		}
		for (Class<Roomba> roomba: sims.keySet()){
			int totalBallsHitOut = 0;
			int totalBallsCollected = 0;
			List<Integer> ballsHit = new ArrayList<Integer>();
			List<Integer> ballsCollected = new ArrayList<Integer>();
			for (Simulation sim: sims.get(roomba)){
				totalBallsHitOut += sim.getBallsHit();
				totalBallsCollected += sim.getBallsCollected();
				ballsHit.add(sim.getBallsHit());
				ballsCollected.add(sim.getBallsCollected());
			}
			output.println("For Strategy: " + roomba.toString());
			output.println("\tTotal balls hit out: " + totalBallsHitOut);
			output.println("\tTotal balls Collected: " + totalBallsCollected);
			
			
			String hits = "";
			hits += ballsHit.get(0);
			for (int i = 1; i < ballsHit.size(); i++){
				hits += ", " + ballsHit.get(i);
			}
			output.println("\tBalls Hit Out: ");
			output.println(hits);
			
			String collects = "";
			collects += ballsCollected.get(0);
			for (int i = 1; i < ballsCollected.size(); i++){
				collects += ", " + ballsCollected.get(i);
			}
			output.println("\tBalls Collected: ");
			output.println(collects);
			
			
			output.println();
		}
	}
	
	
	/**
	 * returns the row that the roomba is currently in. The roomba
	 * starts in row 1, which is the upper-most row of the range.
	 * 
	 * @return the row that the roomba is currently in.
	 */
	public int getRow(){
		return row;
	}
	
	/**
	 * returns the column that the roomba is currently in. The roomba
	 * starts in column 1, which is the left-most column of the range.
	 * 
	 * @return the column that the roomba is currenly in.
	 */
	public int getColumn(){
		return column;
	}
	
	
	/**
	 * returns the total number of balls that the roomba has collected in this
	 * simulation
	 * 
	 * @return the number of balls this roomba has collected.
	 */
	public int getBallsCollected(){
		return ballsCollected;
	}
	
	/**
	 * returns the number of balls that have been hit out in this simulation 
	 * 
	 * @return the number of balls that have been hit out in this simulation.
	 */
	public int getBallsHit(){
		return ballsHit;
	}

	/**
	 * unless you're implementing the greedy algorithm, don't use this method
	 * in your roomba algo, since it's pretty much just cheating.
	 * 
	 * @param row the row of the cell you are querying
	 * @param column the column of the cell you are querying
	 * @return the number of balls that are in this square
	 */
	public int getBalls(int row, int column){
		if (row > range.getRows() || column > range.getColumns()){
			throw new IllegalArgumentException("Out of Bounds");
		}
		return range.getBalls(row, column);
	}
	
	/**
	 * returns the timestep that this simulation is on.
	 * 
	 * @return the timestep this simulation is on.
	 */
	public int getTime(){
		return time;
	}
}