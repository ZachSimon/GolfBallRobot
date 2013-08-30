package model;

import java.util.Random;
import org.apache.commons.math3.distribution.PoissonDistribution;

public class DrivingRange {
	
	public static final int LENGTH_OF_RANGE = 75;
	public static final int WIDTH_OF_RANGE = 40;
	public static final int BALLS_HIT_LAMBDA = 10;
	public static final int BALL_DIST_AVG = 50;
	public static final double PICK_UP_RATE = .8;	
	public static final double[] BALLS_HIT;
	public static final double[] DISTANCE_HIT;
	public static final double[] WIDTH_HIT;
	
	private final Random r = new Random();
	private Matrix data;
	
	static {
		BALLS_HIT = calculatePoisson(BALLS_HIT_LAMBDA,
				4*BALLS_HIT_LAMBDA);
//		DISTANCE_HIT = calculatePoisson(BALL_DIST_AVG, LENGTH_OF_RANGE);
		DISTANCE_HIT = poissonApache(BALL_DIST_AVG, LENGTH_OF_RANGE);
		WIDTH_HIT = uniformCumulative(WIDTH_OF_RANGE);
	}
	
	public DrivingRange(){
		data = new Matrix(LENGTH_OF_RANGE, WIDTH_OF_RANGE);
	}

	private static double[] uniformCumulative(int max){
		double[] result = new double[max];
		for (int i = 0; i < result.length; i++){
			result[i] = (i + 1) * (1.0/max);
		}
		return result;
	}
	
	public int hitBalls(){
		double randy = r.nextDouble();
		int balls = 0;
		while (randy > BALLS_HIT[balls]){
			balls++;
		}
		
		for (int i = 0; i < balls; i ++){
			// place a ball in a random plot
			double distance = r.nextDouble();
			int row = 1;
			while (row < DISTANCE_HIT.length && distance >= DISTANCE_HIT[row - 1]){
				row++;
			}
			double width = r.nextDouble();
			int column = 1;
			while (column < WIDTH_HIT.length && width >= WIDTH_HIT[column  - 1]){
				column++;
			}
			data.incrementElement(row, column);
		}
		return balls;
	}
	
	public int collectBalls(int row, int column){
		int ballsInCell = data.getElement(row, column);
		int ballsCollected = (int)(PICK_UP_RATE * (ballsInCell + 0.0));
		int ballsLeftOver = ballsInCell - ballsCollected;
		data.setElement(row, column, ballsLeftOver);
		return ballsCollected;
	}
	

		
	
	public static double[] calculatePoisson(int lambda, int max){
		// we will truncate the distribution so that it never produces a value
		// more than 4 times the average.
		double[] distribution = new double[max];
		
		// this stores a running factorial, used in the Poisson distribution;
		long factorial = 1;
		
		double sum = 0;
		for (int i = 0; i < distribution.length; i++){
			if (i != 0){
				factorial = factorial*i;
			}
			double prob = (Math.pow(lambda, i)
					*Math.exp(-1*lambda)) / factorial;
			sum+= prob;
			
			if (i != 0){
				prob += distribution[i - 1];
			}
			distribution[i] = prob;
		}
		distribution[distribution.length - 1] = 1;
		return distribution;
	}
	
	
	public static double[] poissonApache(int mean, int max){
		double[] result = new double[max];
		PoissonDistribution pois = new PoissonDistribution(mean);
		for (int i = 0; i < result.length; i++){
			result[i] = pois.cumulativeProbability(i);
		}
		result[result.length - 1] = 1;
		return result;
	}
	
	public int getBalls(int row, int column){
		if (row > data.getRows() || column > data.getColumns()){
			throw new IllegalArgumentException("out of bounds");
		}
		
		return data.getElement(row, column);
	}
	
	public int getRows(){
		return data.getRows();
	}
	
	public int getColumns(){
		return data.getColumns();
	}

}