package WebdriverPro.web.programs;

public class RemoveStringJunk {

	
	public static void removeRegex(String s) {
		
		String regex = "[^a-zA-Z0-9]";
		s.replaceAll(regex, "");
		System.out.println(s);
		
	}
	
	
	
	public static void main(String[] args) {
		removeRegex("r   g g r g r ");
	}
	
}
