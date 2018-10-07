
//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           (Cheese Eater)
// Files:           (a list of all source files used by that program)
// Course:          (300, Spring, 2018)
//
// Author:          (Aniya Allen)
// Email:           (aallen27@wisc.edu)
// Lecturer's Name: (Gary Dahl)
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    (Aramide Dada)
// Partner Email:   (adada@wisc.edu)
// Lecturer's Name: (Mouna Kacem)
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   _X_ Write-up states that pair programming is allowed for this assignment.
//   _X__ We have both read and understand the course Pair Programming Policy.
//   _X__ We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully 
// acknowledge and credit those sources of help here.  Instructors and TAs do 
// not need to be credited here, but tutors, friends, relatives, room mates 
// strangers, etc do.  If you received no outside help from either type of 
// source, then please explicitly indicate NONE.
//
// Persons:         (identify each person and describe their help in detail)
// Online Sources:  (identify each URL and describe their assistance in detail)
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
import java.util.Random;
import java.util.Scanner;

public class Main {
	public static final int ROOM_HEIGHT = 10;
	public static final int ROOM_WIDTH = 20;
	public static final char WALL = '#';
	public static final char CHEESE = '%';
	public static final char MOUSE = '@';
	public static final char EMPTY_ROOM = '.';

	public static void main(String[] args) {

		int numberOfWalls = 20;
		Random randGen = new Random();

		char[][] room = new char[ROOM_HEIGHT][ROOM_WIDTH];

		// After declaring the array, call placeWalls to initialize the
		// contents
		int[][] cheesePositions = new int[10][2];

		// position of mouse
		// create mouse position here and make sure it is not where there is a
		// cheese or wall
		int mouseX = randGen.nextInt(room[0].length);
		int mouseY = randGen.nextInt(room.length);
		char move = ' ';
		Scanner scnr = new Scanner(System.in);
		int[] newLocations;

		System.out.println("Welcome to the Cheese Eater simulation.");
		System.out.println("=======================================");

		int counter = 1;
		int numTimes = 0;

		placeWalls(room, numberOfWalls, randGen);
		placeCheeses(cheesePositions, room, randGen);
		System.out.println("Enter the number of steps for this simulation to run: ");
		numTimes = scnr.nextInt();
		scnr.nextLine();
		printRoom(room, cheesePositions, mouseX, mouseY);

		do {

			System.out.println("Enter the next step you'd like the mouse to take (WASD): ");
			move = scnr.nextLine().charAt(0);
			newLocations = moveMouse(mouseX, mouseY, room, move);
			// Updates the mouse's location
			room[mouseY][mouseX] = EMPTY_ROOM;
			mouseX = newLocations[0];
			mouseY = newLocations[1];

			tryToEatCheese(mouseX, mouseY, cheesePositions);
			printRoom(room, cheesePositions, mouseX, mouseY);
			counter++;
		} while (counter <= numTimes);

		System.out.println("==================================================");
		System.out.println("Thank you for running the Cheese Eater simulation.");

	}

	/*
	 * This purpose of this method is to randomly place the walls in the room.
	 * 
	 * 
	 */
	public static void placeWalls(char[][] room, int numberOfWalls, Random randGen) {
		int count = 0;
		int row = 0;
		int col = 0;
		// To initialize the array with all periods
		for (row = 0; row < room.length; row++) {
			for (col = 0; col < room[row].length; col++) {
				room[row][col] = '.';
			}
		}
		// To replace the . with randomly positioned #s
		while (count < numberOfWalls) {
			row = randGen.nextInt(room.length);
			col = randGen.nextInt(room[0].length);
			if (room[row][col] == EMPTY_ROOM) {
				room[row][col] = WALL;
				count++;
			}
		}
	}

	/*
	 * This purpose of this method is to randomly place the cheeses in the room.
	 * 
	 * 
	 */
	public static void placeCheeses(int[][] cheesePositions, char[][] room, Random randGen) {
		int row = 0;
		int col = 0;
		int count = 0;

		while (count < cheesePositions.length) {
			row = randGen.nextInt(room.length);
			col = randGen.nextInt(room[0].length);

			for (int i = 0; i < count; i++) {
				if (cheesePositions[i][0] == col && cheesePositions[i][1] == row) {
					col = randGen.nextInt(room[0].length);
					row = randGen.nextInt(room.length);
					i = -1;
				}
			}

			if (room[row][col] == '.') {
				cheesePositions[count][0] = col;
				cheesePositions[count][1] = row;
				count++;
			} else {
				continue;
			}
		}

	}

