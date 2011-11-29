package com.scoreiq;

import javax.microedition.khronos.opengles.GL10;

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
		for(int i=0;i<size();i++){
			mChildren.get(i).setTextureCoordinates(getUv(0));
		}
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
		if(number<Math.pow(10.0f, size())){
			int dig = size()-1;
			int n;
			while(dig>=0){
				n=(int)(number/Math.pow(10.0f, dig));
				mChildren.get(dig).setTextureCoordinates(getUv(n));
				dig--;
			}
		}
	}
}
