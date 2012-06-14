varying vec3 vertex_normal;
varying vec3 vertex_position;
uniform sampler2D texture;
varying vec2 vertex_tex_coord;

void main(void)
{
    float l0linear_att;
    vec4 l0contrib;
    vec3 l0light_direction, l0eye_direction, l0reflection_direction;
    vec4 Iamb, Idiff, Ispec;
    //Calculates Light0 contribution 
    l0light_direction=gl_LightSource[0].position.xyz-vertex_position;
    l0linear_att=length(l0light_direction);
    l0linear_att=1.0/(gl_LightSource[0].constantAttenuation+
            (gl_LightSource[0].linearAttenuation*l0linear_att) +
            (gl_LightSource[0].quadraticAttenuation*l0linear_att*l0linear_att));
    l0light_direction=normalize(l0light_direction);
    l0eye_direction=normalize(-vertex_position);
    l0reflection_direction=normalize(-reflect(l0light_direction, vertex_normal));
    Iamb=gl_FrontLightProduct[0].ambient*gl_LightSource[0].ambient;  
    Idiff=gl_FrontLightProduct[0].diffuse*gl_LightSource[0].diffuse*max(dot(vertex_normal, l0light_direction), 0.0);
    Ispec=gl_FrontLightProduct[0].specular*gl_LightSource[0].specular
            *pow(max(dot(l0reflection_direction, l0eye_direction), 0.0), gl_FrontMaterial.shininess);
    l0contrib=Iamb+Idiff+Ispec;
 
    gl_FragColor=l0contrib*l0linear_att+texture2D(texture, vertex_tex_coord);
}