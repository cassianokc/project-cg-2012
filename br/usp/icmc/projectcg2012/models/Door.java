package br.usp.icmc.projectcg2012.models;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.sound.sampled.*;
import javax.sound.sampled.LineEvent.Type;

/**
 *
 * @author cassianokc
 *
 */
public class Door extends AnimatedModel
{

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
     * Current state of door, 0 is opening and 1 is open, 2 is closing and 3 is
     * closed .
     */
    private char doorState;

    /**
     * Construct a Door object.
     *
     * @param file The file containing the object.
     * @throws IOException
     */
    public Door(File file, float xn, float yn, float zn,
            float xref, float yref, float zref,
            float xcenter, float ycenter, float zcenter,
            float minDistance) throws IOException
    {
        super(file, xcenter, ycenter, zcenter, minDistance);
        this.doorState = 3;
        this.alpha = 0f;
        this.xref = xref;
        this.yref = yref;
        this.zref = zref;
        this.xn = xn;
        this.yn = yn;
        this.zn = zn;
        this.xcenter = xcenter;
        this.ycenter = ycenter;
        this.zcenter = zcenter;
        this.minDistance = minDistance;
    }

    @Override
    public void draw(GLAutoDrawable glAutoDrawable)
    {
        GL gl = glAutoDrawable.getGL();
        if (doorState == 0)
        {
            if (alpha < 90f)
            {
                alpha++;
            } else
            {
                doorState = 1;
            }
        }
        else if (doorState == 2)
        {
            if (alpha > 0f)
            {
                alpha--;
            } else
            {
                doorState = 3;
            }
        }
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glPushMatrix();
        gl.glTranslatef(xref, yref, zref);
        gl.glRotatef(alpha, xn, yn, zn);
        gl.glTranslatef(-xref, -yref, -zref);
        super.draw(glAutoDrawable);
        gl.glPopMatrix();
    }

    public void animate()
    {
        try {
            playClip(new File("./project-cg-2012/door.wav"));
        } catch (IOException ex) {
            Logger.getLogger(Door.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Door.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Door.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Door.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (this.doorState == 0 || this.doorState == 1)
        {
            doorState = 2;
        } else
        {
            doorState = 0;
        }
    }
}
