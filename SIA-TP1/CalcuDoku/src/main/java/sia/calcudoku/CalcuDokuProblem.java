package sia.calcudoku;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sia.board.Board;
import sia.gps.api.GPSProblem;
import sia.gps.api.GPSRule;
import sia.gps.api.GPSState;
import sia.parser.Parser;

public class CalcuDokuProblem implements GPSProblem {

	private Board board;
	private Heuristic h;
	private int[][] goalBoard;

	public CalcuDokuProblem(String init_board_path, String goal_board_path,
			Heuristic h) throws IOException {
		board = Parser.parse(init_board_path);
		this.goalBoard = Parser.parseGoalBoard(goal_board_path);
		this.h = h;
	}

	@Override
	public GPSState getInitState() {
		return new CalcuDokuState(board);
	}

	@Override
	public List<GPSRule> getRules() {
		List<GPSRule> rules = new ArrayList<GPSRule>();
		for (int i = 0; i < board.getSize(); i++)
			for (int j = 0; j < board.getSize(); j++) {
				if (board.getCell(i, j).getNumber() == 0) {
					for (int k = 1; k < board.getSize() + 1; k++) {
						rules.add(new CalcuDokuRule(k, i, j));
					}
				}
			}
		return rules;
	}

	@Override
	public Integer getHValue(GPSState state) {
		CalcuDokuState st = (CalcuDokuState) state;
		st.updateProblem();
		Board board = st.getBoard();
		switch (h) {
		case BLOCKS:
			return (st.cellsLeft() * board.blocksLeft())
					/ board.getBlocksSize();
		case ROWS_AND_COLUMNS:
			return ((st.cellsLeft() * board.rowsAndColsLeft()) / (board
					.getSize() * 2));
		case BOTH:
		default:
			return Math.max(
					(st.cellsLeft() * board.blocksLeft())
							/ board.getBlocksSize(), ((st.cellsLeft() * board
							.rowsAndColsLeft()) / (board.getSize() * 2)));
		}
	}

	public boolean isGoalState() {
		for (int i = 0; i < goalBoard.length; i++) {
			for (int j = 0; j < goalBoard[i].length; j++) {
				if (goalBoard[i][j] != board.getCell(i, j).getNumber()) {
					return false;
				}
			}
		}
		return true;
	}
}
