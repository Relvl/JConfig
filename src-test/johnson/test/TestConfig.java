package johnson.test;

import johnson.jconfig.AJconfigBase;
import johnson.jconfig.annotations.JConfigClass;
import johnson.jconfig.annotations.JConfigComment;
import johnson.jconfig.annotations.JConfigElement;
import johnson.jconfig.annotations.JConfigIgnore;

/**
 * @author Johnson on 07.11.2015.
 */
@JConfigClass("config/test-config")
@JConfigComment("Тестовые настройки приложения")
public class TestConfig extends AJconfigBase {
	@JConfigIgnore
	public static Character SIMPLE_CHAR = 'J';
	@JConfigElement("FunnyJConfig")
	public static String SIMPLE_STRING = "jConfig is funny!";

	public static Byte SIMPLE_BYTE = 127;
	@JConfigComment("Тестовый шорт")
	public static Short SIMPLE_SHORT = 32767;
	@JConfigElement("SimpleInt")
	public static Integer SIMPLE_INT = 0x7fffffff;
	public static Long SIMPLE_LONG = 0x7fffffffffffffffL;
	public static Float SIMPLE_FLOAT = 1234567890.1234567890f;
	public static Double SIMPLE_DOUBLE = 1234567890.1234567890d;

	public static void load() {
		loadInternal(TestConfig.class);
	}

	public static void store() {
		storeInternal(TestConfig.class);
	}

	public static void main(String[] args) {
		TestConfig.store();
	}

	@JConfigComment("Настройки базы данных")
	public static class DataBase {
		public static Short SIMPLE_SHORT = 32767;
		public static Integer SIMPLE_INT = 0x7fffffff;
		public static Long SIMPLE_LONG = 0x7fffffffffffffffL;

		@JConfigComment("Настройки соединения с сервером SQL.")
		@JConfigElement("ODBCConfig")
		public static class Connection {
			@JConfigComment("Класс драйвера ODBC.")
			@JConfigElement("Driver")
			public static String DRIVER = "com.mysql.jdbc.Driver";
		}
	}
}
