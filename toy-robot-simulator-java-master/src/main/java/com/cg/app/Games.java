package com.cg.app;

import com.cg.app.exception.ToyException;
import com.cg.app.simulator.*;

public class Games {

	TableBoard squareBoard;
	ToyRobot robot;

	public Games(TableBoard squareBoard, ToyRobot robot) {
		this.squareBoard = squareBoard;
		this.robot = robot;
	}

	/**
	 * Places the robot on the squareBoard  in position X,Y and facing NORTH, SOUTH, EAST or WEST
	 *
	
	 */
	public boolean placeToy(Position position) throws ToyException {

		if (squareBoard == null)
			throw new ToyException("Invalid squareBoard object");

		if (position == null)
			throw new ToyException("Invalid position object");

		if (position.getDirection() == null)
			throw new ToyException("Invalid direction value");

		// validate the position
		if (!squareBoard.isValidPosition(position))
			return false;

		// if position is valid then assign values to fields
		robot.setPosition(position);
		return true;
	}

	/**
	 * Evals and executes a string command.
	 *
	 */
	public String val(String inputString) throws ToyException {
		String[] args = inputString.split(" ");

		// validate command
		Commands command;
		try {
			command = Commands.valueOf(args[0]);
		} catch (IllegalArgumentException e) {
			throw new ToyException("Invalid command");
		}
		if (command == Commands.PLACE && args.length < 2) {
			throw new ToyException("Invalid command");
		}

		// validate PLACE params
		String[] params;
		int x = 0;
		int y = 0;
		Direction commandDirection = null;
		if (command == Commands.PLACE) {
			params = args[1].split(",");
			try {
				x = Integer.parseInt(params[0]);
				y = Integer.parseInt(params[1]);
				commandDirection = Direction.valueOf(params[2]);
			} catch (Exception e) {
				throw new ToyException("Invalid command");
			}
		}

		String output;

		switch (command) {
		case PLACE:
			output = String.valueOf(placeToy(new Position(x, y, commandDirection)));
			break;
		case MOVE:
			Position newPosition = robot.getPosition().getNextPosition();
			if (!squareBoard.isValidPosition(newPosition))
				output = String.valueOf(false);
			else
				output = String.valueOf(robot.move(newPosition));
			break;
		case LEFT:
			output = String.valueOf(robot.rotateLeft());
			break;
		case RIGHT:
			output = String.valueOf(robot.rotateRight());
			break;
		case REPORT:
			output = report();
			break;
		default:
			throw new ToyException("Invalid command");
		}

		return output;
	}

	/**
	 * Returns the X,Y and Direction of the robot
	 */
	public String report() {
		if (robot.getPosition() == null)
			return null;

		return robot.getPosition().getX() + "," + robot.getPosition().getY() + "," + robot.getPosition().getDirection().toString();
	}
}
