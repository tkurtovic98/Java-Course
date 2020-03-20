package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;

class UniqueNumbersTest {

	@Test
	void treeSizeTest1() {
		TreeNode glava = null;
		int size =  UniqueNumbers.treeSize(glava);
		assertEquals(0,size);
	}
	
	@Test
	void treeSizeTest2() {
		TreeNode glava = null;
		glava = UniqueNumbers.addNode(glava,21);
		glava = UniqueNumbers.addNode(glava,30);
		glava = UniqueNumbers.addNode(glava,25);
		glava = UniqueNumbers.addNode(glava,30);
		assertEquals(3,UniqueNumbers.treeSize(glava));
	}
	
	@Test
	void containsValueTest() {
		TreeNode glava = null;
		boolean bool = UniqueNumbers.containsValue(glava,4);
		assertEquals(false,bool);
	}
	
	@Test
	void containsValueTest2() {
		TreeNode glava = null;
		glava = UniqueNumbers.addNode(glava,21);
		glava = UniqueNumbers.addNode(glava,30);
		glava = UniqueNumbers.addNode(glava,25);
		glava = UniqueNumbers.addNode(glava,30);
		
		assertEquals(true,UniqueNumbers.containsValue(glava, 30));
		assertEquals(false,UniqueNumbers.containsValue(glava, 40));

	}
	
	@Test
	void addNodeTest() {
		TreeNode glava = null;		
		glava = UniqueNumbers.addNode(glava,40);
		glava = UniqueNumbers.addNode(glava,32);
		glava = UniqueNumbers.addNode(glava,50);
		glava = UniqueNumbers.addNode(glava,45);
		
		assertEquals(40, glava.value);
		assertEquals(32, glava.left.value);
		assertEquals(50, glava.right.value);
        assertEquals(45, glava.right.left.value);
	}

}
