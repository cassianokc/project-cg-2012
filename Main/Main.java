/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import View.Renderer;
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.swing.JFrame;

/**
 *
 * @author gustavo
 */
public class Main {
    private static GLCanvas canvas;
    private static GLCapabilities caps;
    
    public static void main(String[] args) {
        //acelera o rendering   
        caps = new GLCapabilities();
        caps.setDoubleBuffered(true);
        caps.setHardwareAccelerated(true);

        //cria o painel e adiciona um ouvinte GLEventListener
        canvas = new GLCanvas(caps);
        Renderer r;
        r = new Renderer();


        canvas.addGLEventListener(r);
        canvas.addKeyListener(r);
        canvas.addMouseListener(r);
        canvas.addMouseMotionListener(r);
        

        //cria uma janela e adiciona o painel
        JFrame frame = new JFrame("Projeto");
        frame.getContentPane().add(canvas);
        //frame.addKeyListener(r);
        //frame.addMouseListener(r);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //inicializa o sistema e chama display() a 60 fps
        Animator animator = new FPSAnimator(canvas, 60);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }
    
    public static GLCanvas getCanvas(){
        return Main.canvas;
    }
}
