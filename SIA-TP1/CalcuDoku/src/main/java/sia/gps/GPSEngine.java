package sia.gps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import sia.calcudoku.CalcuDokuProblem;
import sia.calcudoku.CalcuDokuState;
import sia.gps.api.GPSProblem;
import sia.gps.api.GPSRule;
import sia.gps.api.GPSState;
import sia.gps.exception.NotAppliableException;

public abstract class GPSEngine {

	protected Collection<GPSNode> open = new LinkedList<GPSNode>();

	protected Set<GPSState> visited = new HashSet<GPSState>();

	protected GPSProblem problem;

	public void engine(GPSProblem myProblem) {

		problem = myProblem;

		GPSNode rootNode = new GPSNode(problem.getInitState(), 0);
		boolean finished = false;
		boolean failed = false;
		long explosionCounter = 0;

		open.add(rootNode);
		System.out.println("Solving problem. Please be patient...");
		long start_time = System.currentTimeMillis();
		while (!failed && !finished) {
			if (open.size() <= 0) {
				failed = true;
			} else {
				GPSNode currentNode = removeFirst();
				if (isGoal(currentNode)) {
					System.out.println("OK! solution found!");
					System.out.println(currentNode.getSolution());
					System.out.println("Expanded nodes: " + explosionCounter);
					System.out.println("Frontier nodes: " + open.size());
					System.out.println("Total states: "
							+ (explosionCounter + open.size()));
					System.out
							.println("Total time: "
									+ (System.currentTimeMillis() - start_time)
									+ " ms");
					((CalcuDokuState) currentNode.getState()).getBoard();
					return;
				} else {
					explosionCounter++;
					explode(currentNode);
				}
			}
		}

		if (failed) {
			System.err.println("FAILED! solution not found!");
		}
	}

	private boolean isGoal(GPSNode currentNode) {
		((CalcuDokuState) currentNode.getState()).updateProblem();
		return currentNode.getState() != null
				&& ((CalcuDokuProblem) problem).isGoalState()
				&& currentNode.getState().isFinished();
	}

	protected boolean explode(GPSNode node) {
		List<GPSRule> rules = problem.getRules();
		if (rules == null || rules.isEmpty()) {
			System.err.println("No rules!");
			return false;
		}
		List<GPSNode> newNodes = new ArrayList<GPSNode>();
		for (GPSRule rule : rules) {
			GPSState newState = null;
			try {
				((CalcuDokuState) node.getState()).updateProblem();
				newState = rule.evalRule(node.getState());
			} catch (NotAppliableException e) {
				// Do nothing
			}
			if (newState != null && !checkVisited(newState)) {
				GPSNode newNode = new GPSNode(newState, node.getCost()
						+ rule.getCost());
				newNode.setParent(node);
				newNodes.add(newNode);
				visited.add(newNode.getState());
			}
		}
		sortChildren(newNodes);
		for (GPSNode n : newNodes) {
			addNode(n);
		}
		return true;
	}

	protected void sortChildren(List<GPSNode> newNodes) {
	}

	private boolean checkVisited(GPSState state) {
		return visited.contains(state);
	}

	public abstract void addNode(GPSNode node);

	protected GPSNode removeFirst() {
		return ((List<GPSNode>) open).remove(0);
	}
}
