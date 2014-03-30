package sia.block;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MultiplicationBlock extends Block {

	public MultiplicationBlock(int result, int n, int size) {
		super(result, n, size);
	}

	@Override
	public boolean solvesBlock() {
		int ans = 1;
		for (Integer i : getCellNumbers()) {
			ans *= i;
		}
		return equalsResult(ans);
	}

	@Override
	protected void generatePossibleSolutions() {
		int[] values = new int[getBoardMax()];
		for (int i = 0; i < values.length; i++)
			values[i] = i + 1;
		possibleLists = new ArrayList<List<Integer>>();
		generatePossibles(values, 0, 1, new LinkedList<Integer>());
	}

	@SuppressWarnings("unchecked")
	private void generatePossibles(int[] values, int index, int product,
			LinkedList<Integer> stack) {
		if (product == getResult() && stack.size() == size())
			possibleLists.add((List<Integer>) stack.clone());

		if (product * values[index] > getResult() || stack.size() >= size())
			return;
		for (int i = index; i < values.length; i++) {
			stack.push(values[i]);
			generatePossibles(values, i, product * values[i], stack);
			stack.pop();
		}
	}

	@Override
	protected String Operation() {
		return "*";
	}
}
