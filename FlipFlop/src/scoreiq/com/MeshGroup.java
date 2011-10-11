package scoreiq.com;

import java.util.Vector;
import javax.microedition.khronos.opengles.GL10;

public class MeshGroup extends Mesh {
	private Vector<Mesh> mChildren = new Vector<Mesh>();
	
	@Override
	public void draw(GL10 gl){
		gl.glPushMatrix();
	    multiplyMatricies(gl);
		
		int num = mChildren.size();
		for(int i=0;i<num;i++){
			mChildren.get(i).draw(gl);
		}
		gl.glPopMatrix();
	}
	
	private void multiplyMatricies(GL10 gl){
		gl.glTranslatef(x, y, z);
	    gl.glRotatef(rx, 1, 0, 0);
	    gl.glRotatef(ry, 0, 1, 0);
	    gl.glRotatef(rz, 0, 0, 1);
	}
	
	public boolean addMesh(Mesh object){
		return mChildren.add(object);
	}
	
	public void clear(){
		mChildren.clear();
	}
}
