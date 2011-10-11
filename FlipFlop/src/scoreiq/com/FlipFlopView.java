package scoreiq.com;

import android.opengl.GLSurfaceView;
import android.app.Activity;
import java.util.Stack;

public class FlipFlopView extends GLSurfaceView {
	OGLRenderer renderer;
	
	public FlipFlopView(Activity act){
		super(act);
		renderer = new OGLRenderer();
		setRenderer(renderer);
	}
}
