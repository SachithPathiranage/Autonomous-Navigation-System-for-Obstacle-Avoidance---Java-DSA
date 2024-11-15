import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class PathPlanningCLI {
	// Method to visualize the grid, obstacles, and path in CLI
	public static void visualizeGrid(Grid grid, List<PathPlanner.Node> path) {
		int numRows = grid.getNumRows();
		int numCols = grid.getNumCols();

		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				if (grid.isObstacle(row, col)) {
					System.out.print("# "); // Obstacle
				} else if (path != null && containsNode(path, row, col)) {
					System.out.print("* "); // Path node
				} else {
					System.out.print(". "); // Empty cell
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	// Method to check if a given node is present in the path
	private static boolean containsNode(List<PathPlanner.Node> path, int row, int col) {
		for (PathPlanner.Node node : path) {
			if (node.row == row && node.col == col) {
				return true;
			}
		}
		return false;
	}

	// Method to interactively test path planning algorithm
	public static void runCLI() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Welcome to the Path Planning CLI");
		System.out.println("Please select the input method:");
		System.out.println("1. Generate random grid with obstacles");
		System.out.println("2. Provide input from a text file");
		int inputMethod = scanner.nextInt();

		Grid grid;
		RobotState startState, goalState;

		if (inputMethod == 1) {
			System.out.println("Please enter the grid size (rows columns):");
			int numRows = scanner.nextInt();
			int numCols = scanner.nextInt();

			System.out.println("Please enter the obstacle density (0.0 - 1.0):");
			double obstacleDensity = scanner.nextDouble();

			// Generate random grid with obstacles
			grid = new Grid(numRows, numCols);
			grid.generateRandomObstacles(obstacleDensity);

			System.out.println("Please enter the start position (row column):");
			int startRow = scanner.nextInt();
			int startCol = scanner.nextInt();
			startState = new RobotState(startRow, startCol, Direction.UP);

			System.out.println("Please enter the goal position (row column):");
			int goalRow = scanner.nextInt();
			int goalCol = scanner.nextInt();
			goalState = new RobotState(goalRow, goalCol, Direction.UP);

		} else if (inputMethod == 2) {
			System.out.println("Please enter the path to the input file:");
			String filePath = scanner.next();

			try {
				grid = Grid.readGridFromFile(filePath);
			} catch (FileNotFoundException e) {
				System.out.println("File not found. Exiting...");
				return;
			}

			System.out.println("Please enter the start position (row column):");
			int startRow = scanner.nextInt();
			int startCol = scanner.nextInt();
			startState = new RobotState(startRow, startCol, Direction.UP);

			System.out.println("Please enter the goal position (row column):");
			int goalRow = scanner.nextInt();
			int goalCol = scanner.nextInt();
			goalState = new RobotState(goalRow, goalCol, Direction.UP);
		} else {
			System.out.println("Invalid input method selected. Exiting...");
			return;
		}

		// Test path planning algorithm on the read grid
		PathPlanningSimulation.testAlgorithm(grid, startState, goalState);

		scanner.close();
	}

	public static void main(String[] args) {
		runCLI();
	}
}