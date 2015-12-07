package lk.ac.mrt.ent.dialog.imoni.main;

/**
 * @author Anuruddha Hettiarachchi
 *
 */

public class TextValidation {
	public static final String USERNAME_PATTERN = "";
  
	public static boolean ValidateText(String text) {
		return (text != null) && (text.trim().length() > 0);
	}
  
	public static boolean ValidateUsername(String username) {
		return true;
	}
}