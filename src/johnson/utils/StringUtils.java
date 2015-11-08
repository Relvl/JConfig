package johnson.utils;

/**
 * @author Johnson on 08.11.2015.
 */
public class StringUtils {
	private StringUtils() {}

	public static boolean isNullOrEmpty(String string) {
		return string == null || string.isEmpty();
	}

	/**
	 * Возвращает отформатированную в виде camelCase (или CamelCase) строку.
	 *
	 * @param string               Исходная строка
	 * @param firstWordToLowerCase Начинать ли искомую строку с маленького символа (lowercase).
	 */
	public static String toCamelCase(String string, boolean firstWordToLowerCase) {
		boolean isPrevLowerCase = false, isNextUpperCase = !firstWordToLowerCase;
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < string.length(); i++) {
			char currentChar = string.charAt(i);
			if (!Character.isLetterOrDigit(currentChar)) {
				isNextUpperCase = result.length() > 0 || isNextUpperCase;
			}
			else {
				result.append(
						isNextUpperCase ? Character.toUpperCase(currentChar) :
								isPrevLowerCase ? currentChar : Character.toLowerCase(currentChar)
				);
				isNextUpperCase = false;
			}
			isPrevLowerCase = result.length() > 0 && Character.isLowerCase(currentChar);
		}
		return result.toString();
	}
}
