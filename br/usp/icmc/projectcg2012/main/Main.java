/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.icmc.projectcg2012.main;

import br.usp.icmc.projectcg2012.view.Renderer;
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.swing.JFrame;

/**
 *
 * @author gustavo
 */
public class Main
{

    private static GLCanvas canvas;
    private static GLCapabilities caps;
    //private static InputDevice mouse;
    private static Renderer renderer;

    public static void main(String[] args)
    {
        //acelera o rendering   
        caps = new GLCapabilities();
        caps.setDoubleBuffered(true);
        caps.setHardwareAccelerated(true);

        //cria o painel e adiciona um ouvinte GLEventListener
        canvas = new GLCanvas(caps);
        renderer = new Renderer();

        canvas.addGLEventListener(renderer);
        canvas.addKeyListener(renderer.getInput());
        canvas.addMouseListener(renderer.getInput());
        canvas.addMouseMotionListener(renderer.getInput());


        //cria uma janela e adiciona o painel
        JFrame frame = new JFrame("Projeto");
        frame.getContentPane().add(canvas);
        //frame.addKeyListener(r);
        //frame.addMouseListener(r);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //inicializa o sistema e chama display() a 30 fps
        Animator animator = new FPSAnimator(canvas, 30);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }

    public static GLCanvas getCanvas()
    {
        return Main.canvas;
    }

    public static Renderer getRenderer()
    {
        return Main.renderer;
    }
}
