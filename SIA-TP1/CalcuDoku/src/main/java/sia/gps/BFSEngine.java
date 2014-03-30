package sia.gps;

import java.util.Queue;


public class BFSEngine extends GPSEngine{

	@Override
	public void addNode(GPSNode node) {
		Queue<GPSNode> queue = (Queue<GPSNode>) open;
		queue.offer(node);
	}
	
}
