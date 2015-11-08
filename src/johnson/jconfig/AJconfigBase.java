package johnson.jconfig;

import johnson.jconfig.annotations.JConfigClass;
import johnson.jconfig.dom.JConfigDomDocument;
import johnson.jconfig.dom.JConfigDomElement;
import johnson.jconfig.exceptions.JConfigRException;
import johnson.jconfig.serialize.IJConfigClassSerializator;
import johnson.jconfig.serialize.JConfigSerializatorStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Johnson on 07.11.2015.
 */
public abstract class AJconfigBase {
	private static final Map<Class<?>, Logger> loggers = new HashMap<>();

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
