package br.usp.icmc.projetocg.view;

import br.usp.icmc.projetocg.models.*;
import br.usp.icmc.projetocg.engine.InputDevice;
import br.usp.icmc.projetocg.main.Main;
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

    private float alpha;  // camera current degree of vision, right left
    private float beta;  // camera current degree of vision, up and down
    private float posx;  // camera current x position
    private float posy;  // camera current y position
    private float posz;  // camera current z position
    /*
     * Array containing all normal models.
     */
    private ArrayList normalModels;
    /*
     * Array containing all animated models, latter these array will be searched
     * when someone presses an action button, to see if there it is an animated
     * model in range.
     */
    private ArrayList animatedModels;
    /*
     * Array containing all skybox pieces, latter these array will be seached
     * when someone presses to move, to see if there it occur a collision.
     *
     */
    private ArrayList skyboxModels;
    private InputDevice input;

    public Renderer() {
        this.normalModels = new ArrayList<Model>();
        this.animatedModels = new ArrayList<Model>();
        this.skyboxModels = new ArrayList<SkyboxPiece>();
        this.alpha = 0f;
        this.beta = 0f;
        posx = 5f;
        posy = 1f;
        posz = 5f;
        input = new InputDevice(Main.getCanvas(), this);
    }

    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glEnable(GL.GL_LIGHTING);
        gl.glEnable(GL.GL_LIGHT0);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glShadeModel(GL.GL_SMOOTH);
        lighting(drawable);
        /*
         * Loads and compiles, adding to the proper array list all models.
         */
        loadSkyboxModels(drawable);
        loadNormalModels(drawable);
        loadAnimatedModels(drawable);

        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // Defines backgroundColor
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glShadeModel(GL.GL_SMOOTH);
        input.hideCursor(Main.getCanvas());
        input.centerMouse(Main.getCanvas());

    }

    public void display(GLAutoDrawable drawable) {
        //função de desenho
        GL gl = drawable.getGL();
        Iterator it;
        Model model;
        GLU glu = new GLU();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
        glu.gluLookAt(posx, posy, posz, //camera position
                posx + Math.cos(alpha), posy + beta, posz + Math.sin(alpha), //vector to where the camera points
                0.0, 1.0, 0.0); // viewup vector

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0, 1.0, 0.1, 30.0);
        /*
         * Draws the models.
         */
        it = this.skyboxModels.iterator();
        while (it.hasNext()) {
            model = (Model) it.next();
            model.draw(drawable);
        }
        it = this.normalModels.iterator();
        while (it.hasNext()) {
            model = (Model) it.next();
            model.draw(drawable);
        }
        it = this.animatedModels.iterator();
        while (it.hasNext()) {
            model = (Model) it.next();
            model.draw(drawable);
        }
        gl.glFlush();


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
    
    

    public void loadSkyboxModels(GLAutoDrawable drawable) {
        try {
            SkyboxPiece model = new SkyboxPiece(new File("./project-cg-2012/floor.obj"));
            this.skyboxModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL);
            model = new SkyboxPiece(new File("./project-cg-2012/ceilling.obj"));
            this.skyboxModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL);
            model = new SkyboxPiece(new File("./project-cg-2012/wall1.obj"));
            this.skyboxModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL);
            model = new SkyboxPiece(new File("./project-cg-2012/wall2.obj"));
            this.skyboxModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL);
            model = new SkyboxPiece(new File("./project-cg-2012/wall3.obj"));
            this.skyboxModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL);
            model = new SkyboxPiece(new File("./project-cg-2012/wall4.obj"));
            this.skyboxModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL);
            model = new SkyboxPiece(new File("./project-cg-2012/wall5.obj"));
            this.skyboxModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL);
            model = new SkyboxPiece(new File("./project-cg-2012/wall6.obj"));
            this.skyboxModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL);
            model = new SkyboxPiece(new File("./project-cg-2012/wall7.obj"));
            this.skyboxModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL);
            model = new SkyboxPiece(new File("./project-cg-2012/wall8.obj"));
            this.skyboxModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL);



        } catch (IOException ex) {
            Logger.getLogger(Renderer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadNormalModels(GLAutoDrawable drawable) {
    }

    public void loadAnimatedModels(GLAutoDrawable drawable) {
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public void setBeta(float beta) {
        this.beta = beta;
    }

    public void setPosx(float posx) {
        this.posx = posx;
    }

    public void setPosy(float posy) {
        this.posy = posy;
    }

    public void setPosz(float posz) {
        this.posz = posz;
    }

    public float getAlpha() {
        return this.alpha;
    }

    public float getBeta() {
        return this.beta;
    }

    public InputDevice getInput() {
        return this.input;
    }

    public float getPosx() {
        return this.posx;
    }

    public float getPosy() {
        return this.posy;
    }

    public float getPosz() {
        return this.posz;
    }
}