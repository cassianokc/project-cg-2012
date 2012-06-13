package br.usp.icmc.projectcg2012.models;

import com.sun.opengl.util.texture.*;
import java.io.*;
import java.util.StringTokenizer;
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
        float coords[];
        int count1;
        BufferedReader in = null;
        StringTokenizer tok = null;
        String line = null;
        count1 = 0;
        coords = new float[12];
        in = new BufferedReader(new FileReader(file));
        while ((line = in.readLine()) != null)
        {
            line = line.trim();
            if (line.length() > 0)
            {
                if (line.charAt(0) == 'v' && line.charAt(1) == ' ')
                {
                    tok = new StringTokenizer(line, " ");
                    tok.nextToken();
                    coords[count1] = Float.parseFloat(tok.nextToken());
                    count1++;
                }
            }
            count1++;
        }
        x0 = coords[0];
        y0 = coords[1];
        z0 = coords[2];
        x1 = coords[3];
        y1 = coords[4];
        z1 = coords[5];
        x2 = coords[6];
        y2 = coords[7];
        z2 = coords[8];
        x3 = coords[9];
        y3 = coords[10];
        z3 = coords[11];
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
