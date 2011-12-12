package com.scoreiq;

public class Vector3d {
	public float x;
	public float y;
	public float z;
	public float w;

	public Vector3d() {
		x = y = z = w = 0;
	}

	public Vector3d(float ix, float iy, float iz, float iw) {
		x = ix;
		y = iy;
		z = iz;
		w = iw;
	}

	public Vector3d(float ix, float iy, float iz) {
		x = ix;
		y = iy;
		z = iz;
		w = 0;
	}

	public Vector3d add(float ix, float iy, float iz) {
		x += ix;
		y += iy;
		z += iz;
		return this;
	}

	public Vector3d add(Vector3d v) {
		return new Vector3d(x + v.x, y + v.y, z + v.z, w + v.w);
	}

	public Vector3d set(Vector3d v) {
		x = v.x;
		y = v.y;
		z = v.z;
		w = v.w;
		return this;
	}
	
	public Vector3d set(float ix, float iy, float iz, float iw) {
		x = ix;
		y = iy;
		z = iz;
		w = iw;
		return this;
	}

	public Vector3d multiply(float f) {
		return new Vector3d(x * f, y * f, z * f);
	}

}
