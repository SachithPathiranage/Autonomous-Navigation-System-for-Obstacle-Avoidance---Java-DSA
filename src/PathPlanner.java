import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

// This class implements the A* algorithm for path planning, using adjacency lists to represent the connectivity of grid cells.
// Includes reading the grid layout and obstacle positions from a text file, as well as implementing the A* algorithm for pathfinding while avoiding obstacles
public class PathPlanner {
	// Node class for A* algorithm
	static class Node { // Represents each cell in the grid
		int row, col;
		double g, h;
		Node parent;
		List<Node> neighbors;
		boolean obstacle; // Flag to indicate if the node represents an obstacle

		public Node(int row, int col, boolean obstacle) {
			this.row = row;
			this.col = col;
			this.g = Double.POSITIVE_INFINITY;
			this.h = 0;
			this.parent = null;
			this.neighbors = new ArrayList<>();
			this.obstacle = obstacle;
		}
	}

	// Implements the A* algorithm for pathfinding, taking into account obstacles
	public static List<Node> findPath(Grid grid, Node start, Node goal) {
		PriorityQueue<Node> openList = new PriorityQueue<>((a, b) -> Double.compare(a.g + a.h, b.g + b.h));
		Set<Node> closedList = new HashSet<>();

		start.g = 0;
		start.h = calculateHeuristic(start, goal);
		// Initializing the priority queue
		openList.add(start);

		while (!openList.isEmpty()) {
			// Exploring nodes in order of increasing total cost
			Node current = openList.poll();

			if (current == goal) {
				return reconstructPath(current);
			}

			closedList.add(current);

			for (Node neighbor : current.neighbors) {
				if (!closedList.contains(neighbor) && !neighbor.obstacle) { // Check if neighbor is not in closed list
																			// and not an obstacle
					double tentativeG = current.g + 1; // Assuming uniform cost for moving between adjacent cells

					if (tentativeG < neighbor.g) {
						neighbor.parent = current;
						neighbor.g = tentativeG;
						neighbor.h = calculateHeuristic(neighbor, goal);

						if (!openList.contains(neighbor)) {
							openList.add(neighbor);
						}
					}
				}
			}
		}

		return null; // No path found
	}

	// Method to compute the heuristic (Manhattan distance) between two nodes
	private static double calculateHeuristic(Node node, Node goal) {
		return Math.abs(node.row - goal.row) + Math.abs(node.col - goal.col);
	}

	// Method to reconstruct the path from the goal node back to the start node.
	private static List<Node> reconstructPath(Node goal) {
		List<Node> path = new ArrayList<>();
		Node current = goal;

		while (current != null) {
			path.add(current);
			current = current.parent;
		}

		Collections.reverse(path);
		return path;
	}
}
