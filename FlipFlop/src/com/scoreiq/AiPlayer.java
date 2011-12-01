package com.scoreiq;

import android.util.Log;

public class AiPlayer extends Player {
	private static final int PAD_UNKNOWN = -1;
	private static final int PAD_INACTIVE = -2;
	public static final int PAD_NONE = -10;
	private int pads[];
	private int alreadyFlipedPadIndex = PAD_NONE;

	private String debugTag = "TEST_AI";

	public AiPlayer() {
		name = "AiPlayer";
		pads = new int[12];
		reset();
	}

	public void reset() {
		super.reset();
		for (int i = 0; i < pads.length; i++)
			pads[i] = PAD_UNKNOWN;
		
		alreadyFlipedPadIndex = PAD_NONE;

		Log.d(debugTag, String.format(
				"Player %s: ----------------RESET-----------------------",
				name));
	}

	public int getMove() {
		Log.d(debugTag,
				String.format(
						"\nPlayer %s: getMove() -------------------START--------------------",
						name));
		tracePads();

		int padIndex = PAD_NONE;

		if (alreadyFlipedPadIndex == PAD_NONE) {
			padIndex = getPadIndexOfKnownPair();

			if (padIndex == PAD_NONE)
				padIndex = getRandomUnknownPad();
			
			if (padIndex == PAD_NONE)
				padIndex = getRandomActivePad();

			alreadyFlipedPadIndex = padIndex;
		} else {
			padIndex = getIndexOfPairByIndex(alreadyFlipedPadIndex);
			
			if (padIndex == PAD_NONE)
				padIndex = getRandomUnknownPad();
			
			if (padIndex == PAD_NONE)
				padIndex = getRandomActivePad();

			alreadyFlipedPadIndex = PAD_NONE;
		}

		Log.d(debugTag,
				String.format(
						"Player %s: getMove() return index = [%d] ----------------FINISH-----------------------\n\n",
						name, padIndex));
		return padIndex;
	}

	private int getIndexOfPairByIndex(int pairIndex) {
		for (int i = 0; i < pads.length; i++)
			if (i != pairIndex)
				if (pads[i] == pads[pairIndex] && pads[i] > 0) {
					Log.d(debugTag,
							String.format(
									"Player %s: getIndexOfPairByIndex():pair for index %d is index %d",
									name, pairIndex, i));
					return i;
				}

		Log.d(debugTag, String.format(
				"Player %s: getIndexOfPairByIndex(): no pair for index %d",
				name, pairIndex));
		return PAD_NONE;
	}

	private int getRandomActivePad() {
		int n = (int) (getActivePadsCount() * Math.random());
		int i = getCountedActivePad(n);
		Log.d(debugTag,
				String.format("Player %s: getRandomActivePad():%d", name, i));
		return i;
	}

	//TODO: если остался 1 неизвестный пад? криво отрабатывает.
	private int getRandomUnknownPad() {
		int n = (int) (getUnknownPadsCount() * Math.random()+1);
		int i = getCountedUnknownPad(n);
		Log.d(debugTag,
				String.format("Player %s: getRandomUnknownPad():%d", name, i));
		return i;
	}

	private int getPadIndexOfKnownPair() {
		for (int i = 0; i < pads.length; i++)
			for (int q = i + 1; q < pads.length; q++)
				if (pads[i] == pads[q] && pads[i] >= 0) {
					Log.d(debugTag,
							String.format(
									"Player %s: getPadIndexOfKnownPair():  pads[%d] = pads[%d] = %d",
									name, i, q, pads[i]));
					return i;
				}

		return PAD_NONE;
	}

	public void rememberPad(int padNum, int padFace) {
		if (padNum < pads.length) {
			pads[padNum] = padFace;
			Log.d(debugTag, String.format(
					"Player %s: I remember pad [%d] = %d", name, padNum,
					padFace));
		}
	}

	private void tracePads() {
		String arr = "";
		for (int i = 0; i < pads.length; i++)
			arr += pads[i] + " ";
		Log.d(debugTag, String.format("Player pads = %s", arr));
	}

	public void setInactive(int index) {
		pads[index] = PAD_INACTIVE;
	}

	private int getActivePadsCount() {
		int num = 0;
		for (int i = 0; i < pads.length; i++)
			if (pads[i] != PAD_INACTIVE)
				num++;

		Log.d(debugTag, String.format("Player: getActivePadsCount() = %d", num));
		return num;
	}

	private int getUnknownPadsCount() {
		int num = 0;
		for (int i = 0; i < pads.length; i++)
			if (pads[i] == PAD_UNKNOWN)
				num++;

		Log.d(debugTag,
				String.format("Player: getUnknownPadsCount() = %d", num));
		return num;
	}

	private int getCountedActivePad(int num) {
		int count = num;

		if (count > 0) {
			for (int i = 0; i < pads.length; i++) {
				if (pads[i] != PAD_INACTIVE)
					count--;
				if (count == 0) {
					Log.d(debugTag,
							String.format(
									"Player: getCountedActivePad(): index is of %d-th active pad = [%d]",
									num, i));
					return i;
				}
			}
		}
		return PAD_NONE;
	}

	/*
	 * 
D/TEST_AI ( 3887): Player AiPlayer: getMove() -------------------START--------------------
D/TEST_AI ( 3887): Player pads = -2 -2 -2 -2 5 -2 -2 -2 -2 -2 -1 -2 
D/TEST_AI ( 3887): Player: getUnknownPadsCount() = 1
D/TEST_AI ( 3887): Player AiPlayer: getRandomUnknownPad():-10
D/TEST_AI ( 3887): Player: getActivePadsCount() = 2
D/TEST_AI ( 3887): Player AiPlayer: getRandomActivePad():-10
D/TEST_AI ( 3887): Player AiPlayer: getMove() return index = [-10] ----------------FINISH-----------------------
 
	 */
	
	// TODO:
	public int getCountedUnknownPad(int num) {
		int count = num;

		if (count > 0) {
			for (int i = 0; i < pads.length; i++) {
				if (pads[i] == PAD_UNKNOWN)
					count--;
				
				if (count == 0) {
					Log.d(debugTag,
							String.format(
									"Player: getCountedUnknownPad(): index is of %d-th umknown pad = [%d]",
									num, i));
					return i;
				}
			}
		}
		return PAD_NONE;
	}
	
	public void debugSetPads(int pads[]) {
		this.pads = pads;
	}
}
