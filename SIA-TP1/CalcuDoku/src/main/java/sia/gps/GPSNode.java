package sia.gps;

import sia.gps.api.GPSState;

public class GPSNode {

	private GPSState state;

	private GPSNode parent;

	private int cost, hValue;

	public GPSNode(GPSState state, Integer cost) {
		super();
		this.state = state;
		this.cost = cost;
	}

	public GPSNode getParent() {
		return parent;
	}

	public void setParent(GPSNode parent) {
		this.parent = parent;
	}

	public GPSState getState() {
		return state;
	}

	public Integer getCost() {
		return cost;
	}

	@Override
	public String toString() {
		return state.toString();
	}

	public String getSolution() {
		if (this.parent == null) {
			return this.state.toString();
		}
		return this.parent.getSolution() + "\n" + this.state;
	}

	@Override
	public int hashCode() {
		return state.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return state.compare(((GPSNode)obj).getState());
	}
	
	public void setHValue(int hValue) {
		this.hValue = hValue;
	}
		
	public int f(){
		return cost + hValue;
	}

	public int h() {
		return hValue;
	}
	
}
