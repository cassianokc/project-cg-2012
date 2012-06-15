varying vec3 vertex_normal;
varying vec3 vertex_position;
uniform sampler2D texture;
varying vec2 vertex_tex_coord;



void main(void)
{
    float l0linear_att;
    vec4 l0contrib, l1contrib=vec4(0.0, 0.0, 0.0, 0.0);
    vec3 eye_direction, l0light_direction, l0reflection_direction, l1light_direction, l1reflection_direction, l1spot_direction;
    vec4 Iamb, Idiff, Ispec;
    
    eye_direction=normalize(-vertex_position);

    //Calculates Light0 contribution 
    l0light_direction=gl_LightSource[0].position.xyz-vertex_position;
    l0linear_att=length(l0light_direction);
    l0linear_att=1.0/(gl_LightSource[0].constantAttenuation+
            (gl_LightSource[0].linearAttenuation*l0linear_att) +
            (gl_LightSource[0].quadraticAttenuation*l0linear_att*l0linear_att));
    l0light_direction=normalize(l0light_direction);
    l0reflection_direction=normalize(-reflect(l0light_direction, vertex_normal));
    Iamb=gl_FrontLightProduct[0].ambient*gl_LightSource[0].ambient;
    Idiff=gl_FrontLightProduct[0].diffuse*gl_LightSource[0].diffuse*max(dot(vertex_normal, l0light_direction), 0.0);
    Ispec=gl_FrontLightProduct[0].specular*gl_LightSource[0].specular
            *pow(max(dot(l0reflection_direction, eye_direction), 0.0), gl_FrontMaterial.shininess);
    l0contrib=Iamb+Idiff+Ispec;
    //Calculates Light1 contribution
    l1light_direction=gl_LightSource[1].position.xyz-vertex_position;
    l1spot_direction=normalize(gl_LightSource[1].spotDirection);
    l1light_direction=normalize(l1light_direction);
    l1reflection_direction=normalize(-reflect(l1light_direction, vertex_normal));

    if (dot(-l1light_direction, l1spot_direction)>0.97)
    {

        l1contrib=vec4(1.0, 1.0, 1.0, 1.0);
    }

    gl_FragColor=texture2D(texture, vertex_tex_coord)*(l0contrib*l0linear_att+l1contrib);
}