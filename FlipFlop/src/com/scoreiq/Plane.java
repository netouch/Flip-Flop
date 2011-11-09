package com.scoreiq;

public class Plane extends Mesh {
	
	public Plane(){
		this(1,1);
	}
	
	public Plane(float width , float height){
		this(width, height, 0.0f, 0.0f, 1.0f, 1.0f);
	}
	
	public Plane(float width , float height, float u_left, float w_down, float u_right, float w_top){
		float[] vertices = new float[4*3];
		float[] colors = new float[4*4];
		short[] indices = new short[6];
		float[] uwCoords = new float[4*2];
		
		
		vertices[0] = -width/2;
		vertices[1] = -height/2;
		vertices[2] = 0.0f;
		
		vertices[3] = width/2;
		vertices[4] = -height/2;
		vertices[5] = 0.0f;
		
		vertices[6] = -width/2;
		vertices[7] = height/2;
		vertices[8] = 0.0f;
		
		vertices[9] = width/2;
		vertices[10] = height/2;
		vertices[11] = 0.0f;
		
		uwCoords[0]=u_left;
		uwCoords[1]=w_top;
		
		uwCoords[2]=u_right;
		uwCoords[3]=w_top;
		
		uwCoords[4]=u_left;
		uwCoords[5]=w_down;
		
		uwCoords[6]=u_right;
		uwCoords[7]=w_down;
		
		for(int i=0;i<16;i++)colors[i] = 1.0f;
				
		//face one
		indices[0] = 0;
		indices[1] = 1;
		indices[2] = 2;
					
		//face two
		indices[3] = 1;
		indices[4] = 3;
		indices[5] = 2;
		
		setVertices(vertices);
		setIndices(indices);
		//setColors(colors);
		setTextureCoordinates(uwCoords);
	}
}
