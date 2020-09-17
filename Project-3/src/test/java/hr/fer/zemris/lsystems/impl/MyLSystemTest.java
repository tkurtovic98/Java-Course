package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.LSystem;

class MyLSystemTest {

	@Test
	void testGenerate() {
		LSystemBuilderImpl builder = new LSystemBuilderImpl();
		builder
		.setAxiom("F")
		.registerProduction('F', "F+F--F+F");
		
		LSystem system = builder.build();

		assertEquals("F",system.generate(0));
		assertEquals("F+F--F+F",system.generate(1));
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F",system.generate(2));
	}
	
	@Test
	void testGenerate2() {
		LSystemBuilderImpl builder = new LSystemBuilderImpl();
		builder
		.setAxiom("F")
		.registerProduction('F', "F+F−F−F+F");
		
		LSystem system = builder.build();
		
		assertEquals("F",system.generate(0));
		assertEquals("F+F−F−F+F",system.generate(1));
		assertEquals("F+F−F−F+F+F+F−F−F+F−F+F−F−F+F−F+F−F−F+F+F+F−F−F+F",system.generate(2));
		assertEquals("F+F−F−F+F+F+F−F−F+F−F+F−F−F+F−F+F−F−F+F+F+F−F−F+F+" + 
				"F+F−F−F+F+F+F−F−F+F−F+F−F−F+F−F+F−F−F+F+F+F−F−F+F−" + 
				"F+F−F−F+F+F+F−F−F+F−F+F−F−F+F−F+F−F−F+F+F+F−F−F+F−" + 
				"F+F−F−F+F+F+F−F−F+F−F+F−F−F+F−F+F−F−F+F+F+F−F−F+F+" + 
				"F+F−F−F+F+F+F−F−F+F−F+F−F−F+F−F+F−F−F+F+F+F−F−F+F",system.generate(3));
	}
	
}
