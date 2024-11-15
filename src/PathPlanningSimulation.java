import java.util.List;
import java.util.Random;

public class PathPlanningSimulation {
	// This method generates a random grid with obstacles based on the specified
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

	// This method tests the path planning algorithm on a given grid with specified
	// start and goal positions.
	// It converts the grid into nodes, adds neighbors for each node, and then finds
	// a path using the A* algorithm.
	// Finally, it visualizes the grid, obstacles, and the found path.
	public static void testAlgorithm(Grid grid, RobotState startState, RobotState goalState) {
		int numRows = grid.getNumRows();
		int numCols = grid.getNumCols();

		// Ensure startState and goalState are within the bounds of the grid
		if (!grid.isValidPosition(startState.getRow(), startState.getColumn())) {
			System.out.println("Error: Start state is out of bounds.");
			return;
		}

		if (!grid.isValidPosition(goalState.getRow(), goalState.getColumn())) {
			System.out.println("Error: Goal state is out of bounds.");
			return;
		}

		// Convert grid to nodes
		PathPlanner.Node[][] nodes = new PathPlanner.Node[numRows][numCols];
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				nodes[row][col] = new PathPlanner.Node(row, col, grid.isObstacle(row, col));
			}
		}

		// Add neighbors for each node
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				if (!nodes[row][col].obstacle) {
					// Add neighboring nodes (up, down, left, right)
					if (row > 0 && !nodes[row - 1][col].obstacle)
						nodes[row][col].neighbors.add(nodes[row - 1][col]);
					if (row < numRows - 1 && !nodes[row + 1][col].obstacle)
						nodes[row][col].neighbors.add(nodes[row + 1][col]);
					if (col > 0 && !nodes[row][col - 1].obstacle)
						nodes[row][col].neighbors.add(nodes[row][col - 1]);
					if (col < numCols - 1 && !nodes[row][col + 1].obstacle)
						nodes[row][col].neighbors.add(nodes[row][col + 1]);
				}
			}
		}

		// Define start and goal nodes
		PathPlanner.Node startNode = nodes[startState.getRow()][startState.getColumn()];
		PathPlanner.Node goalNode = nodes[goalState.getRow()][goalState.getColumn()];

		// Find path using A* algorithm
		List<PathPlanner.Node> path = PathPlanner.findPath(grid, startNode, goalNode);

		// Print scenario details
		System.out.println("Scenario: Start (" + startState.getRow() + ", " + startState.getColumn() + ") | Goal ("
				+ goalState.getRow() + ", " + goalState.getColumn() + ")");

		// Print or visualize the grid, obstacles, and path
		System.out.println("Grid:");
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				if (grid.isObstacle(row, col)) {
					System.out.print("# ");
				} else if (startState.getRow() == row && startState.getColumn() == col) {
					System.out.print("S ");
				} else if (goalState.getRow() == row && goalState.getColumn() == col) {
					System.out.print("G ");
				} else if (path != null && path.contains(nodes[row][col])) {
					System.out.print("* ");
				} else {
					System.out.print(". ");
				}
			}
			System.out.println();
		}

		// Print path
		if (path != null) {
			System.out.println("Path found:");
			for (PathPlanner.Node node : path) {
				System.out.println("(" + node.row + ", " + node.col + ")");
			}
		} else {
			System.out.println("No path found.");
		}
	}

	// This method visualizes the grid, obstacles, and path by printing them to the
	// console. It prints "#" for obstacles, "*" for path nodes, and "." for empty
	// cells.
	public static void visualizeGrid(Grid grid, List<PathPlanner.Node> path) {
		int numRows = grid.getNumRows();
		int numCols = grid.getNumCols();

		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				if (grid.isObstacle(row, col)) {
					System.out.print("# "); // Obstacle
				} else {
					if (path != null && containsNode(path, row, col)) {
						System.out.print("* "); // Path node
					} else {
						System.out.print(". "); // Empty cell
					}
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

	// This method serves as the entry point of the program.
	// It initializes the number of rows, columns, and obstacle density, generates a
	// random grid, defines start and goal positions,
	// and tests the path planning algorithm on the generated grid.
	public static void main(String[] args) {
		int numRows = 10; // Example number of rows
		int numCols = 10; // Example number of columns
		double obstacleDensity = 0.3; // Example obstacle density (30%)

		// Generate random grid with obstacles
		Grid grid = Grid.generateRandomGrid(numRows, numCols, obstacleDensity);

		// Set start and goal positions
		RobotState startState = new RobotState(0, 0, Direction.RIGHT); // Example start position
		RobotState goalState = new RobotState(numRows - 1, numCols - 1, Direction.LEFT); // Example goal position

		// Test path planning algorithm on the generated grid
		testAlgorithm(grid, startState, goalState);
	}
}