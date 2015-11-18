package johnson.jconfig;

import johnson.jconfig.annotations.JConfigClass;
import johnson.jconfig.dom.JConfigDomDocument;
import johnson.jconfig.dom.JConfigDomElement;
import johnson.jconfig.exceptions.JConfigRException;
import johnson.jconfig.serialize.IJConfigClassSerializator;
import johnson.jconfig.serialize.JConfigSerializatorStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Johnson on 07.11.2015.
 */
public abstract class AJconfigBase {
	private static final Map<Class<?>, Logger> loggers = new HashMap<>();
	private static final Pattern xmlTagPattern = Pattern.compile("<\\s*(\\w+)(\\s+\\w+=\".*?\"\\s*)*((/>)|(>(.*)</\\1>))", Pattern.DOTALL);

	private static Logger getLogger(Class<?> clazz) {
		if (!loggers.containsKey(AJconfigBase.class)) {
			loggers.put(AJconfigBase.class, LoggerFactory.getLogger(AJconfigBase.class));
		}
		if (!loggers.containsKey(clazz)) {
			loggers.put(clazz, LoggerFactory.getLogger(clazz));
		}
		return loggers.get(clazz);
	}

	protected static void loadInternal(Class<? extends AJconfigBase> clazz) {
		if (clazz == null) {
			getLogger(AJconfigBase.class).error("Config class is null");
			throw new JConfigRException("Config class is null");
		}

		JConfigClass configClassAnnotation = clazz.getAnnotation(JConfigClass.class);
		if (configClassAnnotation == null) {
			getLogger(clazz).error("Config '" + clazz.getSimpleName() + ".class' class must be annotated with '@JConfigClass'");
			throw new JConfigRException("Config class must be annotated with JConfigClass");
		}

		String fileName = configClassAnnotation.fileName() + ".xml";

		String fileContent = "", line;
		try (FileReader fr = new FileReader(fileName);
		     BufferedReader br = new BufferedReader(fr)
		) {
			while ((line = br.readLine()) != null) {
				fileContent += line + "\n";
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		fileContent = fileContent.replaceAll("<!--.*?-->", "");
		fileContent = fileContent.replaceAll("<\\?xml.*?>", "");

		System.out.println(fileContent);

		parseLevelRecursive(fileContent, 0);
	}

	private static int parseLevelRecursive(String content, int level) {
		Matcher m = xmlTagPattern.matcher(content);
		System.out.println("==========================================================");
		int found = 0;
		String tagName = "";
		while (m.find()) {
			tagName = m.group(1);
			System.out.println("---------------- " + tagName);
			if (m.group(2) != null) {
				System.out.println(m.group(2));
				System.out.println("----");
			}
			if (m.group(6) != null) {
				System.out.println(m.group(6));
				parseLevelRecursive(m.group(6), level + 1);
			}
			found++;
		}
		System.out.println("Found subtags of <" + tagName + ">: " + found);
		return 0;
	}

	protected static void storeInternal(Class<? extends AJconfigBase> clazz) throws JConfigRException {
		if (clazz == null) {
			getLogger(AJconfigBase.class).error("Config class is null");
			throw new JConfigRException("Config class is null");
		}

		JConfigClass configClassAnnotation = clazz.getAnnotation(JConfigClass.class);
		if (configClassAnnotation == null) {
			getLogger(clazz).error("Config '" + clazz.getSimpleName() + ".class' class must be annotated with '@JConfigClass'");
			throw new JConfigRException("Config class must be annotated with JConfigClass");
		}

		// TODO Запись только в EJconfigLoadTarget.FILESYSTEM

		String fileName = configClassAnnotation.fileName();

		// Создаем временный файл с папками, и удаляем файл.
		File file = new File(fileName);
		if (!file.mkdirs() || !file.delete()) {
			getLogger(clazz).error("Cannot create config file: " + file);
		}

		// Создаем документ и настраиваем его.
		JConfigDomDocument domDocument = new JConfigDomDocument();
		domDocument.setPrettyPrint(configClassAnnotation.isPrettyPrinting());
		domDocument.setTypes(configClassAnnotation.serializeTypes());

		// Получаем сериализатор класса и сериализуем конфиг с его помощью.
		IJConfigClassSerializator serializer = JConfigSerializatorStorage.getInstance().getSerializerForClass(clazz);
		if (serializer != null) {
			try {
				JConfigDomElement rootElement = serializer.serialize(clazz);
				if (rootElement == null) {
					throw new JConfigRException("Root element is null: " + clazz.getSimpleName());
				}
				domDocument.setRootElement(rootElement);
			}
			catch (IllegalAccessException e) {
				throw new JConfigRException(e);
			}
		}

		// Пишем получившиеся данные в поток и файл.
		try {
			if (configClassAnnotation.systemOut()) {
				domDocument.writeToStreams(() -> System.out);
			}
			domDocument.writeToFiles(fileName);
		}
		catch (IOException e) {
			getLogger(clazz).error("", e);
		}
	}
}
