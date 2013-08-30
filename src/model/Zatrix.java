package model;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <b>Zatrix</b> stores Doubles in a Zatrix.
 * 
 * note: Zatrix objects use 1-based indexing
 */
public class Zatrix {
	private List<List<Double>> data;
	
	private final int rows;
	private final int columns;
	
	/**
	 * constructs a new Zatrix object
	 * @param rows: the number of rows in this Zatrix
	 * @param columns: the number of columns in this Zatrix
	 */
	public Zatrix(int rows, int columns){
		if (rows < 0 || columns < 0){
			throw new IllegalArgumentException("rows and columns cannot be <0");
		}
		this.rows = rows;
		this.columns = columns;
		data = new ArrayList<List<Double>>();
		for (int i = 0; i < rows; i++){
			List<Double> row = new ArrayList<Double>();
			for (int j = 0; j < columns; j++){
				row.add(0.0);
			}
			data.add(row);
		}
	}
	
	/**
	 * returns the data stored in the element at row row and column column
	 * 
	 * @param row: the row of the element to be returned
	 * @param column: the column of the element to be returned
	 * @return the data stored at this element
	 */
	public double getElement(int row, int column){
		if (row > data.size() || column > data.get(row - 1).size()
				|| row < 1 || column < 1){
			System.out.println("Tried to access(" + row + ", " + column + ")");
			System.out.println("Zatrix Dims: " + rows + " " + columns);
			System.out.println("Data.size() = " + data.size());
			throw new IllegalArgumentException("out of bound indicies");
		}
		
		return data.get(row - 1).get(column - 1);
	}
	
	public void incrementElement(int row, int column){
		if (row > data.size() || column > data.get(row - 1).size()
				|| row < 1 || column < 1){
			throw new IllegalArgumentException("out of bound indicies");
		}
		data.get(row - 1).set(column - 1, data.get(row - 1).get(column - 1).doubleValue() + 1.0);
	}
	
	public void setElement(int row, int column, double value){
		if (row > data.size() || column > data.get(row - 1).size()
				|| row < 1 || column < 1){
			throw new IllegalArgumentException("out of bound indicies");
		}
		data.get(row - 1).set(column - 1, value);
	}
	
	
	public void addZatrix(Zatrix z){
		if (z.getColumns() != getColumns() || z.getRows() != getRows()){
			throw new IllegalArgumentException("Zatrix dimensions must agree");
		}
		for (int i = 1; i <= getRows(); i++){
			for (int j = 1; j <= getColumns(); j++){
				setElement(i, j, this.getElement(i,j) + z.getElement(i,j));
			}
		}
	}
	
	public int oneNorm(){
		int norm = 0;
		for (List<Double> l: data){
			for (Double i: l){
				norm += i;
			}
		}
		return norm;
	}
	
	public int getRows(){
		return this.rows;
	}
	
	public int getColumns(){
		return this.columns;
	}
	
	public void print(PrintStream out){
		for (int i = 1; i <= rows; i++){
			for (int j = 1; j < columns; j++){
				out.print("  " + getElement(i, j));
			}
			out.println();
		}
	}
}
