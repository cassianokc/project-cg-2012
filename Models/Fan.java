package Models;

import java.io.File;
import java.io.IOException;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

/**
 *
 * @author cassianokc
 */
public class Fan extends Model {

    private float alpha;
    private float delta;
    private float xcenter, ycenter, zcenter;
    private float xaxis1, yaxis1, zaxis1;
    private float xaxis2, yaxis2, zaxis2;

     /**
     * Construct a Fan object.
     *
     * @param file The file containing the object.
     * @throws IOException
     */
    public Fan(File file) throws IOException {
        super(file);
    }
    
    @Override
    public void draw(GLAutoDrawable glAutodrawable) {
        alpha++;
        GL gl = glAutodrawable.getGL();
        gl.glPushMatrix();
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glRotatef(alpha, 0, 1, 0);
        super.draw(glAutodrawable);
        gl.glPopMatrix();
               
    }
}
