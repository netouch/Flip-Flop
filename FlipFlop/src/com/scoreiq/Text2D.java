package com.scoreiq;


public class Text2D extends MeshGroup {
	private float uvOfDigits[] = { 0.0f, 0.0f, 0.25f, 0.0f, 0.5f, 0.0f, 0.75f,
			0.0f, 0.0f, 0.25f, 0.25f, 0.25f, 0.5f, 0.25f, 0.75f, 0.25f, 0.0f,
			0.5f, 0.25f, 0.5f };

	private float uvTmp[];

	public Text2D(int numDigits, float size, int texId) {
		uvTmp = new float[8];
		Plane tmp;
		for (int i = 0; i < numDigits; i++) {
			tmp = new Plane(size, size);
			tmp.x = i * size + size / 10;
			tmp.setTextureCoordinates(getUv(0));
			tmp.setTextureId(texId);
			addMesh(tmp);
		}
	}
	
	public void reset(){
		setNumber(0);
	}

	private float[] getUv(int n) {
		uvTmp[0] = uvOfDigits[n * 2];
		uvTmp[1] = uvOfDigits[n * 2 + 1] + 0.25f;

		uvTmp[2] = uvOfDigits[n * 2] + 0.25f;
		uvTmp[3] = uvOfDigits[n * 2 + 1] + 0.25f;

		uvTmp[4] = uvOfDigits[n * 2];
		uvTmp[5] = uvOfDigits[n * 2 + 1];

		uvTmp[6] = uvOfDigits[n * 2] + 0.25f;
		uvTmp[7] = uvOfDigits[n * 2 + 1];

		return uvTmp;
	}

	public void setNumber(int number) {
		if(number<Math.pow(10.0f, size()))
			splitNumber(number, size());
	}
	
	private int splitNumber(int number, int pow){
		int n = (int)(number/Math.pow(10.0f, pow-1));
		mChildren.get(size()-pow).setTextureCoordinates(getUv(n));
		if(pow == 1) return 0;
		else return splitNumber((int)(number-n*Math.pow(10.0f, pow-1)), pow-1);
	}
}
