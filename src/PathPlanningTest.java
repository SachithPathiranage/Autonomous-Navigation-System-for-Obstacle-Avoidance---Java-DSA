public class PathPlanningTest {
	public static void main(String[] args) {
		testBasicScenario();
		testObstacleAvoidance();
		testComplexGrid();
		testEdgeCases();
		testRandomizedGrids();
		testOptimalPath();
		testUnreachableGoal();
		testPerformance();
	}

	// Test the algorithm on a simple grid with no obstacles
	private static void testBasicScenario() {
		Grid grid = new Grid(
				new boolean[][] { { false, false, false }, { false, false, false }, { false, false, false } });
		RobotState startState = new RobotState(0, 0, Direction.RIGHT);
		RobotState goalState = new RobotState(2, 2, Direction.RIGHT);

		testScenario("Basic Scenario", grid, startState, goalState);
	}

	// Evaluate the algorithm's ability to navigate around obstacles of varying
	// shapes and sizes
	private static void testObstacleAvoidance() {
		Grid grid1 = new Grid(
				new boolean[][] { { false, false, false }, { false, true, false }, { false, false, false } });
		RobotState startState1 = new RobotState(0, 0, Direction.RIGHT);
		RobotState goalState1 = new RobotState(2, 2, Direction.RIGHT);

		Grid grid2 = new Grid(
				new boolean[][] { { false, true, false }, { false, true, false }, { false, false, false } });
		RobotState startState2 = new RobotState(0, 0, Direction.RIGHT);
		RobotState goalState2 = new RobotState(2, 2, Direction.RIGHT);

		testScenario("Obstacle Avoidance (1)", grid1, startState1, goalState1);
		testScenario("Obstacle Avoidance (2)", grid2, startState2, goalState2);
	}

	// Test on a large grid with multiple obstacles and narrow passages
	private static void testComplexGrid() {
		Grid grid = new Grid(new boolean[][] { { false, false, false, true, false }, { true, true, false, true, false },
				{ false, false, false, false, false }, { false, true, true, true, true },
				{ false, false, false, false, false } });
		RobotState startState = new RobotState(0, 0, Direction.RIGHT);
		RobotState goalState = new RobotState(4, 4, Direction.RIGHT);
		testScenario("Complex Grid", grid, startState, goalState);
	}

	// Evaluate the algorithm's behavior in edge cases such as start and goal
	// positions near obstacles or at grid boundaries
	private static void testEdgeCases() {
		// Test scenarios near obstacles or at grid boundaries
		Grid grid1 = new Grid(
				new boolean[][] { { true, false, false }, { false, false, false }, { false, false, false } });
		RobotState startState1 = new RobotState(0, 0, Direction.RIGHT);
		RobotState goalState1 = new RobotState(2, 2, Direction.RIGHT);

		Grid grid2 = new Grid(
				new boolean[][] { { false, false, true }, { false, false, false }, { false, false, false } });
		RobotState startState2 = new RobotState(0, 0, Direction.RIGHT);
		RobotState goalState2 = new RobotState(2, 2, Direction.RIGHT);

		testScenario("Edge Case (1)", grid1, startState1, goalState1);
		testScenario("Edge Case (2)", grid2, startState2, goalState2);
	}

	// Test on randomly generated grids with varying obstacle densities
	private static void testRandomizedGrids() {
		for (int i = 0; i < 3; i++) {
			int numRows = 5 + i;
			int numCols = 5 + i;
			double obstacleDensity = 0.3 + (i * 0.1);

			Grid grid = Grid.generateRandomGrid(numRows, numCols, obstacleDensity);
			RobotState startState = new RobotState(0, 0, Direction.RIGHT);
			RobotState goalState = new RobotState(numRows - 1, numCols - 1, Direction.RIGHT);

			testScenario("Randomized Grid (Density: " + obstacleDensity + ")", grid, startState, goalState);
		}
	}

	// Verify that the algorithm consistently finds the optimal path between start
	// and goal nodes
	private static void testOptimalPath() {
		Grid grid = new Grid(
				new boolean[][] { { false, false, false }, { false, true, false }, { false, false, false } });
		RobotState startState = new RobotState(0, 0, Direction.RIGHT);
		RobotState goalState = new RobotState(2, 2, Direction.RIGHT);
		testScenario("Optimal Path", grid, startState, goalState);
	}

	// Test the algorithm's response when the goal node is unreachable due to
	// obstacles
	private static void testUnreachableGoal() {
		Grid grid = new Grid(
				new boolean[][] { { false, false, false }, { true, true, true }, { false, false, false } });
		RobotState startState = new RobotState(0, 0, Direction.RIGHT);
		RobotState goalState = new RobotState(2, 2, Direction.RIGHT);
		testScenario("Unreachable Goal", grid, startState, goalState);
	}

	// Measure the algorithm's execution time on grids of increasing sizes
	private static void testPerformance() {
		int[] gridSize = new int[] { 10, 10 }; // Initial grid size

		for (int i = 0; i < 5; i++) {
			Grid grid = Grid.generateRandomGrid(gridSize[0], gridSize[1], 0.3);
			RobotState startState = new RobotState(0, 0, Direction.RIGHT);
			RobotState goalState = new RobotState(gridSize[0] - 1, gridSize[1] - 1, Direction.RIGHT);

			long startTime = System.currentTimeMillis();
			PathPlanningSimulation.testAlgorithm(grid, startState, goalState);
			long endTime = System.currentTimeMillis();
			System.out.println("Performance Test (Grid Size: " + gridSize[0] + "x" + gridSize[1] + "): "
					+ (endTime - startTime) + " ms");

			// Increase grid size for next iteration
			gridSize[0] += 5;
			gridSize[1] += 5;
		}
	}

	private static void testScenario(String scenarioName, Grid grid, RobotState startState, RobotState goalState) {
		System.out.println("Testing " + scenarioName + ":");
		PathPlanningSimulation.testAlgorithm(grid, startState, goalState);
		System.out.println("-------------------------------------");
		System.out.println();
	}
}
