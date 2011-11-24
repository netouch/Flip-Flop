package com.scoreiq;

import android.util.Log;

public class AiPlayer extends Player {
	private static final int PAD_UNKNOWN = -1;
	private static final int PAD_INACTIVE = -2;
	private static final int PAD_NONE = -10;
	private int pads[];
	private int alreadyFlipedPadIndex = PAD_NONE;

	public AiPlayer() {
		name = "AiPlayer";
		pads = new int[12];
		for (int i = 0; i < pads.length; i++)
			pads[i] = PAD_UNKNOWN;
	}

	public int getMove() {
		Log.d("TEST", String.format("Player %s: getMove()", name));
		int padIndex = PAD_NONE;
		
		if(alreadyFlipedPadIndex == PAD_NONE){
			getPadIndexOfKnownPair();
			int i = (int) (getActivePadsCount() * Math.random());
			padIndex = getCountedActivePad(i); 
		}
		return padIndex;
	}

	private int getPadIndexOfKnownPair() {
		int indexPairedPad=PAD_NONE;
		return indexPairedPad;
	}

	public void rememberPad(int padNum, int padFace) {
		if (padNum < pads.length)
			pads[padNum] = padFace;
		String arr = "";
		for (int i = 0; i < pads.length; i++)
			arr += pads[i] + " ";
		Log.d("TEST", String.format("Player pads = %s", arr));
	}

	public void setInactive(int index) {
		pads[index] = PAD_INACTIVE;
	}

	private int getActivePadsCount() {
		int num = 0;
		for (int i = 0; i < pads.length; i++)
			if (pads[i] != PAD_INACTIVE)
				num++;
		return num;
	}

	private int getUnknownPadsCount() {
		int num = 0;
		for (int i = 0; i < pads.length; i++)
			if (pads[i] == PAD_UNKNOWN)
				num++;
		return num;
	}

	private int getCountedActivePad(int num) {
		int y = 0;
		int padIndex = 0;
		for (int i = 0; i < pads.length; i++) {
			if (y == num)
				padIndex = i;
			if (pads[i] != PAD_INACTIVE)
				y++;
		}
		return padIndex;
	}
}
