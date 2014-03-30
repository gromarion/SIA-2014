package sia.calcudoku;

import sia.board.Board;
import sia.gps.api.GPSRule;
import sia.gps.api.GPSState;
import sia.gps.exception.NotAppliableException;

public class CalcuDokuRule implements GPSRule {

	int value, i, j;

	public CalcuDokuRule(int value, int i, int j) {
		this.value = value;
		this.i = i;
		this.j = j;
	}

	@Override
	public Integer getCost() {
		return 1;
	}

	@Override
	public String getName() {
		return "value: " + value;
	}

	@Override
	public GPSState evalRule(GPSState state) throws NotAppliableException {
		Board board = ((CalcuDokuState) state).getBoard();
		if (!(board.getPossibbles(i, j).contains(value)))
			throw new NotAppliableException();
		return new CalcuDokuState((CalcuDokuState) state, value, i, j);
	}

	@Override
	public String toString() {
		return "Value: " + value + "  at position: " + "(" + i + "," + j + ")";
	}

}
