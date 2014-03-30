package sia.block;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DivisionBlock extends Block {

	public DivisionBlock(int result, int n) {
		super(result, n, 2);
	}

	@Override
	public boolean solvesBlock() {
		List<Integer> cell_numbers = getCellNumbers();
		if (cell_numbers.size() != 2)
			return false;
		Collections.sort(cell_numbers);
		if (cell_numbers.get(1) % cell_numbers.get(0) != 0)
			return false;
		return equalsResult(cell_numbers.get(1) / cell_numbers.get(0));
	}

	@Override
	public void generatePossibleSolutions() {
		possibleLists = new ArrayList<List<Integer>>();
		ArrayList<Integer> aux;
		for (int i = getBoardMax(); i > 0; i--) {
			if (i % getResult() == 0) {
				aux = new ArrayList<Integer>();
				aux.add(i);
				aux.add(i / getResult());
				possibleLists.add(aux);
			}
		}
	}

	@Override
	protected String Operation() {
		return "/";
	}
}
