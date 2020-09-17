package searching.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;
import searching.slagalica.gui.SlagalicaViewer;

public class SlagalicaMain {
	public static void main(String[] args) {
		
		if(args[0].length() != 9) {
			System.out.println("Invalid number of numbers!");
			return;
		}
		List<Integer> list = new ArrayList<>();
		int[] array = new int[9];
		for(int i = 0; i < 9; i++) {
			if(list.contains(Integer.valueOf(args[0].charAt(i)))) {
				System.out.println("Duplicate numbers found!");
				return;
			}
			int number = Integer.valueOf(args[0].charAt(i) - '0');
			if(number > 9 || number < 0) {
				System.out.println("Invalid numbers found!");
				return;
			}
			list.add(number);
			array[i] = number;
		}
		
		
		Slagalica slagalica = new Slagalica(new KonfiguracijaSlagalice(array));
		Node<KonfiguracijaSlagalice> rješenje = SearchUtil.bfs(slagalica, slagalica, slagalica);
		if (rješenje == null) {
			System.out.println("Nisam uspio pronaći rješenje.");
		} else {
			System.out.println("Imam rješenje. Broj poteza je: " + rješenje.getCost());
			List<KonfiguracijaSlagalice> lista = new ArrayList<>();
			Node<KonfiguracijaSlagalice> trenutni = rješenje;
			while (trenutni != null) {
				lista.add(trenutni.getState());
				trenutni = trenutni.getParent();
			}
			Collections.reverse(lista);
			lista.stream().forEach(k -> {
				System.out.println(k);
				System.out.println();
			});
		}
		SlagalicaViewer.display(rješenje);
	}
}
