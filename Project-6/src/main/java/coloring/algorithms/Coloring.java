package coloring.algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import marcupic.opjj.statespace.coloring.Picture;

/**
 * Class is used to either check if 
 * the state passed to it is acceptable,
 * either to generate new states or to 
 * return the beggining state
 * 
 * @author Tomislav Kurtović
 *
 */
public class Coloring implements Consumer<Pixel>,  
Function<Pixel,List<Pixel>>, Predicate<Pixel>, Supplier<Pixel>{

	private Pixel reference;
	private Picture picture;
	private int fillColor;
	private int refColor;
	
	public Coloring(Pixel reference, Picture picture, int fillColor) {
		super();
		this.reference = reference;
		this.picture = picture;
		this.fillColor = fillColor;
		refColor = picture.getPixelColor(reference.x, reference.y);
	}

	@Override
	public Pixel get() {
		return reference;
	}

	@Override
	public boolean test(Pixel t) {
		return refColor == picture.getPixelColor(t.x, t.y) ;
	}

	@Override
	public List<Pixel> apply(Pixel t) {
		List<Pixel> neighbours = new LinkedList<>();
		neighbours.add(new Pixel(t.x, t.y + 1 ));
		neighbours.add(new Pixel(t.x , t.y - 1 ));
		neighbours.add(new Pixel(t.x + 1, t.y  ));
		neighbours.add(new Pixel(t.x - 1, t.y));
		return neighbours;
	}

	@Override
	public void accept(Pixel t) {
		picture.setPixelColor(t.x, t.y, fillColor);
	}
}
