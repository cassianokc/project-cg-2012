
package br.usp.icmc.projectcg2012.models;

import java.io.File;
import java.io.IOException;
/**
 *
 * @author cassianokc
 */
public class SkyboxPiece extends Model{
    float x0, y0, z0;
    float x1, y1, z1;
    float x2, y2, z2;
    public SkyboxPiece(File file) throws IOException{
        super(file);
    }    
}


