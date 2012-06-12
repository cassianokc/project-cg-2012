
package br.usp.icmc.projectcg2012.models;

import com.sun.opengl.util.texture.*;
import java.io.File;
import java.io.IOException;
import javax.media.opengl.GLAutoDrawable;
/**
 *
 * @author cassianokc
 */
public class SkyboxPiece extends Model{
    float x0, y0, z0;
    float x1, y1, z1;
    float x2, y2, z2;
    private com.sun.opengl.util.texture.Texture tex;
    public SkyboxPiece(File file) throws IOException{
        super(file);
    }  
     public SkyboxPiece(File file, File tex) throws IOException{
        super(file);
        this.tex = TextureIO.newTexture(tex, false);
    }  
     
     @Override
    public void draw(GLAutoDrawable gLAutoDrawable) {
         super.draw(gLAutoDrawable);
     }
     
     
}


