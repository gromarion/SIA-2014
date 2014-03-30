package sia.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sia.block.Block;

public class Board {

	private int size;
	private Cell[][] cells;
	private ArrayList<Block> blocks;

	public Board(int size) {
		this.size = size;
		cells = new Cell[size][size];
		blocks = new ArrayList<Block>();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				cells[i][j] = new Cell(new Coordinate(i, j));
			}
		}
	}
	
	public List<Block> getBlocks() {
		return blocks;
	}

	public void setCellValue(int i, int j, int value) {
		cells[i][j].setNumber(value);
	}

	public boolean completed() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (cells[i][j].getNumber() == 0) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean isFinished() {
		if (!completed()) {
			return false;
		}
		boolean cols = columnsFinished();
		boolean rows = rowsFinished();
		boolean blocks = blocksFinished();
		return cols && rows && blocks;
	}

	private boolean blocksFinished() {
		for (Block b : blocks) {
			if (!b.solvesBlock()) {
				return false;
			}
		}
		return true;
	}

	public int blocksLeft() {
		int count = 0;
		for (Block b : blocks) {
			if (!b.solvesBlock()) {
				count++;
			}
		}
		return count;
	}

	public int rowsAndColsLeft() {
		int count = 0;
		boolean completed;
		for (int i = 0; i < size; i++) {
			completed = true;
			for (int j = 0; j < size; j++) {
				if (cells[i][j].getNumber() == 0) {
					completed = false;
				}
			}
			if (completed == true) {
				count++;
			}
		}
		for (int j = 0; j < size; j++) {
			completed = true;
			for (int i = 0; i < size; i++) {
				if (cells[i][j].getNumber() == 0) {
					completed = false;
				}
			}
			if (completed == true) {
				count++;
			}
		}
		return size + size - count;
	}

	private boolean rowsFinished() {
		ArrayList<Integer> numbers = null;
		for (int i = 0; i < size; i++) {
			numbers = new ArrayList<Integer>();
			for (int j = 0; j < size; j++) {
				Integer cellNumber = cells[i][j].getNumber();
				if (numbers.contains(cellNumber)) {
					return false;
				}
				numbers.add(cellNumber);
			}
		}
		return true;
	}

	private boolean columnsFinished() {
		ArrayList<Integer> numbers = null;
		for (int j = 0; j < size; j++) {
			numbers = new ArrayList<Integer>();
			for (int i = 0; i < size; i++) {
				Integer cellNumber = cells[i][j].getNumber();
				if (numbers.contains(cellNumber)) {
					return false;
				}
				numbers.add(cellNumber);
			}
		}
		return true;
	}

	public int getSize() {
		return size;
	}

	public void addBlock(Block block) {
		blocks.add(block);
	}

	public Cell getCell(int i, int j) {
		return cells[i][j];
	}

	public boolean isCorrect() {
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (cells[i][j].getBlock() == null)
					return false;
		for (Block g : blocks) {
			if (g.getPossibleSolutions().isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public int getBlocksSize() {
		return blocks.size();
	}

	private List<Integer> getPossiblesInColumn(int j) {
		List<Integer> possibles = new ArrayList<Integer>();
		for (int i = 1; i < size + 1; i++)
			possibles.add(i);
		for (int i = 0; i < size; i++) {
			Integer aux;
			if ((aux = cells[i][j].getNumber()) != 0) {
				possibles.remove(aux);
			}
		}
		return possibles;
	}

	private List<Integer> getPossiblesInRow(int i) {
		List<Integer> possibles = new ArrayList<Integer>();
		for (int j = 1; j < size + 1; j++)
			possibles.add(j);
		for (int j = 0; j < size; j++) {
			Integer aux;
			if ((aux = cells[i][j].getNumber()) != 0) {
				possibles.remove(aux);
			}
		}
		return possibles;
	}

	public List<Integer> getPossibbles(int i, int j) {
		List<Integer> possibles = new ArrayList<Integer>();
		List<Integer> rowPossibles = getPossiblesInRow(i);
		List<Integer> colPossibles = getPossiblesInColumn(j);
		for (Integer possible : cells[i][j].getBlock().getPossibles()) {
			if (rowPossibles.contains(possible)
					&& colPossibles.contains(possible)) {
				if (cells[i][j].getBlock().size() == cells[i][j].getBlock()
						.getCellNumbers().size() + 1) {
					int aux = cells[i][j].getNumber();
					cells[i][j].setNumber(possible);
					if (cells[i][j].getBlock().solvesBlock()) {
						possibles.add(possible);
					}
					cells[i][j].setNumber(aux);

				} else {
					possibles.add(possible);
				}
			}
		}
		return possibles;
	}
	
	@Override
	public String toString() {
		String ans = "";
		for (Cell[] row : cells) {
			for (Cell cell : row) {
				ans += cell.getNumber() + " ";
			}
			ans += "\n";
		}
		for (Block block : blocks) {
			ans += block.toString();
		}
		return ans;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(cells);
		result = prime * result + ((blocks == null) ? 0 : blocks.hashCode());
		result = prime * result + size;
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
		Board other = (Board) obj;
		if (!Arrays.deepEquals(cells, other.cells))
			return false;
		if (blocks == null) {
			if (other.blocks != null)
				return false;
		} else if (!blocks.equals(other.blocks))
			return false;
		if (size != other.size)
			return false;
		return true;
	}

}
