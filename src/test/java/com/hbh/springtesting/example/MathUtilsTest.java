package com.hbh.springtesting.example;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MathUtilsTest {
	
	@Test
	void testAdd() {
		MathUtils mathUtils = new MathUtils();
		int expected = 2;
		int actual = mathUtils.add(1,1);
		assertEquals(expected, actual, "The add method should add 2 numbers");
		
	}

}
