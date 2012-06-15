varying vec3 vertex_normal;
varying vec3 vertex_position;
varying vec2 vertex_tex_coord;

void main(void)
{
    
    vertex_position=vec3(gl_ModelViewMatrix*gl_Vertex);
    vertex_normal=normalize(gl_NormalMatrix*gl_Normal);
    vertex_tex_coord=vec2(gl_MultiTexCoord0);
    if (dot(-vertex_position, vertex_normal)<0.0)
        vertex_normal=-vertex_normal;

    gl_Position=gl_ModelViewProjectionMatrix * gl_Vertex;


}