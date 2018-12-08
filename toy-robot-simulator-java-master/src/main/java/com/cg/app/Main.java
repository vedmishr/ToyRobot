package com.cg.app;

import com.cg.app.exception.ToyException;
import com.cg.app.simulator.SquareBoard;
import com.cg.app.simulator.ToyRobot;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		SquareBoard squareBoard = new SquareBoard(4, 4);
		ToyRobot robot = new ToyRobot();
		Games game = new Games(squareBoard, robot);

		System.out.println("Toy Robot Simulator");
		System.out.println("Enter a command, Valid commands are:");
		System.out.println("PLACE X,Y,NORTH/SOUTH/EAST/WEST, MOVE, LEFT, RIGHT, REPORT or EXIT");
		Scanner scanner=new Scanner(System.in);

		boolean keepRunning = true;
		while (keepRunning) {
			String inputString = scanner.nextLine();
			if ("EXIT".equals(inputString)) {
				keepRunning = false;
			} else {
				try {
					String outputVal = game.val(inputString);
					if("REPORT".equals(inputString))
						System.out.println(outputVal);
				} catch (ToyException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
}