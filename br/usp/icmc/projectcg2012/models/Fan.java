/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.icmc.projectcg2012.models;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author cassianokc
 */
public class Fan extends AnimatedModel {

    float xn, yn, zn;
    float xref, yref, zref;
    float alpha;
    boolean on;

    public Fan(File file, float xn, float yn, float zn,
            float xref, float yref, float zref,
            float xcenter, float ycenter, float zcenter,
            float minDistance) throws IOException {
        super(file, xcenter, ycenter, zcenter, minDistance);  //fan cant be animated so far.
        alpha = 0;
        this.xn = xn;
        this.yn = yn;
        this.zn = zn;
        this.xref = xref;
        this.yref = yref;
        this.zref = zref;
        on = false;
    }

    @Override
    public void animate() {
        setOn(!isOn());
        if (isOn()) {
            try {
                playClip(new File("./project-cg-2012/fan.wav"));
            } catch (IOException ex) {
                Logger.getLogger(Door.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(Door.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(Door.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Door.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            stopClip();
        }
    }

    @Override
    public void draw(GLAutoDrawable gLAutoDrawable) {
        if (isOn()) {
            alpha += 15f;
        }
        GL gl = gLAutoDrawable.getGL();
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glPushMatrix();
        gl.glTranslatef(xref, yref, zref);
        gl.glRotatef(alpha, xn, yn, zn);
        gl.glTranslatef(-xref, -yref, -zref);
        super.draw(gLAutoDrawable);
        gl.glPopMatrix();

    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }
}
