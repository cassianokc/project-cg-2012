package View;


import Main.Main;
import Models.Model;
import com.sun.opengl.util.GLUT;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.MemoryImageSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class Renderer implements GLEventListener, MouseListener, MouseMotionListener, KeyListener {

    private float alpha;  // angulo de visao atual da camera, direita esquerda
    private float beta;  // angulo de visao atual da camera, cima baixo
    private float posx;  // posicao x atual da camera
    private float posy;  // posicao y atual da camera, provavelmente nunca sera mudado
    private float posz;  // posicao z atual da camera
    private float zoom;
    ArrayList models;
    private int prevMouseX;
    private int prevMouseY;
    private boolean mousePosKnown = false; 

    public Renderer() {
        this.models = new ArrayList<Model>();
        this.alpha = 0f;
        this.beta = 0f;
        this.zoom = 1f;
        posx = 0f;
        posy = 0f;
        posz = 0f;
    }

    public void init(GLAutoDrawable drawable) {
        try {
            GL gl = drawable.getGL();
            GLU glu = new GLU();
            Model model;
            gl.glEnable(GL.GL_LIGHTING);
            gl.glEnable(GL.GL_LIGHT0);
            gl.glEnable(GL.GL_DEPTH_TEST);
            gl.glShadeModel(GL.GL_SMOOTH);
            lighting(drawable);
            model = new Model(new File("/home/gustavo/USP/CG/PROJETOCG/project-cg-2012/soccerball.obj"));
            model.facetNormals();
            model.vertexNormals(90);
            model.dump(false);
            models.add(model);
            model.compile(drawable, Model.WF_MATERIAL
                    | Model.WF_SMOOTH);


            gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); //define cor de fundo
            gl.glEnable(GL.GL_DEPTH_TEST);
            gl.glShadeModel(GL.GL_SMOOTH);
            
            hideCursor(Main.getCanvas());  /* ESCONDE O CURSOR */
            centerMouse(Main.getCanvas()); /* CENTRALIZA O MOUSE */
        } catch (IOException ex) {
            Logger.getLogger(Renderer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void display(GLAutoDrawable drawable) {
        //função de desenho
        zoom = zoom + 1;
        GL gl = drawable.getGL();
        Iterator it = models.iterator();
        Model model;

        GLUT glut = new GLUT();
        GLU glu = new GLU();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL.GL_MODELVIEW); //define que a matrix e a model view
        gl.glLoadIdentity();
        glu.gluLookAt(posx, posy, posz, //posição da câmera
                posx + Math.cos(alpha), posy + beta, posz + Math.sin(alpha), //para onde a câmera aponta, posy da camera funcionando
                0.0, 1.0, 0.0); //vetor viewup , TODO: nao mexer

        gl.glMatrixMode(GL.GL_PROJECTION); //define que a matrix é a de projeção
        gl.glLoadIdentity();
        glu.gluPerspective(60.0, 1.0, 0.1, 10.0);

        gl.glPushMatrix();
        while (it.hasNext()) {
            gl.glMatrixMode(GL.GL_MODELVIEW); //define que a matrix e a model view
            gl.glRotatef(zoom, 0, 1, 0);
            model = (Model) it.next();
            model.draw(drawable);
        }
        gl.glPopMatrix();




        gl.glFlush(); //processa as rotinas OpenGL o mais rápido possível


    }

    private void lighting(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        float[] ambient = {0.3f, 0.3f, 0.3f, 1.0f};
        float[] diffuse = new float[]{0.75f, 0.75f, 0.75f, 1.0f};
        float[] specular = new float[]{0.7f, 0.7f, 0.7f, 1.0f};
        float[] position = new float[]{10, 10, 50, 1.0f};

        // Define os parametros da luz de numero 0
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, ambient, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, diffuse, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, specular, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, position, 0);
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
        //função para mudança no display
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_PAGE_UP:
                beta = beta + 0.1f;
                break;
            case KeyEvent.VK_PAGE_DOWN:
                beta = beta - 0.1f;
                break;
            case KeyEvent.VK_P:
                alpha = alpha + 0.1f;
                break;
            case KeyEvent.VK_O:
                alpha = alpha - 0.1f;
                break;
            case KeyEvent.VK_UP:
                posz = (float) (posz + 0.1f * Math.sin(alpha));
                posx = (float) (posx + 0.1f * Math.cos(alpha));
                break;
            case KeyEvent.VK_DOWN:
                posz = (float) (posz - 0.1f * Math.sin(alpha));
                posx = (float) (posx - 0.1f * Math.cos(alpha));
                break;
            case KeyEvent.VK_LEFT:
                posz = (float) (posz - 0.1f * Math.cos(alpha));
                posx = (float) (posx + 0.1f * Math.sin(alpha));
                break;
            case KeyEvent.VK_RIGHT:
                posz = (float) (posz + 0.1f * Math.cos(alpha));
                posx = (float) (posx - 0.1f * Math.sin(alpha));
                break;
            case KeyEvent.VK_A:
                Model model = (Model) models.get(0);
                model.writeOBJ("/home/cassianokc/teste.obj", model.WF_COLOR | model.WF_SMOOTH);
                System.out.println("salvou");
                break;
            case KeyEvent.VK_B:
                zoom = zoom * 0.809f;
                break;
        }
        System.out.print("X:" + posx + " Y:" + posy + " Z: " + posz);
        System.out.println();
    }

    public void mouseClicked(MouseEvent e) {
        System.out.print("MOUSE CLICKED");
        System.out.println();
    }
    
    public void mousePressed(MouseEvent e) {
        System.out.print("MOUSE PRESSED");
        System.out.println();
    }
    
    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void mouseDragged(MouseEvent me) {
        
    }
    
    public void mouseMoved(MouseEvent me) {
        //System.out.print("MOUSEX: " + me.getX() + " MOUSEY: " + me.getY());
        //System.out.println();
        processMouseMove(me);
        //centerMouse(Main.getCanvas());
    }
    
    // Keep the cusor in the middle of the window.
  public void centerMouse( GLCanvas canvas )
    {
      //Applet applet = canvas.getParent();
      Point locOnScreen = canvas.getLocationOnScreen();
      // int middleX = locOnScreen.x + (applet.getWidth() / 2);
      // int middleY = locOnScreen.y + (applet.getHeight() / 2);
      int middleX = locOnScreen.x + (canvas.getWidth() / 2);
      int middleY = locOnScreen.y + (canvas.getHeight() / 2);
      try{
	Robot rob = new Robot();
	// Re-setting mouse coordinates.
	rob.mouseMove( middleX, middleY );
	//prevMouseX = middleX;
	//prevMouseY = middleY;
      } catch(Exception e){ System.out.println(e); }
    }

  public boolean isMouseAtBorder( GLCanvas canvas,
				  MouseEvent mouse )
    {
      int curMouseX = mouse.getX();
      int curMouseY = mouse.getY();

      if( curMouseX <= 0 || curMouseY <= 0 ) {
	return true; }
      if( curMouseX >=  canvas.getWidth()-1 || 
	  curMouseY >=  canvas.getHeight()-1 ) {
	return true; }

      return false;
    }
  
  public void processMouseMove( MouseEvent mouse )
    {
      int curMouseX = mouse.getX();
      int curMouseY = mouse.getY();

      /* System.out.println ( "curMouseX:"+curMouseX); //ddd
       System.out.println ( "curMouseY:"+curMouseY); //ddd
       System.out.println ( "prevMouseX:"+prevMouseX); //ddd
      System.out.println ( "prevMouseY:"+prevMouseY); //ddd
       System.out.println ( "mousePosKnown:"+mousePosKnown); //ddd*/

      // Limit max mouse move.
      if( mousePosKnown )
      {
	final int maxStep = 12;

	int rightStep  = curMouseX - prevMouseX;
        //System.out.print("RIGHT:" + rightStep + " = " + curMouseX + " - " + prevMouseX);
        //System.out.println();
	if( rightStep > maxStep )
        { 
            alpha = alpha + 0.08f; /* DIREITA */
            rightStep = maxStep; 
        }
	else if( rightStep < -maxStep ) 
        {
            alpha = alpha - 0.08f; /* ESQUERDA */
            rightStep = -maxStep; 
            
        }

	int upStep  = prevMouseY - curMouseY;
	if( upStep > maxStep )
        {
            upStep = maxStep;
            //beta = beta + 0.075f;      /* CIMA */
        }
	else if( upStep < -maxStep)
        { 
                
            upStep = -maxStep;
            //beta = beta - 0.075f;     /* BAIXO */
        }

	//view.lookRight( rightStep );
	//view.lookUp( upStep );

      } else { 
	mousePosKnown = true; // Called only once.
      }
      prevMouseX = curMouseX;
      prevMouseY = curMouseY;

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