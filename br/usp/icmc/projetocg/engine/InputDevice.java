/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.icmc.projetocg.engine;

import br.usp.icmc.projetocg.view.Renderer;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.MemoryImageSource;
import javax.media.opengl.GLCanvas;

/**
 *
 * @author gustavo
 */


public class InputDevice implements MouseListener, MouseMotionListener, KeyListener {
    private GLCanvas canvas;
    private Renderer renderer;
    private int prevMouseX;
    private int prevMouseY;
    private boolean mousePosKnown = false;
    private boolean isRelative = true;
    private float alpha;  // angulo de visao atual da camera, direita esquerda
    private float beta;  // angulo de visao atual da camera, cima baixo
    private float posx;  // posicao x atual da camera
    private float posy;  // posicao y atual da camera, provavelmente nunca sera mudado
    private float posz;  // posicao z atual da camera
    
    public InputDevice(GLCanvas canvas, Renderer renderer){
        this.canvas = canvas;
        this.renderer = renderer;
        this.alpha = this.renderer.getAlpha();
        this.beta = this.renderer.getBeta();
        this.posx = this.renderer.getPosx();
        this.posy = this.renderer.getPosy();
        this.posz = this.renderer.getPosz();
    }
    
    public void setCanvas(GLCanvas canvas)
    {
        this.canvas = canvas;
    }
    
    public void setRenderer(Renderer renderer)
    {
        this.renderer = renderer;
    }
    
    public void setRelative(boolean bool)
    {
        this.isRelative = bool;
    }
    
    public boolean isRelative()
    {
        return this.isRelative;
    }
    
    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent me) {
    }
    
    public void mouseClicked(MouseEvent me) {
        
    }

    public void mousePressed(MouseEvent me) {
    }
    
    public void mouseMoved(MouseEvent me) {
        if(isRelative())
        {
            processMouseMove(me, this.canvas);
            centerMouse(this.canvas);
        }
    }

    public void keyTyped(KeyEvent ke) {
    }

    public void keyPressed(KeyEvent ke) {
        switch (ke.getKeyCode()) {
            case KeyEvent.VK_W:
                posz = (float) (posz + 0.2f * Math.sin(alpha));
                posx = (float) (posx + 0.2f * Math.cos(alpha));
                renderer.setPosx(posx);
                renderer.setPosz(posz);
                break;
            case KeyEvent.VK_S:
                posz = (float) (posz - 0.2f * Math.sin(alpha));
                posx = (float) (posx - 0.2f * Math.cos(alpha));
                renderer.setPosx(posx);
                renderer.setPosz(posz);
                break;
            case KeyEvent.VK_A:
                posz = (float) (posz - 0.2f * Math.cos(alpha));
                posx = (float) (posx + 0.2f * Math.sin(alpha));
                renderer.setPosx(posx);
                renderer.setPosz(posz);
                break;
            case KeyEvent.VK_D:
                posz = (float) (posz + 0.2f * Math.cos(alpha));
                posx = (float) (posx - 0.2f * Math.sin(alpha));
                renderer.setPosx(posx);
                renderer.setPosz(posz);
                break;
            case KeyEvent.VK_SPACE:
               setRelative(!isRelative());
                break;
        }
    }

    public void keyReleased(KeyEvent ke) {
    }
    
    public void centerMouse(GLCanvas canvas)
    {
      Point locOnScreen = canvas.getLocationOnScreen();
      
      int middleX = locOnScreen.x + (canvas.getWidth() / 2);
      int middleY = locOnScreen.y + (canvas.getHeight() / 2);
      
      try{
	Robot rob = new Robot();
	// Re-setting mouse coordinates.
	rob.mouseMove( middleX, middleY );
	prevMouseX = middleX;
	prevMouseY = middleY;
      } catch(Exception e){ System.out.println(e); }
    }
  
    public void processMouseMove(MouseEvent mouse, GLCanvas canvas)
    {
        int curMouseX = mouse.getX();
        int curMouseY = mouse.getY();

        final int maxStep = 5;

	int rightStep  = curMouseX - (canvas.getWidth() / 2);
	if( rightStep > maxStep )
        {
            alpha = renderer.getAlpha() + 0.06f; /* DIREITA */
            renderer.setAlpha(alpha);
            rightStep = maxStep;
        }
	else if( rightStep < -maxStep ) 
        {
            alpha = renderer.getAlpha() - 0.06f; /* ESQUERDA */
            renderer.setAlpha(alpha);
            rightStep = -maxStep; 
            
        }

        int upStep  = curMouseY - (canvas.getHeight() / 2);
	if( upStep > maxStep )
        {
            beta = renderer.getBeta() - 0.06f;      /* BAIXO */
            renderer.setBeta(beta);
            upStep = maxStep;
        }
	else if( upStep < -maxStep)
        {
            beta = renderer.getBeta() + 0.06f;     /* CIMA */
            renderer.setBeta(beta);
            upStep = -maxStep;
        }
    }
  
    public void hideCursor( GLCanvas canvas )
    {
      int w = 1;
      int h = 1;
      int pix[] = new int[w * h];

      Image image = Toolkit.getDefaultToolkit().createImage(
	new MemoryImageSource(w, h, pix, 0, w));
      Cursor cursor =
	Toolkit.getDefaultToolkit().createCustomCursor
	(image, new Point(0, 0), "custom");
      canvas.setCursor(cursor);
    }
}