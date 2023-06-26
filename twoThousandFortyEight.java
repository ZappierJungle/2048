import java.util.Random;
import java.util.Scanner;

public class twoThousandFortyEight {
	static boolean freeplay = false;
	static int score = 0;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int rows = 4;
		int cols = 4;
		int characterLimit = 4;
		int board[][] = new int[rows][cols];
		addNumber(board, rows, cols);
		addNumber(board, rows, cols);
		printBoard(board, rows, cols, characterLimit);
		while (!gameEnd(board, rows, cols, scanner)) {
			playerMove(board, scanner, rows, cols);
			addNumber(board, rows, cols);
			for (int row = 0; row < rows; row++) {
				for (int col = 0; col < cols; col++) {
					if (numberToString(board[row][col], characterLimit).length() > characterLimit) characterLimit++;
				}
			}
			printBoard(board, rows, cols, characterLimit);
		}
		scanner.close();
	}

	private static boolean gameEnd(int[][] board, int rows, int cols, Scanner scanner) {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				if (board[row][col] == 2048 && freeplay == false) {
					System.out.println("Congratulations! You beat 2048");
					String input;
					System.out.println("Would you like to continue? Yes(y) or No(n)");
					while (true) {
						input = scanner.nextLine();
						if (input.equals("y") || input.equals("n")) break;
						System.out.println("Invalid input ");
					}
					if (input.equals("y")) {
						freeplay = true;
						return false;
					}
					return true;
				}
			}
		}
		if (validMove(board, "w", false, rows, cols) || validMove(board, "s", false, rows, cols)
				|| validMove(board, "a", false, rows, cols) || validMove(board, "d", false, rows, cols)) {
			return false;
		}
		System.out.println("You filled the board up and have no moves left, meaning you lost.");
		return true;
	}

	private static void playerMove(int[][] board, Scanner scanner, int rows, int cols) {
		String playerMove;
		System.out.println("(w) up, (a) left, (s) down, or (d) right");
		while (true) {
			playerMove = scanner.nextLine();
			if (validMove(board, playerMove, true, rows, cols)) break;
		}
		combineNumbers(board, playerMove, rows, cols);
	}

	private static boolean validMove(int[][] board, String direction, boolean update, int rows, int cols) {
		switch (direction) {
		case "w":
			for (int row = 1; row < rows; row++) {
				for (int col = 0; col < cols; col++) {
					if (board[row][col] != 0 && (board[row - 1][col] == 0 || board[row][col] == board[row - 1][col])) {
						return true;
					}
				}
			}
			if (update) {
				System.out.println("Unable to move numbers up");
			}
			return false;
		case "s":
			for (int row = rows - 1; row > 0; row--) {
				for (int col = 0; col < cols; col++) {
					if (board[row - 1][col] != 0 && (board[row][col] == 0 || board[row - 1][col] == board[row][col])) {
						return true;
					}
				}
			}
			if (update) {
				System.out.println("Unable to move numbers down");
			}
			return false;
		case "a":
			for (int row = 0; row < rows; row++) {
				for (int col = 1; col < cols; col++) {
					if (board[row][col] != 0 && (board[row][col - 1] == 0 || board[row][col] == board[row][col - 1])) {
						return true;
					}
				}
			}
			if (update) {
				System.out.println("Unable to move numbers left");
			}
			return false;

		case "d":
			for (int row = 0; row < cols; row++) {
				for (int col = cols - 1; col > 0; col--) {
					if (board[row][col - 1] != 0 && (board[row][col] == 0 || board[row][col - 1] == board[row][col])) {
						return true;
					}
				}
			}
			if (update) {
				System.out.println("Unable to move numbers right");
			}
			return false;
		default:
			if (update) {
				System.out.println("Invalid input");
			}
			return false;
		}
	}

	private static void combineNumbers(int[][] board, String playerMove, int rows, int cols) {
		switch (playerMove) {
		case "w":
			for (int i = 0; i < rows; i++) {
				moveUp(board, rows, cols);
			}
			for (int col = 0; col < cols; col++) {
				for (int row = 1; row < rows; row++) {
					if (board[row][col] != 0 && board[row - 1][col] == board[row][col]) {
						board[row - 1][col] = board[row - 1][col] * 2;
						score += board[row - 1][col];
						board[row][col] = 0;
					}
				}
			}
			for (int i = 0; i < rows; i++) {
				moveUp(board, rows, cols);
			}
			break;
		case "s":
			for (int i = 0; i < rows; i++) {
				moveDown(board, rows, cols);
			}
			for (int row = rows - 1; row > 0; row--) {
				for (int col = 0; col < cols; col++) {
					if (board[row - 1][col] != 0 && board[row][col] == board[row - 1][col]) {
						board[row][col] = board[row][col] * 2;
						score += board[row][col];
						board[row - 1][col] = 0;
					}
				}
			}
			for (int i = 0; i < rows; i++) {
				moveDown(board, rows, cols);
			}
			break;
		case "a":
			for (int i = 0; i < cols; i++) {
				moveLeft(board, rows, cols);
			}
			for (int col = 1; col < cols; col++) {
				for (int row = 0; row < rows; row++) {
					if (board[row][col] != 0 && board[row][col - 1] == board[row][col]) {
						board[row][col - 1] = board[row][col - 1] * 2;
						score += board[row][col - 1];
						board[row][col] = 0;
					}
				}
			}
			for (int i = 0; i < cols; i++) {
				moveLeft(board, rows, cols);
			}
			break;
		case "d":
			for (int i = 0; i < cols; i++) {
				moveRight(board, rows, cols);
			}
			for (int col = cols - 1; col > 0; col--) {
				for (int row = 0; row < rows; row++) {
					if (board[row][col - 1] != 0 && board[row][col] == board[row][col - 1]) {
						board[row][col] = board[row][col] * 2;
						score += board[row][col];
						board[row][col - 1] = 0;
					}
				}
			}
			for (int i = 0; i < cols; i++) {
				moveRight(board, rows, cols);
			}
			break;
		default:
			break;
		}
	}

	private static void moveUp(int[][] board, int rows, int cols) {
		for (int col = 0; col < cols; col++) {
			for (int row = 1; row < rows; row++) {
				if (board[row][col] != 0 && board[row - 1][col] == 0) {
					board[row - 1][col] = board[row][col];
					board[row][col] = 0;
				}
			}
		}
	}

	private static void moveDown(int[][] board, int rows, int cols) {
		for (int row = rows - 1; row > 0; row--) {
			for (int col = 0; col < cols; col++) {
				if (board[row - 1][col] != 0 && board[row][col] == 0) {
					board[row][col] = board[row - 1][col];
					board[row - 1][col] = 0;
				}
			}
		}
	}

	private static void moveLeft(int[][] board, int rows, int cols) {
		for (int col = 1; col < cols; col++) {
			for (int row = 0; row < rows; row++) {
				if (board[row][col] != 0 && board[row][col - 1] == 0) {
					board[row][col - 1] = board[row][col];
					board[row][col] = 0;
				}
			}
		}
	}

	private static void moveRight(int[][] board, int rows, int cols) {
		for (int col = cols - 1; col > 0; col--) {
			for (int row = 0; row < rows; row++) {
				if (board[row][col - 1] != 0 && board[row][col] == 0) {
					board[row][col] = board[row][col - 1];
					board[row][col - 1] = 0;
				}
			}
		}
	}

	private static void addNumber(int[][] board, int rows, int cols) {
		Random rand = new Random();
		boolean valid = false;
		while (!valid) {
			int row = rand.nextInt(rows);
			int col = rand.nextInt(cols);
			if (board[row][col] == 0) {
				int number = rand.nextInt(10) < 9 ? 2 : 4;
				board[row][col] = number;
				valid = true;
			}
		}
	}

	private static void printBoard(int[][] board, int rows, int cols, int characterLimit) {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				System.out.print("+");
				for (int i = 0; i < characterLimit; i++) {
					System.out.print("-");
				}
			}
			System.out.print("+\n|");
			for (int col = 0; col < cols; col++) {
				System.out.print(numberToString(board[row][col], characterLimit) + "|");
			}
			System.out.println();
		}
		for (int col = 0; col < cols; col++) {
			System.out.print("+");
			for (int i = 0; i < characterLimit; i++) {
				System.out.print("-");
			}
		}
		System.out.println("+");
		System.out.println("Score: " + score);
	}

	private static String numberToString(int number, int characterLimit) {
		String numberString = " ";
		if (number == 0) {
			for (int i = 1; i < characterLimit; i++) {
				numberString = " " + numberString;
			}
			return numberString;
		} else {
			numberString = Integer.toString(number);
			while (numberString.length() < characterLimit) {
				numberString = " " + numberString;
			}
			return numberString;
		}
	}
}