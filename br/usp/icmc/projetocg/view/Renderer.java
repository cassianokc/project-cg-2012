package br.usp.icmc.projetocg.view;


import br.usp.icmc.projetocg.engine.InputDevice;
import br.usp.icmc.projetocg.main.Main;
import br.usp.icmc.projetocg.models.Model;
import com.sun.opengl.util.GLUT;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class Renderer implements GLEventListener {

    private float alpha;  // angulo de visao atual da camera, direita esquerda
    private float beta;  // angulo de visao atual da camera, cima baixo
    private float posx;  // posicao x atual da camera
    private float posy;  // posicao y atual da camera, provavelmente nunca sera mudado
    private float posz;  // posicao z atual da camera
    private float zoom;
    ArrayList models;
    private InputDevice input;

    public Renderer() {
        this.models = new ArrayList<Model>();
        this.alpha = 0f;
        this.beta = 0f;
        this.zoom = 1f;
        posx = 5f;
        posy = 1f;
        posz = 5f;
        input = new InputDevice(Main.getCanvas(), this);
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
            model = new Model(new File ("/home/gustavo/USP/CG/PROJETOCG/project-cg-2012/skybox.obj"));
            model.facetNormals();
            model.vertexNormals(90);
            model.dump(false);
            models.add(model);
            model.compile(drawable, Model.WF_MATERIAL
                    | Model.WF_COLOR);

            gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); //define cor de fundo
            gl.glEnable(GL.GL_DEPTH_TEST);
            gl.glShadeModel(GL.GL_SMOOTH);
            
            /* MOUSE EVENTS */
            input.hideCursor(Main.getCanvas());  /* ESCONDE O CURSOR */
            input.centerMouse(Main.getCanvas()); /* CENTRALIZA O CURSOR NA TELA */
        } catch (IOException ex) {
            Logger.getLogger(Renderer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void display(GLAutoDrawable drawable) {
        //função de desenho
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
        glu.gluPerspective(60.0, 1.0, 0.1, 30.0);

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
        float[] position = new float[]{10, 10, 5, 1.0f};

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
    
    public void setAlpha(float alpha)
    {
        this.alpha = alpha;
    }
    
    public void setBeta(float beta)
    {
        this.beta = beta;
    }
    
    public void setPosx(float posx)
    {
        this.posx = posx;
    }
    
    public void setPosy(float posy)
    {
        this.posy = posy;
    }
    
    public void setPosz(float posz)
    {
        this.posz = posz;
    }
    
    public void setZoom(float zoom)
    {
        this.zoom = zoom;
    }
    
    public float getAlpha()
    {
        return this.alpha;
    }
    
    public float getBeta()
    {
        return this.beta;
    }
    
    public InputDevice getInput()
    {
        return this.input;
    }
    
    public float getPosx()
    {
        return this.posx;
    }
    
    public float getPosy()
    {
        return this.posy;
    }
    
    public float getPosz()
    {
        return this.posz;
    }
    
    public float getZoom()
    {
        return this.zoom;
    }
}