package sia.calcudoku;

import sia.block.Block;
import sia.board.Board;
import sia.gps.api.GPSState;

public class CalcuDokuState implements GPSState {

	private Board board;
	private int[][] values;
	private int depth = 0;
	private int last = 0;

	public CalcuDokuState(Board board) {
		this.board = board;
		values = new int[board.getSize()][board.getSize()];
	}

	public CalcuDokuState(CalcuDokuState state, int value, int i, int j) {
		last = value;
		setValues(state.values);
		this.board = state.board;
		this.depth = state.depth + 1;
		values[i][j] = value;
	}

	@Override
	public boolean compare(GPSState state) {
		CalcuDokuState st = (CalcuDokuState) state;
		return depth == st.getDepth() && compareValues(st);
	}

	private boolean compareValues(CalcuDokuState state) {
		for (int i = 0; i < values.length; i++)
			for (int j = 0; j < values.length; j++)
				if (values[i][j] != state.values[i][j])
					return false;
		return true;

	}

	@Override
	public boolean isFinished() {
		return depth == board.getSize() * board.getSize() && board.isFinished();
	}

	public int[][] getValues() {
		return values;
	}

	private void setValues(int[][] v) {
		values = new int[v.length][v.length];
		for (int i = 0; i < values.length; i++)
			for (int j = 0; j < values.length; j++)
				values[i][j] = v[i][j];

	}

	public int getDepth() {
		return depth;
	}

	public void updateProblem() {
		for (int i = 0; i < values.length; i++)
			for (int j = 0; j < values.length; j++)
				board.setCellValue(i, j, values[i][j]);
	}

	public Board getBoard() {
		return board;
	}

	public int cellsLeft() {
		return values.length * values.length - depth;
	}

	@Override
	public String toString() {
		String ans = "Depth: " + depth + "\nValues:\n-------";
		for (int i = 0; i < values.length; i++) {
			ans += "\n";
			for (int j = 0; j < values.length; j++)
				ans += values[i][j] + " ";
		}
		ans += "\n";
		for (Block block : board.getBlocks()) {
			ans += block.toString();
		}
		return ans;
	}

	@Override
	public int hashCode() {
		int a = 1;
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values.length; j++) {
				if (values[i][j] != 0)
					a *= values[i][j] * 7;
			}
		}
		return a;
	}

	@Override
	public boolean equals(Object obj) {
		return compare((CalcuDokuState) obj);
	}

	public int getLast() {
		return last;
	}
}
