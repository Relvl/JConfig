package johnson.test;

import johnson.jconfig.AJconfigBase;
import johnson.jconfig.EJconfigLoadTarget;
import johnson.jconfig.annotations.JConfigClass;
import johnson.jconfig.annotations.JConfigComment;
import johnson.jconfig.annotations.JConfigElement;
import johnson.jconfig.annotations.JConfigIgnore;
import johnson.jconfig.dom.io.EJConfigDomType;
import johnson.jconfig.dom.io.impl.JConfigDomJSONWriter;

/**
 * Тестовый класс конфигурации, демонстрирующий возможности системы.
 *
 * @author Johnson on 07.11.2015.
 */
@JConfigClass(
		fileName = "test-config", // Имя файла конфига. К нему будет приписываться соответствующее расширение (.xml, .json) при записи в файл.
		serializeTypes = {EJConfigDomType.XML, EJConfigDomType.JSON}, // Массив типов сериализации.
		isPrettyPrinting = true, // "Человекочитаемый" вывод результата сохранения конфигов.
		target = EJconfigLoadTarget.FILESYSTEM, // Цель загрузки (и сохранения) конфигов. RESOURCES/FILESYSTEM/URL. Сохранение возможно только для цели FILESYSTEM.
		systemOut = true // Попутный вывод в Sytem.out (для отладки).
)
@JConfigComment("Тестовые настройки приложения")
@JConfigElement("MyMegaSuperTestUltraClass")
public class TestConfig extends AJconfigBase {
	@JConfigIgnore
	public static Character SIMPLE_CHAR = 'J';
	@JConfigElement("FunnyJConfig")
	public static String SIMPLE_STRING = "jConfig is funny!";
	public static Byte SIMPLE_BYTE = 127;
	@JConfigComment("Тестовый шорт")
	public static Short SIMPLE_SHORT = 32767;
	@JConfigElement(value = "SimpleInt", valueIsAttribute = true)
	public static Integer SIMPLE_INT = 0x7fffffff;
	public static Long SIMPLE_LONG = 0x7fffffffffffffffL;
	public static Float SIMPLE_FLOAT = 1234567890.1234567890f;
	public static Double SIMPLE_DOUBLE = 1234567890.1234567890d;

	static {
		// Разрешает JSON сериализатору использовать комментарии и прочие вольности, не разрешенные стандартом.
		JConfigDomJSONWriter.setGenerateValidJsonOnly(false);
	}

	public static void load() {
		loadInternal(TestConfig.class);
	}

	public static void store() {
		storeInternal(TestConfig.class);
	}

	public static void main(String[] args) {
		TestConfig.load();
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
