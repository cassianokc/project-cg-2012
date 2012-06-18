package br.usp.icmc.projectcg2012.view;

import br.usp.icmc.projectcg2012.models.*;
import br.usp.icmc.projectcg2012.engine.InputDevice;
import br.usp.icmc.projectcg2012.main.Main;
import com.sun.opengl.util.GLUT;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class Renderer implements GLEventListener
{

    private float alpha;  // camera current degree of vision, right left
    private float beta;  // camera current degree of vision, up and down
    private float posx;  // camera current x position
    private float posy;  // camera current y position
    private float posz;  // camera current z position
    private float ang_senoide;
    private float radius;
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

    public Renderer()
    {
        this.normalModels = new ArrayList<Model>();
        this.animatedModels = new ArrayList<AnimatedModel>();
        this.skyboxModels = new ArrayList<SkyboxModel>();
        this.alpha = 90f;
        this.beta = 0f;
        posx = 1f;
        posy = 1.8f;
        posz = 1f;
        ang_senoide = 0.0f;
        radius = 100000.0f;
        input = new InputDevice(Main.getCanvas(), this);
    }

    public void init(GLAutoDrawable drawable)
    {
        GL gl = drawable.getGL();
        drawable.setGL(new DebugGL(drawable.getGL()));
        gl.glEnable(GL.GL_LIGHTING);
        gl.glEnable(GL.GL_LIGHT0);
        gl.glEnable(GL.GL_LIGHT1);
        gl.glEnable(GL.GL_DEPTH_TEST);
        shader(drawable);
        /*
         * Loads and compiles, adding to the proper array list all models.
         */
        loadSkyboxModels(drawable);
        loadNormalModels(drawable);
        loadAnimatedModels(drawable);

        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // Defines backgroundColor
        input.hideCursor(Main.getCanvas());
        input.centerMouse(Main.getCanvas());

    }

    public void display(GLAutoDrawable drawable)
    {
        GL gl = drawable.getGL();
        Iterator it;
        SkyboxModel skyboxModel;
        AnimatedModel animatedModel;
        Model model;
        GLU glu = new GLU();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
        glu.gluLookAt(posx, 1.8f + Math.sin(ang_senoide) * 0.06f, posz, //camera position
                (radius) * (Math.sin(alpha)) + (posx) * (Math.sin(alpha)), radius * beta + 1.8 + posy + Math.sin(ang_senoide) * 0.06f, (-radius) * (Math.cos(alpha)) + (posz) * (Math.cos(alpha)), //vector to where the camera points
                0.0, 1.0, 0.0); // viewup vector
        lighting(drawable);

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0, 1.0, 0.1, 30.0);
        /*
         * Draws the models.
         */
        it = this.skyboxModels.iterator();
        while (it.hasNext())
        {
            skyboxModel = (SkyboxModel) it.next();
            skyboxModel.draw(drawable);
        }
        it = this.normalModels.iterator();
        while (it.hasNext())
        {
            model = (Model) it.next();
            model.draw(drawable);
        }
        it = this.animatedModels.iterator();
        while (it.hasNext())
        {
            animatedModel = (AnimatedModel) it.next();
            animatedModel.draw(drawable);
        }
        gl.glFlush();


    }

    private void lighting(GLAutoDrawable drawable)
    {
        GL gl = drawable.getGL();
        float[] white =
        {
            1f, 1f, 1f, 1f
        };
        float[] grey =
        {
            0.5f, 0.5f, 0.5f, 1f
        };
        float[] l0position = new float[]
        {
            2.229693f, 2.476059f, 3.166049f, 1f
        };

        float[] l1position = new float[]
        {
            posx, 1.5f, posz - 0.2f, 1.0f
        //0f, 0f, 0f, 1f
        };
        float[] l1direction = new float[]
        {
            (radius) * ((float) Math.sin(alpha)) + (posx) * ((float) Math.sin(alpha)) - posx, radius * beta + posy - 1.6f, (-radius) * ((float) Math.cos(alpha)) + (posz) * ((float) Math.cos(alpha)) - posz, 1.0f
        //0f, 0f, 1f, 1f
        };
        if (Math.random() < 0.1f)
        {
            gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, white, 0);
            gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, white, 0);
            gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, white, 0);
        } else
        {
            gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, grey, 0);
            gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, grey, 0);
            gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, grey, 0);

        }
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, l0position, 0);
        gl.glLightf(GL.GL_LIGHT0, GL.GL_CONSTANT_ATTENUATION, 1f);
        gl.glLightf(GL.GL_LIGHT0, GL.GL_LINEAR_ATTENUATION, 0.0f);
        gl.glLightf(GL.GL_LIGHT0, GL.GL_QUADRATIC_ATTENUATION, 0.1f);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, l1position, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPOT_DIRECTION, l1direction, 0);
        gl.glLightf(GL.GL_LIGHT1, GL.GL_SPOT_CUTOFF, 4f);



    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
    {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged)
    {
        //função para mudança no display
    }

    public void loadSkyboxModels(GLAutoDrawable drawable)
    {
        try
        {
            SkyboxModel model = new SkyboxModel(new File("./project-cg-2012/floortex.obj"));
            model.compile(drawable, Model.WF_MATERIAL | Model.WF_SMOOTH | Model.WF_TEXTURE);
            this.skyboxModels.add(model);
            model = new SkyboxModel(new File("./project-cg-2012/ceillingtex.obj"));
            this.skyboxModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL | Model.WF_SMOOTH | Model.WF_TEXTURE);
            model = new SkyboxModel(new File("./project-cg-2012/wall1tex.obj"));
            this.skyboxModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL | Model.WF_SMOOTH | Model.WF_TEXTURE);
            model = new SkyboxModel(new File("./project-cg-2012/wall2tex.obj"));
            this.skyboxModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL | Model.WF_SMOOTH | Model.WF_TEXTURE);
            model = new SkyboxModel(new File("./project-cg-2012/wall3tex.obj"));
            this.skyboxModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL | Model.WF_SMOOTH | Model.WF_TEXTURE);
            model = new SkyboxModel(new File("./project-cg-2012/wall4tex.obj"));
            this.skyboxModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL | Model.WF_SMOOTH | Model.WF_TEXTURE);
            model = new SkyboxModel(new File("./project-cg-2012/wall5tex.obj"));
            this.skyboxModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL | Model.WF_SMOOTH | Model.WF_TEXTURE);
            model = new SkyboxModel(new File("./project-cg-2012/wall6tex.obj"));
            this.skyboxModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL | Model.WF_SMOOTH | Model.WF_TEXTURE);
            model = new SkyboxModel(new File("./project-cg-2012/wall7tex.obj"));
            this.skyboxModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL | Model.WF_SMOOTH | Model.WF_TEXTURE);
            model = new SkyboxModel(new File("./project-cg-2012/wall8tex.obj"));
            this.skyboxModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL | Model.WF_SMOOTH | Model.WF_TEXTURE);
            model = new SkyboxModel(new File("./project-cg-2012/wall9tex.obj"));
            this.skyboxModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL | Model.WF_SMOOTH | Model.WF_TEXTURE);
            model = new SkyboxModel(new File("./project-cg-2012/wall10tex.obj"));
            this.skyboxModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL | Model.WF_SMOOTH | Model.WF_TEXTURE);
            model = new SkyboxModel(new File("./project-cg-2012/wall11tex.obj"));
            this.skyboxModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL | Model.WF_SMOOTH | Model.WF_TEXTURE);
            model = new SkyboxModel(new File("./project-cg-2012/wall12tex.obj"));
            this.skyboxModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL | Model.WF_SMOOTH | Model.WF_TEXTURE);
            model = new SkyboxModel(new File("./project-cg-2012/wall13tex.obj"));
            this.skyboxModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL | Model.WF_SMOOTH | Model.WF_TEXTURE);
            model = new SkyboxModel(new File("./project-cg-2012/wall14tex.obj"));
            this.skyboxModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL | Model.WF_SMOOTH | Model.WF_TEXTURE);
            model = new SkyboxModel(new File("./project-cg-2012/wall15tex.obj"));
            this.skyboxModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL | Model.WF_SMOOTH | Model.WF_TEXTURE);
            model = new SkyboxModel(new File("./project-cg-2012/wall16tex.obj"));
            this.skyboxModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL | Model.WF_SMOOTH | Model.WF_TEXTURE);
            model = new SkyboxModel(new File("./project-cg-2012/wall17tex.obj"));
            this.skyboxModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL | Model.WF_SMOOTH | Model.WF_TEXTURE);
            model = new SkyboxModel(new File("./project-cg-2012/wall18tex.obj"));
            this.skyboxModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL | Model.WF_SMOOTH | Model.WF_TEXTURE);
        } catch (IOException ex)
        {
            Logger.getLogger(Renderer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadNormalModels(GLAutoDrawable drawable)
    {
        try
        {

            Model model = new Model(new File("./project-cg-2012/toptex.obj"));
            this.normalModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL | Model.WF_SMOOTH | Model.WF_TEXTURE);
            model = new Model(new File("./project-cg-2012/barreltex.obj"));
            this.normalModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL | Model.WF_SMOOTH | Model.WF_TEXTURE);
            model = new Model(new File("./project-cg-2012/cadeira.obj"));
            this.normalModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL | Model.WF_SMOOTH | Model.WF_TEXTURE);
            model = new Model(new File("./project-cg-2012/table.obj"));
            this.normalModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL | Model.WF_SMOOTH | Model.WF_TEXTURE);
            model = new Model(new File("./project-cg-2012/axe.obj"));
            this.normalModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL | Model.WF_SMOOTH | Model.WF_TEXTURE);
            model = new Model(new File("./project-cg-2012/trashcan.obj"));
            this.normalModels.add(model);
            model.compile(drawable, Model.WF_MATERIAL | Model.WF_SMOOTH | Model.WF_TEXTURE);


        } catch (IOException ex)
        {
            Logger.getLogger(Renderer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadAnimatedModels(GLAutoDrawable drawable)
    {
        try
        {
            Fan fan = new Fan(new File("./project-cg-2012/fan.obj"), 0f, 1f, 0f, 2.177557f, 2.903257f, 3.170074f, 0f, 1.8f, 3f, 2.0f);
            this.animatedModels.add(fan);
            fan.compile(drawable, Model.WF_MATERIAL | Model.WF_SMOOTH);
            Door door = new Door(new File("./project-cg-2012/door1tex.obj"), 0f, -1f, 0f, 6.45f, 1.8f, 2.9f, 5.55f, 1.8f, 2.9f, 0.5f);
            this.animatedModels.add(door);
            door.compile(drawable, Model.WF_MATERIAL | Model.WF_SMOOTH | Model.WF_TEXTURE);
            door = new Door(new File("./project-cg-2012/door2tex.obj"), 0f, -1f, 0f, 8.3f, 1.8f, 2.9f, 7.4f, 1.8f, 2.9f, 0.5f);
            this.animatedModels.add(door);
            door.compile(drawable, Model.WF_MATERIAL | Model.WF_SMOOTH | Model.WF_TEXTURE);
            door = new Door(new File("./project-cg-2012/door3tex.obj"), 0f, -1f, 0f, 3.9f, 1.8f, 4.34f, 4.8f, 1.8f, 4.34f, 0.5f);
            this.animatedModels.add(door);
            door.compile(drawable, Model.WF_MATERIAL | Model.WF_SMOOTH | Model.WF_TEXTURE);
            door = new Door(new File("./project-cg-2012/door4tex.obj"), 0f, -1f, 0f, 6.65f, 1.8f, 4.34f, 7.55f, 1.8f, 4.34f, 0.5f);
            this.animatedModels.add(door);
            door.compile(drawable, Model.WF_MATERIAL | Model.WF_SMOOTH | Model.WF_TEXTURE);
            door = new Door(new File("./project-cg-2012/door5tex.obj"), 0f, -1f, 0f, 8.3f, 1.8f, 4.07f, 8.3f, 1.8f, 3.17f, 0.5f);
            this.animatedModels.add(door);
            door.compile(drawable, Model.WF_MATERIAL | Model.WF_SMOOTH | Model.WF_TEXTURE);




        } catch (IOException ex)
        {
            Logger.getLogger(Renderer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void shader(GLAutoDrawable drawable)
    {
        GL gl = drawable.getGL();
        int v = gl.glCreateShader(GL.GL_VERTEX_SHADER);
        int f = gl.glCreateShader(GL.GL_FRAGMENT_SHADER);

        String[] vsrc = readShader("./project-cg-2012/shaders/vertex_shader.c");
        gl.glShaderSource(v, 1, vsrc, null, 0);
        gl.glCompileShader(v);

        String[] fsrc = readShader("./project-cg-2012/shaders/fragment_shader.c");
        gl.glShaderSource(f, 1, fsrc, null, 0);
        gl.glCompileShader(f);

        int shaderprogram = gl.glCreateProgram();
        gl.glAttachShader(shaderprogram, v);
        gl.glAttachShader(shaderprogram, f);
        gl.glLinkProgram(shaderprogram);
        gl.glValidateProgram(shaderprogram);
        gl.glUseProgram(shaderprogram);



    }

    private String[] readShader(String shadername)
    {
        try
        {

            BufferedReader brv = new BufferedReader(new FileReader(shadername));

            StringBuilder buffer = new StringBuilder();
            String line;
            while ((line = brv.readLine()) != null)
            {
                buffer.append(line).append("\r\n");
            }
            brv.close();
            return new String[]
                    {
                        buffer.toString()
                    };
        } catch (IOException ex)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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

    public void setAngSenoide(float ang_senoide)
    {
        this.ang_senoide = ang_senoide;
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

    public float getAngSenoide()
    {
        return this.ang_senoide;
    }

    public ArrayList getAnimatedModels()
    {
        return animatedModels;
    }

    public ArrayList getSkyboxModels()
    {
        return skyboxModels;
    }
}