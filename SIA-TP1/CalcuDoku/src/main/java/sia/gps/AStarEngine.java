package sia.gps;

import java.util.Comparator;
import java.util.TreeSet;

public class AStarEngine extends GPSEngine{
	
	public AStarEngine() {
		
		open = new TreeSet<GPSNode>(new Comparator<GPSNode>() {
			@Override
			public int compare(GPSNode node1, GPSNode node2) {
				if(node1.f() != node2.f())
					return node1.f() - node2.f();
				else if(node1.h() != node2.h())
					return node1.h() - node2.h();
				return 1;
			}
		});
	}
	
	@Override
	public void addNode(GPSNode node) {
		node.setHValue(problem.getHValue(node.getState()));
		open.add(node);
	}
	
	@Override
	protected GPSNode removeFirst() {
		return ((TreeSet<GPSNode>)open).pollFirst();
	}
}
