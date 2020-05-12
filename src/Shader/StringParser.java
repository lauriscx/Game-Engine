package Shader;

public class StringParser {
	private int position = 0;
	private String source;
	private boolean parsed = false;
	
	public int 		setSource		(String source) {
		this.source = source;
		return source.length();
	}
	public String 	getSource		() {
		return source;
	}
	public boolean	parsed			() {
		return parsed;
	}
	public void 	set				(int possition) {
		position = possition;
	}
	public void 	reset			() {
		position = 0;
		parsed = false;
	}
	public String 	parseFullChunk	(String beginingKeyWord, String endKeyWord) {
		int begindex = source.indexOf(beginingKeyWord, position);
		int endindex =  source.indexOf(endKeyWord, begindex);
		if(begindex != -1 && endindex != -1) {
			position = endindex + endKeyWord.length();
			parsed = !source.substring(position - 1, source.length()).contains(beginingKeyWord);
			return source.substring(begindex, endindex + endKeyWord.length()).replaceAll("\\s+", " ");
		}
		parsed = true;
		return "";
	}
	public String 	parseChunk		(String beginingKeyWord, String endKeyWord) {
		int begindex = source.indexOf(beginingKeyWord, position);
		//System.out.println(begindex + " " + position);
		int endindex =  source.indexOf(endKeyWord, begindex);
		if(begindex != -1 && endindex != -1) {
			position = endindex + endKeyWord.length();
			parsed =  !source.substring(position, source.length()).contains(beginingKeyWord);
			return source.substring(begindex + beginingKeyWord.length(), endindex - endKeyWord.length() + 1).replaceAll("\\s+", " ");
		}
		parsed = true;
		return "";
	}
	public void 	removeComments	() {
		source = source.replaceAll("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)","");
	}
	
	public static String	word(String text, int wordIndex) {
		return text.split("\\s+")[wordIndex];
	}
}