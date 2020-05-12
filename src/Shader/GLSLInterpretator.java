package Shader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class GLSLInterpretator {
	private Map<String, Pair<String, String>> constants = new HashMap<String, Pair<String, String>>();
	private List<String> textureTypes = new ArrayList<String>();
	
	GLSLInterpretator(){
		textureTypes.add("sampler1D");
		textureTypes.add("sampler2D");
		textureTypes.add("sampler3D");
		textureTypes.add("samplerCube");
		textureTypes.add("sampler2DRect");
		textureTypes.add("sampler1DArray");
		textureTypes.add("sampler2DArray");
		textureTypes.add("samplerCubeArray");
		textureTypes.add("samplerBuffer");
		textureTypes.add("sampler2DMS");
		textureTypes.add("sampler2DMSArray");
	}
	
	public String Interpretator	(String sourceCode) {
		StringBuilder Code = new StringBuilder();
		String lines[] = sourceCode.split("\\r?\\n");
		for(int l = 0; l < lines.length; l++) {
			if(lines[l].startsWith("#version")) {
				Code.append(lines[l] + "\n");
				for(Entry<String, Pair<String, String>> constant : constants.entrySet()) {
					Code.append("const " + constant.getValue().getFirst() + " " + constant.getKey() + " = " + constant.getValue().getSecond() + ";\n");
				}
			} else {
				if(lines[l].startsWith("#include")) {
					int StartIndex 	= lines[l].indexOf("\"") + 1;
					int EndIndex 	= lines[l].indexOf("\"", StartIndex);
					String Path = lines[l].substring(StartIndex, EndIndex);
					Code.append(Interpretator(readFile(Path)) + "\n");
				} else {
					Code.append(lines[l] + "\n");
				}
			}
		}
		//System.out.println(Code.toString());
		return Code.toString();
	}
	public String readFile		(String file) {
		try {
			return new String(Files.readAllBytes(Paths.get(file)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Error";
	}
	public void addConstants	(String type, String name, String value) {
		constants.put(name,  new Pair<String, String>(type, value));
	}
	public void findUniforms	(String code, List<Pair<String, String>> uniforms) {
		StringParser parser = new StringParser();
		parser.setSource(code);
		parser.removeComments();
		String Temp;
		while(!parser.parsed()) {
			Temp = parser.parseChunk("uniform ", ";");
			for(Entry<String, Pair<String, String>> constant : constants.entrySet()) {
				if(Temp.contains("[" + constant.getKey() + "]")) {
					for(int i= 0; i < Integer.parseInt(constant.getValue().getSecond()); i++) {
						String temp = Temp.replace("[" + constant.getKey() + "]", "[" + i + "]");
						uniforms.add(new Pair<String, String>(StringParser.word(temp, 0), StringParser.word(temp, 1)));
					}
				} else {
					uniforms.add(new Pair<String, String>(StringParser.word(Temp, 0), StringParser.word(Temp, 1)));					
				}
			}
		}
	}
	public void findStructs		(String code, Map<String, List<Pair<String, String>>> structs) {
		List<Pair<String, String>> stuctContent;
		
		StringParser parserStruct = new StringParser();
		StringParser parserContent = new StringParser();
		parserStruct.setSource(code);
		parserStruct.removeComments();
		
		String parsedStruct;
		String structName;
		String Temp;
		
		while(!parserStruct.parsed()) {
			stuctContent = new ArrayList<Pair<String, String>>();
			parsedStruct = parserStruct.parseChunk("struct ", "};").trim();//last edit trim added
			parserContent.setSource(parsedStruct);
			parserContent.reset();
			structName = parserContent.parseChunk("", "{").trim();
			while(!parserContent.parsed()) {
				Temp = parserContent.parseChunk("", ";").trim();
				if(Temp.length() > 0) {
					stuctContent.add(new Pair<String, String>(StringParser.word(Temp, 0), StringParser.word(Temp, 1)));
				}
			}
			structs.put(structName, stuctContent);
		}
	}
	public void parseUniforms	(Map<String, List<Pair<String, String>>> structs, Pair<String, String> rawUniform, List<String> uniform, String prefix, Map<String, Pair<Integer, Integer>> textures) {
		if(structs.containsKey(rawUniform.getFirst())) {
			prefix += rawUniform.getSecond() + ".";
			List<Pair<String, String>> contents = structs.get(rawUniform.getFirst());
			for(Pair<String, String> content : contents) {
				parseUniforms(structs, content, uniform, prefix, textures);
			}	
		} else {
			for(String texture : textureTypes) {
				if(texture.equals(rawUniform.getFirst())) {
					textures.put(prefix + rawUniform.getSecond(), new Pair<Integer, Integer>(-1, -1));
					break;
				} else {
					uniform.add(prefix + rawUniform.getSecond());
				}
			}
		}
	}
	public void parseAttributes	(String code, Map<String, Integer> attributes) {
		StringParser parser = new StringParser();
		parser.setSource(code);
		parser.removeComments();
		int i = 0;
		while(!parser.parsed()) {
			//Parse from glsl (Layout = 0 (index))
			String temp = parser.parseChunk("in ", ";");
			attributes.put(temp, i);
			//System.out.println(temp + " " + i);
			i++;
		}
	}
}