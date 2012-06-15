/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.icmc.projectcg2012.models;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

/**
 *
 * @author cassianokc
 */
public abstract class AnimatedModel extends Model
{

    public AnimatedModel(File file, float xcenter, float ycenter, float zcenter, float minDistance) throws IOException
    {
        super(file);
        this.xcenter = xcenter;
        this.ycenter = ycenter;
        this.zcenter = zcenter;
        this.minDistance = minDistance;
        this.clip = null;
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
    protected static Clip clip;

    protected static void playClip(final File clipFile) throws IOException,
            UnsupportedAudioFileException, LineUnavailableException, InterruptedException
    {
        class AudioListener implements LineListener
        {

            private boolean done = false;

            @Override
            public synchronized void update(LineEvent event)
            {
                LineEvent.Type eventType = event.getType();
                if (eventType == LineEvent.Type.STOP || eventType == LineEvent.Type.CLOSE)
                {
                    done = true;
                    notifyAll();
                }
            }

            public synchronized void waitUntilDone() throws InterruptedException
            {
                while (!done)
                {
                    wait();
                }
            }
        }
        new Thread(new Runnable()
        { // the wrapper thread is unnecessary, unless it blocks on the Clip finishing, see comments

            public void run()
            {

                try
                {
                    AudioListener listener = new AudioListener();
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(clipFile);
                    clip = AudioSystem.getClip();
                    clip.addLineListener(listener);
                    clip.open(audioInputStream);
                    try
                    {
                        clip.start();
                        listener.waitUntilDone();
                    } finally
                    {
                        clip.close();
                        audioInputStream.close();
                    }
                } catch (Exception e)
                {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }
    
    protected static void stopClip()
    {
        if(clip != null)
        {
            clip.stop();
        }
    }
}
