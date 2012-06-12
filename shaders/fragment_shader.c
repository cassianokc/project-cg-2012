varying vec3 N;
varying vec3 v;
uniform sampler2D myTexture;
varying vec2 vTexCoord;

void main(void)
{
    float d;
    vec4 finalColor=vec4(0.0, 0.0, 0.0, 0.0);
    
    vec3 L=gl_LightSource[0].position.xyz-v;
    d = length(L);
    d = 1.0 / ( gl_LightSource[0].constantAttenuation + 
	(gl_LightSource[0].linearAttenuation*d) + 
	(gl_LightSource[0].quadraticAttenuation*d*d) );
    L = normalize(L);
    vec3 E=normalize(-v); // we are in Eye Coordinates, so EyePos is (0,0,0)  
    vec3 R=normalize(-reflect(L, N));

    //calculate Ambient Term:  
    vec4 Iamb=gl_FrontLightProduct[0].ambient*gl_LightSource[0].ambient;
    ;

    //calculate Diffuse Term:  
    vec4 Idiff=gl_FrontLightProduct[0].diffuse*gl_LightSource[0].diffuse*max(dot(N, L), 0.0) ;

    // calculate Specular Term:
    vec4 Ispec=gl_FrontLightProduct[0].specular*gl_LightSource[0].specular
            *pow(max(dot(R, E), 0.0), 0.3*gl_FrontMaterial.shininess);

    finalColor=Iamb+Idiff+Ispec;
    // write Total Color:
    gl_FragColor=((texture2D(myTexture, vTexCoord))+finalColor)*d;
}
