package scoreiq.com;

import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.app.Activity;
import java.util.Stack;

public class FlipFlopView extends GLSurfaceView {
	OGLRenderer renderer;
	public MeshGroup padList;
	
	public FlipFlopView(Activity act){
		super(act);
		renderer = new OGLRenderer();
		setRenderer(renderer);
		
		padList = new MeshGroup();
		renderer.setDrawing(padList);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		return true;
	}
}
