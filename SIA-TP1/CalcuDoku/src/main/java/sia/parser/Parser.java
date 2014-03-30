package sia.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import sia.block.AdditionBlock;
import sia.block.Block;
import sia.block.DivisionBlock;
import sia.block.MultiplicationBlock;
import sia.block.SubstractionBlock;
import sia.board.Board;

public class Parser {

	public static Board parse(String fileName) throws IOException {
		System.out.print("Opening file '" + fileName + "'...");
		FileReader input = new FileReader(fileName);
		BufferedReader bufReader = new BufferedReader(input);
		System.out.println("DONE!");
		System.out.print("Parsing '" + fileName + "'...");
		String line = bufReader.readLine();
		int max_value = Integer.valueOf(line);
		Board board = new Board(max_value);

		while ((line = bufReader.readLine()) != null) {
			String splited[] = line.split(":");
			if (splited.length != 3) {
				bufReader.close();
				throw new IllegalArgumentException(
						"There are more arguments than expected (3 arguments per line)!!!");
			}
			int operation_value = Integer.valueOf(splited[0]);
			String operation = splited[1];
			String cells[] = splited[2].split(";");
			Block block = getBlock(operation.charAt(0), operation_value,
					max_value, cells.length);
			board.addBlock(block);

			for (String cell : cells) {
				String[] coordinate = validateCoordinate(cell);
				Integer i = Integer.valueOf(coordinate[0]);
				Integer j = Integer.valueOf(coordinate[1]);
				validateCoordinateValues(i, j);
				block.addCell(board.getCell(i, j));
			}
		}

		input.close();
		bufReader.close();

		validateBoard(board);

		return board;
	}

	public static int[][] parseGoalBoard(String goal_board_path)
			throws IOException {
		FileReader input;
		input = new FileReader(goal_board_path);
		BufferedReader bufReader = new BufferedReader(input);
		String line = bufReader.readLine();
		int[][] ans = new int[Integer.parseInt(line)][Integer.parseInt(line)];
		int i = 0;
		while ((line = bufReader.readLine()) != null) {
			String[] values = line.split(" ");
			for (int j = 0; j < values.length; j++) {
				ans[i][j] = Integer.parseInt(values[j]);
			}
			i++;
		}
		bufReader.close();
		return ans;
	}

	private static Block getBlock(char operation, int operation_value,
			int max_value, int cell_amount) {
		switch (operation) {
		case '/':
			return new DivisionBlock(operation_value, max_value);
		case '*':
			return new MultiplicationBlock(operation_value, max_value,
					cell_amount);
		case '+':
			return new AdditionBlock(operation_value, max_value, cell_amount);
		case '-':
			return new SubstractionBlock(operation_value, max_value);
		default:
			throw new IllegalArgumentException("Operation '" + operation
					+ "' not supported!");
		}
	}

	private static String[] validateCoordinate(String cell) {
		String[] coordinate = cell.split(",");
		if (coordinate.length != 2) {
			throw new IllegalArgumentException("Invalid coordinate format!");
		}
		return coordinate;
	}

	private static void validateCoordinateValues(Integer i, Integer j) {
		if (i == null || j == null) {
			throw new IllegalArgumentException("Invalid coordinate values!");
		}
	}

	private static void validateBoard(Board board) {
		if (!board.isCorrect()) {
			throw new IllegalStateException(
					"There is no solution for one of the blocks!");
		}
		System.out.println("DONE!");
	}
}
