package br.usp.icmc.projectcg2012.models;

import com.sun.opengl.util.texture.*;
import java.io.File;
import java.io.IOException;
import javax.media.opengl.GLAutoDrawable;

/**
 *
 * @author cassianokc
 */
public class SkyboxModel extends Model
{

    float x0, y0, z0;
    float x1, y1, z1;
    float x2, y2, z2;
    float x3, y3, z3;

    public SkyboxModel(File file) throws IOException
    {
        super(file);
    }

    @Override
    public void draw(GLAutoDrawable gLAutoDrawable)
    {
        super.draw(gLAutoDrawable);
    }

    public boolean collision(float x, float y, float z,
            float xn, float yn, float zn, float h)
    /*
     * TODO IMPLEMENTAR PARA TRATAR COLISAO. Dado um ponto (x, y, z), e uma
     * direcao (xn, yn, zn), dizer se esse vetor interceptara o plano definido
     * por a=(x0, y0, z0), b=(x1, y1, z1), c=(x2, y2, z2) e d=(x3, y3, z3) em
     * uma distancia entre 0 e h.
     */
    {

        return false;
    }
}
