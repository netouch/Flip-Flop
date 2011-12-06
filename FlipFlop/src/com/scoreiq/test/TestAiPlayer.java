package com.scoreiq.test;


import com.scoreiq.AiPlayer;

import junit.framework.*;


public class TestAiPlayer extends TestCase {
	public void test_smoke(){
		AiPlayer ai = new AiPlayer(12);
	}
	
	public void test_getCountedUnknownPad(){
		AiPlayer ai = new AiPlayer(12);
		int pads[] = {-2,-2,-2,2,-2,-2,6,-2,5,3,-1,5};
		ai.debugSetPads(pads);
		assertEquals(10, ai.getCountedUnknownPad(1));
	}
	
}
