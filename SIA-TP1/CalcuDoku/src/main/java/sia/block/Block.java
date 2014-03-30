package sia.block;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sia.board.Cell;

public abstract class Block {
	private int result;
	private int quantCells;
	private int boardMax;
	private List<Cell> cells = new ArrayList<Cell>();
	protected List<List<Integer>> possibleLists;

	public Block(int result, int boardMax, int size) {
		this.result = result;
		this.boardMax = boardMax;
		this.quantCells = size;
		generatePossibleSolutions();
	}

	public boolean equalsResult(int r) {
		return result == r;
	}

	public int getResult() {
		return result;
	}

	public int getBoardMax() {
		return boardMax;
	}

	public abstract boolean solvesBlock();

	public void addCell(Cell c) {
		cells.add(c);
		setCell(c);
	}

	public void setCell(Cell c) {
		c.setBlock(this);
	}

	public void addCells(List<Cell> cells) {
		for (Cell c : cells) {
			addCell(c);
		}
	}

	public List<Integer> getCellNumbers() {
		List<Integer> l = new ArrayList<Integer>();
		for (Cell c : cells) {
			if (c.getNumber() != 0) {
				l.add(c.getNumber());
			}
		}
		return l;
	}

	public List<Integer> getPossibles() {
		Set<Integer> s = new HashSet<Integer>();
		List<List<Integer>> lists;
		if (getCellNumbers().isEmpty()) {
			lists = possibleLists;
		} else {
			lists = new ArrayList<List<Integer>>();
			for (List<Integer> l : possibleLists) {
				lists.add(l);
				for (Integer i : getCellNumbers()) {
					if (!l.contains(i)) {
						lists.remove(l);
						break;
					}
				}
			}
		}
		for (List<Integer> l : lists) {
			s.addAll(l);
		}
		return new ArrayList<Integer>(s);
	}

	public int size() {
		return quantCells;
	}

	public List<Cell> getCells() {
		return cells;
	}

	public List<List<Integer>> getPossibleSolutions() {
		return possibleLists;
	}

	protected abstract void generatePossibleSolutions();

	@Override
	public String toString() {
		String ans = "[ ";
		for (Cell cell : cells) {
			ans += cell.toString();
		}
		return ans + " " + Operation() + getResult() + " ]";
	}

	protected abstract String Operation();
}
