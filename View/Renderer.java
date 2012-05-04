package View;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;
import com.sun.opengl.util.GLUT;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;
import Models.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Renderer implements GLEventListener, MouseListener, KeyListener {

    private float alpha;  // angulo de visao atual da camera, direita esquerda
    private float beta;  // angulo de visao atual da camera, cima baixo
    private float posx;  // posicao x atual da camera
    private float posy;  // posicao y atual da camera, provavelmente nunca sera mudado
    private float posz;  // posicao z atual da camera
    private float zoom;
    ArrayList models;

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
            model = new Model(new File("/home/cassianokc/soccerball.obj"));
            model.facetNormals();
            model.vertexNormals(90);
            model.dump(false);
            models.add(model);
            model.compile(drawable, Model.WF_MATERIAL
                    | Model.WF_SMOOTH);


            gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); //define cor de fundo
            gl.glEnable(GL.GL_DEPTH_TEST);
            gl.glShadeModel(GL.GL_SMOOTH);
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

    public static void main(String[] args) {
        //acelera o rendering   
        GLCapabilities caps = new GLCapabilities();
        caps.setDoubleBuffered(true);
        caps.setHardwareAccelerated(true);

        //cria o painel e adiciona um ouvinte GLEventListener
        GLCanvas canvas = new GLCanvas(caps);
        Renderer r;
        r = new Renderer();


        canvas.addGLEventListener(r);

        //cria uma janela e adiciona o painel
        JFrame frame = new JFrame("Projeto");
        frame.getContentPane().add(canvas);
        frame.addKeyListener(r);
        frame.addMouseListener(r);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //inicializa o sistema e chama display() a 60 fps
        Animator animator = new FPSAnimator(canvas, 60);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
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
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        System.out.println("apertou");
    }

    public void mouseReleased(MouseEvent e) {
        System.out.println("Soltou");
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}