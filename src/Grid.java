import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

// This Grid class allows to create a grid of a specified size and mark cells as obstacles.
public class Grid {
	private int numRows;
	private int numCols;
	private boolean[][] grid; // Represents the grid cells, true for occupied by obstacle, false for empty

	// Constructor to initialize the grid with given dimensions
	public Grid(int numRows, int numCols) {
		this.numRows = numRows;
		this.numCols = numCols;
		this.grid = new boolean[numRows][numCols]; // By default, all cells are initialized as empty
	}

	// Constructor that accepts a boolean 2D array representing the grid
	public Grid(boolean[][] grid) {
		this.grid = grid;
		this.numRows = grid.length;
		this.numCols = grid[0].length;
	}

	// Getters
	public int getNumRows() {
		return numRows;
	}

	public int getNumCols() {
		return numCols;
	}

	// Setters
	public void setGrid(boolean[][] grid) {
		this.grid = grid;
	}

	// Method to set an obstacle at the specified position
	public void setObstacle(int row, int col) {
		if (isValidPosition(row, col)) {
			grid[row][col] = true;
		} else {
			System.out.println("Invalid position for setting obstacle.");
		}
	}

	// Method to check an obstacle at the specified position
	public boolean isObstacle(int row, int col) {
		return grid[row][col];
	}

	// Method to generate random obstacles based on obstacle density
	public void generateRandomObstacles(double obstacleDensity) {
		Random random = new Random();
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				grid[row][col] = random.nextDouble() < obstacleDensity;
			}
		}
	}

	// Generate a random grid with obstacles based on the specified
	// number of rows, columns, and obstacle density.
	public static Grid generateRandomGrid(int numRows, int numCols, double obstacleDensity) {
		Random random = new Random();
		Grid grid = new Grid(numRows, numCols);

		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				if (random.nextDouble() < obstacleDensity) {
					grid.setObstacle(row, col); // Place obstacle
				}
			}
		}

		return grid;
	}

	// Method to check if a cell is empty (not occupied by an obstacle)
	public boolean isCellEmpty(int row, int col) {
		if (isValidPosition(row, col)) {
			return !grid[row][col];
		} else {
			System.out.println("Invalid position for checking cell state.");
			return false;
		}
	}

	// Helper method to validate if a position is within the grid boundaries
	public boolean isValidPosition(int row, int col) {
		return row >= 0 && row < numRows && col >= 0 && col < numCols;
	}

	// Method to read grid locations from a file
	public static Grid readGridFromFile(String filePath) throws FileNotFoundException {
		File file = new File(filePath);
		Scanner scanner = new Scanner(file);
		int numRows = scanner.nextInt();
		int numCols = scanner.nextInt();
		Grid grid = new Grid(numRows, numCols);
		while (scanner.hasNext()) {
			int row = scanner.nextInt();
			int col = scanner.nextInt();
			grid.grid[row][col] = true;
		}
		scanner.close();
		return grid;
	}

}
