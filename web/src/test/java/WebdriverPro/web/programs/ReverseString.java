package WebdriverPro.web.programs;

public class ReverseString {

	public void reverseCharAt(String s) {
		// s= "Hello world!";
		int length = s.length(); // s+1
		String rev = "";
		
		for (int i = length - 1; i >= 0; i--) {
			rev = rev + s.charAt(i);
		}
		System.out.println(rev);
	}

	
	public void reverseBuffer(String s) {
		StringBuffer sb = new StringBuffer();
		System.out.println(sb.reverse());
	}
	
	public static void main(String[] args) {

		ReverseString reverse = new ReverseString();

		reverse.reverseCharAt("Hello World!");
		
		reverse.reverseBuffer("Hello World!");
	}

}
