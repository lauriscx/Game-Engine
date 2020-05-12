package Shader;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniformMatrix2fv;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.opengl.GL30.glBindFragDataLocation;
import static org.lwjgl.opengl.GL40.glUniform1d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.joml.Matrix2f;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Shader {	
	private Map<String, Pair<String, Integer>> vertex 		= new HashMap<String, Pair<String, Integer>>();/*FileName, <Code, ShaderID>*/
	private Map<String, Pair<String, Integer>> geometry 	= new HashMap<String, Pair<String, Integer>>();/*FileName, <Code, ShaderID>*/
	private Map<String, Pair<String, Integer>> fragment 	= new HashMap<String, Pair<String, Integer>>();/*FileName, <Code, ShaderID>*/
	
	private Map<String, Integer> attributes 				= new HashMap<String, Integer>();/*Name, UniformID*/
	private Map<String, Integer> fragments 					= new HashMap<String, Integer>();/*Name, UniformID*/
	private Map<String, Integer> uniforms 					= new HashMap<String, Integer>();/*Name, UniformID*/
	private Map<String, Pair<Integer, Integer>> textures 	= new HashMap<String, Pair<Integer, Integer>>();/*Name, <SlotID, UniformID>*/
	
	private GLSLInterpretator interpretator = new GLSLInterpretator();
	
	private int ID;
	private int programID;
	
	public Shader() {
		interpretator.addConstants("int", "MAX_LIGHTS", "100");
		
		this.ID = ShadersList.AddShader(this);
		this.programID = glCreateProgram();
		
		addAttribute("Position"		, 0);
		addAttribute("UV"			, 1);
		addAttribute("Color"		, 2);
		addAttribute("Normal"		, 3);
		addAttribute("Tangent"		, 4);
		addAttribute("BiTangent"	, 5);
		addAttribute("WeightValue"	, 6);
		addAttribute("VertexIndex"	, 7);
	}
	

	private void parseData		(String code) {
		Map<String, List<Pair<String, String>>> structs = new HashMap<String, List<Pair<String, String>>>();
		interpretator.findStructs(code, structs);
		
		List<Pair<String, String>> rawUniforms = new ArrayList<Pair<String, String>>();
		interpretator.findUniforms(code, rawUniforms);

		List<String> uniforms = new ArrayList<String>();
		for(Pair<String, String> rawUniform : rawUniforms) {
			String prefix = new String();
			interpretator.parseUniforms(structs, rawUniform, uniforms, prefix, textures);
		}
		
		for(String uniform : uniforms) {
			addUniform(uniform);
		}
		
		/*Map<String, Integer> attributes = new HashMap<String, Integer>();
		parseAttributes(code, attributes);
		for(Entry<String, Integer> attribute : attributes.entrySet()) {
			addAttribute(attribute.getKey(), attribute.getValue());
		}*/
	}

	public void addVertexFile	(String file) {
		String nativeCode = interpretator.readFile(file);
		addVertexCode(file, nativeCode);
	}
	public void addGeometryFile	(String file) {
		String nativeCode = interpretator.readFile(file);
		addGeometryCode(file, nativeCode);
	}
	public void addFragmentFile	(String file) {
		String nativeCode = interpretator.readFile(file);
		addFragmentCode(file, nativeCode);
	}
	
	public void addVertexCode	(String name, String code) {
		code = interpretator.Interpretator(code);
		parseData(code);
		vertex.put(name, new Pair<String, Integer>(code, -1));
	}
	public void addGeometryCode	(String name, String code) {
		code = interpretator.Interpretator(code);
		parseData(code);
		geometry.put(name, new Pair<String, Integer>(code, -1));
	}
	public void addFragmentCode	(String name, String code) {
		code = interpretator.Interpretator(code);
		parseData(code);
		fragment.put(name, new Pair<String, Integer>(code, -1));
	}
		
	public int 	getID	() {
		return this.ID;
	}
	public int 	program	() {
		return this.programID;
	}
	public void compile	() {
		for (Entry<String, Pair<String, Integer>> code : vertex.entrySet()) {
			code.getValue().setSecond(glCreateShader(GL_VERTEX_SHADER));
			glShaderSource(code.getValue().getSecond(), code.getValue().getFirst());
			glCompileShader(code.getValue().getSecond());
			glAttachShader(programID, code.getValue().getSecond());
		}

		for (Entry<String, Pair<String, Integer>> code : geometry.entrySet()) {
			code.getValue().setSecond(glCreateShader(GL_GEOMETRY_SHADER));
			glShaderSource(code.getValue().getSecond(), code.getValue().getFirst());
			glCompileShader(code.getValue().getSecond());
			glAttachShader(programID, code.getValue().getSecond());
		}
		
		for (Entry<String, Pair<String, Integer>> code : fragment.entrySet()) {
			code.getValue().setSecond(glCreateShader(GL_FRAGMENT_SHADER));
			glShaderSource(code.getValue().getSecond(), code.getValue().getFirst());
			glCompileShader(code.getValue().getSecond());
			glAttachShader(programID, code.getValue().getSecond());
		}

		setAttributes();
		
		glLinkProgram(programID);

		if (glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE) {
		    System.err.println("Shader: " + programID + "\ncompaling error: " + glGetProgramInfoLog(programID, glGetProgrami(programID, GL_INFO_LOG_LENGTH)));
		}
		
		glValidateProgram(programID);
		
		setupUniforms();
		setupTextures();
	}
	public void start	() {
		glUseProgram(programID);
	}
	public void stop	() {
		glUseProgram(0);
	}

	public void update() {
		start();
		sendUniformPointLight		("light"	, LightsLists.pointLights());
		sendUniformDirectionLight	("Dirlight"	, LightsLists.directionLights());
		sendUniformSpotLight		("Spotlight", LightsLists.spotLights());
		
		sendUniform("PointLightNumber"		, LightsLists.pointLights().size());
		sendUniform("DirectionLightNumber"	, LightsLists.directionLights().size());
		sendUniform("SpotLightNumber"		, LightsLists.spotLights().size());
	}
	
	public void addAttribute	(String name, int value	) {
		attributes.put(name, value);
	}
	public void setAttributes	(						) {
		for(Entry<String, Integer> attribute : attributes.entrySet()) {
			glBindAttribLocation(this.programID, attribute.getValue(), attribute.getKey());
		}
	}
	
	public void addUniform				(String name										) {
		uniforms.put(name, -1);
	}
	public void addUniform				(String ... name									) {
		for(String _name : name) {
			uniforms.put(_name, -1);
		}
	}
	public void AddStructureUniform		(String name, String ... variables					) {
		for(String _name : variables) {
			uniforms.put(name + "." + _name, -1);
		}
	}

	public void AddUniformArray			(String name, int from, int to						) {
		for(int i = from; i < to; i++) {
			uniforms.put(name + "[" + i + "]", -1);
		}
	}
	public void AddStructureUniformArray(String name, int from, int to, String ... variables) {
		for(int i = from; i < to; i++) {
			for(String _name : variables) {
				uniforms.put(name + "[" + i + "]." + _name, -1);
			}
		}
	}
	public void setupUniforms			(													) {
		start();
		for (Entry<String, Integer> entry : uniforms.entrySet()) {
			String Name = entry.getKey();
			int Location = glGetUniformLocation(programID, Name);
			entry.setValue(Location);
			if(Location == -1) {
				System.err.println("Uniform " + Name + " " + Location + " setup failed!");
			}
		}
		stop();
	}

	public void addTextures		(String ... names) {
		for(String name : names) {
			textures.put(name, new Pair<Integer, Integer>(-1, 0));
		}
	}
	public void setupTextures	(				) {
		start();
		int i = 0;
		for (Entry<String, Pair<Integer, Integer>> texture : textures.entrySet()) {
			texture.getValue().setFirst(glGetUniformLocation(programID, texture.getKey()));//set texture unifrom location.
			texture.getValue().setSecond(i);//saves texture id for render bind slot.
			glUniform1i(texture.getValue().getFirst(), i);//set texture id.
			i++;
		}
		stop();
	}
	public int 	getTextureSlot	(String name	) {
		return textures.get(name).getSecond();
	}
	
	public void addFragment		(String name, int index	) {
		fragments.put(name, index);
	}
	public void setupFragments	(						) {
		for(Entry<String, Integer> fragment : fragments.entrySet()) {
			glBindFragDataLocation(programID, 3, fragment.getKey());
		}
	}
	
	/* Implement uniform blocks */
	public void AddUniformBlock		(String name, int index, int size	) {
	}
	public void SetUniformBlockIndex(String name, int index				) {
	}
	public void SetUniformBlockSize	(String name, int size				) {
	}
	public void SetUpUniformBlocks	(									) {
		start();
		System.err.println("Not implemented yet!");
		stop();
	}
	public void SendUniformBlocks	(String name, Object data			) {
	}
	
	private int getLocation	(String name) 	{
		if(uniforms.get(name) != null) {
			return uniforms.get(name);
		}
		return 0;
	}
	
	public void sendUniform(String name, int 		value) {
		glUniform1i(getLocation(name), value);
	}
	public void sendUniform(String name, float 		value) {
		glUniform1f(getLocation(name), value);
	}
	public void sendUniform(String name, double 	value) {
		glUniform1d(getLocation(name), value);
	}
	public void sendUniform(String name, boolean 	value) {
		int _value = 0;
		if(value) {
			_value = 1;
		}
		glUniform1i(getLocation(name), _value);
	}
	public void sendUniform(String name, Vector2f 	value) {
		glUniform2f(getLocation(name), value.x, value.y);
	}
	public void sendUniform(String name, Vector3f 	value) {
		glUniform3f(getLocation(name), value.x, value.y, value.z);
	}
	public void sendUniform(String name, Vector4f 	value) {
		glUniform4f(getLocation(name), value.x, value.y, value.z, value.w);
	}
	public void sendUniform(String name, Matrix2f 	value) {
		float[] data = new float[2 * 2];
		value.get(data);
		glUniformMatrix2fv(getLocation(name), false, data);
	}
	public void sendUniform(String name, Matrix3f 	value) {
		float[] data = new float[3 * 3];
		value.get(data);
		//glUniformMatrix3fv(getLocation(name), false, MatrixBuffer3);
		
	}
	public void sendUniform(String name, Matrix4f 	value) {
		float[] data = new float[4 * 4];
		value.get(data);
		glUniformMatrix4fv(getLocation(name), false, data);
	}
	public void sendUniform(String name, int[] 		value) {
		for(int i = 0; i < value.length; i++) {
			sendUniform(name+"["+i+"]", value[i]);
		}
	}
	public void sendUniform(String name, float[] 	value) {
		for(int i = 0; i < value.length; i++) {
			sendUniform(name+"["+i+"]", value[i]);
		}
	}
	public void sendUniform(String name, double[] 	value) {
		for(int i = 0; i < value.length; i++) {
			sendUniform(name+"["+i+"]", value[i]);
		}
	}
	public void sendUniform(String name, boolean[] 	value) {
		for(int i = 0; i < value.length; i++) {
			sendUniform(name+"["+i+"]", value[i]);
		}
	}
	public void sendUniform(String name, Vector2f[] value) {
		for(int i = 0; i < value.length; i++) {
			sendUniform(name+"["+i+"]", value[i]);
		}
	}
	public void sendUniform(String name, Vector3f[] value) {
		for(int i = 0; i < value.length; i++) {
			sendUniform(name+"["+i+"]", value[i]);
		}
	}
	public void sendUniform(String name, Vector4f[] value) {
		for(int i = 0; i < value.length; i++) {
			sendUniform(name+"["+i+"]", value[i]);
		}
	}
	public void sendUniform(String name, Matrix2f[] value) {
		for(int i = 0; i < value.length; i++) {
			sendUniform(name+"["+i+"]", value[i]);
		}	
	}
	public void sendUniform(String name, Matrix3f[] value) {
		for(int i = 0; i < value.length; i++) {
			sendUniform(name+"["+i+"]", value[i]);
		}
	}
	public void sendUniform(String name, Matrix4f[] value) {
		for(int i = 0; i < value.length; i++) {
			if(value[i] != null) {
				sendUniform(name + "[" + i + "]", value[i]);
			}
		}
	}

	public void sendUniformDirectionLight	(String name, List<DirectionLight> value) {
		for(int i = 0; i < value.size(); i++) {
			if(value.get(i) != null) {
				sendUniform(name + "[" + i + "].light.intensivity", value.get(i).intensivity());
				sendUniform(name + "[" + i + "].light.color"		, value.get(i).color());
				sendUniform(name + "[" + i + "].direction"		, value.get(i).Direction());
			}
		}
	}
	public void sendUniformPointLight		(String name, List<PointLight> 	value	) {
		for(int i = 0; i < value.size(); i++) {
			if(value.get(i) != null) {
				sendUniform(name + "[" + i + "].baselight.intensivity"	, value.get(i).intensivity());
				sendUniform(name + "[" + i + "].constant"				, value.get(i).attenuationConstant());
				sendUniform(name + "[" + i + "].linear"					, value.get(i).attenuationLinear());
				sendUniform(name + "[" + i + "].quadratic"				, value.get(i).attenuationQuadratic());
				sendUniform(name + "[" + i + "].position"				, value.get(i).Position());
				sendUniform(name + "[" + i + "].baselight.color"		, value.get(i).color());
				sendUniform(name + "[" + i + "].range"					, value.get(i).Range());
			}
		}
	}
	public void sendUniformSpotLight		(String name, List<SpotLight> 	value	) {
		for(int i = 0; i < value.size(); i++) {
			if(value.get(i) != null) {
				sendUniform(name + "[" + i + "].intensivity", value.get(i).intensivity());
				sendUniform(name + "[" + i + "].color"		, value.get(i).color());
			}
		}
	}
	
	public void cleanUp	() {
			stop();
			
			for (Entry<String, Pair<String, Integer>> code : vertex.entrySet()) {
				glDetachShader(this.programID, code.getValue().getSecond());
				glDeleteShader(code.getValue().getSecond());
			}

			for (Entry<String, Pair<String, Integer>> code : geometry.entrySet()) {
				glDetachShader(this.programID, code.getValue().getSecond());
				glDeleteShader(code.getValue().getSecond());
			}
			
			for (Entry<String, Pair<String, Integer>> code : fragment.entrySet()) {
				glDetachShader(this.programID, code.getValue().getSecond());
				glDeleteShader(code.getValue().getSecond());
			}

			glDeleteProgram(this.programID);
		}
}