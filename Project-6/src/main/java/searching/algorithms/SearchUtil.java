package searching.algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Class used to implement searching algorithms
 * @author Tomislav Kurtović
 *
 */
public class SearchUtil {

	/**
	 * Used to perform a bfs searching type of algorithm
	 * on all the states 
	 * @param s0 begin state
	 * @param succ next states
	 * @param goal acceptable state
	 * @return wanted state if exists
	 */
	public static <S> Node<S> bfs(Supplier<S> s0, Function<S,List<Transition<S>>> succ, Predicate<S> goal) {
		List<Node<S>> toFind = new LinkedList<>();
		toFind.add(new Node<S>(null, 0, s0.get()));
		int i = 0;
		
		while(i < toFind.size()) {
			Node<S> node = toFind.get(i);
			
			if(goal.test(node.getState())) {
				return node;
			}
			
			List<Transition<S>> transitions = succ.apply(node.getState());
			for(Transition<S> tran : transitions) {
				Node<S> next = new Node<S>(node, node.getCost() + tran.getCost(), tran.getState());
				toFind.add(toFind.size(), next);;
			}
			i++;
		}
		return null;
	}
	
}
