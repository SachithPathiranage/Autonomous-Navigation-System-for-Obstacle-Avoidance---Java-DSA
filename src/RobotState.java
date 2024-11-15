// This RobotState class allows to create instances representing the state of the robot at any given time. 
// Can set and retrieve its position and orientation as needed.

// row and column represent the current position of the robot on the grid.
// orientation represents the direction the robot is facing (e.g., north, south, east, west).
public class RobotState {
	private int row; // Current row position of the robot
	private int column; // Current column position of the robot
	private Direction orientation; // Current orientation of the robot

	// Constructor to initialize the robot state with given position and orientation
	public RobotState(int row, int column, Direction orientation) {
		this.row = row;
		this.column = column;
		this.orientation = orientation;
	}

	// Getters
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public Direction getOrientation() {
		return orientation;
	}
}
