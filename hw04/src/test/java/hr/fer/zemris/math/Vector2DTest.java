package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Vector2DTest {

	private double epsilon = 0.1;
	
	@Test
	void testVector2D() {
		Vector2D vector = new Vector2D(0,0);
		assertNotNull(vector);
		
		vector = new Vector2D(-5.424,3.23);
		assertNotNull(vector);
		
		vector = new Vector2D(123.3,1.4);
		assertNotNull(vector);
		
	    vector = new Vector2D(-1,100);
		assertNotNull(vector);
	}

	@Test
	void testGetX() {
		Vector2D vector = new Vector2D(0,0);
		assertNotNull(vector);
		assertEquals(0,vector.getX());
		
		vector = new Vector2D(-5.424,3.23);
		assertNotNull(vector);
		assertEquals(-5.424,vector.getX());
		
		vector = new Vector2D(123.3,1.4);
		assertNotNull(vector);
		assertEquals(123.3,vector.getX());
		
	    vector = new Vector2D(-1,100);
		assertNotNull(vector);
		assertEquals(-1,vector.getX());
	}

	@Test
	void testGetY() {
		Vector2D vector = new Vector2D(0,0);
		assertNotNull(vector);
		assertEquals(0,vector.getY());
		
		vector = new Vector2D(-5.424,3.23);
		assertNotNull(vector);
		assertEquals(3.23,vector.getY());
		
		vector = new Vector2D(123.3,1.4);
		assertNotNull(vector);
		assertEquals(1.4,vector.getY());
		
	    vector = new Vector2D(-1,100);
		assertNotNull(vector);
		assertEquals(100,vector.getY());
	}

	@Test
	void testTranslate() {
		Vector2D vector = new Vector2D(-1,100);
		vector.translate(new Vector2D(5,10));
		
		assertEquals(4,vector.getX());
		assertEquals(110,vector.getY());
		
		vector.translate(new Vector2D(1.5,-5.4));
		assertEquals(5.5,vector.getX());
		assertEquals(104.6,vector.getY());
	}

	@Test
	void testTranslated() {
		Vector2D vector = new Vector2D(-4,15.5);
		Vector2D translatedVector = vector.translated(new Vector2D(5,10));
		
		assertEquals(-4,vector.getX());
		assertEquals(15.5,vector.getY());
		assertEquals(1,translatedVector.getX());
		assertEquals(25.5,translatedVector.getY());
		
		Vector2D newVector = translatedVector.translated(new Vector2D(-15,9));
		assertEquals(-14,newVector.getX());
		assertEquals(34.5,newVector.getY());
	}

	@Test
	void testRotate() {
		Vector2D vector = new Vector2D(55,0);
		vector.rotate(Math.PI/6);
		assertEquals( 47.63139720814413, vector.getX() , epsilon);
		assertEquals( 27.5, vector.getY() , epsilon);
		
		Vector2D vector2 = new Vector2D(3,3);
		vector2.rotate(Math.PI);
		assertEquals( -3, vector2.getX() ,epsilon);
		assertEquals( -3, vector2.getY() ,epsilon);
		
	}

	@Test
	void testRotated() {
		Vector2D vector = new Vector2D(55,0);
		Vector2D rotatedVector = vector.rotated(Math.PI/6);
		assertEquals( 47.63139720814413, rotatedVector.getX() , epsilon);
		assertEquals( 27.5, rotatedVector.getY() , epsilon);
	}

	@Test
	void testScale() {
		Vector2D vector = new Vector2D(-4,15.5);
		
		vector.scale(5);
		assertEquals(-20,vector.getX());
		assertEquals(77.5,vector.getY());
		
		vector.scale(-2);
		assertEquals(40,vector.getX());
		assertEquals(-155,vector.getY());
		
		vector.scale(0.5);
		assertEquals(20,vector.getX());
		assertEquals(-77.5,vector.getY());
		
	}

	@Test
	void testScaled() {
		Vector2D vector = new Vector2D(-4,15.5);
		Vector2D scaledVector = vector.scaled(5);
		
		assertEquals(-20,scaledVector.getX());
		assertEquals(77.5,scaledVector.getY());
		
		scaledVector = vector.scaled(-2);
		assertEquals(8,scaledVector.getX());
		assertEquals(-31,scaledVector.getY());
		
		scaledVector = vector.scaled(0.5);
		assertEquals(-2,scaledVector.getX());
		assertEquals(7.75,scaledVector.getY());
	}

	@Test
	void testCopy() {
		Vector2D vector = new Vector2D(-2,30.25);
		Vector2D vectorNew = vector.copy();
		
		assertEquals(vector.getX(),vectorNew.getX());
		assertEquals(vector.getY(),vectorNew.getY());
	}

}
