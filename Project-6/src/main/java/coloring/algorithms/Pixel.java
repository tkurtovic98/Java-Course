package coloring.algorithms;

/**
 * Class used to represent one pixel on image
 * @author Tomislav Kurtović
 *
 */
public class Pixel {

	/**
	 * x of pixel
	 */
	public int x;
	/**
	 * y of pixel
	 */
	public int y;

	/**
	 * Constructor of pixel
	 * @param x
	 * @param y
	 */
	public Pixel(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "Pixel (" + x + "," + y + ")";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		Pixel other = (Pixel) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}
