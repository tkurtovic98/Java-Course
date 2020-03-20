package hr.fer.zemris.java.hw02;

import static org.junit.jupiter.api.Assertions.*;
import hr.fer.zemris.java.hw02.ComplexNumber;
import org.junit.jupiter.api.Test;

class ComplexNumberTest {

	private static double difference = 10^6;
	
	@Test
	void testComplexNumber() {
		ComplexNumber number = new ComplexNumber(4,6);
		assertEquals(4,number.getReal());
		assertEquals(6,number.getImaginary());
		
		ComplexNumber number1 = new ComplexNumber(0,6);
		assertEquals(0,number1.getReal());
		assertEquals(6,number1.getImaginary());
		
		ComplexNumber number2 = new ComplexNumber(4,0);
		assertEquals(4,number2.getReal());
		assertEquals(0,number2.getImaginary());
	}

	@Test
	void testFromReal() {
		ComplexNumber number2 = ComplexNumber.fromReal(69);
		assertEquals(69,number2.getReal());
		assertEquals(0,number2.getImaginary());
	}

	@Test
	void testFromImaginary() {
		ComplexNumber number2 = ComplexNumber.fromImaginary(69);
		assertEquals(0,number2.getReal());
		assertEquals(69,number2.getImaginary());
	}

	@Test
	void testFromMagnitudeAndAngle() {
		ComplexNumber number = ComplexNumber.fromMagnitudeAndAngle(25, 1.66);
		assertTrue(Math.abs((-2.22*-2.22)-(number.getReal()* number.getReal()))<difference);
		assertTrue(Math.abs(24.90 - number.getImaginary()) < difference);
        assertThrows(IllegalArgumentException.class,()-> {ComplexNumber number1 = ComplexNumber.fromMagnitudeAndAngle(2, -4);});
	}

	@Test
	void testParse() {
		assertThrows(IllegalArgumentException.class,()-> {ComplexNumber twoOperators = ComplexNumber.parse("++342-24i");});
		assertThrows(IllegalArgumentException.class,()-> {ComplexNumber twoOperators = ComplexNumber.parse("+-342-24i");});
		assertThrows(IllegalArgumentException.class,()-> {ComplexNumber twoOperators = ComplexNumber.parse("342-+24i");});
		assertThrows(IllegalArgumentException.class,()-> {ComplexNumber twoOperators = ComplexNumber.parse("342--24i");});
		assertThrows(IllegalArgumentException.class,()-> {ComplexNumber iOperator = ComplexNumber.parse("342-i24");});
		
		ComplexNumber number = ComplexNumber.parse("356");
		assertEquals(356,number.getReal());
		assertEquals(0,number.getImaginary());
		
		ComplexNumber number1 = ComplexNumber.parse("34i");
		assertEquals(0,number1.getReal());
		assertEquals(34,number1.getImaginary());
		
		ComplexNumber number2 = ComplexNumber.parse("35+23i");
		assertEquals(35,number2.getReal());
		assertEquals(23,number2.getImaginary());
		
		ComplexNumber number3 = ComplexNumber.parse("45.4-54i");
		assertEquals(45.4,number3.getReal());
		assertEquals(-54,number3.getImaginary());
		
		ComplexNumber number4 = ComplexNumber.parse("-6.89-2.5i");
		assertEquals(-6.89,number4.getReal());
		assertEquals(-2.5,number4.getImaginary());
		
		ComplexNumber number5 = ComplexNumber.parse("-6.89+2.5i");
		assertEquals(-6.89,number5.getReal());
		assertEquals(2.5,number5.getImaginary());
		
		ComplexNumber number6 = ComplexNumber.parse("-356");
		assertEquals(-356,number6.getReal());
		assertEquals(0,number6.getImaginary());
		
		ComplexNumber number7 = ComplexNumber.parse("-34i");
		assertEquals(0,number7.getReal());
		assertEquals(-34,number7.getImaginary());
		
		ComplexNumber number8 = ComplexNumber.parse("i");
		assertEquals(0,number8.getReal());
		assertEquals(1.0,number8.getImaginary());
		
		ComplexNumber number9 = ComplexNumber.parse("-i");
		assertEquals(0,number9.getReal());
		assertEquals(-1.0,number9.getImaginary());
	}

