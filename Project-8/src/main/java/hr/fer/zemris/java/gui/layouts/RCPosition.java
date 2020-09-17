package hr.fer.zemris.java.gui.layouts;

/**
 * Class that is used to represent cell in 
 * the layoutManager {@link CalcLayout}
 * @author Tomislav Kurtović
 *
 */
public class RCPosition {
	/**
	 * Row of position
	 */
	private int row;
	/**
	 * column of position
	 */
	private int column;
	/**
	 * Constructo
	 * @param row
	 * @param column
	 */
	public RCPosition(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	/**
	 * returns row
	 * @return
	 */
	public int getRow() {
		return row;
	}

	/**
	 * returns column
	 * @return
	 */
	public int getColumn() {
		return column;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
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
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
}
