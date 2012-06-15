/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.icmc.projectcg2012.models;

import java.io.File;
import java.io.IOException;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

/**
 *
 * @author cassianokc
 */
public class Fan extends AnimatedModel
{

    float xn, yn, zn;
    float xref, yref, zref;
    float alpha;

    public Fan(File file, float xn, float yn, float zn, float xref, float yref, float zref) throws IOException
    {
        super(file, 0.0f, 0.0f, 0.0f, 0.0f);  //fan cant be animated so far.
        alpha = 0;
        this.xn = xn;
        this.yn = yn;
        this.zn = zn;
        this.xref = xref;
        this.yref = yref;
        this.zref = zref;
    }

    @Override
    public void animate()
    {
    }

    @Override
    public void draw(GLAutoDrawable gLAutoDrawable)
    {
        alpha = alpha + 1f;
        GL gl = gLAutoDrawable.getGL();
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glPushMatrix();  
        gl.glTranslatef(xref, yref, zref);
        gl.glRotatef(alpha, xn, yn, zn);
        gl.glTranslatef(-xref, -yref, -zref);
        super.draw(gLAutoDrawable);
        gl.glPopMatrix();
        
    }
}
