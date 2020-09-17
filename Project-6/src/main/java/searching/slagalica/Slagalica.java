package searching.slagalica;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import searching.algorithms.Transition;


/**
 * Class is used to either check if 
 * the state passed to it is acceptable,
 * either to generate new states or to 
 * return the beggining state
 * 
 * @author Tomislav Kurtović
 *
 */
public class Slagalica implements Supplier<KonfiguracijaSlagalice>, Function<KonfiguracijaSlagalice,
List<Transition<KonfiguracijaSlagalice>>>,Predicate<KonfiguracijaSlagalice>{
	private KonfiguracijaSlagalice config;
	
	private int[] goal = {1,2,3,4,5,6,7,8,0};
	
	public Slagalica(KonfiguracijaSlagalice config) {
		super();
		this.config = config;
	}

	@Override
	public boolean test(KonfiguracijaSlagalice t) {
		int[] copyOfConfig = t.getPolje();
			for(int i : goal) {
				if(copyOfConfig[i] != goal[i]) return false;
			}
		return true;
	}

	@Override
	public List<Transition<KonfiguracijaSlagalice>> apply(KonfiguracijaSlagalice t) {
		List<Transition<KonfiguracijaSlagalice>> list = new LinkedList<>();
		int indexOfSpace = t.indexOfSpace();
		
		if(isInRange(indexOfSpace-1)) {
			KonfiguracijaSlagalice nextConfig = new KonfiguracijaSlagalice(newConfiguration(-1, t));
			list.add(new Transition<KonfiguracijaSlagalice>(nextConfig, 1));
		}
		
		if(isInRange(indexOfSpace + 1)) {
			KonfiguracijaSlagalice nextConfig = new KonfiguracijaSlagalice(newConfiguration(1, t));
			list.add(new Transition<KonfiguracijaSlagalice>(nextConfig, 1));
		}
		
		if(isInRange(indexOfSpace + 3)){
			KonfiguracijaSlagalice nextConfig = new KonfiguracijaSlagalice(newConfiguration(3, t));
			list.add(new Transition<KonfiguracijaSlagalice>(nextConfig, 1));
		}
		
		if(isInRange(indexOfSpace - 3)){
			KonfiguracijaSlagalice nextConfig = new KonfiguracijaSlagalice(newConfiguration(-3, t));
			list.add(new Transition<KonfiguracijaSlagalice>(nextConfig, 1));
		}
		
		return list;
	}

	private int[] newConfiguration(int i, KonfiguracijaSlagalice t) {
		int[] nextConfig = t.getPolje();
		int indexOfSpace = t.indexOfSpace();
		int supstitution = nextConfig[indexOfSpace + i];
		nextConfig[indexOfSpace + i] = nextConfig[indexOfSpace];
		nextConfig[indexOfSpace] = supstitution;
		return nextConfig;
	}

	private boolean isInRange(int i) {
		return i >= 0 && i < 9;
	}

	@Override
	public KonfiguracijaSlagalice get() {
		return config;
	}

}
