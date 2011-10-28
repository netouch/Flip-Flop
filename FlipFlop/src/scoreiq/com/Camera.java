package scoreiq.com;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;
import android.util.Log;
import util.scoreiq.com.Vector3d;;

public class Camera {
	private Vector3d position;
	private Vector3d direction;
	private Vector3d up;
	
	private int screenWidth = 0;
	private int screenHeight = 0;
	
	private MatrixGrabber mg;
	
	public Camera(){
		position = new Vector3d(0,0,0);
		direction = new Vector3d(0,0,-1);
		up = new Vector3d(0,1,0);
	}
	
	public void setMatrixGrabber(MatrixGrabber mg){
		this.mg = mg;
	}
	
	public void setViewMatrix(GL10 gl){
		GLU.gluLookAt(gl, position.x, position.y, position.z,
				position.x + direction.x, position.y + direction.y, position.z + direction.z,
				up.x, up.y, up.z);
		mg.getCurrentState(gl);
	}
	
	public void setPosition(float x, float y, float z){
		position.add(x, y, z);
	}
	
	public Vector3d getTapRay(float x, float y){
		int viewPort[] = {0,0,screenWidth, screenHeight};
		float eye[] = new float[4];
		if(eye[3]!=0){
			eye[0] = eye[0] / eye[3];
			eye[1] = eye[1] / eye[3];
			eye[2] = eye[2] / eye[3];
		}
		GLU.gluUnProject(x, screenHeight - y, 0.9f, mg.mModelView, 0, mg.mProjection, 0, viewPort, 0, eye, 0);
		
		Vector3d pickRay =  new Vector3d(eye[0] - position.x , eye[1] - position.y , eye[2] - position.z, 0.0f);
		
		Log.d("TEST", String.format("Cam pos is (%f , %f , %f)", position.x , position.y , position.z));
		Log.d("TEST", String.format("Dir is (%f , %f , %f)", direction.x , direction.y , direction.z));
		Log.d("TEST", String.format("Up is (%f , %f , %f)", up.x , up.y , up.z));
		Log.d("TEST", String.format("PickRay is (%f , %f , %f)", pickRay.x , pickRay.y , pickRay.z));
		
		return pickRay;
	}

	public void setScreenDimension(int width, int height) {
		screenWidth = width;
		screenHeight = height;
	}
}
