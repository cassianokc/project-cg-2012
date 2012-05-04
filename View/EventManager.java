package View;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author cassianokc
 *//*
public class EventManager implements MouseListener, KeyListener {
    private Renderer r;
    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_PAGE_UP:
                r.beta = r.beta + 0.1f;
                break;
            case KeyEvent.VK_PAGE_DOWN:
                r.beta = r.beta - 0.1f;
                break;
            case KeyEvent.VK_P:
                r.alpha = r.alpha + 0.1f;
                break;
            case KeyEvent.VK_O:
                r.alpha = r.alpha - 0.1f;
                break;
            case KeyEvent.VK_UP:
                r.posz = (float) (r.posz + 0.1f * Math.sin(alpha));
                r.posx = (float) (r.posx + 0.1f * Math.cos(alpha));
                break;
            case KeyEvent.VK_DOWN:
                r.posz = (float) (r.posz - 0.1f * Math.sin(alpha));
                r.posx = (float) (r.posx - 0.1f * Math.cos(alpha));
                break;
            case KeyEvent.VK_LEFT:
                r.posz = (float) (r.posz - 0.1f * Math.cos(alpha));
                r.posx = (float) (r.posx + 0.1f * Math.sin(alpha));
                break;
            case KeyEvent.VK_RIGHT:
                r.posz = (float) (r.posz + 0.1f * Math.cos(alpha));
                r.posx = (float) (r.posx - 0.1f * Math.sin(alpha));
                break;
            case KeyEvent.VK_B:
                r.zoom = r.zoom * 0.809f;
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
}*/
