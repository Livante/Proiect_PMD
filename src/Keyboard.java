import java.util.Scanner;

public class Keyboard {

	private Scanner scanner=new Scanner(System.in);

	public String readFromMyKeyboard(String msg) {
		System.out.println(msg);
		return scanner.nextLine();
	}
}
