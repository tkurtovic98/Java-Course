/**
 * 	Paket sadrži sve zadatke prve domaće zadaće
 */
package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Razred funkcionira kao implementacija uređenog binarnog stabla.
 * Sadrži pomoćnu strukturu za pohranu podataka o čvoru i metode za
 * dodavanje novog čvora, dobivanje veličine stabla, prisutnost nekog čvora u 
 * stablu te metodu za ispis vrijednosti pojedinog čvora u uzlazno ili silazno sortiranom 
 * poretku
 * @author Tomislav Kurtović
 */

public class UniqueNumbers {
	
	/**
	 * Struktura podataka koja služi kao reprezentacija 
	 * čvora binarnog stabla
	 */
	
	static class TreeNode {
		
		TreeNode left;
		TreeNode right;
		int value;
	}
	
	/**
	 * Metoda preko tipkovnice dobiva podatke o čvoru,
	 * a zatim poziva prikladne metode
	 * za ispis i dodavanje čvora.
	 * 
	 */
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		TreeNode glava = null;
		int nodeValue;

		while (true) {

			System.out.printf("Unesite broj > ");
			String entry = sc.nextLine();

			if (entry.equalsIgnoreCase("kraj")) {
				
				break;
			}

			try {
				nodeValue = Integer.parseInt(entry);
		        
				if (containsValue(glava, nodeValue)) {
					System.out.println("Broj već postoji.Preskačem");
				}
				else {
					glava = addNode(glava,nodeValue);
					System.out.println("Dodano");
				}
			} catch (IllegalArgumentException ex) {
				System.out.println("'" + entry + "' nije cijeli broj.");
			}

		}
		
		if(treeSize(glava)> 0) {
			System.out.printf("Ispis od najmanjeg :");
			printFromMinimum(glava);
			System.out.println("");
			System.out.printf("Ispis od največeg :");
			printFromMaximum(glava);
			System.out.println("");
			
		}

		sc.close();

	}
	
	/**
	 * Metoda ispisuje vrijednosti članova
	 *  stabla od največeg prema najmanjem
	 * 
	 * @param node glava binarnog stabla 
	 */
	private static void printFromMaximum(TreeNode node) {
		
		if(node == null) {
			return;
		}
		
		printFromMaximum(node.right);
		System.out.printf("%d ", node.value);
		printFromMaximum(node.left);

	}
	
	/**
	 * Metoda ispisuje vrijednosti članova
	 *  stabla od najmanjeg prema največem
	 * 
	 * @param node glava binarnog stabla 
	 */

	private static void printFromMinimum(TreeNode node) {
	
		if(node == null) {
			return;
		}
		
		printFromMinimum(node.left);
		System.out.printf("%d ", node.value);
	    printFromMinimum(node.right);

	}
	
	/**
	 * Metoda dodaje novi čvor u binarno stablo
	 * 
	 * @param node čvor koji se treba dodati
	 * @param value vrijednost čvora
	 * @return  dodani čvor u binarno stablo
	 */

	public static TreeNode addNode(TreeNode node, int value) {
		
		TreeNode newNode=new TreeNode();
		
		if(node == null) {
			newNode.value = value;
			newNode.left = null;
			newNode.right = null;
			return newNode;
		}
		else {
			if(value > node.value) {
				node.right = addNode(node.right, value);
				
			}
			else if (value < node.value){
				node.left = addNode(node.left, value);
				
			}
			
			return node;
		}
		
	}
	
	/**
	 * Metoda vrača vrijednost binarnog stabla
	 * 
	 * @param node glava binarnog stabla 
	 * @return veličina binarnog stabla
	 */
	
	public static int treeSize(TreeNode node) {
		
		if(node == null) {
			return 0;
		}
		
		else {
			return treeSize(node.left) + treeSize(node.right) + 1;
		}

	}
	
	/**
	 * Metoda ispituje je li 
	 * binarno stablo sadrži čvor sa predanom vrijednošću
	 *
	 * @param node glava binarnog stabla
	 * @param value vrijednost koja se ispituje
	 * @return true ako nađe čvor sa predanom vrijednošču inače false
	 */
	
	public static boolean containsValue(TreeNode node,int value) {

		if(node == null) {
			return false;
		}
		
		if(node.value == value) {
		   return true;
		} else {
		   return containsValue(node.left, value) || containsValue(node.right, value);
		}
		
	}
}
