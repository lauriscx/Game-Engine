#version 430

in vec3 Position;
in vec2 UV;
in vec3 Color;
in vec3 Normal;
in vec3 Tangent;
in vec3 BiTangent;
in vec4 WeightValue;
in ivec4 VertexIndex;

uniform mat4 ModelTransformation;
uniform mat4 Camera;
uniform mat4 Projection;

out vec2 textureCoords;
out vec3 directionToEye;
out mat3 tangentspace;
out vec3 worldposition;
out vec3 cameraPosition;

void main () {
	gl_PointSize = 10.0;
	vec4 worldPosition 	= ModelTransformation * vec4(Position, 1.0);
	gl_Position 		= Projection * Camera * worldPosition;
	textureCoords 		= UV;

	vec3 tangent 	= normalize((ModelTransformation * vec4(Tangent, 0.0)).xyz);
	vec3 biTangent  = normalize((ModelTransformation * vec4(BiTangent, 0.0)).xyz);
	vec3 normal 	= normalize((ModelTransformation * vec4(Normal, 0.0)).xyz);

	tangent = normalize(tangent - dot(tangent, normal) * normal);// make sure angle between normal and tangent is 90 degres

	//mat3 TangentSpace = mat3(tangent, biTangent, normal);
	
	
	mat3 TangentSpace = mat3(
		tangent.x, biTangent.x, normal.x,
		tangent.y, biTangent.y, normal.y,
		tangent.z, biTangent.z, normal.z
	);
	
	tangentspace 	= TangentSpace;
	worldposition 	= TangentSpace * worldPosition.xyz;
	cameraPosition 	= TangentSpace * ((inverse(Camera) * vec4(0, 0, 0, 1)).xyz);
	directionToEye 	= normalize( cameraPosition - worldposition );
}