	/*
	 * This purpose of this method is to print out the contents in the room,
	 * including the cheese and mouse.
	 * 
	 * 
	 */
	public static void printRoom(char[][] room, int[][] cheesePositions, int mouseX, int mouseY) {
		// In Print room method, we go through each location and print the
		// appropriate character for each position
		for (int i = 0; i < room.length; i++) {
			for (int j = 0; j < room[i].length; j++) {

				for (int k = 0; k < cheesePositions.length; k++) {
					if (cheesePositions[k][0] == j && cheesePositions[k][1] == i) {
						room[i][j] = CHEESE;

					}

				}
				// Places mouse at randomly chosen position
				if (i == mouseY && j == mouseX) {
					room[i][j] = MOUSE;
				}

			}
		}

		for (int i = 0; i < room.length; i++) {
			for (int j = 0; j < room[i].length; j++) {
				System.out.print(room[i][j]);
			}
			System.out.println();
		}
	}

	/*
	 * This purpose of this method is to move the mouse through the room.
	 * 
	 * 
	 */
	public static int[] moveMouse(int mouseX, int mouseY, char[][] room, char move) {
		// Stores new location
		int[] newLocation = new int[2];

		// Move up
		if (move == 'W' || move == 'w') {
			// If the user pick a location outside of the room
			if (mouseY - 1 < 0 || mouseY - 1 > room.length - 1 || mouseX < 0 || mouseX > room[0].length - 1) {
				System.out.println("WARNING: Mouse cannot move outside the room.");
				return null;
			}
			// If the user picks a location that has a wall
			if (room[mouseY - 1][mouseX] == WALL) {
				System.out.println("WARNING: Mouse cannot move outside the room.");
				return null;
			} else {
				newLocation[0] = mouseX;
				newLocation[1] = mouseY - 1;
				return newLocation;
			}
		}
		// Move left
		else if (move == 'A' || move == 'a') {
			// If the user pick a location outside of the room
			if (mouseY < 0 || mouseY > room.length - 1 || mouseX - 1 < 0 || mouseX - 1 > room[0].length - 1) {
				System.out.println("WARNING: Mouse cannot move outside the room.");
				return null;
			}
			// If the user picks a location that has a wall
			if (room[mouseY][mouseX - 1] == WALL) {
				System.out.println("WARNING: Mouse cannot move outside the room.");
				return null;
			} else {
				newLocation[0] = mouseX - 1;
				newLocation[1] = mouseY;
				return newLocation;
			}
		}
		// Move down
		else if (move == 'S' || move == 's') {
			// If the user pick a location outside of the room
			if (mouseY + 1 < 0 || mouseY + 1 > room.length - 1 || mouseX < 0 || mouseX > room[0].length - 1) {
				System.out.println("WARNING: Mouse cannot move outside the room.");
				return null;
			}
			// If the user picks a location that has a wall
			if (room[mouseY + 1][mouseX] == WALL) {
				System.out.println("WARNING: Mouse cannot move outside the room.");
				return null;
			} else {
				newLocation[0] = mouseX;
				newLocation[1] = mouseY + 1;
				return newLocation;
			}
		}
		// Move right
		else if (move == 'D' || move == 'd') {
			// If the user pick a location outside of the room
			if (mouseY < 0 || mouseY > room.length - 1 || mouseX + 1 < 0 || mouseX + 1 > room[0].length - 1) {
				System.out.println("WARNING: Mouse cannot move outside the room.");
				return null;
			}
			// If the user picks a location that has a wall
			if (room[mouseY][mouseX + 1] == WALL) {
				System.out.println("WARNING: Mouse cannot move outside the room.");
				return null;
			} else {
				newLocation[0] = mouseX + 1;
				newLocation[1] = mouseY;
				return newLocation;
			}
		} else {
			System.out.println("WARNING: Didn't recognize move command: " + move);
			return null;
		}
	}

	/*
	 * This purpose of this method is to make the mouse eat the cheese and count the
	 * number of cheese it eats.
	 * 
	 * 
	 */
	public static boolean tryToEatCheese(int mouseX, int mouseY, int[][] cheesePositions) {
		for (int i = 0; i < cheesePositions.length; i++) {
			if (cheesePositions[i][0] == mouseX && cheesePositions[i][1] == mouseY) {
				cheesePositions[i][0] = -1;
				cheesePositions[i][1] = -1;
				return true;
			}
		}
		return false;

	}

}
