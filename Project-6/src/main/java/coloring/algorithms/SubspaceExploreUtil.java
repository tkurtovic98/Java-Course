package coloring.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
/**
 * Class used to implement searching algorithms
 * @author Tomislav Kurtović
 *
 */
public class SubspaceExploreUtil {


	/**
	 * Used to perform a bfs searching type of algorithm
	 * on all the pixels 
	 * @param s0 begin position pixel
	 * @param succ next pixels
	 * @param process adds pixels
	 * @param acceptable acceptable pixels
	 */
	public static <S> void bfs( Supplier<S> s0, Consumer<S> process, Function<S,List<S>> succ, Predicate<S> acceptable) {
		List<S> toFind = new LinkedList<>();
		toFind.add(s0.get());
		int i = 0;
		
		while(i < toFind.size()) {
			S next =  toFind.get(i);
			if(!acceptable.test(next)) {
				i++;
				continue;
			}
			process.accept(next);
			toFind.addAll(succ.apply(next));
			i++;
		}
	}
	
	/**
	 * Used to perform a dfs searching type of algorithm
	 * on all the pixels 
	 * @param s0 begin position pixel
	 * @param succ next pixels
	 * @param process adds pixels
	 * @param acceptable acceptable pixels
	 */
	public static <S> void dfs( Supplier<S> s0, Consumer<S> process, Function<S,List<S>> succ, Predicate<S> acceptable) {
		List<S> toFind = new LinkedList<>();
		toFind.add(s0.get());
		int i = 0;
		
		while(i < toFind.size()) {
			S next = toFind.get(i);
			if(!acceptable.test(next)) {
				i++;
				continue;
			}
			process.accept(next);
			toFind.addAll(0, succ.apply(next));
			i = 0;
		}
	}
	
	/**
	 * Used to perform a bfs searching type of algorithm
	 * on all the pixels 
	 * @param s0 begin position pixel
	 * @param succ next pixels
	 * @param process adds Pixels
	 * @param acceptable acceptable pixels
	 */
	public static <S> void bfsv( Supplier<S> s0, Consumer<S> process, Function<S,List<S>> succ, Predicate<S> acceptable) {
		List<S> toFind = new LinkedList<>();
		Set<S> visited = new HashSet<>();
		toFind.add(s0.get());
		visited.add(s0.get());
		int i = 0;
		
		while(i < toFind.size()) {
			S next = toFind.get(i);
			if(!acceptable.test(next)) {
				i++;
				continue;
			}
			process.accept(next);
			List<S> children = succ.apply(next);
			children.removeAll(visited);
			toFind.addAll(children);
			visited.addAll(children);
			i++;
		}
	}
	
}