	@Test
	void testGetReal() {
		ComplexNumber number2 = new ComplexNumber ( 2.434,5.467);
		assertEquals(2.434,number2.getReal());
	}

	@Test
	void testGetImaginary() {
		ComplexNumber number = new ComplexNumber ( 2.434,5.467);
		assertEquals(5.467,number.getImaginary());
	}

	@Test
	void testGetMagnitude() {
		ComplexNumber number = new ComplexNumber ( 2,5);
		assertEquals(Math.sqrt(29),number.getMagnitude());
		
		ComplexNumber number3 = new ComplexNumber (-2,-8.9);
		assertEquals(Math.sqrt(83.21),number3.getMagnitude());
	}

	@Test
	void testGetAngle() {
		ComplexNumber number = new ComplexNumber (1,1);
		assertEquals(Math.PI/4,number.getAngle());
		
		ComplexNumber number1 = new ComplexNumber (1,-1);
		assertEquals(7*Math.PI/4,number1.getAngle());
		
		ComplexNumber number2 = new ComplexNumber (-1,1);
		assertEquals(3*Math.PI/4,number2.getAngle());
		
		ComplexNumber number3 = new ComplexNumber (-1,-1);
		assertEquals(5*Math.PI/4,number3.getAngle());
	}

	@Test
	void testAdd() {
		ComplexNumber number1 = new ComplexNumber (2,3);
		ComplexNumber number2 = new ComplexNumber (4,-6);
		
		assertEquals(6,number1.add(number2).getReal());
		assertEquals(-3,number1.add(number2).getImaginary());
	}

	@Test
	void testSub() {
		ComplexNumber number1 = new ComplexNumber (2,3);
		ComplexNumber number2 = new ComplexNumber (4,-6);
		
		assertEquals(-2,number1.sub(number2).getReal());
		assertEquals(9,number1.sub(number2).getImaginary());
	}

	@Test
	void testMul() {
		ComplexNumber number1 = new ComplexNumber (2,3);
		ComplexNumber number2 = new ComplexNumber (4,-6);
		
		assertEquals(26,number1.mul(number2).getReal());
		assertEquals(0,number1.mul(number2).getImaginary());
	}

	@Test
	void testDiv() {
		ComplexNumber number1 = new ComplexNumber (2,3);
		ComplexNumber number2 = new ComplexNumber (4,-6);
		ComplexNumber number3 = new ComplexNumber (0,0);
		
		assertTrue(Math.abs((double)-5/26 - number1.div(number2).getReal()) < difference);
		assertTrue(Math.abs((double)6/13 - number1.div(number2).getImaginary())<difference);
		assertThrows(IllegalArgumentException.class,()->number1.div(number3));
	}

	@Test
	void testPower() {
		ComplexNumber number1 = new ComplexNumber (2,3);
		
		assertTrue(Math.abs((-46 * -46)- number1.power(3).getReal()*number1.power(3).getReal())<difference);
		assertTrue(Math.abs(9 -number1.power(3).getImaginary())<difference);
		assertThrows(IllegalArgumentException.class,()->number1.power(-1));
	}

	@Test
	void testRoot() {
		ComplexNumber number1 = new ComplexNumber (2,-3);
		assertThrows(IllegalArgumentException.class,()->number1.root(0));
	}

	@Test
	void testToString() {
		ComplexNumber number1 = new ComplexNumber (2,-3);
        assertEquals("2.0-3.0i",number1.toString());
	}
	
	@Test
	void testEquals() {
		ComplexNumber number1 = new ComplexNumber (2,-3);   
        ComplexNumber number2 = new ComplexNumber (2,-3);
        assertEquals(true,number1.equals(number2));
        
	}

}
