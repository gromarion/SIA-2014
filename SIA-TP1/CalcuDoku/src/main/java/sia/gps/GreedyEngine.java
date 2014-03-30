package sia.gps;

import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import sia.calcudoku.CalcuDokuState;

public class GreedyEngine extends DFSEngine {

	@Override
	protected void sortChildren(List<GPSNode> newNodes) {
		Comparator<GPSNode> comparator = new Comparator<GPSNode>() {
			@Override
			public int compare(GPSNode node1, GPSNode node2) {
				int hValueDif = problem.getHValue(node2.getState())
						- problem.getHValue(node1.getState());
				if (hValueDif != 0) {
					return hValueDif;
				}
				Integer a = ((CalcuDokuState) node1.getState()).getLast();
				Integer b = ((CalcuDokuState) node2.getState()).getLast();
				int ans = a.compareTo(b);
				return ans != 0 ? ans : 1;
			}
		};
		SortedSet<GPSNode> ordered = new TreeSet<GPSNode>(comparator);
		ordered.addAll(newNodes);
		newNodes.clear();
		newNodes.addAll(ordered);
	}
}
