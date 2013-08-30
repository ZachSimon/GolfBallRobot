package model;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <b>Matrix</b> stores integers in a matrix.
 * 
 * note: Matrix objects use 1-based indexing
 */
public class Matrix {
	private List<List<Integer>> data;
	
	private final int rows;
	private final int columns;
	
	/**
	 * constructs a new Matrix object
	 * @param rows: the number of rows in this matrix
	 * @param columns: the number of columns in this matrix
	 */
	public Matrix(int rows, int columns){
		if (rows < 0 || columns < 0){
			throw new IllegalArgumentException("rows and columns cannot be <0");
		}
		this.rows = rows;
		this.columns = columns;
		data = new ArrayList<List<Integer>>();
		for (int i = 0; i < rows; i++){
			List<Integer> row = new ArrayList<Integer>();
			for (int j = 0; j < columns; j++){
				row.add(0);
			}
			data.add(row);
		}
	}
	
	public void print(PrintStream out){
		for (int i = 1; i <= rows; i++){
			for (int j = 1; j < columns; j++){
				out.print("  " + getElement(i, j));
			}
			out.println();
		}
	}
	
	public void addMatrix(Matrix z){
		if (z.getColumns() != getColumns() || z.getRows() != getRows()){
			throw new IllegalArgumentException("Matrix dimensions must agree");
		}
		for (int i = 1; i <= getRows(); i++){
			for (int j = 1; j <= getColumns(); j++){
				setElement(i, j, this.getElement(i,j) + z.getElement(i,j));
			}
		}
	}
	
	/**
	 * returns the data stored in the element at row row and column column
	 * 
	 * @param row: the row of the element to be returned
	 * @param column: the column of the element to be returned
	 * @return the data stored at this element
	 */
	public int getElement(int row, int column){
		if (row > data.size() || column > data.get(row - 1).size()
				|| row < 1 || column < 1){
			throw new IllegalArgumentException("out of bound indicies");
		}
		return data.get(row - 1).get(column - 1);
	}
	
	public void incrementElement(int row, int column){
		if (row > data.size() || column > data.get(row - 1).size()
				|| row < 1 || column < 1){
			throw new IllegalArgumentException("out of bound indicies");
		}
		data.get(row - 1).set(column - 1, data.get(row - 1).get(column - 1).intValue() + 1);
	}
	
	public void setElement(int row, int column, int value){
		if (row > data.size() || column > data.get(row - 1).size()
				|| row < 1 || column < 1){
			throw new IllegalArgumentException("out of bound indicies");
		}
		data.get(row - 1).set(column - 1, value);
	}
	
	public int oneNorm(){
		int norm = 0;
		for (List<Integer> l: data){
			for (Integer i: l){
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
	
}
