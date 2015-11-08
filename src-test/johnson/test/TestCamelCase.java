package johnson.test;

import johnson.utils.StringUtils;

/**
 * @author Johnson on 08.11.2015.
 */
public class TestCamelCase {

	private static String[] TEST = {
			"normalCamelCaseName",
			"NotCamelCaseName",
			"CONSTANT_TO_CAMEL_CASE",
			"Text To Camel Case",
			"Text to camel case",
			"ОтЖиМаЕмСя На ШиФфТе, ДрУзЯфФкИ!:)",
			"-(*&*&%&%$^&^*()Знаков*&^%*(&$препинания....и.нечитаемых-----------знаков^    (Может*90Быть&(*?*?:СКОЛЬКО*?%?:%угодно!",
			"И, напоследок, русская строка со знаками препинания (локализация!).",
			"somewhere"
	};

	public static void main(String[] args) {
		for (String str : TEST) {
			System.out.println("===========================");
			System.out.println("Source string: '" + str + "'");
			System.out.println("Result string: '" + StringUtils.toCamelCase(str, true) + "'");
			System.out.println("Result string: '" + StringUtils.toCamelCase(str, false) + "' (firstWordToLowerCase = false)");
		}
	}

}
