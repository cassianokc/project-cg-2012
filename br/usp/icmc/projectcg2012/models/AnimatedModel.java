/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.icmc.projectcg2012.models;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author cassianokc
 */
public abstract class AnimatedModel extends Model
{

    public AnimatedModel(File file, float xcenter, float ycenter, float zcenter, float radius) throws IOException
    {
        super(file);
        this.xcenter = xcenter;
        this.ycenter = ycenter;
        this.zcenter = zcenter;
        this.minDistance = minDistance;
    }

    public boolean isNear(float x, float y, float z)
    {
        return (this.minDistance > (x - this.xcenter) * (x - this.xcenter) + (y - this.ycenter) * (y - this.ycenter)
                + (z - this.zcenter) * (z - this.zcenter));
        
    }

    public abstract void animate();
    protected float xcenter;
    protected float ycenter;
    protected float zcenter;
    protected float minDistance;
}
