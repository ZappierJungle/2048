import java.util.Random;
import java.util.Scanner;

public class twoThousandFortyEight {
	static boolean freeplay = false;
	static int score = 0;
	static int rows = 4;
	static int cols = 4;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int characterLimit = 4;
		int board[][] = new int[rows][cols];
		addNumber(board);
		addNumber(board);
		printBoard(board, characterLimit);
		while (!gameEnd(board, scanner)) {
			playerMove(board, scanner);
			addNumber(board);
			for (int row = 0; row < rows; row++) {
				for (int col = 0; col < cols; col++) {
					if (numberToString(board[row][col], characterLimit).length() > characterLimit)
						characterLimit++;
				}
			}
			printBoard(board, characterLimit);
		}
		System.out.println("Your final score was " + score);
		scanner.close();
	}

	private static boolean gameEnd(int[][] board, Scanner scanner) {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				if (board[row][col] == 2048 && freeplay == false) {
					System.out.println("Congratulations! You beat 2048");
					String input;
					System.out.println("Would you like to continue? Yes(y) or No(n)");
					while (true) {
						input = scanner.nextLine();
						if (input.equals("y") || input.equals("n"))
							break;
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
		if (validMove(board, "w", false) || validMove(board, "s", false) || validMove(board, "a", false) || validMove(board, "d", false)) {
			return false;
		}
		System.out.println("You filled the board up and have no moves left, meaning you lost.");
		return true;
	}

	private static void playerMove(int[][] board, Scanner scanner) {
		String playerMove;
		System.out.println("(w) up, (a) left, (s) down, or (d) right");
		while (true) {
			playerMove = scanner.nextLine();
			if (validMove(board, playerMove, true))
				break;
		}
		combineNumbers(board, playerMove);
	}

	private static boolean validMove(int[][] board, String direction, boolean update) {
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

	private static void combineNumbers(int[][] board, String playerMove) {
		switch (playerMove) {
		case "w":
			for (int i = 0; i < rows; i++) {
				moveUp(board);
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
				moveUp(board);
			}
			break;
		case "s":
			for (int i = 0; i < rows; i++) {
				moveDown(board);
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
				moveDown(board);
			}
			break;
		case "a":
			for (int i = 0; i < cols; i++) {
				moveLeft(board);
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
				moveLeft(board);
			}
			break;
		case "d":
			for (int i = 0; i < cols; i++) {
				moveRight(board);
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
				moveRight(board);
			}
			break;
		default:
			break;
		}
	}

	private static void moveUp(int[][] board) {
		for (int col = 0; col < cols; col++) {
			for (int row = 1; row < rows; row++) {
				if (board[row][col] != 0 && board[row - 1][col] == 0) {
					board[row - 1][col] = board[row][col];
					board[row][col] = 0;
				}
			}
		}
	}
	private static void moveDown(int[][] board) {
		for (int row = rows - 1; row > 0; row--) {
			for (int col = 0; col < cols; col++) {
				if (board[row - 1][col] != 0 && board[row][col] == 0) {
					board[row][col] = board[row - 1][col];
					board[row - 1][col] = 0;
				}
			}
		}
	}
	private static void moveLeft(int[][] board) {
		for (int col = 1; col < cols; col++) {
			for (int row = 0; row < rows; row++) {
				if (board[row][col] != 0 && board[row][col - 1] == 0) {
					board[row][col - 1] = board[row][col];
					board[row][col] = 0;
				}
			}
		}
	}
	private static void moveRight(int[][] board) {
		for (int col = cols - 1; col > 0; col--) {
			for (int row = 0; row < rows; row++) {
				if (board[row][col - 1] != 0 && board[row][col] == 0) {
					board[row][col] = board[row][col - 1];
					board[row][col - 1] = 0;
				}
			}
		}
	}

	private static void addNumber(int[][] board) {
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

	private static void printBoard(int[][] board, int characterLimit) {
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
