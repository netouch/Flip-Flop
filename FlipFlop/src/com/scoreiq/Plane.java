package com.scoreiq;

public class Plane extends Mesh {
	
	public Plane(){
		this(1,1);
	}
	
	public Plane(float width , float height){
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
		
		uwCoords[0]=0.0f;
		uwCoords[1]=1.0f;
		
		uwCoords[2]=1.0f;
		uwCoords[3]=1.0f;
		
		uwCoords[4]=0.0f;
		uwCoords[5]=0.0f;
		
		uwCoords[6]=1.0f;
		uwCoords[7]=0.0f;
		
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
