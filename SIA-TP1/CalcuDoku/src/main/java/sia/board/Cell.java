package sia.board;

import sia.block.Block;

public class Cell {
	private int number = 0;
	private Block block;
	private Coordinate coordinate;
	
	public Cell(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
	
	public void setBlock(Block block){
		this.block = block;
	}
	
	@Override
	public String toString() {
		return "[ " + coordinate.toString() + " ]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((block == null) ? 0 : block.hashCode());
		result = prime * result + number;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (block == null) {
			if (other.block != null)
				return false;
		} else if (!block.equals(other.block))
			return false;
		if (number != other.number)
			return false;
		return true;
	}
	
	public Block getBlock(){
		return block;
	}
	
	public void setNumber(int number){
		this.number = number;
	}
	
	public int getNumber(){
		return number;
	}
}