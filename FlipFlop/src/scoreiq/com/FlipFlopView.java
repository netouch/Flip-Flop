package scoreiq.com;

import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import android.app.Activity;

import java.util.List;
import java.util.Stack;
import java.util.Vector;

public class FlipFlopView extends GLSurfaceView {
	OGLRenderer renderer;
	public Vector<Pad> pads = new Vector<Pad>();
	public MeshGroup visibleGroup = new MeshGroup();
	
	public FlipFlopView(Activity act){
		super(act);
		renderer = new OGLRenderer();
		setRenderer(renderer);
		
		renderer.setDrawing(visibleGroup);
		Log.d("TEST", String.format("View created"));
	}
	

	@Override
	public boolean onTouchEvent(MotionEvent event){
		//for(int i=0;i<padList.size();i++)
		//TODO: Warning! Bidlocode!! add UIActiveArea etc...
		int xPos=0;
		if(event.getX()<=160)xPos=0;
		else if(event.getX()<=320)xPos=1;
		else xPos = 2;
		
		if(!pads.get(xPos).isFlipping())pads.get(xPos).Rotate(180,1);
		
		//Log.d("TEST", String.format("SomeBody touched me!"));
		return true;
	}
	
	public void addPad(Pad mPad){
		pads.add(mPad);
		visibleGroup.addMesh(mPad);
	}

}
