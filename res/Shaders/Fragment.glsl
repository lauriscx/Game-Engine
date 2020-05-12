#version 430

out vec4 Pixel;

#include "res/Shaders/Lighting.glsl"

uniform sampler2D DiffuseMap;
uniform sampler2D NormalMap;
uniform sampler2D DisplacmentMap;
uniform PointLight light[MAX_LIGHTS];
uniform DirectionLight Dirlight[MAX_LIGHTS];
uniform int PointLightNumber;
uniform Material material;

in vec2 textureCoords;
in vec3 directionToEye;
in mat3 tangentspace;
in vec3 worldposition;


void main () {
	float height_scale = 0.1;
	float height 	= texture2D(DisplacmentMap, textureCoords).r;
	vec2 p 			= directionToEye.xy / directionToEye.z * (height * height_scale); //pallax mapping
	vec2 Paralax 	= textureCoords;// - p;
	vec3 normal 	= Normal(NormalMap, Paralax);
	vec3 color 		= vec3(0, 0, 0);	

	for(int i = 0; i < PointLightNumber; i++){
		color += CalcPointLight(light[i], material, normal, worldposition, directionToEye, tangentspace);
	}

	color += CalcDirectionLight(Dirlight[0], material, normal, directionToEye, tangentspace);
	
	color += vec3(0.1, 0.1, 0.1);
	Pixel = vec4(color, 1) * (texture2D(DiffuseMap, Paralax) + material.color);
}