package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class RangeGUI2 extends JComponent{

	private Simulation currentSim;
	
	public static final int SCALE_FACTOR = 15;
	
	public RangeGUI2(Simulation firstSim){
		this.currentSim = firstSim;
	}
	
	public void changeSimulation(Simulation nextSim){
		this.currentSim = nextSim;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		// draw Rows
		for (int i = 0; i <= DrivingRange.WIDTH_OF_RANGE; i++){
			g2d.drawLine(0, SCALE_FACTOR * i,
					SCALE_FACTOR * DrivingRange.LENGTH_OF_RANGE, SCALE_FACTOR * i);
		}
		
		//draw Cols
		for (int i = 0; i <= DrivingRange.LENGTH_OF_RANGE; i++){
			g2d.drawLine(SCALE_FACTOR * i, 0, SCALE_FACTOR * i, DrivingRange.WIDTH_OF_RANGE * SCALE_FACTOR);
		}
		
		// Draw in Robot
		g2d.setColor(Color.RED);
		g2d.fillRect(
				SCALE_FACTOR*(currentSim.getRow() - 1), 
				SCALE_FACTOR*(DrivingRange.WIDTH_OF_RANGE - currentSim.getColumn()),
				SCALE_FACTOR, SCALE_FACTOR);
		
		/* we want to take the ball in row i, column j and...
		 * 
		 * we want the row to go to y, and the column to go to x
		 * 
		 * we want to the row to go directly related to y, and column to go inversely
		 * 
		 * so where x and y are the leading edges of the graphics, and were drawing 1 px,
		 * 
		 * then (x,y) = (width - i, j - 1)
		 */
		
		
		
		
		
		// write in number of balls
		for (int i = 0; i < DrivingRange.LENGTH_OF_RANGE; i++){
			for (int j = 0; j < DrivingRange.WIDTH_OF_RANGE; j++){
//				g2d.drawString("" + currentSim.getBalls(i + 1, j + 1),
//							(DrivingRange.WIDTH_OF_RANGE - i)* SCALE_FACTOR, j * SCALE_FACTOR);
//							// note that the width - i and 
				g2d.drawString(currentSim.getBalls(i + 1, j + 1) + "", SCALE_FACTOR * i, SCALE_FACTOR * (DrivingRange.WIDTH_OF_RANGE - j));
			}
		}
		
		g2d.drawString("Balls Collected: " +
				currentSim.getBallsCollected(), 0,
				SCALE_FACTOR*DrivingRange.WIDTH_OF_RANGE + 10);
		g2d.drawString(currentSim.getCurrentRoomba().toString(),
				0, SCALE_FACTOR * DrivingRange.WIDTH_OF_RANGE + 20);
				
	}
}
