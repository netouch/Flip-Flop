package com.scoreiq;

public class Vector3d {
	public float x;
	public float y;
	public float z;
	public float w;

	public Vector3d(){
	x=y=z=w=0;
	}

	public Vector3d(float ix, float iy, float iz, float iw){
		x=ix;
		y=iy;
		z=iz;
		w=iw;
	}

	public Vector3d(float ix, float iy, float iz){
		x=ix;
		y=iy;
		z=iz;
		w=0;
	}

	public void add(float ix, float iy, float iz){
		x+=ix;
		y+=iy;
		z+=iz;
	}

}
