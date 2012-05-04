package Models;

import java.io.File;
import java.io.IOException;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

/**
 *
 * @author cassianokc
 * 
 */
public class Door extends Model {

    private float alpha;
    /**
     * Reference point to rotation vector.
     */
    private float xref, yref, zref;
    /**
     * Rotation vector.
     */
    private float xn, yn, zn;
    /**
     * Current state of door, 0 is normal, 1 is opening and 2 is closing.
     */
    private char doorState;


    /**
     * Construct a Fan object.
     *
     * @param file The file containing the object.
     * @throws IOException
     */
    public Door(File file, float xref, float yref, float zref, 
            float xn, float yn, float zn) throws IOException {
        super(file);
        this.xref = xref;
        this.yref = yref;
        this.zref = zref;
        this.xn = xn;
        this.yn = yn;
        this.zn = zn;

    }

    @Override
    public void draw(GLAutoDrawable glAutodrawable) {
        if (doorState == 1){
            if (alpha < 90f)
                alpha++;
            else doorState = 0;
        }
        if (doorState == 2){
            if (alpha < 90f)
                alpha++;
            else doorState = 0;
        }        
        GL gl = glAutodrawable.getGL();
        gl.glPushMatrix();
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glTranslatef(xref, yref, zref);
        gl.glRotatef(alpha, xn, yn, zn);
        super.draw(glAutodrawable);
        gl.glPopMatrix();
    }

    public void open() {
        this.doorState = 1;
    }

    public void close() {
        this.doorState = 2;
    }
}
