package sia.gps;

import sia.calcudoku.CalcuDokuState;

public class IterativeDFSEngine extends DFSEngine {
	private int limit;

	public IterativeDFSEngine(int limit) {
		this.limit = limit;
	}

	@Override
	protected boolean explode(GPSNode node) {
		if (((CalcuDokuState) node.getState()).getDepth() > limit) {
			open.clear();
			visited.clear();
			open.add(new GPSNode(problem.getInitState(), 0));
			limit++;
			return true;
		}
		return super.explode(node);
	}
}
